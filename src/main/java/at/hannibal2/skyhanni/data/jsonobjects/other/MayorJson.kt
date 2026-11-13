package at.hannibal2.skyhanni.data.jsonobjects.other

import kotlinx.serialization.Serializable

@Serializable
data class MayorJson(
    val mayor: MayorInfo?,
    val current: MayorElection?,
)

@Serializable
data class MayorInfo(
    val key: String,
    val name: String,
    val perks: List<MayorPerk>?,
    // Ministers won't exist,
    // when the current mayor is a special mayor
    val minister: Minister?,
    val election: MayorElection?,
)

@Serializable
data class MayorElection(
    val year: Int,
    val candidates: List<MayorCandidate>,
)

@Serializable
data class MayorCandidate(
    val key: String,
    val name: String,
    val perks: List<MayorPerk>?,
    val votes: Int,
)

@Serializable
data class Minister(
    val key: String,
    val name: String,
    val perk: MayorPerk?,
)

@Serializable
data class MayorPerk(
    val name: String,
    val description: String,
    val minister: Boolean = false,
)
