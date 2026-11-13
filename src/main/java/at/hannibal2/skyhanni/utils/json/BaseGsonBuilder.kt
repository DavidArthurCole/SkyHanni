package at.hannibal2.skyhanni.utils.json

import at.hannibal2.skyhanni.utils.system.PlatformUtils
import com.google.gson.GsonBuilder

object BaseGsonBuilder {
    fun gson(): GsonBuilder = GsonBuilder()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .serializeSpecialFloatingPointValues()
        .registerTypeAdapterFactory(ShadowedFieldAdapterFactory)
        .registerTypeAdapterFactory(PropertyAdapterFactory)
        .registerTypeAdapterFactory(SkyblockStatMapTypeAdapterFactory)
        .registerTypeAdapterFactory(KSerializerTypeAdapterFactory)
        .enableComplexMapKeySerialization()

    fun lenientGson(): GsonBuilder {
        if (PlatformUtils.isDevEnvironment) return gson()
        return gson()
            .registerTypeAdapterFactory(SkippingTypeAdapterFactory)
            .registerTypeAdapterFactory(ListEnumSkippingTypeAdapterFactory)
    }
}
