package at.hannibal2.skyhanni.utils.vector

import at.hannibal2.skyhanni.utils.NumberUtil.roundTo
import at.hannibal2.skyhanni.utils.vector.VectorBounds.boxAround
import at.hannibal2.skyhanni.utils.vector.VectorBounds.boxFromOrigin
import at.hannibal2.skyhanni.utils.vector.VectorBounds.boxTo
import at.hannibal2.skyhanni.utils.vector.VectorBounds.nearestPointOnSegment
import at.hannibal2.skyhanni.utils.vector.VectorFormat.formatRounded
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

@JvmRecord
@Suppress("TooManyFunctions", "DEPRECATION")
data class SkyHanniVector(
    val x: Double,
    val y: Double,
    val z: Double,
) {

    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    inline fun map(transform: (Double) -> Double) = SkyHanniVector(transform(x), transform(y), transform(z))

    inline fun zip(other: SkyHanniVector, transform: (Double, Double) -> Double) =
        SkyHanniVector(transform(x, other.x), transform(y, other.y), transform(z, other.z))

    operator fun plus(other: SkyHanniVector) = zip(other) { a, b -> a + b }

    operator fun minus(other: SkyHanniVector) = zip(other) { a, b -> a - b }

    operator fun times(other: SkyHanniVector) = zip(other) { a, b -> a * b }

    operator fun div(other: SkyHanniVector) = zip(other) { a, b -> a / b }

    operator fun times(scalar: Double) = map { it * scalar }

    operator fun times(scalar: Int) = times(scalar.toDouble())

    operator fun div(scalar: Double) = map { it / scalar }

    operator fun unaryMinus() = map { -it }

    fun add(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) = SkyHanniVector(this.x + x, this.y + y, this.z + z)

    fun up(offset: Number = 1) = copy(y = y + offset.toDouble())

    fun down(offset: Number = 1) = copy(y = y - offset.toDouble())

    fun addHalf() = add(0.5, 0.5, 0.5)

    infix fun dot(other: SkyHanniVector) = x * other.x + y * other.y + z * other.z

    infix fun cross(other: SkyHanniVector) = SkyHanniVector(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x,
    )

    fun lengthSquared() = this dot this

    fun length() = sqrt(lengthSquared())

    fun normalized() = this / length()

    fun isNormalized(tolerance: Double = 0.01) = (lengthSquared() - 1.0).absoluteValue < tolerance

    fun isZero() = x == 0.0 && y == 0.0 && z == 0.0

    fun reciprocal() = map { 1.0 / it }

    fun distanceSq(other: SkyHanniVector) = (other - this).lengthSquared()

    fun distance(other: SkyHanniVector) = sqrt(distanceSq(other))

    fun distanceSqIgnoreY(other: SkyHanniVector): Double {
        val dx = other.x - x
        val dz = other.z - z
        return dx * dx + dz * dz
    }

    fun distanceIgnoreY(other: SkyHanniVector) = sqrt(distanceSqIgnoreY(other))

    fun distanceChebyshevIgnoreY(other: SkyHanniVector) = max(abs(x - other.x), abs(z - other.z))

    fun equalsIgnoreY(other: SkyHanniVector) = x == other.x && z == other.z

    fun minComponent() = min(x, min(y, z))

    fun maxComponent() = max(x, max(y, z))

    fun elementwiseMin(other: SkyHanniVector) = zip(other) { a, b -> min(a, b) }

    fun elementwiseMax(other: SkyHanniVector) = zip(other) { a, b -> max(a, b) }

    fun floor() = map { floor(it) }

    fun ceil() = map { ceil(it) }

    fun blockCenter() = floor().addHalf()

    fun roundTo(precision: Int) = map { it.roundTo(precision) }

    fun lerp(other: SkyHanniVector, factor: Double) = this + (other - this) * factor

    fun midpoint(other: SkyHanniVector) = lerp(other, 0.5)

    fun rotateAroundY(theta: Double): SkyHanniVector {
        val c = cos(theta)
        val s = sin(theta)
        return SkyHanniVector(x * c + z * s, y, -x * s + z * c)
    }

    fun toDoubleArray() = doubleArrayOf(x, y, z)

    fun toVec3() = Vec3(x, y, z)

    fun toBlockPos(): BlockPos = BlockPos.containing(x, y, z)

    fun toChatFormat() = "x: ${x.toInt()}, y: ${y.toInt()}, z: ${z.toInt()}"

    fun toLocalFormat() = "${x.toInt()} ${y.toInt()} ${z.toInt()}"

    fun toCleanString(separator: String = ", ") = listOf(x, y, z).joinToString(separator)

    fun asStoredString() = "$x:$y:$z"

    override fun equals(other: Any?) =
        this === other || other is SkyHanniVector && x == other.x && y == other.y && z == other.z

    override fun hashCode(): Int {
        var result = (x + 0.0).hashCode()
        result = 31 * result + (y + 0.0).hashCode()
        result = 31 * result + (z + 0.0).hashCode()
        return result
    }

    @Deprecated("Use add with Double arguments", ReplaceWith("add(x.toDouble(), y.toDouble(), z.toDouble())"))
    fun add(x: Int = 0, y: Int = 0, z: Int = 0) = add(x.toDouble(), y.toDouble(), z.toDouble())

    @Deprecated("Use dot", ReplaceWith("this dot other"))
    fun dotProduct(other: SkyHanniVector) = this dot other

    @Deprecated("Use cross", ReplaceWith("this cross other"))
    fun crossProduct(other: SkyHanniVector) = this cross other

    @Deprecated("Use normalized", ReplaceWith("normalized()"))
    fun normalize() = normalized()

    @Deprecated("Use reciprocal", ReplaceWith("reciprocal()"))
    fun inverse() = reciprocal()

    @Deprecated("Use minComponent", ReplaceWith("minComponent()"))
    fun min() = minComponent()

    @Deprecated("Use maxComponent", ReplaceWith("maxComponent()"))
    fun max() = maxComponent()

    @Deprecated("Use elementwiseMin", ReplaceWith("elementwiseMin(other)"))
    fun minOfEachElement(other: SkyHanniVector) = elementwiseMin(other)

    @Deprecated("Use elementwiseMax", ReplaceWith("elementwiseMax(other)"))
    fun maxOfEachElement(other: SkyHanniVector) = elementwiseMax(other)

    @Deprecated("Use formatRounded from VectorFormat", ReplaceWith("formatRounded(accuracy, splitChar)"))
    fun printWithAccuracy(accuracy: Int, splitChar: String = " ") = formatRounded(accuracy, splitChar)

    @Deprecated("Vectors are immutable, copying is never needed", ReplaceWith("this"))
    fun clone() = this

    @Deprecated("Use floor", ReplaceWith("floor()"))
    fun roundToBlock() = floor()

    @Deprecated("Use lerp", ReplaceWith("lerp(other, factor)"))
    fun slope(other: SkyHanniVector, factor: Double) = lerp(other, factor)

    @Deprecated("Use lerp", ReplaceWith("lerp(other, factor)"))
    fun interpolate(other: SkyHanniVector, factor: Double) = lerp(other, factor)

    @Deprecated("Use midpoint", ReplaceWith("midpoint(other)"))
    fun middle(other: SkyHanniVector) = midpoint(other)

    @Deprecated("Use boxAround from VectorBounds", ReplaceWith("boxAround(expand)"))
    fun boundingCenter(expand: Double): AABB = boxAround(expand)

    @Deprecated("Use boxFromOrigin from VectorBounds", ReplaceWith("boxFromOrigin(offX, offY, offZ)"))
    fun boundingToOffset(offX: Double, offY: Double, offZ: Double): AABB = boxFromOrigin(offX, offY, offZ)

    @Deprecated("Use boxTo from VectorBounds", ReplaceWith("boxTo(other)"))
    fun axisAlignedTo(other: SkyHanniVector): AABB = boxTo(other)

    @Deprecated("Use times", ReplaceWith("this * scalar"))
    fun scale(scalar: Double) = this * scalar

    @Deprecated("Use unaryMinus", ReplaceWith("-this"))
    fun negated() = -this

    @Deprecated("Use rotateAroundY", ReplaceWith("rotateAroundY(theta)"))
    fun rotateXZ(theta: Double) = rotateAroundY(theta)

    @Deprecated("Use nearestPointOnSegment from VectorBounds", ReplaceWith("nearestPointOnSegment(startPos, endPos)"))
    fun nearestPointOnLine(startPos: SkyHanniVector, endPos: SkyHanniVector) = nearestPointOnSegment(startPos, endPos)

    companion object {
        val ZERO = SkyHanniVector(0.0, 0.0, 0.0)
        val X_AXIS = SkyHanniVector(1.0, 0.0, 0.0)
        val Y_AXIS = SkyHanniVector(0.0, 1.0, 0.0)
        val Z_AXIS = SkyHanniVector(0.0, 0.0, 1.0)
    }
}
