package com.amannegi.kmmdemo.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.amannegi.kmmdemo.Product
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetailsScreen(navController: NavController, product: Product) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product.title ?: "Product",
                        style = MaterialTheme.typography.h6
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { scaffoldPadding ->

        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
        ) {
            val pageState = rememberPagerState()

            HorizontalPager(
                state = pageState,
                count = product.images?.size ?: 0,
//                contentPadding = PaddingValues(horizontal = 32.dp)
            ) { page ->
                AsyncImage(
                    model = product.images?.get(page),
                    contentDescription = "Product image $page",
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(id = R.drawable.ic_round_imagesearch_roller_24),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            HorizontalPagerIndicator(
                pagerState = pageState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Text(
                text = "${product.description}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                product.discountPercentage?.let {
                    if (product.discountPercentage != 0.0) {
                        Text(
                            text = "${product.discountPercentage}% off",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF02b502)
                        )
                    }
                }
                Text(
                    text = "₹${product.price}",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                RatingBar(
                    modifier = Modifier.fillMaxWidth(0.3f),
                    numberOfStars = 5,
                    rating = (product.rating ?: 0).toFloat(),
                    starSpacing = 2.dp
                )
                Text(
                    text = "${product.rating}",
                    style = MaterialTheme.typography.caption
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            ) {
                Text(
                    text = "Only ${product.stock} left!",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    MyApplicationTheme {
        ProductDetailsScreen(navController = rememberNavController(), product = dummyProduct)
    }
}