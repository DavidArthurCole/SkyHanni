package at.hannibal2.skyhanni.utils.tracker

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SessionUptime {
    @Serializable
    @SerialName("normal")
    data class Normal(@SerialName("session") val sessionType: NormalSession) : SessionUptime() {
        override fun toString(): String = sessionType.toString()
    }

    @Serializable
    @SerialName("garden")
    data class Garden(@SerialName("session") val sessionType: GardenSession) : SessionUptime() {
        override fun toString(): String = sessionType.toString()
    }

    val garden get() = when (this) {
        is Garden -> sessionType
        else -> null
    }
}

enum class NormalSession(val displayName: String) {
    NORMAL("All"),
    ;
    override fun toString() = displayName
}

enum class GardenSession(val displayName: String) {
    PEST("Pest Killing"),
    VISITOR("Accepting Visitors"),
    CROP("Breaking Crops"),
    UNKNOWN("Unknown"),
    ;

    override fun toString() = displayName
}
