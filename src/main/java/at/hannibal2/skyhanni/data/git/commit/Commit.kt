package at.hannibal2.skyhanni.data.git.commit

import kotlinx.serialization.Serializable

@Serializable
data class Commit(
    val committer: ShortCommitAuthor,
)
