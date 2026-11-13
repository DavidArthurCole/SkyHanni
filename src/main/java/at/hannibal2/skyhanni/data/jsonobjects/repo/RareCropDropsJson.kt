package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RareCropDropsJson(
    @SerialName("special_crops") val specialCrops: Map<String, RareCropDropInfo>,
)

@Serializable
data class RareCropDropInfo(
    @SerialName("armor_type") val armorType: String,
    val chance: List<Double>,
)
