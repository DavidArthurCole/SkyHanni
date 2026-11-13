package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.Serializable

@Serializable
class RescueParkourJson(
    val mage: Map<String, List<LorenzVec>>,
    val barb: Map<String, List<LorenzVec>>,
)
