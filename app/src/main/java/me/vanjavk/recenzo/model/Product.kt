package me.vanjavk.recenzo.model
import kotlinx.serialization.Serializable

@Serializable
data class Product(val id: Long, val title: String = "", val decription: String, val image: String)