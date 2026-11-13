package at.hannibal2.skyhanni.utils.json

import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toGson
import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toKotlinx
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlinx.serialization.KSerializer

object KSerializerTypeAdapterFactory : TypeAdapterFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val serializer = SkyHanniJson.serializerFor(type.type, type.rawType) as? KSerializer<Any> ?: return null
        return adapterFor(gson, serializer).nullSafe() as TypeAdapter<T>
    }

    @Suppress("UNCHECKED_CAST")
    private fun adapterFor(gson: Gson, serializer: KSerializer<Any>): TypeAdapter<Any> = when (serializer) {
        is GsonAdapterSerializer<*> -> serializer.adapter as TypeAdapter<Any>
        is StringSerializer<*> -> StringAdapter(serializer as StringSerializer<Any>)
        is LongSerializer<*> -> LongAdapter(serializer as LongSerializer<Any>)
        else -> TreeAdapter(serializer, gson.getAdapter(JsonElement::class.java))
    }

    private class StringAdapter(private val serializer: StringSerializer<Any>) : TypeAdapter<Any>() {
        override fun write(out: JsonWriter, value: Any) {
            out.value(serializer.encode(value))
        }

        override fun read(reader: JsonReader): Any = serializer.decode(reader.nextString())
    }

    private class LongAdapter(private val serializer: LongSerializer<Any>) : TypeAdapter<Any>() {
        override fun write(out: JsonWriter, value: Any) {
            out.value(serializer.encode(value))
        }

        override fun read(reader: JsonReader): Any = serializer.decode(reader.nextLong())
    }

    private class TreeAdapter(
        private val serializer: KSerializer<Any>,
        private val elementAdapter: TypeAdapter<JsonElement>,
    ) : TypeAdapter<Any>() {
        override fun write(out: JsonWriter, value: Any) {
            elementAdapter.write(out, SkyHanniJson.json.encodeToJsonElement(serializer, value).toGson())
        }

        override fun read(reader: JsonReader): Any =
            SkyHanniJson.json.decodeFromJsonElement(serializer, elementAdapter.read(reader).toKotlinx())
    }
}
