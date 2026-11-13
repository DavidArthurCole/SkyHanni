package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.system.ModVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNames

@Serializable
data class EnforcedConfigValuesJson(
    @SerialName("enforced_config_values") val enforcedConfigValues: List<EnforcedValueData>,
)

@Serializable
data class EnforcedValueData(
    @SerialName("enforced_values") @JsonNames("enforcedValues") val enforcedValues: List<EnforcedValue> = listOf(),
    @SerialName("notification_psa") @JsonNames("notificationPSA") val notificationPSA: List<String>? = null,
    @SerialName("chat_psa") @JsonNames("chatPSA") val chatPSA: List<String>? = null,
    @SerialName("minimum_affected_version") @JsonNames("minimumAffectedVersion")
    val minimumAffectedVersion: ModVersion? = null,
    @SerialName("affected_version") @JsonNames("affectedVersion") val affectedVersion: ModVersion,
    @SerialName("affected_minecraft_versions") @JsonNames("affectedMinecraftVersions")
    val affectedMinecraftVersions: List<String>? = null,
    @SerialName("extra_message") @JsonNames("extraMessage") val extraMessage: String? = null,
)

@Serializable
data class EnforcedValue(
    val path: String,
    val value: JsonElement,
)
