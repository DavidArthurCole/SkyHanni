package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import kotlinx.serialization.Serializable

@Serializable
data class NeuEssenceShopJson(
    val name: String,
    val costs: List<Int>,
)
