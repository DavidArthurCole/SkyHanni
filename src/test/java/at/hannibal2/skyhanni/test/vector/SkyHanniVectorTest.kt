package at.hannibal2.skyhanni.test.vector

import at.hannibal2.skyhanni.utils.vector.SkyHanniVector
import at.hannibal2.skyhanni.utils.vector.VectorBlocks.blockCenter
import at.hannibal2.skyhanni.utils.vector.VectorBlocks.floorToBlock
import at.hannibal2.skyhanni.utils.vector.VectorFormat.asStoredString
import at.hannibal2.skyhanni.utils.vector.VectorFormat.formatRounded
import at.hannibal2.skyhanni.utils.vector.VectorFormat.parseStored
import at.hannibal2.skyhanni.utils.vector.VectorFormat.toChatFormat
import at.hannibal2.skyhanni.utils.vector.VectorFormat.toLocalFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.math.PI

class SkyHanniVectorTest {

    private val a = SkyHanniVector(1.0, 2.0, 3.0)
    private val b = SkyHanniVector(4.0, 6.0, 8.0)

    @Test
    fun `arithmetic operators are elementwise`() {
        assertEquals(SkyHanniVector(5.0, 8.0, 11.0), a + b)
        assertEquals(SkyHanniVector(3.0, 4.0, 5.0), b - a)
        assertEquals(SkyHanniVector(4.0, 12.0, 24.0), a * b)
        assertEquals(SkyHanniVector(4.0, 3.0, 8.0 / 3.0), b / a)
        assertEquals(SkyHanniVector(2.0, 4.0, 6.0), a * 2.0)
        assertEquals(SkyHanniVector(2.0, 4.0, 6.0), a * 2)
        assertEquals(SkyHanniVector(0.5, 1.0, 1.5), a / 2.0)
        assertEquals(SkyHanniVector(-1.0, -2.0, -3.0), -a)
    }

    @Test
    fun `dot and cross follow the right hand rule`() {
        assertEquals(40.0, a dot b)
        assertEquals(SkyHanniVector.Z_AXIS, SkyHanniVector.X_AXIS cross SkyHanniVector.Y_AXIS)
        assertEquals(-SkyHanniVector.Z_AXIS, SkyHanniVector.Y_AXIS cross SkyHanniVector.X_AXIS)
    }

    @Test
    fun `negative zero equals positive zero and hashes identically`() {
        val positive = SkyHanniVector(0.0, 0.0, 0.0)
        val negative = SkyHanniVector(-0.0, -0.0, -0.0)
        assertEquals(positive, negative)
        assertEquals(positive.hashCode(), negative.hashCode())
        assertTrue(negative.isZero())
        assertNotEquals(positive, SkyHanniVector(0.0, 0.0, 1.0))
    }

    @Test
    fun `distances ignore the requested axes`() {
        val origin = SkyHanniVector.ZERO
        val point = SkyHanniVector(3.0, 10.0, 4.0)
        assertEquals(5.0, origin.distanceIgnoreY(point))
        assertEquals(25.0, origin.distanceSqIgnoreY(point))
        assertEquals(4.0, origin.distanceChebyshevIgnoreY(point))
        assertEquals(125.0, origin.distanceSq(point))
    }

    @Test
    fun `normalized has unit length`() {
        assertTrue(b.normalized().isNormalized())
        assertEquals(1.0, SkyHanniVector(0.0, 5.0, 0.0).normalized().y)
    }

    @Test
    fun `lerp and midpoint interpolate linearly`() {
        assertEquals(a, a.lerp(b, 0.0))
        assertEquals(b, a.lerp(b, 1.0))
        assertEquals(SkyHanniVector(2.5, 4.0, 5.5), a.midpoint(b))
    }

    @Test
    fun `rotateAroundY turns x into negative z`() {
        val rotated = SkyHanniVector.X_AXIS.rotateAroundY(PI / 2).roundTo(6)
        assertEquals(SkyHanniVector(0.0, 0.0, -1.0), rotated)
    }

    @Test
    fun `floor ceil and block helpers snap to the grid`() {
        val v = SkyHanniVector(1.7, -2.3, 3.0)
        assertEquals(SkyHanniVector(1.0, -3.0, 3.0), v.floor())
        assertEquals(SkyHanniVector(2.0, -2.0, 3.0), v.ceil())
        assertEquals(SkyHanniVector(1.5, -2.5, 3.5), v.blockCenter())
        val block = v.floorToBlock()
        assertEquals(1, block.x)
        assertEquals(-3, block.y)
        assertEquals(3, block.z)
    }

    @Test
    fun `component helpers`() {
        assertEquals(1.0, a.minComponent())
        assertEquals(3.0, a.maxComponent())
        assertEquals(SkyHanniVector(1.0, 2.0, 3.0), a.elementwiseMin(b))
        assertEquals(SkyHanniVector(4.0, 6.0, 8.0), a.elementwiseMax(b))
        assertEquals(SkyHanniVector(1.0, 0.5, 0.25), SkyHanniVector(1.0, 2.0, 4.0).reciprocal())
    }

    @Test
    fun `formats match the legacy output`() {
        val v = SkyHanniVector(-91.74, 70.0, 29.26)
        assertEquals("x: -91, y: 70, z: 29", v.toChatFormat())
        assertEquals("-91 70 29", v.toLocalFormat())
        assertEquals("-92 70 29", v.formatRounded(0))
        assertEquals("-91.7 70.0 29.3", v.formatRounded(10))
        assertEquals("-91.74:70.0:29.26", v.asStoredString())
    }

    @Test
    fun `stored string round trips`() {
        val v = SkyHanniVector(-1.5, 64.0, 1234.25)
        assertEquals(v, parseStored(v.asStoredString()))
        assertEquals(SkyHanniVector(1.0, 2.0, 3.0), parseStored("1:2:3"))
    }
}
