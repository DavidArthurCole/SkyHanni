package at.hannibal2.skyhanni.features.fishing.seaCreatureXMLGui

import kotlinx.serialization.Serializable

@Serializable
data class SpecificSeaCreatureSettings(
    var name: String,
    var shouldRenderLootshare: Boolean?,
    var shouldShowHealthOverlay: Boolean?,
    var shouldShareInChat: Boolean?,
    var shouldShowKillTime: Boolean?,
    var shouldSelfNotifyOnCatch: Boolean?,
    var shouldNotifyForNonOwn: Boolean?,
    var shouldHighlight: Boolean?,
    var shouldShareCocoonInChat: Boolean?,
    var shouldWarnWhenCocooned: Boolean?,
)
