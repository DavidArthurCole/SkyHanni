package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlinx.serialization.Serializable

object NeuAbiphoneJson {
    val TYPE: Type = object : TypeToken<Map<String, AbiphoneContactInfo>>() {}.type
}

@Serializable
data class AbiphoneContactInfo(
    val requirement: List<String>? = listOf(),
    val island: String?,
    val x: Int? = 0,
    val y: Int? = 0,
    val z: Int? = 0,
    val callNames: List<String>? = listOf(),
)
