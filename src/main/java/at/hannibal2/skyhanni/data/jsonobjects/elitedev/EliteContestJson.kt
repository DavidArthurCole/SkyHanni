package at.hannibal2.skyhanni.data.jsonobjects.elitedev

import at.hannibal2.skyhanni.config.ConfigManager
import at.hannibal2.skyhanni.features.garden.CropType
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.SimpleTimeMark.Companion.asTimeMark
import kotlin.time.Duration.Companion.minutes
import kotlinx.serialization.Serializable

@Serializable
data class EliteContestsResponse(
    val year: Int,
    val count: Int,
    val complete: Boolean,
    private val contests: Map<String, List<String>>,
) {
    @Suppress("Deprecation")
    val responseContests: List<EliteFarmingContest> by lazy {
        contests.mapNotNull { (timestampStr, cropStrList) ->
            val longTimeStamp = timestampStr.toLongOrNull() ?: return@mapNotNull null
            val crops = cropStrList.mapNotNull { cropStr ->
                CropType.getByNameOrNull(cropStr)
            }.takeIf { it.size == 3 } ?: return@mapNotNull null
            EliteFarmingContest((longTimeStamp * 1000).asTimeMark(), crops)
        }
    }
}

data class EliteContestsRequest(
    val list: List<EliteFarmingContest>,
) : Map<Long, List<String>> by list.associate(
    transform = { it.startTime.toMillis() / 1000 to it.crops.map { crop -> crop.cropName } }
) {
    fun getBody(): String = ConfigManager.gson.toJson(this as Map<Long, List<String>>)
}

@Serializable
data class EliteFarmingContest(
    val startTime: SimpleTimeMark,
    val crops: List<CropType>,
    var boostedCrop: CropType? = null,
) {
    // If hypixel changes the length of a SkyBlock day, we'll have
    // bigger problems than this being hardcoded.
    val endTime get() = startTime + 20.minutes
}
