package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.PrimitiveIngredient
import at.hannibal2.skyhanni.utils.json.WholeNumberSerializer
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuForgeRecipeJson(
    override val type: NeuRecipeType = NeuRecipeType.FORGE,
    val inputs: List<NeuRecipeComponent>,
    @Serializable(with = WholeNumberSerializer::class) val count: Int,
    val overrideOutputId: NeuInternalName? = null,
    @SerialName("duration") private val durationSeconds: Int,
) : NeuAbstractRecipe() {
    val duration by lazy { durationSeconds.seconds }

    override fun getPrimitiveInputs(itemJson: NeuItemJson): List<PrimitiveIngredient> =
        inputs.mapNotNull { it.toPrimitiveIngredientOrNull() }

    override val outputOverride: NeuOverrideProvider
        get() = NeuOverrideProvider(
            overrideItem = overrideOutputId,
            overrideCount = count,
        )
}
