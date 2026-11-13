package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HoppityRabbitTexturesJson(
    @SerialName("textures") val textures: Map<String, List<HoppityRabbitTextureEntry>>,
)

@Serializable
data class HoppityRabbitTextureEntry(
    @SerialName("rabbits") val rabbits: List<String>,
    @SerialName("texture_value_b64") val textureValueB64: String,
    @SerialName("skull_id") val skullId: String,
    @SerialName("texture_id") val textureId: String,
)
