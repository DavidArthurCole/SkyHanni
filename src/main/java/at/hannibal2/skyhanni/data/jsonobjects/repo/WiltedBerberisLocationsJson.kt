package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WiltedBerberisLocationsJson(
    @SerialName("field_centers") val fieldCenters: List<FieldCenter>,
)

@Serializable
data class FieldCenter(
    val position: LorenzVec,
    val count: Int,
)
