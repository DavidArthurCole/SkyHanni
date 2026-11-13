package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuPetsJson(
    @SerialName("pet_rarity_offset") val petRarityOffset: Map<String, Int>,
    @SerialName("pet_levels") val basePetLeveling: List<Int>,
    @SerialName("custom_pet_leveling") val customPetLeveling: Map<String, NeuPetData>,
    @SerialName("pet_types") val petTypes: Map<String, String>,
    @SerialName("id_to_display_name") val displayNameMap: Map<String, String>,
    @SerialName("pet_item_display_name_to_id") val petItemResolution: Map<String, NeuInternalName>,
)

@Serializable
data class NeuPetData(
    @SerialName("type") val type: Int? = null,
    @SerialName("pet_levels") val petLevels: List<Int>? = null,
    @SerialName("max_level") val maxLevel: Int? = null,
    @SerialName("rarity_offset") val rarityOffset: Map<LorenzRarity, Int>? = null,
    @SerialName("xp_multiplier") val xpMultiplier: Double? = null,
)
