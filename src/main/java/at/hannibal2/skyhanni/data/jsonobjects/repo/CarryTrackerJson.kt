package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarryTrackerJson(
    @SerialName("slayer_names") val slayerNames: Map<String, List<String>>,
)
