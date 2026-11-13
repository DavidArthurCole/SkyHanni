package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsJson(
    @SerialName("crimson_armors") val crimsonArmors: List<String>,
    @SerialName("crimson_tiers") val crimsonTiers: Map<String, Int>,
    @SerialName("crimson_prestige_costs") val crimsonPrestigeCosts: Map<String, Map<NeuInternalName, Int>>,
    @SerialName("enchant_multiplier") val enchantMultiplier: Map<String, Float>,
    @SerialName("lava_fishing_rods") val lavaFishingRods: List<NeuInternalName>,
    @SerialName("water_fishing_rods") val waterFishingRods: List<NeuInternalName>,
    @SerialName("dungeon_secret_items") val dungeonSecretItems: Set<NeuInternalName>,
    @SerialName("book_bundle_amount") val bookBundleAmount: Map<String, Int>,
    @SerialName("value_calculation_data") val valueCalculationData: ItemValueCalculationDataJson,
    @SerialName("compact_names") val compactNames: Map<String, String>,
    @SerialName("renamed_enchantments") val renamedEnchantments: Map<String, String>,
    @SerialName("shard_name_overrides") val shardNameOverrides: Map<String, String>,
    @SerialName("distance_enchant_data") val distanceEnchantData: Map<String, DistanceEnchantData>,
    @SerialName("hunting_blackholes") val huntingBlackholes: List<NeuInternalName>?,
    @SerialName("hunting_axes") val huntingAxes: List<NeuInternalName>?,
    @SerialName("has_legacy_gemstone_slots") val hasLegacyGemstoneSlots: List<NeuInternalName>?,
)

@Serializable
data class ItemValueCalculationDataJson(
    @SerialName("always_active_enchants") val alwaysActiveEnchants: Map<String, AlwaysActiveEnchantJson>,
    @SerialName("only_tier_one_prices") val onlyTierOnePrices: List<String>,
    @SerialName("only_tier_five_prices") val onlyTierFivePrices: List<String>,
    @SerialName("endcap_enchants_new") val endcapEnchants: Map<String, List<EndCapData>>? = emptyMap(),
)

@Serializable
data class AlwaysActiveEnchantJson(
    val level: Int,
    @SerialName("items") val internalNames: List<NeuInternalName>,
)

@Serializable
data class EndCapData(
    @SerialName("required_level") val requiredLevel: Int,
    @SerialName("endcap_item") val endcapItem: NeuInternalName,
)

@Serializable
data class DistanceEnchantData(
    val distance: Double,
    @SerialName("max_amount") val maxAmount: Int,
    @SerialName("per_stack_multiplier") val perStackMultiplier: Double,
)
