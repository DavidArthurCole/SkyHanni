package at.hannibal2.skyhanni.test.vector

import at.hannibal2.skyhanni.utils.json.BaseGsonBuilder
import at.hannibal2.skyhanni.utils.vector.SkyHanniBlockVector
import at.hannibal2.skyhanni.utils.vector.SkyHanniVector
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VectorSerializationTest {

    private val gson = BaseGsonBuilder.gson().create()

    private class Holder(
        @Expose val position: SkyHanniVector,
        @Expose val block: SkyHanniBlockVector,
        @Expose val positions: Set<SkyHanniVector>,
        @Expose val byPosition: Map<SkyHanniVector, Int>,
        @Expose val byBlock: Map<SkyHanniBlockVector, Int>,
    )

    @Test
    fun `vectors serialize as colon separated strings`() {
        val json = gson.toJson(SkyHanniVector(1.5, -2.0, 3.0))
        assertEquals("\"1.5:-2.0:3.0\"", json)
        assertEquals("\"1:-2:3\"", gson.toJson(SkyHanniBlockVector(1, -2, 3)))
    }

    @Test
    fun `legacy LorenzVec strings deserialize into both types`() {
        assertEquals(SkyHanniVector(1.0, 2.0, 3.0), gson.fromJson("\"1.0:2.0:3.0\"", SkyHanniVector::class.java))
        assertEquals(SkyHanniBlockVector(1, 2, 3), gson.fromJson("\"1.0:2.0:3.0\"", SkyHanniBlockVector::class.java))
    }

    @Test
    fun `nested fields and map keys round trip`() {
        val holder = Holder(
            position = SkyHanniVector(0.5, 64.0, -0.5),
            block = SkyHanniBlockVector(0, 64, -1),
            positions = setOf(SkyHanniVector(1.0, 1.0, 1.0), SkyHanniVector(2.0, 2.0, 2.0)),
            byPosition = mapOf(SkyHanniVector(-1.0, 0.0, 1.0) to 7),
            byBlock = mapOf(SkyHanniBlockVector(-1, 0, 1) to 9),
        )
        val json = gson.toJson(holder)
        val back = gson.fromJson(json, Holder::class.java)
        assertEquals(holder.position, back.position)
        assertEquals(holder.block, back.block)
        assertEquals(holder.positions, back.positions)
        assertEquals(holder.byPosition, back.byPosition)
        assertEquals(holder.byBlock, back.byBlock)
    }

    @Test
    fun `block vector map keys stored by older versions as doubles still load`() {
        val type = object : TypeToken<Map<SkyHanniBlockVector, Int>>() {}.type
        val loaded: Map<SkyHanniBlockVector, Int> = gson.fromJson("{\"-1.0:0.0:1.0\": 9}", type)
        assertEquals(mapOf(SkyHanniBlockVector(-1, 0, 1) to 9), loaded)
    }
}
