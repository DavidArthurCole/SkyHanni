package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DisabledApiJson(
    @SerialName("disabled_bazaar") val disabledBazaar: Boolean = false,
    @SerialName("disabled_hypixel_items") val disableHypixelItems: Boolean = false,
    @SerialName("disabled_elite_ah") val disabledEliteAh: Boolean = false,
    @SerialName("disabled_elite_bz") val disabledEliteBz: Boolean = false,
    @SerialName("disabled_elite_items") val disabledEliteItems: Boolean = false,
    @SerialName("disabled_elite_lbin") val disabledEliteLbin: Boolean = false,
    @SerialName("disabled_counting_legacy_hoppity_locations") val disabledCountingLegacyHoppityLocations: Boolean = false,
)
