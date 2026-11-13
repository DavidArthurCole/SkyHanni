package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class LaunchersJson(
    val launchers: List<LauncherEntry>,
    @SerialName("generic_stacks") @JsonNames("genericStacks") val genericStacks: List<String>,
)

@Serializable
data class LauncherEntry(
    val name: String,
    @SerialName("first_stacks") @JsonNames("firstStacks") val firstStacks: List<String>,
    val brand: String? = null,
    val flagged: Boolean = false,
) {
    fun getIdPair() = name to flagged
}
