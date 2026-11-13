package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import kotlinx.serialization.Serializable

@Serializable
data class NeuNpcShopRecipeJson(
    override val type: NeuRecipeType = NeuRecipeType.NPC_SHOP,
    val cost: List<NeuRecipeComponent>,
    val result: NeuRecipeComponent,
) : NeuAbstractRecipe() {
    override fun getPrimitiveInputs(itemJson: NeuItemJson) = cost.mapNotNull {
        it.toPrimitiveIngredientOrNull()
    }
    override val outputOverride: NeuOverrideProvider get() = NeuOverrideProvider(result)
}
