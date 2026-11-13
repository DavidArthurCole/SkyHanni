package at.hannibal2.skyhanni.data.jsonobjects.other

import at.hannibal2.skyhanni.utils.json.JsonTreeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject

@Serializable
data class HypixelPlayerApiJson(
    val profiles: List<HypixelApiProfile>,
)

@Serializable
data class HypixelApiProfile(
    val members: Map<String, HypixelApiPlayer> = emptyMap(),
    @SerialName("cute_name") val profileName: String = "",
)

@Serializable
data class HypixelApiPlayer(
    @SerialName("trophy_fish") val trophyFish: HypixelApiTrophyFish? = null,
    val events: HypixelApiEvents? = null,
    @SerialName("nether_island_player_data") val netherData: HypixelApiFactionInfo? = null,
)

@Serializable
data class HypixelApiEvents(
    val easter: HypixelApiEasterEvent? = null,
)

@Serializable
data class HypixelApiEasterEvent(
    val rabbits: HypixelApiRabbits? = null,
)

@Serializable
data class HypixelApiRabbits(
    @SerialName("collected_locations") val collectedLocations: Map<String, List<String>>? = null,
)

@Serializable(with = HypixelApiTrophyFishSerializer::class)
data class HypixelApiTrophyFish(
    val totalCaught: Int,
    val caught: Map<String, Int>,
)

object HypixelApiTrophyFishSerializer : JsonTreeSerializer<HypixelApiTrophyFish>("HypixelApiTrophyFish") {
    private const val TOTAL_KEY = "total_caught"

    override fun toTree(value: HypixelApiTrophyFish): JsonElement = JsonObject(emptyMap())

    override fun fromTree(element: JsonElement): HypixelApiTrophyFish {
        val counts = element.jsonObject.mapNotNull { (name, value) ->
            (value as? JsonPrimitive)?.takeUnless { it.isString }?.intOrNull?.let { name to it }
        }.toMap()
        return HypixelApiTrophyFish(counts[TOTAL_KEY] ?: 0, counts - TOTAL_KEY)
    }
}

@Serializable
data class HypixelApiFactionInfo(
    @SerialName("barbarians_reputation") val barbarianReputation: Int = 0,
    @SerialName("mages_reputation") val mageReputation: Int = 0,
    @SerialName("selected_faction") val currentFaction: String? = null,
)
