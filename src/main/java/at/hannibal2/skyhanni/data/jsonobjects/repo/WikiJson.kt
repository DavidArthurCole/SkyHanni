package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class WikiJson(
    val unofficial: Wiki,
)

@Serializable
data class Wiki(
    val name: String,
    @SerialName("url_prefix") @JsonNames("urlPrefix") val urlPrefix: String,
    @SerialName("full_search_prefix") @JsonNames("fullSearchPrefix") val fullSearchPrefix: String,
)
