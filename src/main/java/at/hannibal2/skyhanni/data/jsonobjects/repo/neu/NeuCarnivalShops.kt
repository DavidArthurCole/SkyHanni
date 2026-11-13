package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import kotlinx.serialization.Serializable

@Serializable
data class NeuMiscJson(
    val carnivalTokenShops: Map<String, Map<String, NeuCarnivalTokenCostJson>>,
)

@Serializable
data class NeuCarnivalTokenCostJson(
    val name: String,
    val costs: List<Int>,
)
