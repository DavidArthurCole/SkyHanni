package at.hannibal2.skyhanni.utils.vector

import kotlin.math.floor

object VectorBlocks {

    val AXIS_DIRECTIONS = setOf(
        SkyHanniBlockVector(1, 0, 0),
        SkyHanniBlockVector(-1, 0, 0),
        SkyHanniBlockVector(0, 1, 0),
        SkyHanniBlockVector(0, -1, 0),
        SkyHanniBlockVector(0, 0, 1),
        SkyHanniBlockVector(0, 0, -1),
    )

    fun SkyHanniVector.floorToBlock() = SkyHanniBlockVector(floor(x).toInt(), floor(y).toInt(), floor(z).toInt())

    fun SkyHanniBlockVector.neighbors() = AXIS_DIRECTIONS.map { this + it }
}
