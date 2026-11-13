package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.features.garden.CropType
import at.hannibal2.skyhanni.features.garden.pests.PestType
import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class GardenJson(
    @SerialName("garden_exp") val gardenExp: List<Int>,
    @SerialName("crop_milestones") val cropMilestones: Map<CropType, List<Int>>,
    @SerialName("crop_milestone_community_help") val cropMilestoneCommunityHelp: Map<String, Boolean>,
    val visitors: Map<String, GardenVisitor>,
    @SerialName("organic_matter") val organicMatter: Map<NeuInternalName, Double>,
    val fuel: Map<NeuInternalName, Double>,
    @SerialName("personal_best_increment") val personalBestIncrement: Map<CropType, Int>,
    @SerialName("pest_rare_drops") val pestRareDrops: Map<PestType, Map<NeuInternalName, Int>>,
    @SerialName("extra_farming_tools") val extraFarmingTools: Set<NeuInternalName>,
    @SerialName("hoe_exp_levels") val hoeExpLevels: List<Int>,
    @SerialName("hoe_exp_overflow") val hoeExpOverflow: Int,
    @SerialName("dna_solver_allows_ends") val dnaSolverAllowsEnds: Boolean = false,
    @SerialName("money_per_hour_ignored_items") val moneyPerHourIgnoredItems: List<NeuInternalName>,
)

@Serializable
data class GardenVisitor(
    @SerialName("rarity") private val _rarity: LorenzRarity,
    @SerialName("new_rarity") private val _newRarity: LorenzRarity? = null,
    val position: LorenzVec? = null,
    @SerialName("skin_or_type") @JsonNames("skinOrType") var skinOrType: String? = null,
    val mode: String? = null,
    @SerialName("need_items") val needItems: List<String>,
    @SerialName("unknown_rewards") val unknownRewards: Boolean = false,
    @SerialName("show_chat_message") val showChatMessage: Boolean = false,
) {
    val rarity: LorenzRarity
        get() = _newRarity ?: _rarity
}
