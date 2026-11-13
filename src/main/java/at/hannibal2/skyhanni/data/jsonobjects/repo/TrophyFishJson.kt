package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.features.fishing.trophy.TrophyRarity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class TrophyFishJson(
    @SerialName("trophy_fish") val trophyFish: Map<String, TrophyFishInfo>,
)

@Serializable
data class TrophyFishInfo(
    @SerialName("display_name") @JsonNames("displayName") val displayName: String,
    val description: String,
    val rate: Int?,
    val fillet: Map<TrophyRarity, Int>,
)
