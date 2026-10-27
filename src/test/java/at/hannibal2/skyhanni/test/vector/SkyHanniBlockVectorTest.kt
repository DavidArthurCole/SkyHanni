package at.hannibal2.skyhanni.test.vector

import at.hannibal2.skyhanni.utils.vector.SkyHanniBlockVector
import at.hannibal2.skyhanni.utils.vector.SkyHanniVector
import at.hannibal2.skyhanni.utils.vector.VectorBlocks.AXIS_DIRECTIONS
import at.hannibal2.skyhanni.utils.vector.VectorBlocks.neighbors
import at.hannibal2.skyhanni.utils.vector.VectorFormat.parseStoredBlock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class SkyHanniBlockVectorTest {

    @Test
    fun `packing round trips across the SkyBlock coordinate range`() {
        listOf(
            Triple(0, 0, 0),
            Triple(1, 2, 3),
            Triple(-1, -2, -3),
            Triple(-541, 94, 227),
            Triple(30_000_000, 2047, -30_000_000),
            Triple(-30_000_000, -2048, 30_000_000),
        ).forEach { (x, y, z) ->
            val v = SkyHanniBlockVector(x, y, z)
            assertEquals(x, v.x, "x of $v")
            assertEquals(y, v.y, "y of $v")
            assertEquals(z, v.z, "z of $v")
            assertEquals(v, SkyHanniBlockVector.fromPacked(v.asLong()))
        }
    }

    @Test
    fun `equality is by coordinate`() {
        assertEquals(SkyHanniBlockVector(1, 2, 3), SkyHanniBlockVector(1, 2, 3))
        assertNotEquals(SkyHanniBlockVector(1, 2, 3), SkyHanniBlockVector(3, 2, 1))
        assertEquals(setOf(SkyHanniBlockVector(1, 2, 3)), setOf(SkyHanniBlockVector(1, 2, 3), SkyHanniBlockVector(1, 2, 3)))
    }

    @Test
    fun `arithmetic and offsets`() {
        val v = SkyHanniBlockVector(1, 2, 3)
        assertEquals(SkyHanniBlockVector(2, 4, 6), v + v)
        assertEquals(SkyHanniBlockVector.ZERO, v - v)
        assertEquals(SkyHanniBlockVector(1, 3, 3), v.up())
        assertEquals(SkyHanniBlockVector(1, 0, 3), v.down(2))
        assertEquals(SkyHanniBlockVector(0, 2, 4), v.offset(x = -1, z = 1))
        assertEquals(6, v.neighbors().size)
        assertEquals(6, AXIS_DIRECTIONS.size)
    }

    @Test
    fun `distances`() {
        val origin = SkyHanniBlockVector.ZERO
        val point = SkyHanniBlockVector(3, 4, 12)
        assertEquals(169L, origin.distanceSq(point))
        assertEquals(13.0, origin.distance(point))
        assertEquals(12, origin.distanceChebyshev(point))
    }

    @Test
    fun `conversions to continuous vectors`() {
        val v = SkyHanniBlockVector(1, -2, 3)
        assertEquals(SkyHanniVector(1.0, -2.0, 3.0), v.toVector())
        assertEquals(SkyHanniVector(1.5, -1.5, 3.5), v.center())
    }

    @Test
    fun `stored string round trips and accepts legacy doubles`() {
        val v = SkyHanniBlockVector(-5, 64, 12)
        assertEquals("-5:64:12", v.asStoredString())
        assertEquals(v, parseStoredBlock(v.asStoredString()))
        assertEquals(v, parseStoredBlock("-5.0:64.0:12.0"))
    }
}
