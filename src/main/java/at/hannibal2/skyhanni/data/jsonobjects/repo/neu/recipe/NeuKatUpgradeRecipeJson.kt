package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.PrimitiveIngredient
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.Serializable

@Serializable
data class NeuKatUpgradeRecipeJson(
    override val type: NeuRecipeType = NeuRecipeType.KAT_UPGRADE,
    val coins: Double,
    private val time: Int,
    val input: NeuInternalName,
    val output: NeuInternalName,
    val items: List<NeuRecipeComponent> = emptyList(),
) : NeuAbstractRecipe() {
    val duration by lazy { time.seconds }

    override fun getPrimitiveInputs(itemJson: NeuItemJson) = buildList {
        items.forEach { add(it.toPrimitiveIngredient()) }
        add(PrimitiveIngredient(input))
        add(PrimitiveIngredient.coinIngredient(coins))
    }

    override val outputOverride: NeuOverrideProvider get() = NeuOverrideProvider(output)
}
