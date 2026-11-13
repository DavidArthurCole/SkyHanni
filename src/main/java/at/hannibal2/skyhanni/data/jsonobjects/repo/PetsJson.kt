package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetsJson(
    val skins: PetsJsonSkins,
)

@Serializable
data class PetsJsonSkins(
    @SerialName("game_variants") val gameVariants: Map<String, Set<NeuInternalName>>,
)
