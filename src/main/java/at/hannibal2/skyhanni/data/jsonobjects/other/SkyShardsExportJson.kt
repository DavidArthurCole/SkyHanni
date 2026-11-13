package at.hannibal2.skyhanni.data.jsonobjects.other

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlinx.serialization.Serializable

object SkyShardsExportJson {
    val TYPE: Type = object : TypeToken<List<SkyShardsExportData>>() {}.type
}

@Serializable
data class SkyShardsExportData(
    val name: String,
    val needed: Int,
    val source: String?,
)
