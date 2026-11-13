package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ModGuiSwitcherJson(
    val mods: Map<String, OtherModInfo>,
)

@Serializable
data class OtherModInfo(
    val description: List<String>,
    val command: String,
    @SerialName("gui_path") @JsonNames("guiPath") val guiPath: List<String>,
)
