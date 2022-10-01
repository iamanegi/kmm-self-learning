package com.amannegi.kmmdemo.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ProductsScreen(navController: NavController, email: String?) {

}

@Preview
@Composable
fun DefaultProductsPreview() {
    MyApplicationTheme {
        ProductsScreen(navController = rememberNavController(), email = "test@email.com")
    }
}