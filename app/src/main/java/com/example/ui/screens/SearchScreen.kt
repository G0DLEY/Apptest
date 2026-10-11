package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MenuItem
import com.example.data.model.Restaurant
import com.example.ui.components.RatingBadge
import com.example.ui.components.VegNonVegIcon
import com.example.ui.theme.FoodioBorderGray
import com.example.ui.theme.FoodioOrange
import com.example.ui.theme.FoodioOrangeContainer
import com.example.ui.theme.FoodioSurfaceGray
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
  restaurants: List<Restaurant>,
  allMenuItems: Map<String, List<MenuItem>>,
  onRestaurantClick: (Restaurant) -> Unit,
  onAddToCart: (MenuItem) -> Unit,
  modifier: Modifier = Modifier
) {
  var query by remember { mutableStateOf("") }

  val popularSearches = listOf(
    "Biryani", "Burger", "Pizza", "Dosa", "Momos", "Filter Coffee", "Chaat", "Waffles", "Wraps"
  )

  // Matching restaurants
  val matchingRestaurants = if (query.isBlank()) emptyList() else {
    restaurants.filter {
      it.name.contains(query, ignoreCase = true) ||
        it.cuisineType.contains(query, ignoreCase = true) ||
        it.tags.any { tag -> tag.contains(query, ignoreCase = true) }
    }
  }

  // Matching dishes
  val matchingDishes = if (query.isBlank()) emptyList() else {
    allMenuItems.values.flatten().filter {
      it.name.contains(query, ignoreCase = true) ||
        it.description.contains(query, ignoreCase = true) ||
        it.category.contains(query, ignoreCase = true)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(PureWhite)
      .padding(16.dp)
  ) {
    // Search TextField
    OutlinedTextField(
      value = query,
      onValueChange = { query = it },
      placeholder = { Text("Search for restaurants and food...") },
      leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = null, tint = FoodioOrange)
      },
      trailingIcon = {
        if (query.isNotEmpty()) {
          IconButton(onClick = { query = "" }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
          }
        }
      },
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = FoodioOrange,
        unfocusedBorderColor = FoodioBorderGray
      ),
      shape = RoundedCornerShape(12.dp),
      singleLine = true,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("search_input_field")
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (query.isBlank()) {
      // Popular Searches
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = FoodioOrange, modifier = Modifier.size(20.dp))
        Text(
          text = "Popular Cuisines & Dishes",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        popularSearches.forEach { term ->
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = FoodioSurfaceGray,
            border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
            modifier = Modifier.clickable { query = term }
          ) {
            Text(
              text = term,
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium,
              color = TextPrimary,
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
            )
          }
        }
      }
    } else {
      // Results list
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        if (matchingRestaurants.isNotEmpty()) {
          item {
            Text(
              text = "Restaurants (${matchingRestaurants.size})",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = TextPrimary,
              modifier = Modifier.padding(vertical = 4.dp)
            )
          }

          items(matchingRestaurants) { restaurant ->
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
              modifier = Modifier
                .fillMaxWidth()
                .clickable { onRestaurantClick(restaurant) }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = restaurant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextPrimary
                  )
                  Text(
                    text = "${restaurant.cuisineType} • ${restaurant.deliveryTimeMinutes} mins",
                    fontSize = 12.sp,
                    color = TextSecondary
                  )
                }
                RatingBadge(rating = restaurant.rating)
              }
            }
          }
        }

        if (matchingDishes.isNotEmpty()) {
          item {
            Text(
              text = "Dishes (${matchingDishes.size})",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = TextPrimary,
              modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
          }

          items(matchingDishes) { dish ->
            val restaurant = restaurants.find { it.id == dish.restaurantId }
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = FoodioSurfaceGray),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    VegNonVegIcon(isVeg = dish.isVeg)
                    Text(
                      text = dish.name,
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      color = TextPrimary
                    )
                  }
                  Text(
                    text = "By ${restaurant?.name ?: "Restaurant"}",
                    fontSize = 12.sp,
                    color = FoodioOrange,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                      if (restaurant != null) onRestaurantClick(restaurant)
                    }
                  )
                  Text(
                    text = "₹${dish.price.toInt()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 2.dp)
                  )
                }

                Button(
                  onClick = { onAddToCart(dish) },
                  colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Text("ADD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }
              }
            }
          }
        }

        if (matchingRestaurants.isEmpty() && matchingDishes.isEmpty()) {
          item {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "No results found for \"$query\"",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextPrimary
              )
              Text(
                text = "Try searching for Biryani, Pizza, Rolls or Haldiram's",
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
              )
            }
          }
        }
      }
    }
  }
}
