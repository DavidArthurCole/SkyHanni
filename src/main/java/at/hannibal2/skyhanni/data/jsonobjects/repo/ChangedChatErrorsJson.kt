package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.system.ModVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangedChatErrorsJson(
    @SerialName("changed_error_messages") val changedErrorMessages: List<RepoErrorData>,
)

@Serializable
data class RepoErrorData(
    @SerialName("message_exact") private val rawMessageExact: List<String>?,
    @SerialName("message_starts_with") private val rawMessageStartsWith: List<String>?,
    @SerialName("message_contains") private val rawMessageContains: List<String>?,
    @SerialName("replace_message") val replaceMessage: String?,
    @SerialName("custom_message") val customMessage: String?,
    @SerialName("fixed_in") val fixedIn: ModVersion?,
) {
    val messageExact get() = rawMessageExact.orEmpty()
    val messageStartsWith get() = rawMessageStartsWith.orEmpty()
    val messageContains get() = rawMessageContains.orEmpty()
}
