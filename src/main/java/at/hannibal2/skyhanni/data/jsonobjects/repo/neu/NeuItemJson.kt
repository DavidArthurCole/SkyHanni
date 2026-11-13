package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.config.ConfigManager
import at.hannibal2.skyhanni.data.jsonobjects.other.NeuNbtInfoJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe.NeuAbstractRecipe
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe.NeuCraftingRecipeJson
import at.hannibal2.skyhanni.test.command.ErrorManager
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.json.fromJson
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtAccounter
import net.minecraft.nbt.NbtIo
import java.io.ByteArrayInputStream
import java.util.Base64
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class NeuItemJson(
    @SerialName("itemid") var itemId: String,
    @SerialName("displayname") val displayName: String? = null,
    /**
     * From the NEU repo, this can be either a JSON object, or an NBT tag string.
     * We have to accept any from 'nbttag' since we don't have a type guarantee,
     * and perform further conversion ourselves. Don't use this field.
     */
    @Deprecated("Use neuNbt or nbtTag instead", ReplaceWith("neuNbt or nbtTag"))
    @SerialName("nbttag")
    private val nbtTagAny: JsonElement,
    val damage: Int? = null,
    val lore: List<String> = emptyList(),
    @SerialName("internalname") val internalName: NeuInternalName,
    @SerialName("crafttext") val craftText: String? = null,
    @SerialName("useneucraft") val useNeuCraft: Boolean = false,
    @SerialName("clickcommand") val clickCommand: String? = null,
    @SerialName("modver") val modVersion: String? = null,
    val vanilla: Boolean = false,
    val infoType: String? = null,
    val info: List<String> = emptyList(),
    val recipe: NeuCraftingRecipeJson? = null,
    val recipes: List<NeuAbstractRecipe> = emptyList(),
    val count: Int? = null,
) {
    companion object {
        private val nbtListRegex = Regex("([\\[,])\\d+:")
    }

    private val fixedNbtTagString by lazy {
        @Suppress("DEPRECATION")
        when (val tag = nbtTagAny) {
            is JsonPrimitive -> tag.content
            else -> tag.toString()
        }
    }

    private val neuParsableNbt by lazy { fixedNbtTagString.replace(nbtListRegex, "$1") }
    val neuNbt by lazy { convertToNeuNbt() }

    private fun getParsedNBT(): CompoundTag {
        return try {
            val decodedBytes = Base64.getDecoder().decode(fixedNbtTagString.toByteArray(Charsets.UTF_8))
            val inputStream = ByteArrayInputStream(decodedBytes)
            NbtIo.readCompressed(inputStream, NbtAccounter.unlimitedHeap())
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to parse NBT tag: $fixedNbtTagString", e)
        }
    }
    val nbtTag get() = getParsedNBT()

    private fun convertToNeuNbt(): NeuNbtInfoJson? = runCatching {
        ConfigManager.gson.fromJson<NeuNbtInfoJson>(neuParsableNbt)
    }.getOrElse {
        ErrorManager.logErrorWithData(
            throwable = it,
            "Error converting NBT to NeuNbtInfoJson",
            extraData = listOf(
                "fixedNbtTagString" to fixedNbtTagString,
                "itemId" to itemId,
                "internalName" to internalName,
                "neuParsableNbt" to neuParsableNbt,
            ).toTypedArray(),
        )
        null
    }
}
