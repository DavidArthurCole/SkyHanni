package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class ItemAliases(
    val global: Map<String, NeuInternalName> = emptyMap(),
)
