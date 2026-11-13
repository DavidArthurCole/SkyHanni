package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorManagerJson(
    @SerialName("break_after") val breakAfter: List<String>,
    val replacements: Map<String, String>,
    @SerialName("entire_replacements") val entireReplacements: Map<String, String>,
    val ignored: List<String>,
)
