package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuAnimatedSkullsJson(
    val skins: Map<String, AnimatedSkinJson>,
    @SerialName("pet_skin_variant") val petSkinVariants: Map<NeuInternalName, List<String>>,
    @SerialName("pet_skin_nbt_name") val petSkinNbtNames: Set<String>,
)

@Serializable
data class AnimatedSkinJson(
    val ticks: Int = 0,
    val textures: List<String>,
)
