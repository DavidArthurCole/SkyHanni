package at.hannibal2.skyhanni.data.jsonobjects.elitedev

import at.hannibal2.skyhanni.data.model.SkyblockStat
import at.hannibal2.skyhanni.features.fishing.trophy.TrophyRarity
import at.hannibal2.skyhanni.features.nether.kuudra.KuudraTier
import at.hannibal2.skyhanni.features.nether.reputationhelper.FactionType
import at.hannibal2.skyhanni.features.slayer.SlayerType
import at.hannibal2.skyhanni.utils.ItemCategory
import at.hannibal2.skyhanni.utils.LorenzColor
import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.NeuInternalName.Companion.toInternalName
import at.hannibal2.skyhanni.utils.NumberUtil.romanToDecimalIfNecessary
import at.hannibal2.skyhanni.utils.SkyBlockItemModifierUtils
import at.hannibal2.skyhanni.utils.TimeUnit
import io.github.notenoughupdates.moulconfig.ChromaColour
import kotlin.time.Duration
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class EliteItemResponse(
    val items: Map<NeuInternalName, EliteItem>,
)

// <editor-fold desc="Helpers to parse to SH formats">
enum class SoulboundType { NONE, SOLO, COOP }

@Serializable
data class EliteSkin(
    val value: String,
    val signature: String,
)

enum class SlotCostType { COINS, ITEM }

@Serializable
data class EliteGemstoneSlotCost(
    private val type: String,
    val coins: Int? = null,
    @SerialName("item_id") val itemId: NeuInternalName? = null,
    @SerialName("amount") val amount: Int? = null,
) {
    val costType: SlotCostType by lazy { SlotCostType.valueOf(type.uppercase()) }
}

@Serializable
data class EliteGemstoneSlot(
    @SerialName("slot_type") private val slotTypeStr: String,
    @SerialName("costs") private val eliteCosts: List<EliteGemstoneSlotCost> = emptyList(),
) {
    val slotType by lazy { SkyBlockItemModifierUtils.GemstoneSlotType.getByName(slotTypeStr) }
    val costs: Map<NeuInternalName, Int> by lazy {
        eliteCosts.mapNotNull { cost ->
            when (cost.costType) {
                SlotCostType.COINS -> NeuInternalName.SKYBLOCK_COIN to (cost.coins ?: 0)
                SlotCostType.ITEM -> {
                    val itemId = cost.itemId ?: return@mapNotNull null
                    itemId to (cost.amount ?: 0)
                }
            }
        }.associate { it }
    }
}

enum class EliteRequirementType {
    CHOCOLATE_FACTORY, COLLECTION,
    CRIMSON_ISLE_REPUTATION, DUNGEON_SKILL,
    DUNGEON_TIER, EASTER_RABBIT,
    GARDEN_LEVEL, HEART_OF_THE_MOUNTAIN,
    KUUDRA_COMPLETION, MELODY_HAIR,
    ONE_OF, PROFILE_AGE,
    SKILL, SLAYER,
    TARGET_PRACTICE, TROPHY_FISHING,
}

@Serializable
data class EliteItemRequirement(
    val type: EliteRequirementType,
    // Slayer, Skill
    val level: Int? = null,
    // Collection, Dungeon
    val tier: Int? = null,

    // Kuudra
    @SerialName("kuudra_tier") val kuudraTier: KuudraTier? = null,
    // Dungeon
    @SerialName("dungeon_type") val dungeonType: String? = null,
    // Slayer
    @SerialName("slayer_boss_type") private val slayerBossTypeStr: String? = null,
    // Skill
    val skill: SkyblockStat? = null,
    // Collection
    val collection: String? = null,
    // Target Practice
    @SerialName("mode") private val eliteMode: String? = null,
    // CI Reputation
    @SerialName("faction") private val ciFactionStr: String? = null,
    @SerialName("reputation") val ciReputation: Int? = null,
    // Trophy Fishing
    @SerialName("reward") private val trophyFishingTier: TrophyRarity? = null,
    // Hoppity
    val rabbit: String? = null,
    // Profile Age
    @SerialName("minimum_age") private val minimumAgeInt: Int? = null,
    @SerialName("minimum_age_unit") private val minimumAgeUnitStr: String? = null,

    // "One of" requirements
    @SerialName("lore_index") val loreIndex: Int? = null,
    @SerialName("requirements") val oneOfRequirements: List<EliteItemRequirement>? = null,
) {
    val minimumAge: Duration? by lazy {
        minimumAgeInt?.let { rawInt ->
            val ageUnit: TimeUnit = minimumAgeUnitStr?.let {
                TimeUnit.getByName(it.uppercase())
            } ?: return@let null
            ageUnit.asDuration(rawInt)
        }
    }
    val slayerBossType: SlayerType? by lazy {
        slayerBossTypeStr?.let {
            SlayerType.getByClassName(it)
        }
    }
    val targetPracticeMode: Int? by lazy { eliteMode?.romanToDecimalIfNecessary() }
    val ciFaction: FactionType? by lazy {
        ciFactionStr?.let {
            FactionType.valueOf(it.removeSuffix("S").uppercase())
        }
    }
}

@Serializable
data class EliteItemCatacombRequirement(
    val type: String,
    @SerialName("dungeon_type") val dungeonType: String? = null,
    val level: Int,
)

enum class UpgradeCostType { ESSENCE, ITEM }

@Serializable
@Suppress("unused")
data class EliteItemUpgradeCost(
    val type: UpgradeCostType,
    @SerialName("essence_type") val eliteEssenceType: String? = null,
    @SerialName("item_id") val itemId: NeuInternalName? = null,
    val amount: Int,
) {
    private val essenceType: NeuInternalName? by lazy {
        eliteEssenceType?.let {
            "ESSENCE_$it".toInternalName()
        }
    }
    private val item: NeuInternalName? by lazy { itemId ?: essenceType }
}

enum class EliteMuseumType { ARMOR_SETS, WEAPONS, RARITIES }
enum class EliteMuseumGameStage {
    STARTER,
    AMATEUR,
    INTERMEDIATE,
    SKILLED,
    EXPERT,
    PROFESSIONAL,
    MASTER,
}

@Serializable
data class EliteMuseumData(
    @SerialName("donation_xp") val donationXp: Int,
    private val parent: Map<String, String>? = null,
    val type: EliteMuseumType,
    @SerialName("armor_set_donation_xp") val armorSetDonationXp: Map<String, Int>? = null,
    @SerialName("game_stage") val gameStage: EliteMuseumGameStage,
    @SerialName("mapped_item_ids") val mappedItemIds: List<NeuInternalName> = emptyList(),
) {
    val selfIdentifier: String? get() = parent?.entries?.first()?.key
    val parentIdentifier: String? get() = parent?.entries?.first()?.value
}

enum class EliteItemOrigin { RIFT, BINGO }
// </editor-fold>

@Serializable
data class EliteItem(
    @SerialName("id") val internalName: NeuInternalName,
    val material: String,
    val durability: Int? = null,
    val skin: EliteSkin? = null,
    val name: String,
    @SerialName("description") val dirtyDescription: String? = null,
    @SerialName("category") private val itemCategory: String? = null,
    @SerialName("tier") val rarity: LorenzRarity? = null,
    @SerialName("has_uuid") val hasUuid: Boolean? = null,
    @SerialName("item_model") val itemModel: String? = null,
    @SerialName("soulbound") val soulboundType: SoulboundType? = null,
    @SerialName("npc_sell_price") val npcSellPrice: Double? = null,
    val origin: EliteItemOrigin? = null,

    // Minecraft fields that hypixel uses
    val unstackable: Boolean? = null,
    val glowing: Boolean? = null,
    @SerialName("can_interact") val canInteract: Boolean? = null,
    @SerialName("can_interact_right_click") val canInteractRightClick: Boolean? = null,

    val furniture: NeuInternalName? = null, // Furniture item, if applicable
    @SerialName("color") val eliteColor: String? = null, // Specific to dye-able items

    // There were way too many specific fields for item modifiers, so we just use a map of Any to Any
    // Create your own data class if you want to parse specific modifiers for an item
    @SerialName("item_specific") val itemSpecific: JsonObject? = null,

    /** Museum Related */
    val museum: Boolean? = null,
    @SerialName("museum_data") val museumData: EliteMuseumData? = null,

    /** Usage Related */
    val requirements: List<EliteItemRequirement>? = null,
    @SerialName("stats") private val eliteStats: Map<String, Double>? = null,
    @SerialName("tiered_stats") private val eliteTieredStats: Map<String, List<Double>>? = null,

    /** Minions */
    @SerialName("generator_tier") val minionTier: Int? = null,
    @SerialName("generator") val minionType: String? = null,

    /** Upgrade Related */
    @SerialName("upgrade_costs") val upgradeCosts: List<List<EliteItemUpgradeCost>>? = null,
    @SerialName("can_have_power_scroll") val canHavePowerScroll: Boolean? = null,
    @SerialName("gemstone_slots") val gemstoneSlots: List<EliteGemstoneSlot>? = null,
    @SerialName("can_have_attributes") val canHaveAttributes: Boolean? = null,

    /** Dungeon Specific */
    @SerialName("dungeon_item") val isDungeonItem: Boolean? = null,
    @SerialName("rarity_salvageable") val raritySalvageable: Boolean? = null,
    @SerialName("catacombs_requirements") val catacombsRequirements: List<EliteItemCatacombRequirement>? = null,

    /** Rift Specific */
    @SerialName("rift_transferrable") val riftTransferrable: Boolean? = null,
    @SerialName("motes_sell_price") val motesSellPrice: Double? = null,
    @SerialName("lose_motes_value_on_transfer") val loseMotesValueOnTransfer: Boolean? = null,
) {
    // Example of why we need this:
    // "description": "%%gray%%%%italic%%A perfectly fine tooth, besides its radiant brightness..."
    val description: String? by lazy {
        dirtyDescription?.let {
            // Match all groups of %%...%%
            val matcher = formattingMatcher.matcher(it)
            if (!matcher.find()) return@let it // No formatting found, return original description
            // Replace all matched groups with their corresponding replacements
            var formattedDescription = it
            do {
                val matchedGroup = matcher.group(1) ?: continue
                val replacementMap = getReplacementMap(matchedGroup)
                for ((key, value) in replacementMap) {
                    formattedDescription = formattedDescription.replace(key, value)
                }
            } while (matcher.find())
            formattedDescription
        }
    }

    val color: ChromaColour? by lazy {
        eliteColor?.let { colorStr ->
            val splits = colorStr.split(",").takeIf { it.size == 3 } ?: return@let null
            val red = splits.getOrNull(0)?.toIntOrNull() ?: return@let null
            val green = splits.getOrNull(1)?.toIntOrNull() ?: return@let null
            val blue = splits.getOrNull(2)?.toIntOrNull() ?: return@let null
            ChromaColour.fromStaticRGB(red, green, blue, 255)
        }
    }
    val category: ItemCategory? by lazy { itemCategory?.let { ItemCategory.valueOf(it) } }
    val stats: Map<SkyblockStat, Double>? by lazy {
        eliteStats?.mapNotNull { (statKey, value) ->
            val skyblockStat = SkyblockStat.getValueOrNull(statKey) ?: return@mapNotNull null
            skyblockStat to value
        }?.associate { it }
    }
    val tieredStats: Map<SkyblockStat, List<Double>>? by lazy {
        eliteTieredStats?.mapNotNull { (statKey, values) ->
            val skyblockStat = SkyblockStat.getValueOrNull(statKey) ?: return@mapNotNull null
            skyblockStat to values
        }?.associate { it }
    }

    companion object {
        private val formattingMatcher = Regex("%%(.*?)%%").toPattern()
        private val replacementCache: MutableMap<String, Map<String, String>> = mutableMapOf()
        private fun getReplacementMap(matchedGroup: String): Map<String, String> {
            if (replacementCache.containsKey(matchedGroup)) {
                return replacementCache[matchedGroup].orEmpty()
            }
            val formatter = matchedGroup.removePrefix("%%").removeSuffix("%%").lowercase()
            val replacement = when (formatter) {
                "italic" -> "§o"
                "bold" -> "§l"
                "underline" -> "§n"
                "strikethrough" -> "§m"
                "obfuscated" -> "§k"
                "reset" -> "§r"
                else -> {
                    try {
                        LorenzColor.valueOf(matchedGroup.uppercase()).getChatColor()
                    } catch (e: Exception) {
                        ""
                    }
                }
            }
            val replacementMap = mapOf("%%$formatter%%" to replacement)
            replacementCache[matchedGroup] = replacementMap
            return replacementMap
        }
    }
}
