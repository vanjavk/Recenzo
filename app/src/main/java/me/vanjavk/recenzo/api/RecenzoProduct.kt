package me.vanjavk.recenzo.api
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecenzoProduct(
//    val id: Long = -1,
    val barcode: String = "",
    val title: String = "",
    val description: String = "",
    @SerialName("image") val picturePath: String = "",
)
