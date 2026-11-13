package at.hannibal2.skyhanni.data.jsonobjects.elitedev

import at.hannibal2.skyhanni.data.jsonobjects.elitedev.EliteLeaderboardType.Crop
import at.hannibal2.skyhanni.data.jsonobjects.elitedev.EliteLeaderboardType.Pest
import at.hannibal2.skyhanni.data.jsonobjects.elitedev.EliteLeaderboardType.Weight
import at.hannibal2.skyhanni.features.garden.CropType
import at.hannibal2.skyhanni.features.garden.pests.PestType
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.json.UuidSerializer
import java.util.UUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ElitePlayerWeightJson(
    val selectedProfileId: String,
    val profiles: List<WeightProfile>,
)

@Serializable
data class EliteWeightResponse(
    val totalWeight: Double,
    val profileId: String,
)

@Serializable
data class WeightProfile(
    val profileId: String,
    val profileName: String,
    val totalWeight: Double,
    val crops: Map<String, Long>,
    val cropWeight: Map<String, Double>,
    val bonusWeight: Map<String, Int>,
    val uncountedCrops: Map<String, Int>,
    val pests: Map<String, Int>,
    val lastUpdated: SimpleTimeMark,
)

@Serializable
data class EliteLeaderboard(
    val rank: Int,
    val amount: Double,
    val minAmount: Double,
    val initialAmount: Double,
    val upcomingRank: Int,
    val upcomingPlayers: List<EliteLeaderboardPlayer>,
    val previous: List<EliteLeaderboardPlayer>?,
    val disabled: Boolean = false,
)

@Serializable
data class EliteLeaderboardPlayer(
    @SerialName("ign") val name: String,
    val profile: String,
    @Serializable(with = UuidSerializer::class) val uuid: UUID,
    val amount: Double,
    val rank: Int? = null,
    val mode: String? = null,
    val meta: JsonObject? = null,
)

@Serializable
data class EliteWeightsJson(
    val crops: Map<String, Double>,
    val pests: PestWeightData,
)

@Serializable
data class PestWeightData(
    val brackets: Map<Int, Int>,
    @SerialName("values") val pestWeights: Map<String, Map<Int, Double>>,
)

@Serializable
sealed class EliteLeaderboardType {
    abstract val mode: EliteLeaderboardMode

    interface WithEnum<E : Enum<E>> {
        val enumValue: E?
    }

    @Serializable
    @SerialName("weight")
    data class Weight(
        val weight: FarmingWeight,
        override val mode: EliteLeaderboardMode,
    ) : EliteLeaderboardType(),
        WithEnum<FarmingWeight> {
        override val enumValue: FarmingWeight get() = weight
        override fun toString() = "${weight}${mode.displaySuffix}"
    }

    @Serializable
    @SerialName("crop")
    data class Crop(
        val crop: CropType,
        override val mode: EliteLeaderboardMode,
    ) : EliteLeaderboardType(), WithEnum<CropType> {
        override val enumValue: CropType get() = crop
        override fun toString() = "${crop.cropName} Collection${mode.displaySuffix}"
    }

    @Serializable
    @SerialName("pest")
    data class Pest(
        val pest: PestType? = null,
        override val mode: EliteLeaderboardMode,
    ) : EliteLeaderboardType(), WithEnum<PestType> {
        override val enumValue: PestType? get() = pest
        override fun toString() = "${pest?.displayName ?: "Pest"} Kills${mode.displaySuffix}"
    }

    val lbName: String
        get() = when (this) {
            is Weight -> "${weight.apiName}${mode.lbSuffix}"
            is Crop -> "${crop.eliteLbName}${mode.lbSuffix}"
            is Pest -> "${pest?.eliteLbName ?: "pests"}${mode.lbSuffix}"
        }

    val type
        get() = when (this) {
            is Weight -> this.weight
            is Crop -> this.crop
            is Pest -> this.pest
        }
}

val EliteLeaderboardType.pest: PestType?
    get() = (this as? Pest)?.pest

val EliteLeaderboardType.crop: CropType?
    get() = (this as? Crop)?.crop

enum class EliteLeaderboardMode(
    val displayName: String,
    val lbSuffix: String = "",
    val displaySuffix: String = "",
) {
    ALL_TIME("All-Time"),
    MONTHLY("Monthly", "-monthly", " Monthly"),
    ;

    override fun toString() = displayName
}

enum class FarmingWeight(val displayName: String, val apiName: String) {
    FARMING_WEIGHT("Farming Weight", "farmingweight"),
    ;

    override fun toString(): String = displayName
}
