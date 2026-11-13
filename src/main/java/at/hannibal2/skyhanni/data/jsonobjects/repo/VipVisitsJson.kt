package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class VipVisitsJson(
    @SerialName("vip_visits") @JsonNames("vipVisits") val vipVisits: List<String>,
)
