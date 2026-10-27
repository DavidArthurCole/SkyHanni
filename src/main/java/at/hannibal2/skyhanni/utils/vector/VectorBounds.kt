package at.hannibal2.skyhanni.utils.vector

import net.minecraft.core.Direction
import net.minecraft.world.phys.AABB

object VectorBounds {

    operator fun SkyHanniVector.get(axis: Direction.Axis): Double = axis.choose(x, y, z)

    fun SkyHanniVector.with(axis: Direction.Axis, value: Double) = when (axis) {
        Direction.Axis.X -> copy(x = value)
        Direction.Axis.Y -> copy(y = value)
        Direction.Axis.Z -> copy(z = value)
    }

    fun SkyHanniVector.boxAround(radius: Double) = AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius)

    fun SkyHanniVector.boxTo(other: SkyHanniVector) = AABB(x, y, z, other.x, other.y, other.z)

    fun SkyHanniVector.boxFromOrigin(sizeX: Double, sizeY: Double, sizeZ: Double) =
        AABB(x, y, z, x + sizeX, y + sizeY, z + sizeZ)

    fun SkyHanniVector.blockBox() = boxFromOrigin(1.0, 1.0, 1.0)

    fun SkyHanniBlockVector.box() = toVector().blockBox()

    fun AABB.minCorner() = SkyHanniVector(minX, minY, minZ)

    fun AABB.maxCorner() = SkyHanniVector(maxX, maxY, maxZ)

    fun AABB.edgeLengths() = maxCorner() - minCorner()

    fun AABB.center() = minCorner().midpoint(maxCorner())

    fun AABB.topCenter() = center().up((maxY - minY) / 2)

    fun AABB.contains(vector: SkyHanniVector) = contains(vector.x, vector.y, vector.z)

    fun AABB.inflate(vector: SkyHanniVector): AABB = inflate(vector.x, vector.y, vector.z)

    fun AABB.cornersAtHeight(y: Double) = listOf(
        SkyHanniVector(minX, y, minZ),
        SkyHanniVector(minX, y, maxZ),
        SkyHanniVector(maxX, y, maxZ),
        SkyHanniVector(maxX, y, minZ),
    )

    fun AABB.edges(): Set<Pair<SkyHanniVector, SkyHanniVector>> {
        val bottom = cornersAtHeight(minY)
        val top = cornersAtHeight(maxY)
        val ring = { corners: List<SkyHanniVector> -> corners.indices.map { corners[it] to corners[(it + 1) % corners.size] } }
        return (ring(bottom) + ring(top) + bottom.zip(top)).toSet()
    }

    fun SkyHanniVector.nearestPointOnSegment(start: SkyHanniVector, end: SkyHanniVector): SkyHanniVector {
        val direction = end - start
        val lengthSquared = direction.lengthSquared()
        if (lengthSquared == 0.0) return start
        val t = ((this - start) dot direction / lengthSquared).coerceIn(0.0, 1.0)
        return start + direction * t
    }
}
