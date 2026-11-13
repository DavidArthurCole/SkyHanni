package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.StringUtils.removeColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DianaJson(
    @SerialName("spade_types") val spadeTypes: List<NeuInternalName>,
    @SerialName("sphinx_questions") val sphinxQuestions: Map<String, String>,
    @SerialName("mythological_mobs") val mythologicalCreatures: Map<String, MythologicalCreatureType>,
)

@Serializable
data class MythologicalCreatureType(
    val name: String,
    val rare: Boolean = false,
    @SerialName("short_names") val shortNames: List<String>? = null,
) {
    val cleanName by lazy { name.removeColor() }
    val trackerId by lazy { cleanName.replace(" ", "_").uppercase() }
    val mobAliases by lazy { listOf(cleanName.lowercase()) + (shortNames.orEmpty()) }
}
