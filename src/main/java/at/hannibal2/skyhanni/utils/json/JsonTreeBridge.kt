package at.hannibal2.skyhanni.utils.json

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import com.google.gson.JsonArray as GsonArray
import com.google.gson.JsonElement as GsonElement
import com.google.gson.JsonNull as GsonNull
import com.google.gson.JsonObject as GsonObject
import com.google.gson.JsonPrimitive as GsonPrimitive

object JsonTreeBridge {

    fun GsonElement.toKotlinx(): JsonElement = when (this) {
        is GsonObject -> JsonObject(entrySet().associateTo(LinkedHashMap()) { it.key to it.value.toKotlinx() })
        is GsonArray -> JsonArray(map { it.toKotlinx() })
        is GsonPrimitive -> when {
            isBoolean -> JsonPrimitive(asBoolean)
            isNumber -> JsonPrimitive(asNumber)
            else -> JsonPrimitive(asString)
        }

        else -> JsonNull
    }

    fun JsonElement.toGson(): GsonElement = when (this) {
        is JsonObject -> GsonObject().also { for ((key, value) in this) it.add(key, value.toGson()) }
        is JsonArray -> GsonArray(size).also { for (element in this) it.add(element.toGson()) }
        is JsonNull -> GsonNull.INSTANCE
        is JsonPrimitive -> toGsonPrimitive()
    }

    private fun JsonPrimitive.toGsonPrimitive(): GsonPrimitive {
        if (isString) return GsonPrimitive(content)
        booleanOrNull?.let { return GsonPrimitive(it) }
        val number: Number = content.toLongOrNull() ?: double
        return GsonPrimitive(number)
    }
}
