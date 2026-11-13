package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityTodosJson(
    @SerialName("community_todos") val communityTodos: List<CommunityTodo>,
)

@Serializable
data class CommunityTodo(
    val id: String,
    val author: String,
    @SerialName("discord_thread") val discordThread: String,
    @SerialName("todo_data") val todoData: String,
)
