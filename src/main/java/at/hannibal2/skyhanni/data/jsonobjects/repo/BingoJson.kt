package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BingoJson(
    @SerialName("bingo_tips") val bingoTips: Map<String, BingoData>,
)

@Serializable
data class BingoData(
    val difficulty: String,
    val note: List<String>,
    val guide: List<String>,
    val found: String?,
)
