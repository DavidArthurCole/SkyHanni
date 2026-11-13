package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttributeGoodRollsJson(
    @SerialName("good_rolls") val goodRolls: Map<String, ItemRoll>,
)

@Serializable
data class ItemRoll(
    val regex: String,
    val list: List<List<String>>,
)
