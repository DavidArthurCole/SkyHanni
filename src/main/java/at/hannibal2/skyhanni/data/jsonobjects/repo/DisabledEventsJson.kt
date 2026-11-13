package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.system.ModVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class DisabledEventsJson(
    @SerialName("disabled_handlers") @JsonNames("disabledHandlers") val disabledHandlers: Set<String> = emptySet(),
    @SerialName("disabled_invokers") @JsonNames("disabledInvokers") val disabledInvokers: Set<String> = emptySet(),
    @SerialName("disabled_handlers_versioned")
    val disabledHandlersVersioned: Set<DisabledEventVersionedJson> = emptySet(),
    @SerialName("disabled_invokers_versioned")
    val disabledInvokersVersioned: Set<DisabledEventVersionedJson> = emptySet(),
)

@Serializable
data class DisabledEventVersionedJson(
    val name: String,
    @SerialName("min_version") val minVersion: ModVersion? = null,
    @SerialName("max_version") val maxVersion: ModVersion? = null,
    @SerialName("mc_versions") val mcVersions: Set<String>? = null,
)
