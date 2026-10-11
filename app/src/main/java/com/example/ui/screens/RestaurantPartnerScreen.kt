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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MenuItem
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.Restaurant
import com.example.ui.components.VegNonVegIcon
import com.example.ui.theme.FoodioBorderGray
import com.example.ui.theme.FoodioNonVegRed
import com.example.ui.theme.FoodioOrange
import com.example.ui.theme.FoodioOrangeContainer
import com.example.ui.theme.FoodioSurfaceGray
import com.example.ui.theme.FoodioVegGreen
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun RestaurantPartnerScreen(
  restaurant: Restaurant,
  menuItems: List<MenuItem>,
  orders: List<Order>,
  onToggleOpen: () -> Unit,
  onToggleItemAvailability: (String, Boolean) -> Unit,
  onAddNewItem: (MenuItem) -> Unit,
  onUpdateOrderStatus: (String, OrderStatus) -> Unit,
  onSwitchRole: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  var showAddDishDialog by remember { mutableStateOf(false) }

  val incomingOrders = orders.filter { it.orderStatus == OrderStatus.PLACED }
  val activeKitchenOrders = orders.filter {
    it.orderStatus == OrderStatus.ACCEPTED || it.orderStatus == OrderStatus.PREPARING
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(FoodioSurfaceGray)
  ) {
    // Header
    Surface(
      color = PureWhite,
      shadowElevation = 2.dp
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Restaurant, contentDescription = null, tint = FoodioOrange)
            Column {
              Text(
                text = restaurant.name,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = TextPrimary
              )
              Text(
                text = "Restaurant Partner Portal",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }
          }

          // Switch Role Pill
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = FoodioOrangeContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange),
            modifier = Modifier.clickable { onSwitchRole() }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = FoodioOrange, modifier = Modifier.size(16.dp))
              Text("Switch Role", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = FoodioOrange)
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Open/Closed Toggle card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (restaurant.isOpen) FoodioVegGreen.copy(alpha = 0.1f) else FoodioNonVegRed.copy(alpha = 0.1f)
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (restaurant.isOpen) FoodioVegGreen else FoodioNonVegRed
          )
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (restaurant.isOpen) "Kitchen is OPEN for Orders" else "Kitchen is currently CLOSED",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (restaurant.isOpen) FoodioVegGreen else FoodioNonVegRed
              )
              Text(
                text = if (restaurant.isOpen) "Customers can order dishes" else "Orders temporarily paused",
                fontSize = 11.sp,
                color = TextSecondary
              )
            }
            Switch(
              checked = restaurant.isOpen,
              onCheckedChange = { onToggleOpen() },
              colors = SwitchDefaults.colors(
                checkedThumbColor = PureWhite,
                checkedTrackColor = FoodioVegGreen
              )
            )
          }
        }
      }
    }

    // Daily Earnings Stats
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Today's Revenue", fontSize = 11.sp, color = TextSecondary)
          Text("₹5,820", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = FoodioOrange)
        }
      }
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Orders Today", fontSize = 11.sp, color = TextSecondary)
          Text("${orders.size + 12}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        }
      }
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Avg Prep Time", fontSize = 11.sp, color = TextSecondary)
          Text("20 mins", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = FoodioVegGreen)
        }
      }
    }

    // Tabs: Orders & Menu
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = PureWhite,
      contentColor = FoodioOrange
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = {
          Text(
            text = "Live Orders (${incomingOrders.size + activeKitchenOrders.size})",
            fontWeight = FontWeight.Bold
          )
        }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = {
          Text(
            text = "Menu Items (${menuItems.size})",
            fontWeight = FontWeight.Bold
          )
        }
      )
    }

    // Tab Contents
    if (selectedTab == 0) {
      // Orders Tab
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        if (incomingOrders.isEmpty() && activeKitchenOrders.isEmpty()) {
          item {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text("No Pending Orders", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
              Text("Switch to Customer role and place an order to see it appear here live!", fontSize = 12.sp, color = TextSecondary)
            }
          }
        }

        if (incomingOrders.isNotEmpty()) {
          item {
            Text(
              text = "Incoming Orders (${incomingOrders.size})",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp,
              color = FoodioOrange
            )
          }

          items(incomingOrders) { order ->
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("Order #${order.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                  Text("₹${order.totalAmount.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = FoodioOrange)
                }

                Text(
                  text = "Customer: ${order.customerName} (${order.customerPhone})",
                  fontSize = 12.sp,
                  color = TextSecondary,
                  modifier = Modifier.padding(vertical = 2.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                order.items.forEach { item ->
                  Text("• ${item.quantity}x ${item.name}", fontSize = 13.sp, color = TextPrimary)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                  OutlinedButton(
                    onClick = { onUpdateOrderStatus(order.id, OrderStatus.CANCELLED) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text("Reject", color = FoodioNonVegRed, fontWeight = FontWeight.Bold)
                  }

                  Button(
                    onClick = { onUpdateOrderStatus(order.id, OrderStatus.ACCEPTED) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text("Accept (20m)", color = PureWhite, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }
        }

        if (activeKitchenOrders.isNotEmpty()) {
          item {
            Text(
              text = "In Kitchen (${activeKitchenOrders.size})",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp,
              color = FoodioVegGreen,
              modifier = Modifier.padding(top = 8.dp)
            )
          }

          items(activeKitchenOrders) { order ->
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("Order #${order.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                  Surface(
                    color = FoodioOrangeContainer,
                    shape = RoundedCornerShape(4.dp)
                  ) {
                    Text(
                      text = order.orderStatus.label,
                      color = FoodioOrange,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }

                order.items.forEach { item ->
                  Text("• ${item.quantity}x ${item.name}", fontSize = 13.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                  onClick = {
                    val next = if (order.orderStatus == OrderStatus.ACCEPTED) OrderStatus.PREPARING else OrderStatus.OUT_FOR_DELIVERY
                    onUpdateOrderStatus(order.id, next)
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = FoodioVegGreen),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = if (order.orderStatus == OrderStatus.ACCEPTED) "Mark Cooking Started" else "Handover to Delivery Partner",
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                  )
                }
              }
            }
          }
        }
      }
    } else {
      // Menu Management Tab
      Column(modifier = Modifier.fillMaxSize()) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Manage Dishes & Stock", fontWeight = FontWeight.Bold, fontSize = 15.sp)
          Button(
            onClick = { showAddDishDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
            shape = RoundedCornerShape(8.dp)
          ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = PureWhite)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add Dish", color = PureWhite, fontWeight = FontWeight.Bold)
          }
        }

        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(menuItems, key = { it.id }) { item ->
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  VegNonVegIcon(isVeg = item.isVeg)
                  Column {
                    Text(item.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("₹${item.price.toInt()} • ${item.category}", fontSize = 12.sp, color = TextSecondary)
                  }
                }

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Text(
                    text = if (item.isAvailable) "In Stock" else "Sold Out",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (item.isAvailable) FoodioVegGreen else FoodioNonVegRed
                  )
                  Switch(
                    checked = item.isAvailable,
                    onCheckedChange = { onToggleItemAvailability(item.id, it) },
                    colors = SwitchDefaults.colors(
                      checkedThumbColor = PureWhite,
                      checkedTrackColor = FoodioVegGreen
                    )
                  )
                }
              }
            }
          }
        }
      }
    }

    // Add Dish Dialog
    if (showAddDishDialog) {
      AddDishDialog(
        restaurantId = restaurant.id,
        onDismiss = { showAddDishDialog = false },
        onAdd = { newItem ->
          onAddNewItem(newItem)
          showAddDishDialog = false
        }
      )
    }
  }
}

@Composable
fun AddDishDialog(
  restaurantId: String,
  onDismiss: () -> Unit,
  onAdd: (MenuItem) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var price by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Bestsellers") }
  var isVeg by remember { mutableStateOf(true) }
  var description by remember { mutableStateOf("") }

  androidx.compose.material3.AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Add New Menu Item", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Dish Name") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = price,
          onValueChange = { price = it },
          label = { Text("Price (₹)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = category,
          onValueChange = { category = it },
          label = { Text("Category (e.g. Starters, Biryani)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Description") },
          modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
          Switch(
            checked = isVeg,
            onCheckedChange = { isVeg = it },
            colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = FoodioVegGreen)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(if (isVeg) "Pure Veg Dish" else "Non-Veg Dish", fontWeight = FontWeight.SemiBold)
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val priceVal = price.toDoubleOrNull() ?: 150.0
          if (name.isNotBlank()) {
            val item = MenuItem(
              id = "custom_" + System.currentTimeMillis(),
              restaurantId = restaurantId,
              name = name,
              description = description,
              price = priceVal,
              category = category,
              isVeg = isVeg,
              isAvailable = true
            )
            onAdd(item)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange)
      ) {
        Text("Add to Menu", color = PureWhite)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
