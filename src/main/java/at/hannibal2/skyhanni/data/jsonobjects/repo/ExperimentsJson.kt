package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExperimentsJson(
    @SerialName("misc_rewards") val miscRewards: List<NeuInternalName>,
    @SerialName("ultra_rare_rewards") val ultraRareRewards: List<NeuInternalName>? = null,
)
