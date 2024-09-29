package at.hannibal2.skyhanni.features.inventory

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.data.ProfileStorageData
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuCarnivalTokenCostJson
import at.hannibal2.skyhanni.data.jsonobjects.repo.neu.NeuMiscJson
import at.hannibal2.skyhanni.events.InventoryCloseEvent
import at.hannibal2.skyhanni.events.InventoryFullyOpenedEvent
import at.hannibal2.skyhanni.events.InventoryOpenEvent
import at.hannibal2.skyhanni.events.InventoryUpdatedEvent
import at.hannibal2.skyhanni.events.NeuRepositoryReloadEvent
import at.hannibal2.skyhanni.events.render.gui.ReplaceItemEvent
import at.hannibal2.skyhanni.features.inventory.EssenceShopHelper.essenceUpgradePattern
import at.hannibal2.skyhanni.features.inventory.EssenceShopHelper.maxedUpgradeLorePattern
import at.hannibal2.skyhanni.skyhannimodule.SkyHanniModule
import at.hannibal2.skyhanni.test.command.ErrorManager
import at.hannibal2.skyhanni.utils.CollectionUtils.addIfNotNull
import at.hannibal2.skyhanni.utils.ItemUtils.createItemStack
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import at.hannibal2.skyhanni.utils.LorenzUtils
import at.hannibal2.skyhanni.utils.NEUInternalName.Companion.asInternalName
import at.hannibal2.skyhanni.utils.NEUItems.getItemStack
import at.hannibal2.skyhanni.utils.NumberUtil.addSeparators
import at.hannibal2.skyhanni.utils.NumberUtil.formatInt
import at.hannibal2.skyhanni.utils.NumberUtil.romanToDecimal
import at.hannibal2.skyhanni.utils.RegexUtils.firstMatcher
import at.hannibal2.skyhanni.utils.RegexUtils.groupOrNull
import at.hannibal2.skyhanni.utils.RegexUtils.matchMatcher
import at.hannibal2.skyhanni.utils.RegexUtils.matches
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@SkyHanniModule
object CarnivalShopHelper {

    // Where the informational item stack will be placed in the GUI
    private const val CUSTOM_STACK_LOCATION = 8

    private var repoEventShops = mutableListOf<EventShop>()
    private var currentProgress: EventShopProgress? = null
    private var currentEventType: String = ""
    private var tokensOwned: Int = 0
    private var tokensNeeded: Int = 0
    private var overviewInfoItemStack: ItemStack? = null
    private var shopSpecificInfoItemStack: ItemStack? = null

    /**
     * REGEX-TEST: Your Tokens: §a1,234,567
     * REGEX-TEST: Your Tokens: §a0
     */
    private val currentTokenCountPattern by patternGroup.pattern(
        "carnival.tokens.current",
        ".*§7Your Tokens: §a(?<tokens>[\\d,]*)"
    )

    /**
     * REGEX-TEST: §8Souvenir Shop
     */
    private val souvenirShopInventoryPattern by patternGroup.pattern(
        "carnival.souvenirshop",
        "(?:§.)*Souvenir Shop"
    )

    data class EventShop(val shopName: String, val upgrades: List<NeuCarnivalTokenCostJson>)
    data class EventShopUpgradeStatus(
        val upgradeName: String,
        val currentLevel: Int,
        val maxLevel: Int,
        val remainingCosts: MutableList<Int>,
    )

    data class EventShopProgress(val shopName: String, val purchasedUpgrades: Map<String, Int>) {
        private val eventShop = repoEventShops.find { it.shopName.equals(shopName, ignoreCase = true) }
        val remainingUpgrades: MutableList<EventShopUpgradeStatus> = eventShop?.upgrades?.map {
            val purchasedAmount = purchasedUpgrades[it.name] ?: 0
            EventShopUpgradeStatus(
                it.name,
                currentLevel = purchasedAmount,
                maxLevel = it.costs.count(),
                remainingCosts = it.costs.drop(purchasedAmount).toMutableList(),
            )
        }.orEmpty().toMutableList()
        val remainingUpgradeSum = remainingUpgrades.sumOf { it.remainingCosts.sum() }
        val nonRepoUpgrades = purchasedUpgrades.filter { purchasedUpgrade ->
            eventShop?.upgrades?.none { it.name.equals(purchasedUpgrade.key, ignoreCase = true) } == true
        }
    }

    @SubscribeEvent
    fun replaceItem(event: ReplaceItemEvent) {
        if (!isEnabled() || repoEventShops.isEmpty() || event.slot != CUSTOM_STACK_LOCATION) return
        tryReplaceShopSpecificStack(event)
        tryReplaceOverviewStack(event)
    }

    private fun tryReplaceShopSpecificStack(event: ReplaceItemEvent) {
        if (currentProgress == null || !repoEventShops.any { it.shopName.equals(event.inventory.name, ignoreCase = true) }) return
        shopSpecificInfoItemStack.let { event.replace(it) }
    }

    private fun tryReplaceOverviewStack(event: ReplaceItemEvent) {
        if (!souvenirShopInventoryPattern.matches(event.inventory.name)) return
        overviewInfoItemStack.let { event.replace(it) }
    }

    @SubscribeEvent
    fun onNeuRepoReload(event: NeuRepositoryReloadEvent) {
        val repoTokenShops = event.readConstant<NeuMiscJson>("carnivalshops").carnivalTokenShops
        repoEventShops = repoTokenShops.map { (key, value) ->
            EventShop(key.replace("_", " "), value.values.toMutableList())
        }.toMutableList()
        checkSavedProgress()
        regenerateOverviewItemStack()
    }

    @SubscribeEvent
    fun onInventoryClose(event: InventoryCloseEvent) {
        currentProgress = null
        currentEventType = ""
        tokensOwned = 0
        tokensNeeded = 0
    }

    @SubscribeEvent
    fun onInventoryOpen(event: InventoryFullyOpenedEvent) {
        processInventoryEvent(event)
    }

    @SubscribeEvent
    fun onInventoryUpdated(event: InventoryUpdatedEvent) {
        processInventoryEvent(event)
    }

    private fun checkSavedProgress() {
        ProfileStorageData.profileSpecific?.carnival?.carnivalShopProgress?.let { storage ->
            storage.keys.forEach { key ->
                if (!repoEventShops.any { shop -> shop.shopName.equals(key, ignoreCase = true) }) {
                    storage.remove(key)
                }
            }
        }
    }

    private fun saveProgress() {
        ProfileStorageData.profileSpecific?.carnival?.carnivalShopProgress?.let { storage ->
            currentProgress?.let { progress ->
                storage[progress.shopName] = progress.purchasedUpgrades
            }
        }
    }

    private fun MutableList<String>.addNeededRemainingTokens(cost: Int, extraFormatting: String? = null) {
        this.add("")
        this.add("§7Total Tokens Needed: §8${cost.addSeparators()}${extraFormatting.orEmpty()}")
        val tokensNeeded = cost - tokensOwned
        if (tokensOwned > 0) this.add("§7Tokens Owned: §8${tokensOwned.addSeparators()}")
        if (tokensNeeded > 0) this.add("§7Additional Tokens Needed: §8${tokensNeeded.addSeparators()}${extraFormatting.orEmpty()}")
        else this.addAll(listOf("", "§e§oYou have enough tokens!"))
    }

    private fun regenerateOverviewItemStack() {
        if (repoEventShops.isEmpty()) return
        overviewInfoItemStack = ProfileStorageData.profileSpecific?.carnival?.carnivalShopProgress?.let { storage ->
            createItemStack(
                "NAME_TAG".asInternalName().getItemStack().item,
                "§bRemaining Carnival Token Upgrades",
                *buildList {
                    var sumTokensNeeded = 0
                    var foundShops = 0
                    repoEventShops.forEach { repoShop ->
                        storage[repoShop.shopName]?.let { purchasedUpgrades ->
                            foundShops++
                            val shopProgress = EventShopProgress(repoShop.shopName, purchasedUpgrades)
                            when (shopProgress.remainingUpgradeSum) {
                                0 -> add("§a${repoShop.shopName} §8- §aall upgrades purchased!")
                                else -> add("§7${repoShop.shopName} §8- ${shopProgress.remainingUpgradeSum.addSeparators()} tokens needed.")
                            }
                            sumTokensNeeded += shopProgress.remainingUpgradeSum
                        } ?: add ("§7${repoShop.shopName} §8- §copen shop to load...")
                    }
                    val extraFormatting = if (foundShops != repoEventShops.size) "*" else ""
                    sumTokensNeeded.takeIf { it > 0 }?.let { addNeededRemainingTokens(it, extraFormatting) }
                }.toTypedArray(),
            )
        }
    }

    private fun regenerateShopSpecificItemStack() {
        shopSpecificInfoItemStack = currentProgress?.let { progress ->
            createItemStack(
                "NAME_TAG".asInternalName().getItemStack().item,
                "§bRemaining $currentEventType Token Upgrades",
                *buildList {
                    val remaining = progress.remainingUpgrades.filter { it.remainingCosts.isNotEmpty() }
                    if (remaining.isEmpty()) add("§a§lAll upgrades purchased!")
                    else {
                        add("")
                        remaining.forEach {
                            add(
                                "  §a${it.upgradeName} §b${it.currentLevel} §7-> §b${it.maxLevel}§7: §8${
                                    it.remainingCosts.sum().addSeparators()
                                }",
                            )
                        }
                        val upgradeTotal = progress.remainingUpgradeSum
                        tokensNeeded = upgradeTotal - tokensOwned
                        upgradeTotal.takeIf { it > 0 }?.let { addNeededRemainingTokens(it) }
                    }

                    if (progress.nonRepoUpgrades.any()) {
                        add("")
                        add("§cFound upgrades not in repo§c§l:")
                        progress.nonRepoUpgrades.forEach { add("  §4${it.key}") }
                    }
                }.toTypedArray(),
            )
        }
    }

    private fun processInventoryEvent(event: InventoryOpenEvent) {
        if (!isEnabled() || repoEventShops.isEmpty()) return
        val matchingShop = repoEventShops.find { it.shopName.equals(event.inventoryName, ignoreCase = true) } ?: return
        currentEventType = matchingShop.shopName
        processEventShopUpgrades(event.inventoryItems)
        processTokenShopFooter(event)
        regenerateShopSpecificItemStack()
        regenerateOverviewItemStack()
        saveProgress()
    }

    private fun processTokenShopFooter(event: InventoryOpenEvent) {
        val tokenFooterStack = event.inventoryItems[32]
        if (tokenFooterStack === null || tokenFooterStack.displayName != "§eCarnival Tokens") {
            ErrorManager.logErrorWithData(
                NoSuchElementException(""),
                "Could not read current Essence Count from inventory",
                extraData = listOf(
                    "inventoryName" to event.inventoryName,
                    "tokenFooterStack" to tokenFooterStack?.displayName.orEmpty(),
                    "populatedInventorySize" to event.inventoryItems.filter { it.value.hasDisplayName() }.size,
                    "eventType" to event.javaClass.simpleName,
                ).toTypedArray(),
            )
            return
        }
        currentTokenCountPattern.firstMatcher(tokenFooterStack.getLore()) {
            tokensOwned = groupOrNull("tokens")?.formatInt() ?: 0
        }
    }

    private fun processEventShopUpgrades(inventoryItems: Map<Int, ItemStack>) {
        /**
         * All upgrades will appear in slots 11 -> 15
         *
         * Filter out items outside of these bounds
         */
        val upgradeStacks = inventoryItems.filter { it.key in 11..15 && it.value.item != null }
        val purchasedUpgrades: MutableMap<String, Int> = buildMap {
            upgradeStacks.forEach {
                // Right now Carnival and Essence Upgrade patterns are 'in-sync'
                // This may change in the future, and this would then need its own pattern
                essenceUpgradePattern.matchMatcher(it.value.displayName) {
                    val upgradeName = groupOrNull("upgrade") ?: return
                    val nextUpgradeRoman = groupOrNull("tier") ?: return
                    val nextUpgrade = nextUpgradeRoman.romanToDecimal()
                    val isMaxed = it.value.getLore().any { loreLine -> maxedUpgradeLorePattern.matches(loreLine) }
                    put(upgradeName, if (isMaxed) nextUpgrade else nextUpgrade - 1)
                }
            }
        }.toMutableMap()
        currentProgress = EventShopProgress(currentEventType, purchasedUpgrades)
    }

    private fun isEnabled() = LorenzUtils.inSkyBlock && SkyHanniMod.feature.event.carnival.tokenShopHelper
}
