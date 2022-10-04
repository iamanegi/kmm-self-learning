package com.amannegi.kmmdemo.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.amannegi.kmmdemo.KtorHelper
import com.amannegi.kmmdemo.Product
import com.amannegi.kmmdemo.ProductsResponse


@Composable
fun ProductsScreen(navController: NavController, email: String?) {

    val scaffoldState = rememberScaffoldState()

    val localConfiguration = LocalConfiguration.current

    val productsResponse by produceState<ProductsResponse?>(initialValue = null) {
        kotlin.runCatching {
            KtorHelper().getProducts()
        }.onSuccess {
            value = it
        }.onFailure {
            value = null
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                Text(
                    text = "Products Screen",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) { scaffoldPadding ->

        LazyVerticalGrid(columns = GridCells.Fixed(count = 2)) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Welcome, ${email ?: "there"}!",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                )
            }

            if (productsResponse == null) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier.requiredHeight(localConfiguration.screenHeightDp.dp - 100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .aspectRatio(1f)
                                .padding(scaffoldPadding)
                        )
                    }
                }
            } else {

                items(productsResponse?.limit ?: 0) { i ->
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        val product = Product(
                            1,
                            "iPhone 9",
                            "An apple mobile which is nothing like apple",
                            549,
                            12.96,
                            4.69,
                            94,
                            "Apple",
                            "smartphones",
                            "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
                            arrayListOf(
                                "https://dummyjson.com/image/i/products/1/1.jpg",
                                "https://dummyjson.com/image/i/products/1/2.jpg",
                                "https://dummyjson.com/image/i/products/1/3.jpg",
                                "https://dummyjson.com/image/i/products/1/4.jpg",
                                "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
                            )
                        )
                        ProductView(product = productsResponse?.products?.get(i) ?: product)
                    }
                }
            }
        }

    }

}

@Composable
fun ProductView(product: Product) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = "Product image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = product.title ?: "Not found",
            style = MaterialTheme.typography.subtitle1
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "₹${product.price}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            product.discountPercentage?.let {
                if (product.discountPercentage != 0.0) {
                    Text(
                        text = "${product.discountPercentage}% off",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF02b502)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultProductsPreview() {
    MyApplicationTheme {
        ProductsScreen(navController = rememberNavController(), email = "test@email.com")
    }
}