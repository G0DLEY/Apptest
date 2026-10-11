package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Address
import com.example.data.model.CartItem
import com.example.data.model.Restaurant
import com.example.data.model.UserRole
import com.example.ui.components.RatingBadge
import com.example.ui.components.VegNonVegIcon
import com.example.ui.theme.FoodioBorderGray
import com.example.ui.theme.FoodioOrange
import com.example.ui.theme.FoodioOrangeContainer
import com.example.ui.theme.FoodioSurfaceGray
import com.example.ui.theme.FoodioVegGreen
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CustomerHomeScreen(
  restaurants: List<Restaurant>,
  favorites: Set<String>,
  currentAddress: Address,
  currentRole: UserRole,
  cartItems: List<CartItem>,
  onAddressClick: () -> Unit,
  onRoleClick: () -> Unit,
  onSearchClick: () -> Unit,
  onRestaurantClick: (Restaurant) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onViewCartClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf("All") }
  var filterVegOnly by remember { mutableStateOf(false) }
  var filterRating4Plus by remember { mutableStateOf(false) }
  var filterFastDelivery by remember { mutableStateOf(false) }
  var filterOffersOnly by remember { mutableStateOf(false) }

  val categories = listOf(
    "All" to "🍽️",
    "Biryani" to "🍗",
    "Burgers" to "🍔",
    "Pizza" to "🍕",
    "South Indian" to "🥞",
    "Chinese" to "🥟",
    "Desserts" to "🧇",
    "Healthy" to "🥗",
    "Rolls" to "🌯",
    "Chai" to "☕"
  )

  val filteredRestaurants = restaurants.filter { r ->
    val matchesCategory = selectedCategory == "All" ||
      r.cuisineType.contains(selectedCategory, ignoreCase = true) ||
      r.tags.any { it.contains(selectedCategory, ignoreCase = true) }

    val matchesVeg = !filterVegOnly || r.isVegOnly
    val matchesRating = !filterRating4Plus || r.rating >= 4.0f
    val matchesDelivery = !filterFastDelivery || r.deliveryTimeMinutes <= 25
    val matchesOffer = !filterOffersOnly || r.offerText.isNotBlank()

    matchesCategory && matchesVeg && matchesRating && matchesDelivery && matchesOffer
  }

  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(PureWhite),
      contentPadding = PaddingValues(bottom = if (cartItems.isNotEmpty()) 90.dp else 24.dp)
    ) {
      // Top Bar: Location & Role Switcher
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(PureWhite)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Address row
            Row(
              modifier = Modifier
                .weight(1f)
                .clickable { onAddressClick() }
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = FoodioOrange,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = currentAddress.label,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = TextPrimary
                  )
                  Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = TextPrimary
                  )
                }
                Text(
                  text = "${currentAddress.street}, ${currentAddress.city}",
                  fontSize = 12.sp,
                  color = TextSecondary,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }

            // Role switcher pill button
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = FoodioOrangeContainer,
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange),
              modifier = Modifier
                .clickable { onRoleClick() }
                .testTag("btn_switch_role")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = when (currentRole) {
                    UserRole.CUSTOMER -> "Customer"
                    UserRole.RESTAURANT_PARTNER -> "Restaurant"
                    UserRole.DELIVERY_PARTNER -> "Delivery"
                    UserRole.ADMIN -> "Admin"
                  },
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = FoodioOrange
                )
                Icon(
                  imageVector = Icons.Default.ArrowDropDown,
                  contentDescription = "Switch Role",
                  tint = FoodioOrange,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Search Bar Trigger
          OutlinedCard(
            onClick = onSearchClick,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = FoodioSurfaceGray),
            border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("search_bar_trigger")
          ) {
            Row(
              modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Search,
                  contentDescription = "Search",
                  tint = FoodioOrange,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Search 'Biryani', 'Pizza' or dishes...",
                  color = TextMuted,
                  fontSize = 14.sp
                )
              }
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .background(FoodioOrange, CircleShape)
              )
            }
          }
        }
      }

      // Promotional Hero Banner
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(150.dp),
          colors = CardDefaults.cardColors(containerColor = FoodioSurfaceGray)
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            Image(
              painter = painterResource(id = R.drawable.img_food_hero),
              contentDescription = "Food hero banner",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.horizontalGradient(
                    colors = listOf(
                      Color.Black.copy(alpha = 0.82f),
                      Color.Black.copy(alpha = 0.45f),
                      Color.Transparent
                    )
                  )
                )
            )

            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
              verticalArrangement = Arrangement.Center
            ) {
              Surface(
                color = FoodioOrange,
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = "WELCOME50",
                  color = PureWhite,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.ExtraBold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Get 50% OFF",
                color = PureWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
              )
              Text(
                text = "Up to ₹100 on your first 3 food orders",
                color = PureWhite.copy(alpha = 0.9f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }

      // Cuisine categories horizontal scroller
      item {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          Text(
            text = "What's on your mind?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
          )

          LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(vertical = 8.dp)
          ) {
            items(categories) { (name, emoji) ->
              val isSelected = selectedCategory == name
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                  .clickable { selectedCategory = name }
                  .width(68.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) FoodioOrangeContainer else FoodioSurfaceGray)
                    .border(
                      2.dp,
                      if (isSelected) FoodioOrange else Color.Transparent,
                      CircleShape
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  Text(text = emoji, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = name,
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) FoodioOrange else TextPrimary,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }
          }
        }
      }

      // Filter chips
      item {
        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.padding(vertical = 8.dp)
        ) {
          item {
            FilterChip(
              selected = filterVegOnly,
              onClick = { filterVegOnly = !filterVegOnly },
              label = { Text("Pure Veg") },
              leadingIcon = {
                VegNonVegIcon(isVeg = true)
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = FoodioOrangeContainer,
                selectedLabelColor = FoodioOrange
              )
            )
          }
          item {
            FilterChip(
              selected = filterRating4Plus,
              onClick = { filterRating4Plus = !filterRating4Plus },
              label = { Text("Rating 4.0+") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = FoodioOrangeContainer,
                selectedLabelColor = FoodioOrange
              )
            )
          }
          item {
            FilterChip(
              selected = filterFastDelivery,
              onClick = { filterFastDelivery = !filterFastDelivery },
              label = { Text("Fast Delivery (<25 min)") },
              leadingIcon = {
                Icon(Icons.Default.DirectionsBike, contentDescription = null, modifier = Modifier.size(16.dp))
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = FoodioOrangeContainer,
                selectedLabelColor = FoodioOrange
              )
            )
          }
          item {
            FilterChip(
              selected = filterOffersOnly,
              onClick = { filterOffersOnly = !filterOffersOnly },
              label = { Text("Great Offers") },
              leadingIcon = {
                Icon(Icons.Default.LocalOffer, contentDescription = null, modifier = Modifier.size(16.dp))
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = FoodioOrangeContainer,
                selectedLabelColor = FoodioOrange
              )
            )
          }
        }
      }

      // Restaurant List Title
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Restaurants to explore (${filteredRestaurants.size})",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }
      }

      // Restaurant Cards
      items(filteredRestaurants, key = { it.id }) { restaurant ->
        RestaurantCardItem(
          restaurant = restaurant,
          isFavorite = favorites.contains(restaurant.id),
          onClick = { onRestaurantClick(restaurant) },
          onToggleFavorite = { onToggleFavorite(restaurant.id) },
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
      }
    }

    // Sticky Bottom Cart Bar
    if (cartItems.isNotEmpty()) {
      val totalItems = cartItems.sumOf { it.quantity }
      val totalCost = cartItems.sumOf { it.totalPrice }

      Surface(
        color = FoodioVegGreen,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp)
          .clickable { onViewCartClick() }
          .testTag("floating_cart_bar")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "$totalItems ITEM${if (totalItems > 1) "S" else ""} ADDED",
              color = PureWhite,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "₹${totalCost.toInt()} plus taxes",
              color = PureWhite,
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              text = "VIEW CART",
              color = PureWhite,
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold
            )
            Icon(
              imageVector = Icons.Default.KeyboardArrowRight,
              contentDescription = null,
              tint = PureWhite
            )
          }
        }
      }
    }
  }
}

@Composable
fun RestaurantCardItem(
  restaurant: Restaurant,
  isFavorite: Boolean,
  onClick: () -> Unit,
  onToggleFavorite: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = PureWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("restaurant_card_${restaurant.id}")
  ) {
    Column {
      // Image simulation header with gradient & badges
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(130.dp)
          .background(
            Brush.linearGradient(
              colors = listOf(
                Color(0xFF2D3748),
                Color(0xFF1A202C)
              )
            )
          )
      ) {
        // Decorative restaurant illustration or badge
        Column(
          modifier = Modifier
            .align(Alignment.CenterStart)
            .padding(16.dp)
        ) {
          Text(
            text = restaurant.name.take(2).uppercase(),
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PureWhite.copy(alpha = 0.2f)
          )
        }

        // Top badges: Veg tag & Favorite button
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (restaurant.isVegOnly) {
            Surface(
              color = PureWhite,
              shape = RoundedCornerShape(6.dp),
              shadowElevation = 2.dp
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                VegNonVegIcon(isVeg = true)
                Text("PURE VEG", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FoodioVegGreen)
              }
            }
          } else {
            Spacer(modifier = Modifier.width(1.dp))
          }

          IconButton(
            onClick = onToggleFavorite,
            modifier = Modifier
              .size(32.dp)
              .background(PureWhite.copy(alpha = 0.85f), CircleShape)
          ) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Favorite",
              tint = if (isFavorite) Color.Red else TextPrimary,
              modifier = Modifier.size(18.dp)
            )
          }
        }

        // Bottom offer banner on image
        if (restaurant.offerText.isNotBlank()) {
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .fillMaxWidth()
              .background(
                Brush.verticalGradient(
                  listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                )
              )
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.LocalOffer,
                contentDescription = null,
                tint = FoodioOrange,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = restaurant.offerText,
                color = PureWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }

      // Details section
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = restaurant.name,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
          )
          RatingBadge(rating = restaurant.rating)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = "${restaurant.deliveryTimeMinutes} mins",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(text = "•", color = TextMuted)
          Text(
            text = "${restaurant.distanceKm} km",
            fontSize = 13.sp,
            color = TextSecondary
          )
          Text(text = "•", color = TextMuted)
          Text(
            text = "₹${restaurant.costForTwo.toInt()} for two",
            fontSize = 13.sp,
            color = TextSecondary
          )
        }

        Text(
          text = restaurant.cuisineType,
          fontSize = 12.sp,
          color = TextMuted,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }
  }
}
