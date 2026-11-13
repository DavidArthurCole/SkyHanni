package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class DisabledFeaturesJson(
    val features: Map<String, Boolean>? = mapOf(),
)
