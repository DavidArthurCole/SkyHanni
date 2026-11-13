package at.hannibal2.skyhanni.data.git.commit

import kotlinx.serialization.Serializable

@Serializable
data class CommitsApiResponse(
    val sha: String,
    val commit: Commit,
)
