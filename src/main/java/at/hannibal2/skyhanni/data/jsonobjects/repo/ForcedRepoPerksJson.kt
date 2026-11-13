package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.data.Perk
import kotlinx.serialization.Serializable

@Serializable
data class ForcedRepoPerksJson(
    val perks: List<Perk>? = null,
)
