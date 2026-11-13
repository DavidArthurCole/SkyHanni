package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class BingoRanksJson(
    val ranks: Map<String, Int>,
)
