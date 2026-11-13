package at.hannibal2.skyhanni.test.utils

import at.hannibal2.skyhanni.utils.json.KSerializerTypeAdapterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class KSerializerBridgeTest {

    private val gson = GsonBuilder()
        .registerTypeAdapterFactory(KSerializerTypeAdapterFactory)
        .create()

    @Serializable
    private data class Simple(val name: String, val count: Int)

    @Serializable
    private data class WithRename(
        @SerialName("full_name") val name: String,
        val age: Int,
    )

    @Serializable
    private data class WithDefault(val name: String, val count: Int = 42)

    @Serializable
    private data class WithNullable(val value: String? = null)

    @Serializable
    private data class Inner(val x: Int)

    @Serializable
    private data class Outer(val inner: Inner, val label: String)

    @Serializable
    private data class WithDerived(val lowest: Long, val lowestVolume: Int) {
        val combined: String by lazy { "$lowest/$lowestVolume" }
    }

    private data class NotAnnotated(val x: Int)

    @Test
    fun `basic deserialization reads all fields`() {
        val result = gson.fromJson("""{"name":"hello","count":5}""", Simple::class.java)
        assertEquals(Simple("hello", 5), result)
    }

    @Test
    fun `SerialName deserialization reads renamed key`() {
        val result = gson.fromJson("""{"full_name":"Alice","age":30}""", WithRename::class.java)
        assertEquals(WithRename("Alice", 30), result)
    }

    @Test
    fun `missing optional field uses Kotlin default value`() {
        val result = gson.fromJson("""{"name":"test"}""", WithDefault::class.java)
        assertEquals(WithDefault("test", 42), result)
    }

    @Test
    fun `unknown keys are ignored`() {
        val result = gson.fromJson("""{"name":"test","count":1,"extra":true}""", WithDefault::class.java)
        assertEquals(WithDefault("test", 1), result)
    }

    @Test
    fun `null value deserializes to null`() {
        val result = gson.fromJson("""{"value":null}""", WithNullable::class.java)
        assertNull(result?.value)
    }

    @Test
    fun `nested serializable type deserializes correctly`() {
        val result = gson.fromJson("""{"inner":{"x":7},"label":"hi"}""", Outer::class.java)
        assertEquals(Outer(Inner(7), "hi"), result)
    }

    @Test
    fun `derived body properties are initialized`() {
        val result = gson.fromJson("""{"lowest":100,"lowestVolume":5}""", WithDerived::class.java)
        assertEquals("100/5", result.combined)
    }

    @Test
    fun `non-annotated class returns null from factory`() {
        val adapter = KSerializerTypeAdapterFactory.create(gson, TypeToken.get(NotAnnotated::class.java))
        assertNull(adapter)
    }

    @Test
    fun `basic serialization emits all fields`() {
        val result = gson.toJson(Simple("hello", 5))
        assertEquals("""{"name":"hello","count":5}""", result)
    }

    @Test
    fun `SerialName serialization uses annotation key`() {
        val result = gson.toJson(WithRename("Alice", 30))
        assertEquals("""{"full_name":"Alice","age":30}""", result)
    }

    @Test
    fun `default values are written`() {
        val result = gson.toJson(WithDefault("test"))
        assertEquals("""{"name":"test","count":42}""", result)
    }

    @Test
    fun `null value is omitted`() {
        val result = gson.toJson(WithNullable(null))
        assertEquals("{}", result)
    }

    @Test
    fun `nested serializable type serializes correctly`() {
        val result = gson.toJson(Outer(Inner(7), "hi"))
        assertEquals("""{"inner":{"x":7},"label":"hi"}""", result)
    }
}
