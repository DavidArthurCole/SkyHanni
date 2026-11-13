package at.hannibal2.skyhanni.test.utils

import at.hannibal2.skyhanni.data.jsonobjects.repo.AnitaUpgradeCostsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ArrowTypeJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.AttributeGoodRollsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.BazaarJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.BeltsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.BingoJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.BingoRanksJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.CarryTrackerJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ChangedChatErrorsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.CommunityTodosJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ContributorsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.CrimsonIsleReputationJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DanceRoomInstructionsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DianaDropsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DianaJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DisabledApiJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DisabledEventsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DragonProfitTrackerItemsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.DungeonHubRacesJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.EnchantedClockJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.EnforcedConfigValuesJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.EnigmaSoulsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ErrorManagerJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.EventWaypointsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.EventsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ExcludedSeaCreatureAreasJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ExperimentsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.FameRankJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.FishingProfitItemsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ForcedRepoPerksJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.GardenJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.GhostDropsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.HideNotClickableItemsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.HoppityEggLocationsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.HoppityRabbitTexturesJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.InfernoMinionFuelsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.IslandGraphSettingsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.IslandTypeJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ItemAliases
import at.hannibal2.skyhanni.data.jsonobjects.repo.ItemDiscountsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ItemDisplayNamesJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ItemsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.LaunchersJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.LividSolverJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.LocationFixJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.MaxwellPowersJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.MetalDetectorChestsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.MiningJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.MinionDropsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.MinionXPJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ModGuiSwitcherJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.MultiFilterJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.ParkourJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.PetsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.PlayerChatFilterJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.RareCropDropsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.RescueParkourJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.RiftEffigiesJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.RiftWoodenButtonsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.SeaCreatureJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.SlayerProfitTrackerItemsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.TreeGiftBonusDropsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.TrophyFishJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.VipVisitsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.WarpsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.WikiJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.WiltedBerberisLocationsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.AbiphoneContactInfo
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuAnimatedSkullsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuAttributeShardJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuEssenceCostJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuEssenceShopJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuGeorgeJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuHoppityJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuItemJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuMiscJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuPetNumsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuPetsJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuRNGScore
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuReforgeJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuSacksJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuSkillLevelJson
import at.hannibal2.skyhanni.data.model.graph.Graph
import at.hannibal2.skyhanni.features.misc.items.enchants.EnchantsJson
import at.hannibal2.skyhanni.features.slayer.RemainingSlayerKills
import at.hannibal2.skyhanni.utils.json.SkyHanniJson
import at.hannibal2.skyhanni.utils.repopatterns.RepoPatternDump
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test
import java.io.File

class RepoDataDecodeTest {

    private val root = System.getProperty("skyhanni.test.repoRoot")?.let(::File)
        ?: listOf("skyhanni", "../versions/26.2/run/skyhanni", "../versions/26.1/run/skyhanni").map(::File)
            .firstOrNull { it.resolve("shrepo/constants").isDirectory }
        ?: File("skyhanni")
    private val failures = mutableListOf<String>()

    private inline fun <reified T : Any> check(repo: String, constant: String) {
        val file = root.resolve("$repo/constants/$constant.json").takeIf { it.isFile } ?: return
        runCatching {
            checkNotNull(SkyHanniJson.decodeOrNull<T> { file.readText() }) { "no serializer resolved" }
        }.onFailure { failures += "$repo/$constant: ${it.message?.lineSequence()?.firstOrNull()}" }
    }

    private inline fun <reified T : Any> sh(constant: String) = check<T>("shrepo", constant)
    private inline fun <reified T : Any> neu(constant: String) = check<T>("neurepo", constant)

    @Test
    fun `SkyHanni repo constants decode`() {
        assumeTrue(root.resolve("shrepo/constants").isDirectory)
        sh<EnforcedConfigValuesJson>("misc/EnforcedConfigValues")
        sh<DisabledApiJson>("misc/DisabledApi")
        sh<ErrorManagerJson>("ErrorManager")
        sh<ChangedChatErrorsJson>("ChangedChatErrors")
        sh<ItemDiscountsJson>("misc/ItemDiscounts")
        sh<ItemsJson>("Items")
        sh<LaunchersJson>("Launchers")
        sh<MultiFilterJson>("IgnoredItems")
        sh<ItemAliases>("ItemAliases")
        sh<ItemDisplayNamesJson>("ItemDisplayNames")
        sh<PetsJson>("Pets")
        sh<ExperimentsJson>("ExperimentationTable")
        sh<DisabledEventsJson>("DisabledEvents")
        sh<Map<String, String>>("Skulls")
        sh<MinionXPJson>("MinionXP")
        sh<MinionDropsJson>("MinionDrops")
        sh<InfernoMinionFuelsJson>("InfernoMinionFuels")
        sh<SlayerProfitTrackerItemsJson>("SlayerProfitTrackerItems")
        sh<RepoPatternDump>("regexesModern")
        sh<Graph>("island_graphs/GLACITE_TUNNELS")
        sh<WikiJson>("misc/Wiki")
        sh<WarpsJson>("Warps")
        sh<PlayerChatFilterJson>("PlayerChatFilter")
        sh<VipVisitsJson>("VipVisits")
        sh<GhostDropsJson>("GhostDrops")
        sh<DragonProfitTrackerItemsJson>("DragonProfitTrackerItems")
        sh<EventsJson>("Events")
        sh<EnigmaSoulsJson>("EnigmaSouls")
        sh<BingoRanksJson>("BingoRanks")
        sh<BingoJson>("Bingo")
        sh<DianaDropsJson>("DianaDrops")
        sh<DianaJson>("events/Diana")
        sh<LividSolverJson>("dungeons/LividSolver")
        sh<ParkourJson>("DeepCavernsParkour")
        sh<EventWaypointsJson>("EventWaypoints")
        sh<DungeonHubRacesJson>("DungeonHubRaces")
        sh<HoppityEggLocationsJson>("HoppityEggLocations")
        sh<HoppityRabbitTexturesJson>("HoppityRabbitTextures")
        sh<MetalDetectorChestsJson>("MetalDetectorChests")
        sh<ParkourJson>("rift/RiftRace")
        sh<RiftEffigiesJson>("RiftEffigies")
        sh<GardenJson>("Garden")
        sh<RiftWoodenButtonsJson>("rift/RiftWoodenButtons")
        sh<WiltedBerberisLocationsJson>("rift/WiltedBerberisLocations")
        sh<TreeGiftBonusDropsJson>("foraging/TreeGiftBonusDrops")
        sh<ParkourJson>("RiftTubulator")
        sh<ParkourJson>("RiftUpsideDownParkour")
        sh<ParkourJson>("RiftLavaMazeParkour")
        sh<DanceRoomInstructionsJson>("DanceRoomInstructions")
        sh<RareCropDropsJson>("ArmorDrops")
        sh<AnitaUpgradeCostsJson>("AnitaUpgradeCosts")
        sh<TrophyFishJson>("TrophyFish")
        sh<RescueParkourJson>("RescueMissionWaypoints")
        sh<ExcludedSeaCreatureAreasJson>("fishing/ExcludedSeaCreatureAreas")
        sh<FishingProfitItemsJson>("FishingProfitItems")
        sh<Map<String, SeaCreatureJson>>("SeaCreatures")
        sh<HideNotClickableItemsJson>("HideNotClickableItems")
        sh<BeltsJson>("Belts")
        sh<CrimsonIsleReputationJson>("CrimsonIsleReputation")
        sh<List<String>>("QuickCraftableItems")
        sh<AttributeGoodRollsJson>("AttributeGoodRolls")
        sh<BazaarJson>("Bazaar")
        sh<CarryTrackerJson>("CarryTracker")
        sh<ContributorsJson>("ContributorList")
        sh<CommunityTodosJson>("community/CommunityTodos")
        sh<EnchantedClockJson>("misc/EnchantedClock")
        sh<ModGuiSwitcherJson>("ModGuiSwitcher")
        sh<ForcedRepoPerksJson>("misc/ForcedRepoPerks")
        sh<Map<String, List<String>>>("IslandTypeTags")
        sh<IslandTypeJson>("misc/IslandType")
        sh<IslandGraphSettingsJson>("misc/IslandGraphSettings")
        sh<FameRankJson>("FameRank")
        sh<MaxwellPowersJson>("MaxwellPowers")
        sh<LocationFixJson>("LocationFix")
        sh<MiningJson>("Mining")
        sh<ArrowTypeJson>("ArrowTypes")
        sh<EnchantsJson>("Enchants")
        sh<RemainingSlayerKills.SlayerData>("Slayer")
        assertTrue(failures.isEmpty()) { failures.joinToString("\n") }
    }

    @Test
    fun `NEU repo constants decode`() {
        assumeTrue(root.resolve("neurepo/constants").isDirectory)
        neu<NeuSkillLevelJson>("leveling")
        neu<Map<String, NeuReforgeJson>>("reforgestones")
        neu<Map<String, NeuReforgeJson>>("reforges")
        neu<Map<String, NeuEssenceCostJson>>("essencecosts")
        neu<NeuPetsJson>("pets")
        neu<NeuAnimatedSkullsJson>("animatedskulls")
        neu<NeuPetNumsJson>("petnums")
        neu<NeuRNGScore>("rngscore")
        neu<NeuHoppityJson>("hoppity")
        neu<Map<String, Map<String, NeuEssenceShopJson>>>("essenceshops")
        neu<NeuMiscJson>("carnivalshops")
        neu<NeuAttributeShardJson>("attribute_shards")
        neu<NeuGeorgeJson>("george")
        neu<Map<String, AbiphoneContactInfo>>("abiphone")
        neu<NeuSacksJson>("sacks")
        assertTrue(failures.isEmpty()) { failures.joinToString("\n") }
    }

    @Test
    fun `NEU repo items decode`() {
        val items = root.resolve("neurepo/items").listFiles { file -> file.extension == "json" }
        assumeTrue(!items.isNullOrEmpty())
        for (file in items.orEmpty()) {
            runCatching {
                SkyHanniJson.json.decodeFromString<NeuItemJson>(file.readText())
            }.onFailure { failures += "${file.name}: ${it.message?.lineSequence()?.firstOrNull()}" }
        }
        assertTrue(failures.isEmpty()) { "${failures.size} items failed:\n" + failures.take(40).joinToString("\n") }
    }
}
