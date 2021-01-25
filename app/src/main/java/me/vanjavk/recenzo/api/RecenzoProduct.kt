package me.vanjavk.recenzo.api
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RecenzoProduct(
    val id: Long = -1,
    val title: String = "",
    val description: String = "",
    @SerializedName("image") val picturePath: String = "",
)
