package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.json.ComponentSerializer
import net.minecraft.network.chat.Component
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ContributorsJson(
    val contributors: Map<String, ContributorJsonEntry>,
)

@Serializable
data class ContributorJsonEntry(
    val suffix: String = "§c:O",
    @SerialName("component_suffix") @JsonNames("componentSuffix")
    @Serializable(with = ComponentSerializer::class)
    val componentSuffix: Component? = null,
    val spinny: Boolean = false,
    @SerialName("upside_down") @JsonNames("upsideDown") val upsideDown: Boolean = false,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("hide_discover_message") val hideDiscoverMessage: Boolean = false,
    @SerialName("exclude_from_achievements") val excludeFromAchievements: Boolean = false,
)
