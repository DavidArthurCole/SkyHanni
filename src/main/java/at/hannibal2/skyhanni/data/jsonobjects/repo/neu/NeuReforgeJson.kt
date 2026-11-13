package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.data.model.SkyblockStatList
import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.NeuInternalName.Companion.toInternalNames
import at.hannibal2.skyhanni.utils.NeuItems
import at.hannibal2.skyhanni.utils.compat.getVanillaItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class NeuReforgeJson(
    val reforgeName: String,
    @SerialName("nbtModifier") val rawNbtModifier: String? = null,
    val internalName: NeuInternalName? = null,
    @SerialName("itemTypes") val rawItemTypes: JsonElement,
    val requiredRarities: List<LorenzRarity>,
    val reforgeCosts: Map<LorenzRarity, Long>? = null,
    val reforgeStats: Map<LorenzRarity, SkyblockStatList>? = null,
    @SerialName("reforgeAbility") val rawReforgeAbility: JsonElement? = null,
) {

    val nbtModifier: String by lazy {
        rawNbtModifier ?: reforgeName.lowercase()
            .replace("[^a-z0-9\\s_-]".toRegex(), "")
            .replace("[\\s-]".toRegex(), "_")
    }
    val reforgeAbility: Map<LorenzRarity, String> by lazy {
        when (val ability = rawReforgeAbility) {
            is JsonObject -> ability.entries.associate { (rarity, text) ->
                LorenzRarity.getByNameOrError(rarity) to (text as JsonPrimitive).content
            }

            is JsonPrimitive -> if (ability.isString) requiredRarities.associateWith { ability.content } else emptyMap()

            else -> emptyMap()
        }
    }
    val itemType: Pair<String, List<NeuInternalName>> by lazy {
        when (val types = rawItemTypes) {
            is JsonObject -> {
                val internalNames = types.strings("internalName").toInternalNames()
                val itemType = types.strings("itemid").flatMap {
                    NeuItems.getInternalNamesForItemId(it.getVanillaItem() ?: return@flatMap emptyList())
                }
                "SPECIAL_ITEMS" to (internalNames + itemType)
            }

            is JsonPrimitive -> types.content.replace("/", "_AND_").uppercase() to emptyList()

            else -> error("rawItemTypes is neither String nor Map: $rawItemTypes")
        }
    }

    private fun JsonObject.strings(key: String): List<String> =
        (this[key] as? JsonArray)?.map { (it as JsonPrimitive).content }.orEmpty()
}
