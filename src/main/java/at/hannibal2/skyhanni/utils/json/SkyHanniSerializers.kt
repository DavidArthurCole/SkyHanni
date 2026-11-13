package at.hannibal2.skyhanni.utils.json

import at.hannibal2.skyhanni.data.IslandType
import at.hannibal2.skyhanni.data.jsonobjects.other.NbtBoolean
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe.NeuAbstractRecipe
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe.NeuRecipeComponent
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.recipe.NeuRecipeType
import at.hannibal2.skyhanni.data.model.SkyblockStat
import at.hannibal2.skyhanni.data.model.SkyblockStatList
import at.hannibal2.skyhanni.data.model.graph.Graph
import at.hannibal2.skyhanni.features.fishing.trophy.TrophyRarity
import at.hannibal2.skyhanni.features.garden.CropType
import at.hannibal2.skyhanni.features.garden.pests.PestType
import at.hannibal2.skyhanni.features.misc.update.ModrinthVersionType
import at.hannibal2.skyhanni.test.command.ErrorManager
import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.NeuInternalName
import at.hannibal2.skyhanni.utils.NeuInternalName.Companion.toInternalName
import at.hannibal2.skyhanni.utils.NeuItems
import at.hannibal2.skyhanni.utils.SafeItemStack
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.SimpleTimeMark.Companion.asTimeMark
import at.hannibal2.skyhanni.utils.Stopwatch
import at.hannibal2.skyhanni.utils.StringUtils
import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toGson
import at.hannibal2.skyhanni.utils.json.JsonTreeBridge.toKotlinx
import at.hannibal2.skyhanni.utils.system.MCVersion
import at.hannibal2.skyhanni.utils.system.ModVersion
import at.hannibal2.skyhanni.utils.tracker.SkyHanniTracker
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.mojang.serialization.JsonOps
import io.github.notenoughupdates.moulconfig.ChromaColour
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import java.time.LocalDate
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object UuidSerializer : StringSerializer<UUID>("UUID", UUID::toString, StringUtils::parseUUID)

object LocalDateSerializer : StringSerializer<LocalDate>("LocalDate", LocalDate::toString, LocalDate::parse)

object DurationMillisSerializer : LongSerializer<Duration>("DurationMillis", { it.inWholeMilliseconds }, { it.milliseconds })

object SimpleTimeMarkSerializer : LongSerializer<SimpleTimeMark>("SimpleTimeMark", { it.toMillis() }, { it.asTimeMark() })

object StopwatchSerializer : StringSerializer<Stopwatch>(
    "Stopwatch",
    { it.getDuration().inWholeMilliseconds.toString() },
    { raw -> raw.toLongOrNull()?.milliseconds?.let { Stopwatch(it) } ?: error("Could not parse Stopwatch duration from '$raw'") },
)

object NbtBooleanSerializer : StringSerializer<NbtBoolean>("NbtBoolean", { it.asString() }, { NbtBoolean.fromString(it) })

object LorenzVecSerializer : StringSerializer<LorenzVec>("LorenzVec", { it.asStoredString() }, { LorenzVec.decodeFromString(it) })

object NeuInternalNameSerializer : StringSerializer<NeuInternalName>("NeuInternalName", { it.asString() }, { it.toInternalName() })

object ModVersionSerializer : StringSerializer<ModVersion>("ModVersion", ModVersion::asString, { ModVersion.fromString(it) })

object MCVersionSerializer : StringSerializer<MCVersion>("MCVersion", MCVersion::asString, { MCVersion.fromString(it) })

object ItemStackSerializer : StringSerializer<SafeItemStack>("ItemStack", { NeuItems.saveNBTData(it) }, { NeuItems.loadNBTData(it) })

object NeuRecipeComponentSerializer : StringSerializer<NeuRecipeComponent>(
    "NeuRecipeComponent",
    { it.toJsonString() },
    { NeuRecipeComponent.fromJsonStringOrNull(it) ?: NeuRecipeComponent.EMPTY },
)

object NeuRecipeTypeSerializer : StringSerializer<NeuRecipeType>(
    "NeuRecipeType",
    { it.neuRepoId },
    { NeuRecipeType.fromNeuId(it) },
)

object TrophyRaritySerializer : StringSerializer<TrophyRarity>(
    "TrophyRarity",
    { it.name },
    { TrophyRarity.getByName(it) ?: error("Could not parse TrophyRarity from '$it'") },
)

object SkyblockStatSerializer : StringSerializer<SkyblockStat>(
    "SkyblockStat",
    { it.name.lowercase() },
    { SkyblockStat.getValue(it.uppercase()) },
)

object LorenzRaritySerializer : EnumNameSerializer<LorenzRarity>("LorenzRarity", LorenzRarity.entries)

object IslandTypeSerializer : EnumNameSerializer<IslandType>("IslandType", IslandType.entries, IslandType.UNKNOWN)

object CropTypeSerializer : EnumNameSerializer<CropType>("CropType", CropType.entries, CropType.WHEAT)

object PestTypeSerializer : EnumNameSerializer<PestType>("PestType", PestType.entries, PestType.UNKNOWN)

object ModrinthVersionTypeSerializer : EnumNameSerializer<ModrinthVersionType>("ModrinthVersionType", ModrinthVersionType.entries)

object TrackerDisplayModeSerializer : EnumNameSerializer<SkyHanniTracker.DefaultDisplayMode>(
    "TrackerDisplayMode",
    SkyHanniTracker.DefaultDisplayMode.entries,
)

object GraphSerializer : GsonAdapterSerializer<Graph>("Graph", Graph.typeAdapter)

object NeuAbstractRecipeSerializer : JsonContentPolymorphicSerializer<NeuAbstractRecipe>(NeuAbstractRecipe::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<NeuAbstractRecipe> {
        val id = element.jsonObject["type"]?.jsonPrimitive?.content
        return id?.let { NeuRecipeType.fromNeuIdOrNull(it) }?.recipeSerializer ?: throw SerializationException("Unknown recipe type: $id")
    }
}

object ComponentSerializer : GsonAdapterSerializer<Component>(
    "Component",
    object : TypeAdapter<Component>() {
        override fun write(out: JsonWriter, value: Component) {
            out.jsonValue(ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, value).getOrThrow().toString())
        }

        override fun read(reader: JsonReader): Component =
            ComponentSerialization.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader)).getOrThrow().first
    },
) {
    override fun toTree(value: Component): JsonElement =
        ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, value).getOrThrow().toKotlinx()

    override fun fromTree(element: JsonElement): Component =
        ComponentSerialization.CODEC.decode(JsonOps.INSTANCE, element.toGson()).getOrThrow().first
}

object ChromaColourSerializer : GsonAdapterSerializer<ChromaColour>(
    "ChromaColor",
    object : TypeAdapter<ChromaColour>() {
        override fun write(out: JsonWriter, value: ChromaColour) {
            out.value(value.toLegacyString())
        }

        override fun read(reader: JsonReader): ChromaColour {
            if (reader.peek() == JsonToken.STRING) return ChromaColour.forLegacyString(reader.nextString())
            val fields = JsonParser.parseReader(reader).asJsonObject
            return ChromaColour(
                fields["hue"].asFloat,
                fields["saturation"].asFloat,
                fields["brightness"].asFloat,
                fields["timeForFullRotationInMillis"].asInt,
                fields["alpha"].asInt,
            )
        }
    },
)

object SkyblockStatListSerializer : GsonAdapterSerializer<SkyblockStatList>(
    "SkyblockStatList",
    object : TypeAdapter<SkyblockStatList>() {
        override fun write(out: JsonWriter, value: SkyblockStatList) {
            out.beginObject()
            for ((stat, amount) in value) out.name(stat.name.lowercase()).value(amount)
            out.endObject()
        }

        override fun read(reader: JsonReader): SkyblockStatList = SkyblockStatList().apply {
            reader.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName().uppercase()
                val amount = reader.nextDouble()
                val stat = SkyblockStat.getValueOrNull(name) ?: run {
                    ErrorManager.logErrorStateWithData(
                        "Unknown stat: '$name'",
                        "Stat list could not parse stat",
                        "failed" to name,
                        betaOnly = true,
                    )
                    continue
                }
                this[stat] = amount
            }
            reader.endObject()
        }
    },
)
