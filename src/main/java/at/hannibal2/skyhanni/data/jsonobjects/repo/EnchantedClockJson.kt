package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnchantedClockJson(
    val boosts: List<BoostJson>,
)

@Serializable
data class BoostJson(
    val name: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("usage_string") val usageString: String?,
    val color: String,
    @SerialName("display_slot") val displaySlot: Int,
    @SerialName("status_slot") val statusSlot: Int,
    @SerialName("usage_hours") val cooldownHours: Int = 0,
)
