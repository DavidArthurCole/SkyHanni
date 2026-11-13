package at.hannibal2.skyhanni.data.git.commit

import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.SimpleTimeMark.Companion.asTimeMark
import java.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortCommitAuthor(
    @SerialName("date") private val dateString: String,
) {
    val date: SimpleTimeMark get() = Instant.parse(dateString).toEpochMilli().asTimeMark()
}
