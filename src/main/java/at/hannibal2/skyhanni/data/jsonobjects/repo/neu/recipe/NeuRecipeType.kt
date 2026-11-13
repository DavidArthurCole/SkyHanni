package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.utils.json.NeuRecipeTypeSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

// Todo, possibly add other recipe types that are not item `recipes`/`recipe` entries,
//  e.g., essence upgrades.
@Serializable(with = NeuRecipeTypeSerializer::class)
enum class NeuRecipeType(
    val neuRepoId: String,
    val useForCraftCost: Boolean = true,
    serializer: () -> KSerializer<out NeuAbstractRecipe>,
) {
    FORGE("forge", serializer = { serializer<NeuForgeRecipeJson>() }),
    TRADE("trade", false, { serializer<NeuTradeRecipeJson>() }),
    MOB_DROP("drops", false, { serializer<NeuMobDropsRecipeJson>() }),
    NPC_SHOP("npc_shop", serializer = { serializer<NeuNpcShopRecipeJson>() }),
    KAT_UPGRADE("katgrade", false, { serializer<NeuKatUpgradeRecipeJson>() }),
    CRAFTING("crafting", serializer = { serializer<NeuCraftingRecipeJson>() }),
    ;

    val recipeSerializer: KSerializer<out NeuAbstractRecipe> by lazy(serializer)

    companion object {
        fun fromNeuIdOrNull(neuId: String): NeuRecipeType? = entries.firstOrNull { it.neuRepoId == neuId }
        fun fromNeuId(neuId: String) = entries.first { it.neuRepoId == neuId }
    }
}
