package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandGreenPrimary

/**
 * Authentic EmpleoHoy brand emblem and wordmark matching the official branding.
 */
@Composable
fun EmpleoHoyEmblem(
    size: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.25f))
            .background(Color.White)
            .padding(size * 0.08f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empleohoy_mark),
            contentDescription = "EmpleoHoy Logo",
            modifier = Modifier.size(size * 0.84f)
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun EmpleoHoyBrandHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        EmpleoHoyEmblem(size = 42.dp)

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // "EMPLEO" in serif with green gradient
                val greenGradient = Brush.horizontalGradient(
                    colors = listOf(BrandGreenLight, BrandGreenDark)
                )

                Text(
                    text = "EMPLEO",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        letterSpacing = 1.5.sp,
                        brush = greenGradient
                    )
                )

                Spacer(modifier = Modifier.width(5.dp))

                // "HOY" in serif grey
                Text(
                    text = "HOY",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 19.sp,
                        letterSpacing = 1.5.sp,
                        color = Color(0xFF9CA3AF)
                    )
                )
            }

            Text(
                text = "Nunca Fue Tan Fácil Buscar Empleo",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun EmpleoHoyFullLockup(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmpleoHoyEmblem(size = 72.dp)

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            val greenGradient = Brush.horizontalGradient(
                colors = listOf(BrandGreenLight, BrandGreenDark)
            )

            Text(
                text = "EMPLEO",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    letterSpacing = 2.sp,
                    brush = greenGradient
                )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "HOY",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    letterSpacing = 2.sp,
                    color = Color(0xFF9CA3AF)
                )
            )
        }
    }
}
