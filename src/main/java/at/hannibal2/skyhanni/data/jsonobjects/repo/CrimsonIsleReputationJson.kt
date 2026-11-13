package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class CrimsonIsleReputationJson(
    val FISHING: Map<String, ReputationQuest>,
    val RESCUE: Map<String, ReputationQuest>,
    val FETCH: Map<String, ReputationQuest>,
    val DOJO: Map<String, ReputationQuest>,
    val MINIBOSS: Map<String, ReputationQuest>,
    val KUUDRA: Map<String, ReputationQuest>,
)

@Serializable
data class ReputationQuest(
    val item: NeuInternalName,
    val location: List<Double>,
)
