package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartCustomizationSelection
import com.example.data.model.CartItem
import com.example.data.model.MenuItem
import com.example.data.model.Restaurant
import com.example.ui.components.CustomizationBottomSheet
import com.example.ui.components.QuantityStepper
import com.example.ui.components.RatingBadge
import com.example.ui.components.VegNonVegIcon
import com.example.ui.theme.FoodioBorderGray
import com.example.ui.theme.FoodioOrange
import com.example.ui.theme.FoodioOrangeContainer
import com.example.ui.theme.FoodioRatingAmber
import com.example.ui.theme.FoodioSurfaceGray
import com.example.ui.theme.FoodioVegGreen
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun RestaurantDetailScreen(
  restaurant: Restaurant,
  menuItems: List<MenuItem>,
  cartItems: List<CartItem>,
  isFavorite: Boolean,
  onBackClick: () -> Unit,
  onToggleFavorite: () -> Unit,
  onAddToCart: (MenuItem, List<CartCustomizationSelection>, String) -> Unit,
  onUpdateQuantity: (String, Int) -> Unit,
  onViewCartClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var vegOnlyFilter by remember { mutableStateOf(false) }
  var selectedCategory by remember { mutableStateOf<String?>(null) }
  var itemToCustomize by remember { mutableStateOf<MenuItem?>(null) }

  val categories = menuItems.map { it.category }.distinct()

  val filteredItems = menuItems.filter { item ->
    val matchesVeg = !vegOnlyFilter || item.isVeg
    val matchesCategory = selectedCategory == null || item.category == selectedCategory
    matchesVeg && matchesCategory
  }

  val groupedByCategory = filteredItems.groupBy { it.category }

  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(PureWhite),
      contentPadding = PaddingValues(bottom = if (cartItems.isNotEmpty()) 90.dp else 24.dp)
    ) {
      // Top Bar & Back button
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier.testTag("btn_back_restaurant")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
          }

          IconButton(onClick = onToggleFavorite) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Favorite",
              tint = if (isFavorite) Color.Red else TextPrimary
            )
          }
        }
      }

      // Restaurant Header Card
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = PureWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = restaurant.name,
                  fontSize = 20.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = TextPrimary
                )
                Text(
                  text = restaurant.cuisineType,
                  fontSize = 13.sp,
                  color = TextSecondary,
                  modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                  text = "${restaurant.area} • ${restaurant.distanceKm} km away",
                  fontSize = 12.sp,
                  color = TextMuted,
                  modifier = Modifier.padding(top = 2.dp)
                )
              }

              RatingBadge(rating = restaurant.rating, count = restaurant.ratingCount)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // Delivery time & Cost
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "🕒 ${restaurant.deliveryTimeMinutes} mins",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
              }
              Text(text = "•", color = TextMuted)
              Text(
                text = "₹${restaurant.costForTwo.toInt()} for two",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
              )
            }

            if (restaurant.offerText.isNotBlank()) {
              Spacer(modifier = Modifier.height(10.dp))
              Surface(
                color = FoodioOrangeContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.LocalOffer,
                    contentDescription = null,
                    tint = FoodioOrange,
                    modifier = Modifier.size(16.dp)
                  )
                  Text(
                    text = restaurant.offerText,
                    color = FoodioOrange,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }
        }
      }

      // Filter Chips (Veg only / Categories)
      item {
        Column(modifier = Modifier.padding(top = 16.dp)) {
          LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            item {
              FilterChip(
                selected = vegOnlyFilter,
                onClick = { vegOnlyFilter = !vegOnlyFilter },
                label = { Text("Veg Only") },
                leadingIcon = { VegNonVegIcon(isVeg = true) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = FoodioOrangeContainer,
                  selectedLabelColor = FoodioOrange
                )
              )
            }

            item {
              FilterChip(
                selected = selectedCategory == null,
                onClick = { selectedCategory = null },
                label = { Text("All Dishes") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = FoodioOrangeContainer,
                  selectedLabelColor = FoodioOrange
                )
              )
            }

            items(categories) { category ->
              FilterChip(
                selected = selectedCategory == category,
                onClick = {
                  selectedCategory = if (selectedCategory == category) null else category
                },
                label = { Text(category) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = FoodioOrangeContainer,
                  selectedLabelColor = FoodioOrange
                )
              )
            }
          }
        }
      }

      // Menu List grouped by category
      groupedByCategory.forEach { (category, items) ->
        item {
          Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 4.dp)) {
            Text(
              text = "$category (${items.size})",
              fontSize = 18.sp,
              fontWeight = FontWeight.ExtraBold,
              color = TextPrimary
            )
          }
        }

        items(items, key = { it.id }) { item ->
          val quantityInCart = cartItems.filter { it.menuItem.id == item.id }.sumOf { it.quantity }
          MenuItemRow(
            item = item,
            quantityInCart = quantityInCart,
            onAddClick = {
              if (item.customizationGroups.isNotEmpty()) {
                itemToCustomize = item
              } else {
                onAddToCart(item, emptyList(), "")
              }
            },
            onIncrease = {
              if (item.customizationGroups.isNotEmpty()) {
                itemToCustomize = item
              } else {
                onUpdateQuantity(item.id, quantityInCart + 1)
              }
            },
            onDecrease = {
              onUpdateQuantity(item.id, quantityInCart - 1)
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
          )
          HorizontalDivider(
            color = FoodioBorderGray.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }
      }
    }

    // Floating Cart Footer
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
          .testTag("btn_view_cart_restaurant")
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

    // Customization Modal if open
    itemToCustomize?.let { item ->
      CustomizationBottomSheet(
        item = item,
        onDismiss = { itemToCustomize = null },
        onAddToCart = { options, instructions ->
          onAddToCart(item, options, instructions)
        }
      )
    }
  }
}

@Composable
fun MenuItemRow(
  item: MenuItem,
  quantityInCart: Int,
  onAddClick: () -> Unit,
  onIncrease: () -> Unit,
  onDecrease: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        VegNonVegIcon(isVeg = item.isVeg)
        if (item.isBestseller) {
          Surface(
            color = FoodioRatingAmber.copy(alpha = 0.15f),
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = "★ BESTSELLER",
              color = FoodioRatingAmber,
              fontSize = 10.sp,
              fontWeight = FontWeight.ExtraBold,
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
          }
        }
      }

      Text(
        text = item.name,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = TextPrimary,
        modifier = Modifier.padding(top = 4.dp)
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(top = 2.dp)
      ) {
        Text(
          text = "₹${item.price.toInt()}",
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = TextPrimary
        )
        if (item.originalPrice > item.price) {
          Text(
            text = "₹${item.originalPrice.toInt()}",
            fontSize = 12.sp,
            color = TextMuted,
            textDecoration = TextDecoration.LineThrough
          )
        }
      }

      Text(
        text = item.description,
        fontSize = 12.sp,
        color = TextSecondary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 4.dp)
      )

      if (item.customizationGroups.isNotEmpty()) {
        Text(
          text = "Customisable",
          fontSize = 11.sp,
          color = FoodioOrange,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }

    Spacer(modifier = Modifier.width(16.dp))

    // Add button or Quantity stepper
    Box(
      modifier = Modifier
        .width(105.dp)
        .padding(top = 8.dp),
      contentAlignment = Alignment.Center
    ) {
      if (quantityInCart == 0) {
        OutlinedButton(
          onClick = onAddClick,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = PureWhite,
            contentColor = FoodioOrange
          ),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, FoodioOrange),
          modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .testTag("btn_add_${item.id}")
        ) {
          Text("ADD", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
        }
      } else {
        QuantityStepper(
          quantity = quantityInCart,
          onIncrease = onIncrease,
          onDecrease = onDecrease,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
