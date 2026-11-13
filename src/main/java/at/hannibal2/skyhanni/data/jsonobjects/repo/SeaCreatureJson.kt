package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzRarity
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class SeaCreatureJson(
    @SerialName("chat_color") val chatColor: String,
    @SerialName("sea_creatures") val seaCreatures: Map<String, SeaCreatureInfo>,
) {
    companion object {
        val TYPE: Type = object : TypeToken<Map<String?, SeaCreatureJson>>() {}.type
    }
}

@Serializable
data class SeaCreatureInfo(
    @SerialName("chat_message") val chatMessage: String? = null,
    @SerialName("alternate_messages") val alternateMessages: List<String>? = null,
    @SerialName("fishing_experience") val fishingExperience: Int = 0,
    val rare: Boolean = false,
    val rarity: LorenzRarity,
    @SerialName("lootshare_sphere_override") @JsonNames("lootshareSphereOverride")
    val lootshareSphereOverride: Boolean? = null,
    @SerialName("old_names") val oldNames: List<String>? = null,
)
