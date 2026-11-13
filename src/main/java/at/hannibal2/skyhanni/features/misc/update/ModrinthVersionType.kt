package at.hannibal2.skyhanni.features.misc.update

import at.hannibal2.skyhanni.utils.json.ModrinthVersionTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ModrinthVersionTypeSerializer::class)
@Suppress("unused")
enum class ModrinthVersionType(vararg val allowedUpdateStreams: String) {
    RELEASE("full", "pre"),
    BETA("pre"),
    ALPHA,
}
