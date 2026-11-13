package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuEssenceCostJson(
    @SerialName("type") val essenceType: String,
    @SerialName("dungeonize") val essenceForDungeonize: Int?,
    @SerialName("1") val essenceFor1: Int?,
    @SerialName("2") val essenceFor2: Int?,
    @SerialName("3") val essenceFor3: Int?,
    @SerialName("4") val essenceFor4: Int?,
    @SerialName("5") val essenceFor5: Int?,
    @SerialName("6") val essenceFor6: Int?,
    @SerialName("7") val essenceFor7: Int?,
    @SerialName("8") val essenceFor8: Int?,
    @SerialName("9") val essenceFor9: Int?,
    @SerialName("10") val essenceFor10: Int?,
    @SerialName("11") val essenceFor11: Int?,
    @SerialName("12") val essenceFor12: Int?,
    @SerialName("13") val essenceFor13: Int?,
    @SerialName("14") val essenceFor14: Int?,
    @SerialName("15") val essenceFor15: Int?,
    @SerialName("items") val extraItems: Map<String, List<String>>?,
    @SerialName("catacombs_requirements") val catacombsRequirements: List<NeuCatacombsRequirements> = emptyList(),
) {
    companion object {
        val TYPE: Type = object : TypeToken<Map<String?, NeuEssenceCostJson>>() {}.type
    }
}

@Serializable
data class NeuCatacombsRequirements(
    @SerialName("type") val type: String,
    @SerialName("dungeon_type") val dungeonType: String?,
    @SerialName("level") val level: Int,
)
