package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import kotlinx.serialization.Serializable

@Serializable
data class NeuTradeRecipeJson(
    override val type: NeuRecipeType = NeuRecipeType.TRADE,
    private val cost: NeuRecipeComponent,
    private val min: Int? = null,
    private val max: Int? = null,
    val result: NeuRecipeComponent,
) : NeuAbstractRecipe() {
    private val costCount
        get() = when {
            min != null && max != null -> (min + max) / 2
            else -> cost.count
        }

    override fun getPrimitiveInputs(itemJson: NeuItemJson) = listOf(
        cost.toPrimitiveIngredient(costCount)
    )
    override val outputOverride: NeuOverrideProvider get() = NeuOverrideProvider(result)
}
