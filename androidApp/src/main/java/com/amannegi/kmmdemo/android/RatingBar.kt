package com.amannegi.kmmdemo.android

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RatingBar(
    modifier: Modifier,
    numberOfStars: Int = 5,
    rating: Float = 0f,
    starHighlightColor: Color = Color.Yellow,
    starBackgroundColor: Color = Color.Gray,
    starSpacing: Dp = 4.dp
) {
    BoxWithConstraints(modifier = modifier) {
        val availableScreenWidth = this.maxWidth

        Row {
            (1..numberOfStars).forEach { step ->
                val currentStarRating = when {
                    (rating > step) -> 1f
                    step.rem(rating) < 1 -> rating - (step - 1f)
                    else -> 0f
                }
                RatingStar(
                    currentRating = currentStarRating,
                    starHighlightColor = starHighlightColor,
                    starBackgroundColor = starBackgroundColor,
                    maxWidth = availableScreenWidth / numberOfStars,
                    horizontalPadding = starSpacing
                )
            }
        }
    }
}

@Composable
fun RatingStar(
    currentRating: Float, starHighlightColor: Color,
    starBackgroundColor: Color, maxWidth: Dp,
    horizontalPadding: Dp
) {
    Canvas(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .width(maxWidth - horizontalPadding.times(2))
            .aspectRatio(1f)
            .clip(starShape)
    ) {

        // star background
        drawRect(brush = SolidColor(starBackgroundColor))

        if (currentRating > 0f) {
            drawRect(
                brush = SolidColor(starHighlightColor),
                size = Size(
                    height = size.height,
                    width = size.width * currentRating
                )
            )
        }
    }
}

private val starShape = GenericShape { size, _ ->
    addPath(starPath(size.height))
}

private val starPath = { size: Float ->
    Path().apply {
        val outerRadius: Float = size / 1.8f
        val innerRadius: Double = outerRadius / 2.5
        var rot: Double = Math.PI / 2 * 3
        val cx: Float = size / 2
        val cy: Float = size / 20 * 11
        var x: Float
        var y: Float
        val step = Math.PI / 5

        moveTo(cx, cy - outerRadius)
        repeat(5) {
            x = (cx + cos(rot) * outerRadius).toFloat()
            y = (cy + sin(rot) * outerRadius).toFloat()
            lineTo(x, y)
            rot += step

            x = (cx + cos(rot) * innerRadius).toFloat()
            y = (cy + sin(rot) * innerRadius).toFloat()
            lineTo(x, y)
            rot += step
        }
        close()
    }
}

@Preview
@Composable
fun RatingBarPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        RatingBar(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            numberOfStars = 5,
            rating = 2.7f,
            starHighlightColor = Color.Green,
            starBackgroundColor = Color.Black,
            starSpacing = 8.dp
        )
    }
}