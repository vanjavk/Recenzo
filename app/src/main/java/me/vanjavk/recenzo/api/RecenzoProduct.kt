package me.vanjavk.recenzo.api
import kotlinx.serialization.Serializable

@Serializable
data class RecenzoProduct(
    val id: Long,
    val title: String,
    val description: String = "",
    val picturePath: String
)
