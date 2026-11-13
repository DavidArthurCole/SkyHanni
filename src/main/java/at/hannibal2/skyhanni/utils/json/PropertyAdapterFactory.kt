package at.hannibal2.skyhanni.utils.json

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.github.notenoughupdates.moulconfig.observer.Property
import java.lang.reflect.ParameterizedType

object PropertyAdapterFactory : TypeAdapterFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (type.rawType != Property::class.java) return null
        val valueType = (type.type as? ParameterizedType)?.actualTypeArguments?.firstOrNull() ?: return null
        return Adapter(gson.getAdapter(TypeToken.get(valueType)) as TypeAdapter<Any?>) as TypeAdapter<T>
    }

    private class Adapter(private val valueAdapter: TypeAdapter<Any?>) : TypeAdapter<Property<Any?>>() {
        override fun write(out: JsonWriter, value: Property<Any?>) {
            valueAdapter.write(out, value.get())
        }

        override fun read(reader: JsonReader): Property<Any?> = Property.of(valueAdapter.read(reader))
    }
}
