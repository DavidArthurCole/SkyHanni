package at.hannibal2.skyhanni.test.utils

import at.hannibal2.skyhanni.config.SackData
import at.hannibal2.skyhanni.config.SkyHanniConfig
import at.hannibal2.skyhanni.config.StorageData
import at.hannibal2.skyhanni.config.storage.AchievementStorage
import at.hannibal2.skyhanni.config.storage.CustomTodosStorage
import at.hannibal2.skyhanni.config.storage.OrderedWaypointsRoutes
import at.hannibal2.skyhanni.config.storage.SeenContributorStorage
import at.hannibal2.skyhanni.config.storage.SpecificSeaCreatureStorage
import at.hannibal2.skyhanni.data.PetDataStorage
import at.hannibal2.skyhanni.data.jsonobjects.local.FriendsJson
import at.hannibal2.skyhanni.data.jsonobjects.local.JacobContestsJson
import at.hannibal2.skyhanni.data.jsonobjects.local.KnownFeaturesJson
import at.hannibal2.skyhanni.data.jsonobjects.local.VisualWordsJson
import at.hannibal2.skyhanni.features.garden.tracker.RareCropTracker
import at.hannibal2.skyhanni.utils.json.BaseGsonBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ConfigFileRoundTripTest {

    private val gson = BaseGsonBuilder.gson().create()

    @BeforeEach
    fun loadTrackerBeforeItsEnum() {
        RareCropTracker.toString()
    }

    @ParameterizedTest
    @MethodSource("configClasses")
    fun `default instance survives a write and read`(clazz: Class<*>) {
        val written = gson.toJson(clazz.getDeclaredConstructor().newInstance())
        assertEquals(written, gson.toJson(gson.fromJson(written, clazz)))
    }

    companion object {
        @JvmStatic
        fun configClasses(): List<Class<*>> = listOf(
            SkyHanniConfig::class.java,
            SackData::class.java,
            FriendsJson::class.java,
            KnownFeaturesJson::class.java,
            JacobContestsJson::class.java,
            VisualWordsJson::class.java,
            PetDataStorage::class.java,
            StorageData::class.java,
            OrderedWaypointsRoutes::class.java,
            CustomTodosStorage::class.java,
            SpecificSeaCreatureStorage::class.java,
            AchievementStorage::class.java,
            SeenContributorStorage::class.java,
        )
    }
}
