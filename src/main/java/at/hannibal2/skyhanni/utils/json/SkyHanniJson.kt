package at.hannibal2.skyhanni.utils.json

import at.hannibal2.skyhanni.utils.SafeItemStack
import io.github.notenoughupdates.moulconfig.ChromaColour
import net.minecraft.network.chat.Component
import java.lang.reflect.Type
import java.time.LocalDate
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf
import kotlin.time.Duration
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializerOrNull

object SkyHanniJson {

    private val externalSerializers: Map<Class<*>, KSerializer<*>> = mapOf(
        UUID::class.java to UuidSerializer,
        Duration::class.java to DurationMillisSerializer,
        LocalDate::class.java to LocalDateSerializer,
        Component::class.java to ComponentSerializer,
        SafeItemStack::class.java to ItemStackSerializer,
        ChromaColour::class.java to ChromaColourSerializer,
    )

    @Suppress("UNCHECKED_CAST")
    val module: SerializersModule = SerializersModule {
        for ((clazz, serializer) in externalSerializers) {
            contextual(clazz.kotlin as KClass<Any>, serializer as KSerializer<Any>)
        }
    }

    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        encodeDefaults = true
        explicitNulls = false
        prettyPrint = true
        serializersModule = module
    }

    @Suppress("UNCHECKED_CAST")
    fun serializerOrNull(type: Type): KSerializer<Any>? = when (type) {
        is Class<*> -> serializerFor(type, type)
        else -> runCatching { module.serializerOrNull(type) }.getOrNull()
    } as KSerializer<Any>?

    inline fun <reified T : Any> decodeOrNull(text: () -> String): T? =
        serializerOrNull(typeOf<T>().javaType)?.let { json.decodeFromString(it, text()) as T }

    fun serializerFor(type: Type, rawType: Class<*>): KSerializer<*>? {
        externalSerializers[rawType]?.let { return it }
        if (SafeItemStack::class.java.isAssignableFrom(rawType)) return ItemStackSerializer
        val sealedRoot = rawType.sealedSerializableRoot()
        if (sealedRoot != null && sealedRoot != rawType) return module.serializerOrNull(sealedRoot)
        val annotation = rawType.getAnnotation(Serializable::class.java) ?: return null
        return annotation.with.objectInstance ?: module.serializerOrNull(type)
    }

    private fun Class<*>.sealedSerializableRoot(): Class<*>? = generateSequence(this) { it.superclass }
        .lastOrNull { it.isAnnotationPresent(Serializable::class.java) && it.kotlin.isSealed }
}
