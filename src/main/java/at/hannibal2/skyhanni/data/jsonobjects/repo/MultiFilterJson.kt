package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class MultiFilterJson(
    val equals: List<String>? = null,
    @SerialName("starts_with") @JsonNames("startsWith") val startsWith: List<String>? = null,
    @SerialName("ends_with") @JsonNames("endsWith") val endsWith: List<String>? = null,
    val contains: List<String>? = null,
    @SerialName("contains_word") @JsonNames("containsWord") val containsWord: List<String>? = null,
    val description: String = "",
)
