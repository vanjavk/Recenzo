package me.vanjavk.recenzo.model

data class Product(
    val _id: Long,
    val barcode: String,
    val title: String,
    val description: String,
    val picturePath: String
)