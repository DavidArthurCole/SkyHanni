package at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe

import at.hannibal2.skyhanni.utils.json.NeuRecipeTypeSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

// Todo, possibly add other recipe types that are not item `recipes`/`recipe` entries,
//  e.g., essence upgrades.
@Serializable(with = NeuRecipeTypeSerializer::class)
enum class NeuRecipeType(
    val neuRepoId: String,
    val useForCraftCost: Boolean = true,
    serializer: () -> KSerializer<out NeuAbstractRecipe>,
) {
    FORGE("forge", serializer = { NeuForgeRecipeJson.serializer() }),
    TRADE("trade", false, { NeuTradeRecipeJson.serializer() }),
    MOB_DROP("drops", false, { NeuMobDropsRecipeJson.serializer() }),
    NPC_SHOP("npc_shop", serializer = { NeuNpcShopRecipeJson.serializer() }),
    KAT_UPGRADE("katgrade", false, { NeuKatUpgradeRecipeJson.serializer() }),
    CRAFTING("crafting", serializer = { NeuCraftingRecipeJson.serializer() }),
    ;

    val recipeSerializer: KSerializer<out NeuAbstractRecipe> by lazy(serializer)

    companion object {
        fun fromNeuIdOrNull(neuId: String): NeuRecipeType? = entries.firstOrNull { it.neuRepoId == neuId }
        fun fromNeuId(neuId: String) = entries.first { it.neuRepoId == neuId }
    }
}
