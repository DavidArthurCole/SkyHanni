package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param ignoredInternalNames Items that lose the display name to another item.
 * @param ambiguousDisplayNames Display names no item wins, written lowercase and with color codes.
 */
@Serializable
data class ItemDisplayNamesJson(
    @SerialName("ignored_internal_names") val ignoredInternalNames: Set<NeuInternalName>,
    @SerialName("ambiguous_display_names") val ambiguousDisplayNames: Set<String>,
    val description: String,
)
