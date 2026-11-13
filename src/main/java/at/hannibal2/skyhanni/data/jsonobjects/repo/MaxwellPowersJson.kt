package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class MaxwellPowersJson(
    val powers: MutableList<String>,
)
