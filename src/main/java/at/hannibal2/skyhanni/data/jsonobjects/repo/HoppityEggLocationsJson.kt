package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.data.IslandType
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.json.TreeSetSerializer
import java.util.TreeSet
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class HoppityEggLocationsJson(
    @SerialName("rabbit_slots") @JsonNames("rabbitSlots") val rabbitSlots: Map<Int, Int>,
    @SerialName("other_upgrade_slots") @JsonNames("otherUpgradeSlots") val otherUpgradeSlots: Set<Int>,
    @SerialName("no_pickblock_slots") @JsonNames("noPickblockSlots") val noPickblockSlots: Set<Int>,
    @SerialName("destructive_slots") @JsonNames("destructiveSlots") val destructiveSlots: Set<Int>,
    @SerialName("barn_index") @JsonNames("barnIndex") val barnIndex: Int,
    @SerialName("info_index") @JsonNames("infoIndex") val infoIndex: Int,
    @SerialName("production_info_index") @JsonNames("productionInfoIndex") val productionInfoIndex: Int,
    @SerialName("prestige_index") @JsonNames("prestigeIndex") val prestigeIndex: Int,
    @SerialName("milestone_index") @JsonNames("milestoneIndex") val milestoneIndex: Int,
    @SerialName("leaderboard_index") @JsonNames("leaderboardIndex") val leaderboardIndex: Int,
    @SerialName("hand_cookie_index") @JsonNames("handCookieIndex") val handCookieIndex: Int,
    @SerialName("time_tower_index") @JsonNames("timeTowerIndex") val timeTowerIndex: Int,
    @SerialName("shrine_index") @JsonNames("shrineIndex") val shrineIndex: Int,
    @SerialName("coach_rabbit_index") @JsonNames("coachRabbitIndex") val coachRabbitIndex: Int,
    @SerialName("rabbit_hitman_index") @JsonNames("rabbitHitmanIndex") val rabbitHitmanIndex: Int,
    @SerialName("max_rabbits") @JsonNames("maxRabbits") val maxRabbits: Int,
    @SerialName("max_prestige") @JsonNames("maxPrestige") val maxPrestige: Int,
    @SerialName("cf_shortcut_index") val cfShortcutIndex: Int,
    @SerialName("chocolate_milestones") @JsonNames("chocolateMilestones")
    @Serializable(with = TreeSetSerializer::class)
    val chocolateMilestones: TreeSet<Long>,
    @SerialName("hitman_costs")
    @Serializable(with = TreeSetSerializer::class)
    val hitmanCosts: TreeSet<Long>,
    @SerialName("chocolate_shop_milestones") @JsonNames("chocolateShopMilestones")
    val chocolateShopMilestones: List<MilestoneJson>,
    @SerialName("chocolate_factory_milestones") @JsonNames("chocolateFactoryMilestones")
    val chocolateFactoryMilestones: List<MilestoneJson>,
    @SerialName("resident_locations") @JsonNames("residentLocations")
    val residentLocations: Map<IslandType, List<String>>,
    @SerialName("api_egg_locations") @JsonNames("apiEggLocations")
    val apiEggLocations: Map<IslandType, Map<String, LorenzVec>>,
    @SerialName("special_rabbits") @JsonNames("specialRabbits") val specialRabbits: List<String>,
)

@Serializable
data class MilestoneJson(
    val amount: Long,
    val rabbit: String,
)
