package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import at.hannibal2.skyhanni.utils.PrimitiveIngredient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuMobDropsRecipeJson(
    override val type: NeuRecipeType = NeuRecipeType.MOB_DROP,
    val name: String? = null,
    val render: String? = null,
    val panorama: String? = null,
    val level: Int = 0,
    val coins: Int = 0,
    val xp: Int? = null,
    @SerialName("combat_xp") val combatXp: Int? = null,
    val drops: List<NeuDropJson>,
    val extra: List<String> = emptyList(),
) : NeuAbstractRecipe() {
    override fun getPrimitiveInputs(itemJson: NeuItemJson) = listOf(
        PrimitiveIngredient(itemJson.internalName)
    )

    override fun getPrimitiveOutputs(itemJson: NeuItemJson) = drops.mapNotNull {
        it.id.toPrimitiveIngredientOrNull()
    }
}

@Serializable
data class NeuDropJson(
    val id: NeuRecipeComponent,
    /**
     * Because of variability in formatting, this is a String.
     * Can be of the format(s);
     * "100%"
     * "0.00004%"
     * "x15-27"
     */
    val chance: String? = null,
    val extra: List<String> = emptyList(),
)
