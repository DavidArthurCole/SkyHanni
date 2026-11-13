package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuSkillLevelJson(
    @SerialName("leveling_xp") val levelingXP: List<Int>,
    @SerialName("leveling_caps") val levelingCaps: Map<String, Int>,
)
