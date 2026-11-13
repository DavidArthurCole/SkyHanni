package at.hannibal2.skyhanni.test.utils

import at.hannibal2.skyhanni.data.IslandType
import at.hannibal2.skyhanni.data.jsonobjects.elitedev.EliteLeaderboardMode
import at.hannibal2.skyhanni.data.jsonobjects.elitedev.EliteLeaderboardType
import at.hannibal2.skyhanni.features.garden.CropType
import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.NeuInternalName.Companion.toInternalName
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.SimpleTimeMark.Companion.asTimeMark
import at.hannibal2.skyhanni.utils.json.BaseGsonBuilder
import at.hannibal2.skyhanni.utils.json.fromJson
import at.hannibal2.skyhanni.utils.system.ModVersion
import at.hannibal2.skyhanni.utils.tracker.GardenSession
import at.hannibal2.skyhanni.utils.tracker.SessionUptime
import com.google.gson.annotations.Expose
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class ConfigWireFormatTest {

    private val gson = BaseGsonBuilder.gson().create()

    private class Storage {
        @Expose
        var owner: UUID? = null

        @Expose
        var position: LorenzVec? = null

        @Expose
        var rarity: LorenzRarity? = null

        @Expose
        var island: IslandType? = null

        @Expose
        var version: ModVersion? = null

        @Expose
        var leaderboard: EliteLeaderboardType? = null

        @Expose
        var session: SessionUptime? = null

        @Expose
        var items: MutableMap<NeuInternalName, Int> = mutableMapOf()

        @Expose
        var marks: MutableMap<UUID, SimpleTimeMark> = mutableMapOf()

        var notExposed: String = "hidden"
    }

    private fun compact(json: String) = json.filterNot { it.isWhitespace() }

    private val stored = """
        {
          "owner": "0b5ab3a8-9d4e-4f6a-8d2b-5a1f3c7e9b10",
          "position": "1.0:2.5:-3.0",
          "rarity": "LEGENDARY",
          "island": "THE_RIFT",
          "version": "9.3.0",
          "leaderboard": {"type": "crop", "crop": "WHEAT", "mode": "ALL_TIME"},
          "session": {"type": "garden", "session": "PEST"},
          "items": {"ENCHANTED_DIAMOND": 3},
          "marks": {"0b5ab3a8-9d4e-4f6a-8d2b-5a1f3c7e9b10": 1700000000000}
        }
    """

    @Test
    fun `stored values decode to their types`() {
        val storage = gson.fromJson<Storage>(stored)
        assertEquals(UUID.fromString("0b5ab3a8-9d4e-4f6a-8d2b-5a1f3c7e9b10"), storage.owner)
        assertEquals(LorenzVec(1.0, 2.5, -3.0), storage.position)
        assertEquals(LorenzRarity.LEGENDARY, storage.rarity)
        assertEquals(IslandType.THE_RIFT, storage.island)
        assertEquals(ModVersion(9, 3, 0), storage.version)
        assertEquals(EliteLeaderboardType.Crop(CropType.WHEAT, EliteLeaderboardMode.ALL_TIME), storage.leaderboard)
        assertEquals(SessionUptime.Garden(GardenSession.PEST), storage.session)
        assertEquals(mapOf("ENCHANTED_DIAMOND".toInternalName() to 3), storage.items)
        assertEquals(1700000000000L.asTimeMark(), storage.marks.values.single())
    }

    @Test
    fun `stored values write back unchanged`() {
        assertEquals(compact(stored), compact(gson.toJson(gson.fromJson<Storage>(stored))))
    }

    @Test
    fun `unknown enum name is kept for the fallback value`() {
        val storage = gson.fromJson<Storage>("""{"island":"SOME_FUTURE_ISLAND"}""")
        assertEquals(IslandType.UNKNOWN, storage.island)
        assertEquals("""{"island":"SOME_FUTURE_ISLAND","items":{},"marks":{}}""", compact(gson.toJson(storage)))
    }
}
