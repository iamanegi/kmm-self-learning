package com.amannegi.kmmdemo.android

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object ProductsScreen : Screen("products_screen")
    object ProductDetailsScreen : Screen("product_details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg}")
            }
        }
    }
}
