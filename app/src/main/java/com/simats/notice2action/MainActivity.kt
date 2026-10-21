package com.simats.notice2action

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.notice2action.ui.theme.Notice2actionTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Notice2actionTheme {
                var showSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(2000L) // Show splash for 2 seconds
                    showSplash = false
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    // Main App Content
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    // Splash Screen Overlay
                    AnimatedVisibility(
                        visible = showSplash,
                        exit = fadeOut(animationSpec = tween(600))
                    ) {
                        SplashScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0072FF),
                        Color(0xFF00C6FF),
                        Color(0xFF6B11CB),
                        Color(0xFF8B2FC9)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Logo Graphic reproducing the uploaded icon
            Box(
                modifier = Modifier
                    .size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                // Document Canvas / Vector representation
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // 1. White Document with folded corner
                    val docPath = Path().apply {
                        moveTo(width * 0.28f, height * 0.15f)
                        lineTo(width * 0.65f, height * 0.15f)
                        lineTo(width * 0.82f, height * 0.32f)
                        lineTo(width * 0.82f, height * 0.85f)
                        arcTo(
                            rect = Rect(width * 0.82f - width * 0.1f, height * 0.85f - height * 0.1f, width * 0.82f, height * 0.85f),
                            startAngleDegrees = 0f,
                            sweepAngleDegrees = 90f,
                            forceMoveTo = false
                        )
                        lineTo(width * 0.28f, height * 0.75f)
                        arcTo(
                            rect = Rect(width * 0.28f, height * 0.75f - height * 0.1f, width * 0.28f + width * 0.1f, height * 0.75f),
                            startAngleDegrees = 90f,
                            sweepAngleDegrees = 90f,
                            forceMoveTo = false
                        )
                        close()
                    }

                    // Document shadow/background card
                    drawPath(
                        path = docPath,
                        color = Color.White.copy(alpha = 0.95f),
                        style = Fill
                    )

                    // Folded corner
                    val foldPath = Path().apply {
                        moveTo(width * 0.65f, height * 0.15f)
                        lineTo(width * 0.65f, height * 0.32f)
                        lineTo(width * 0.82f, height * 0.32f)
                        close()
                    }
                    drawPath(
                        path = foldPath,
                        color = Color(0xFFE0E7FF),
                        style = Fill
                    )

                    // Document lines
                    val lineColor = Color(0xFF6B7280)
                    // Line 1
                    drawRect(
                        color = lineColor,
                        topLeft = Offset(width * 0.36f, height * 0.32f),
                        size = Size(width * 0.25f, height * 0.035f)
                    )
                    // Line 2
                    drawRect(
                        color = lineColor,
                        topLeft = Offset(width * 0.36f, height * 0.40f),
                        size = Size(width * 0.32f, height * 0.035f)
                    )
                    // Line 3
                    drawRect(
                        color = lineColor,
                        topLeft = Offset(width * 0.36f, height * 0.48f),
                        size = Size(width * 0.20f, height * 0.035f)
                    )

                    // Curved Arrow wrapping around
                    val arrowPath = Path().apply {
                        moveTo(width * 0.22f, height * 0.50f)
                        cubicTo(
                            width * 0.10f, height * 0.75f,
                            width * 0.50f, height * 0.95f,
                            width * 0.75f, height * 0.45f
                        )
                    }
                    drawPath(
                        path = arrowPath,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF00D2FF), Color(0xFF9333EA)),
                            start = Offset(0f, height * 0.5f),
                            end = Offset(width, height * 0.3f)
                        ),
                        style = Stroke(width = 12f, cap = StrokeCap.Round)
                    )

                    // Arrow Head pointing up-right
                    val arrowHead = Path().apply {
                        moveTo(width * 0.65f, height * 0.37f)
                        lineTo(width * 0.77f, height * 0.34f)
                        lineTo(width * 0.74f, height * 0.48f)
                    }
                    drawPath(
                        path = arrowHead,
                        color = Color(0xFF9333EA),
                        style = Fill
                    )
                }

                // Checkmark Badge Circle overlay
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .offset(x = 35.dp, y = 20.dp)
                        .shadow(8.dp, RoundedCornerShape(26.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF00C6FF), Color(0xFF0072FF))
                            ),
                            shape = RoundedCornerShape(26.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val checkPath = Path().apply {
                            moveTo(size.width * 0.2f, size.height * 0.55f)
                            lineTo(size.width * 0.45f, size.height * 0.75f)
                            lineTo(size.width * 0.8f, size.height * 0.25f)
                        }
                        drawPath(
                            path = checkPath,
                            color = Color.White,
                            style = Stroke(width = 7f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Title
            Text(
                text = "Notice2Action",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Turn Documents Into Actions",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Notice2actionTheme {
        SplashScreen()
    }
}