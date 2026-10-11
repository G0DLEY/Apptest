package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = FoodioOrange,
  onPrimary = PureWhite,
  primaryContainer = FoodioOnOrangeContainer,
  onPrimaryContainer = FoodioOrangeContainer,
  secondary = FoodioOrangeLight,
  onSecondary = PureWhite,
  background = FoodioCharcoal,
  onBackground = PureWhite,
  surface = FoodioCharcoalLight,
  onSurface = PureWhite,
  surfaceVariant = FoodioCharcoalLight,
  outline = FoodioCharcoalLight
)

private val LightColorScheme = lightColorScheme(
  primary = FoodioOrange,
  onPrimary = PureWhite,
  primaryContainer = FoodioOrangeContainer,
  onPrimaryContainer = FoodioOnOrangeContainer,
  secondary = FoodioCharcoal,
  onSecondary = PureWhite,
  background = FoodioSurfaceGray,
  onBackground = TextPrimary,
  surface = PureWhite,
  onSurface = TextPrimary,
  surfaceVariant = FoodioSurfaceGray,
  outline = FoodioBorderGray
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep Foodio brand signature orange consistent
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
