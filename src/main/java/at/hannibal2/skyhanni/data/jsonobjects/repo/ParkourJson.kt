package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ParkourJson(
    val locations: List<LorenzVec>,
    @SerialName("short_cuts") @JsonNames("shortCuts") val shortCuts: List<ParkourShortCut> = listOf(),
)

@Serializable
data class ParkourShortCut(
    val from: Int,
    val to: Int,
)
