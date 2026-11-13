package at.hannibal2.skyhanni.data.bingo

import at.hannibal2.skyhanni.utils.SimpleTimeMark
import kotlinx.serialization.Serializable

@Serializable
data class BingoApiResponseJson(
    val success: Boolean,
    val lastUpdated: SimpleTimeMark,
    val id: Int,
    val name: String,
    val start: SimpleTimeMark,
    val end: SimpleTimeMark,
    val modifier: BingoModifier,
    val goals: List<BingoGoalJson>,
)

@Suppress("unused")
enum class BingoModifier {
    NORMAL,
    EXTREME,
    SECRET,
}

@Serializable
data class BingoGoalJson(
    val id: String,
    val name: String,
    val tiers: List<Int>?,
    val lore: String?,
    val fullLore: List<String>?,
    val progress: Int?,
    val requiredAmount: Int?,
)
