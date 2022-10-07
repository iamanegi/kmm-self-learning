package com.amannegi.kmmdemo.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amannegi.kmmdemo.Product

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF5F6F94),
            primaryVariant = Color(0xFF97D2EC),
            secondary = Color(0xFFFEF5AC)
        )
    } else {
        lightColors(
            primary = Color(0xFF5F6F94),
            primaryVariant = Color(0xFF97D2EC),
            secondary = Color(0xFFFEF5AC)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {

                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }

                    composable(
                        route = Screen.ProductsScreen.route + "/{email}",
                        arguments = listOf(
                            navArgument("email") {
                                type = NavType.StringType
                                defaultValue = "none@test.com"
                                nullable = true
                            }
                        )
                    ) { entry ->
                        ProductsScreen(
                            navController = navController,
                            email = entry.arguments?.getString("email")
                        )
                    }

                    composable(route = Screen.ProductDetailsScreen.route) {
                        ProductDetailsScreen(
                            navController = navController,
                            product = navController.previousBackStackEntry?.arguments?.getParcelable<Product>("product")
                                ?: dummyProduct
                        )
                    }

                }
            }
        }
    }
}

