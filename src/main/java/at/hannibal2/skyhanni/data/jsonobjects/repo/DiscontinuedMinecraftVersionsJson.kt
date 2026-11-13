package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscontinuedMinecraftVersionsJson(
    val versions: Map<String, DiscontinuedMinecraftVersion>? = mapOf(),
)

@Serializable
data class DiscontinuedMinecraftVersion(
    @SerialName("extra_info") val extraInfo: List<String>? = null,
    @SerialName("config_info") val configInfo: List<String>? = null,
    @SerialName("luck_amount") val luckAmount: Float? = null,
)
