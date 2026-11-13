package at.hannibal2.skyhanni.utils.json

import at.hannibal2.skyhanni.config.ConfigManager
import at.hannibal2.skyhanni.config.core.config.Position
import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toGson
import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toKotlinx
import com.google.gson.TypeAdapter
import java.util.TreeSet
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

object PositionSerializer : GsonReflectiveSerializer<Position>("Position", Position::class.java)

open class StringSerializer<T : Any>(
    serialName: String,
    val encode: (T) -> String,
    val decode: (String) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: T) = encoder.encodeString(encode(value))
    override fun deserialize(decoder: Decoder): T = decode(decoder.decodeString())
}

open class LongSerializer<T : Any>(
    serialName: String,
    val encode: (T) -> Long,
    val decode: (Long) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: T) = encoder.encodeLong(encode(value))
    override fun deserialize(decoder: Decoder): T = decode(decoder.decodeLong())
}

open class EnumNameSerializer<T : Enum<T>>(
    serialName: String,
    entries: Iterable<T>,
    fallback: T? = null,
) : StringSerializer<T>(
    serialName,
    { value -> unknownNames[value]?.takeIf { value == fallback } ?: value.name },
    { raw ->
        val name = raw.replace(" ", "_").uppercase()
        entries.firstOrNull { it.name == name }
            ?: fallback?.also { unknownNames[it] = raw }
            ?: throw SerializationException("No enum constant $serialName.$name")
    },
) {
    private companion object {
        val unknownNames = mutableMapOf<Enum<*>, String>()
    }
}

object WholeNumberSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WholeNumber", PrimitiveKind.DOUBLE)
    override fun serialize(encoder: Encoder, value: Int) = encoder.encodeInt(value)
    override fun deserialize(decoder: Decoder): Int = decoder.decodeDouble().toInt()
}

class TreeSetSerializer<T : Comparable<T>>(element: KSerializer<T>) : KSerializer<TreeSet<T>> {
    private val delegate = SetSerializer(element)
    override val descriptor: SerialDescriptor = SerialDescriptor("TreeSet", delegate.descriptor)
    override fun serialize(encoder: Encoder, value: TreeSet<T>) = delegate.serialize(encoder, value)
    override fun deserialize(decoder: Decoder): TreeSet<T> = TreeSet(delegate.deserialize(decoder))
}

abstract class JsonTreeSerializer<T : Any>(serialName: String) : KSerializer<T> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(serialName)

    abstract fun toTree(value: T): JsonElement
    abstract fun fromTree(element: JsonElement): T

    override fun serialize(encoder: Encoder, value: T) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("${descriptor.serialName} only supports JSON")
        jsonEncoder.encodeJsonElement(toTree(value))
    }

    override fun deserialize(decoder: Decoder): T {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("${descriptor.serialName} only supports JSON")
        return fromTree(jsonDecoder.decodeJsonElement())
    }
}

open class GsonReflectiveSerializer<T : Any>(serialName: String, private val clazz: Class<T>) : JsonTreeSerializer<T>(serialName) {
    override fun toTree(value: T): JsonElement = ConfigManager.gson.toJsonTree(value, clazz).toKotlinx()
    override fun fromTree(element: JsonElement): T = ConfigManager.gson.fromJson(element.toGson(), clazz)
}

open class GsonAdapterSerializer<T : Any>(serialName: String, val adapter: TypeAdapter<T>) : JsonTreeSerializer<T>(serialName) {
    override fun toTree(value: T): JsonElement = adapter.toJsonTree(value).toKotlinx()
    override fun fromTree(element: JsonElement): T = adapter.fromJsonTree(element.toGson())
}
