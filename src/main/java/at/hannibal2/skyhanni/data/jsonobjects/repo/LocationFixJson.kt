package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class LocationFixJson(
    @SerialName("location_fixes") @JsonNames("locationFixes") val locationFixes: Map<String, LocationFix>,
)

@Serializable
data class LocationFix(
    val a: LorenzVec,
    val b: LorenzVec,
    @SerialName("island_name") val islandName: String,
    @SerialName("real_location") val realLocation: String,
)
