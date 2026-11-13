package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import at.hannibal2.skyhanni.utils.PrimitiveIngredient
import at.hannibal2.skyhanni.utils.json.WholeNumberSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuCraftingRecipeJson(
    override val type: NeuRecipeType = NeuRecipeType.CRAFTING,
    /**
     * Top row of the 3x3 crafting grid (L -> R).
     */
    @SerialName("A1") val a1: NeuRecipeComponent? = null,
    @SerialName("A2") val a2: NeuRecipeComponent? = null,
    @SerialName("A3") val a3: NeuRecipeComponent? = null,
    /**
     * Middle row of the 3x3 crafting grid (L -> R).
     */
    @SerialName("B1") val b1: NeuRecipeComponent? = null,
    @SerialName("B2") val b2: NeuRecipeComponent? = null,
    @SerialName("B3") val b3: NeuRecipeComponent? = null,
    /**
     * Bottom row of the 3x3 crafting grid (L -> R).
     */
    @SerialName("C1") val c1: NeuRecipeComponent? = null,
    @SerialName("C2") val c2: NeuRecipeComponent? = null,
    @SerialName("C3") val c3: NeuRecipeComponent? = null,
    /**
     * How many items this recipe produces.
     */
    @SerialName("count") @Serializable(with = WholeNumberSerializer::class) val outputCount: Int = 1,
) : NeuAbstractRecipe() {
    private val primitiveIngredients: List<PrimitiveIngredient> by lazy {
        listOfNotNull(a1, a2, a3, b1, b2, b3, c1, c2, c3).mapNotNull {
            it.toPrimitiveIngredientOrNull()
        }
    }

    override fun getPrimitiveInputs(itemJson: NeuItemJson) = primitiveIngredients
    override val outputOverride: NeuOverrideProvider get() = NeuOverrideProvider(overrideCount = outputCount)
}
