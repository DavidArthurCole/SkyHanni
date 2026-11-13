package at.hannibal2.skyhanni.data.model.waypoints

import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.json.SkyHanniJson
import com.google.auto.service.AutoService
import java.io.ByteArrayInputStream
import java.util.Base64
import java.util.zip.GZIPInputStream
import kotlinx.serialization.Serializable

@AutoService(WaypointFormat::class)
class SkytilsWaypointFormat : WaypointFormat {

    @Serializable
    data class SkytilsWaypoint(
        val name: String,
        val x: Double,
        val y: Double,
        val z: Double,
        val island: String? = null,
        val enabled: Boolean = true,
        val color: Int? = null,
        val addedAt: Long? = null,
    )

    override fun load(string: String): Waypoints<SkyHanniWaypoint>? {
        val json = decodeInput(string) ?: return null
        return try {
            val list = SkyHanniJson.json.decodeFromString<List<SkytilsWaypoint>>(json)
            val waypoints = list.mapIndexed { index, wp ->
                val number = index + 1
                SkyHanniWaypoint(
                    LorenzVec(wp.x, wp.y, wp.z),
                    number,
                    mutableMapOf("name" to wp.name),
                )
            }
            Waypoints(waypoints.toMutableList())
        } catch (e: Exception) {
            ChatUtils.debug(e.stackTraceToString())
            null
        }
    }

    private fun decodeInput(string: String): String? {
        val trimmed = string.trim()
        if (trimmed.startsWith(V1_HEADER)) {
            return decodeV1(trimmed.removePrefix(V1_HEADER))
        }
        // Legacy format: raw JSON array
        return trimmed.takeIf { it.startsWith("[") }
    }

    private fun decodeV1(data: String): String? = try {
        val bytes = Base64.getDecoder().decode(data)
        GZIPInputStream(ByteArrayInputStream(bytes)).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        ChatUtils.debug(e.stackTraceToString())
        null
    }

    override fun canLoad(string: String): Boolean = load(string) != null

    override fun export(waypoints: Waypoints<SkyHanniWaypoint>): String {
        val list = waypoints.map { wp ->
            SkytilsWaypoint(
                name = wp.number.toString(),
                x = wp.location.x,
                y = wp.location.y,
                z = wp.location.z,
            )
        }
        return SkyHanniJson.json.encodeToString(list)
    }

    override val name: String get() = "skytils"

    private companion object {
        const val V1_HEADER = "<Skytils-Waypoint-Data>(V1):"
    }
}
