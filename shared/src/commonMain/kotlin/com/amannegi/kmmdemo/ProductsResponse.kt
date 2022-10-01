package com.amannegi.kmmdemo

@kotlinx.serialization.Serializable
data class ProductsResponse(
    val products: List<Product>? = null,
    val total: Int? = null,
    val skip: Int? = null,
    val limit: Int? = null
)