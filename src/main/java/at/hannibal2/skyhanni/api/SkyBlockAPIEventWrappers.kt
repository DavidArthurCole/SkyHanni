package at.hannibal2.skyhanni.api

import at.hannibal2.skyhanni.data.jsonobjects.other.HypixelApiProfile
import at.hannibal2.skyhanni.data.jsonobjects.other.HypixelPlayerApiJson
import at.hannibal2.skyhanni.events.ProfileViewerDataLoadedEvent
import at.hannibal2.skyhanni.skyhannimodule.SkyHanniModule
import at.hannibal2.skyhanni.test.command.ErrorManager
import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toKotlinx
import at.hannibal2.skyhanni.utils.json.SkyHanniJson
import com.google.gson.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

@SkyHanniModule
object SkyBlockAPIEventWrappers {

    @JvmStatic
    fun onPvLoad(data: JsonObject) {
        try {
            // SbPv only sends the active/selected profile, not all profiles
            val profileData = SkyHanniJson.json.decodeFromJsonElement<HypixelApiProfile>(data.toKotlinx())
            val playerData = HypixelPlayerApiJson(listOf(profileData))

            ProfileViewerDataLoadedEvent(playerData).post()
        } catch (e: Exception) {
            ErrorManager.logErrorWithData(
                e, "Error reading hypixel player api data",
                "data" to data,
            )
        }
    }

}
