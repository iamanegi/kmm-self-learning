package com.amannegi.kmmdemo

@kotlinx.serialization.Serializable
data class Product(
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var price: Int? = null,
    var discountPercentage: Double? = null,
    var rating: Double? = null,
    var stock: Int? = null,
    var brand: String? = null,
    var category: String? = null,
    var thumbnail: String? = null,
    var images: List<String>? = null
)
