package at.hannibal2.skyhanni.data.model.waypoints

import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.json.SkyHanniJson
import at.hannibal2.skyhanni.utils.json.WholeNumberSerializer
import com.google.auto.service.AutoService
import kotlinx.serialization.Serializable

@AutoService(WaypointFormat::class)
class ColeweightWaypointFormat : WaypointFormat {

    @Serializable
    data class ColeweightWaypoint(
        @Serializable(with = WholeNumberSerializer::class) val x: Int,
        @Serializable(with = WholeNumberSerializer::class) val y: Int,
        @Serializable(with = WholeNumberSerializer::class) val z: Int,
        val r: Double,
        val g: Double,
        val b: Double,
        val options: MutableMap<String, String> = mutableMapOf(),
    ) : Copyable<ColeweightWaypoint> {
        override fun copy() = ColeweightWaypoint(x, y, z, r, g, b, options)
    }

    override fun load(string: String): Waypoints<SkyHanniWaypoint>? = try {
        Waypoints(SkyHanniJson.json.decodeFromString<MutableList<ColeweightWaypoint>>(string)).transform { it.load() }
    } catch (e: Exception) {
        ChatUtils.debug(e.stackTraceToString())
        null
    }

    private fun ColeweightWaypoint.load() = SkyHanniWaypoint(
        LorenzVec(x, y, z),
        // TODO refactor
        @Suppress("MapGetWithNotNullAssertionOperator", "UnsafeCallOnNullableType")
        options["name"]!!.toInt(),
        options,
    )

    override fun canLoad(string: String): Boolean {
        return load(string) != null
    }

    override fun export(waypoints: Waypoints<SkyHanniWaypoint>): String =
        SkyHanniJson.json.encodeToString(waypoints.transform { it.export() }.waypoints)

    private fun SkyHanniWaypoint.export(): ColeweightWaypoint = with(location) {
        ColeweightWaypoint(
            x.toInt(),
            y.toInt(),
            z.toInt(),
            r = 0.0,
            g = 1.0,
            b = 0.0,
            options,
        )
    }

    override val name: String get() = "coleweight"
}
