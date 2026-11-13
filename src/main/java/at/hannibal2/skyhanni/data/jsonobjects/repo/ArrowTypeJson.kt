package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class ArrowTypeJson(
    val arrows: Map<String, ArrowAttributes>,
)

@Serializable
data class ArrowAttributes(
    val arrow: String,
)
