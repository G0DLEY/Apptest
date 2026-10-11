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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.Coupon
import com.example.data.model.DeliveryPartner
import com.example.data.model.Order
import com.example.data.model.Restaurant
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
fun AdminScreen(
  restaurants: List<Restaurant>,
  deliveryPartners: List<DeliveryPartner>,
  orders: List<Order>,
  coupons: List<Coupon>,
  onToggleRestaurantApproval: (String) -> Unit,
  onAddCoupon: (Coupon) -> Unit,
  onDeleteCoupon: (String) -> Unit,
  onSwitchRole: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  var showAddCouponDialog by remember { mutableStateOf(false) }

  val totalGMV = orders.sumOf { it.totalAmount } + 48250.0 // seeded platform volume
  val platformCommission = totalGMV * 0.15 // 15% platform take-rate

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
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = FoodioOrange, modifier = Modifier.size(28.dp))
          Column {
            Text("Foodio Central Admin", fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, color = TextPrimary)
            Text("Ecosystem Analytics & Partner Operations", fontSize = 12.sp, color = TextSecondary)
          }
        }

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
    }

    // High level metrics
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Platform GMV", fontSize = 11.sp, color = TextSecondary)
          Text("₹${totalGMV.toInt()}", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = FoodioOrange)
        }
      }
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Commissions (15%)", fontSize = 11.sp, color = TextSecondary)
          Text("₹${platformCommission.toInt()}", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = FoodioVegGreen)
        }
      }
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Active Partners", fontSize = 11.sp, color = TextSecondary)
          Text("${restaurants.size + deliveryPartners.size}", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        }
      }
    }

    // Admin Tabs
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = PureWhite,
      contentColor = FoodioOrange
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("Restaurants (${restaurants.size})", fontWeight = FontWeight.Bold) }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("Coupons (${coupons.size})", fontWeight = FontWeight.Bold) }
      )
      Tab(
        selected = selectedTab == 2,
        onClick = { selectedTab = 2 },
        text = { Text("Riders (${deliveryPartners.size})", fontWeight = FontWeight.Bold) }
      )
    }

    // Tab content
    when (selectedTab) {
      0 -> {
        // Restaurants approval
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(restaurants) { rest ->
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(rest.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                  Text("${rest.area} • ${rest.cuisineType}", fontSize = 12.sp, color = TextSecondary)
                  Text(
                    text = if (rest.isApproved) "Status: Approved & Live" else "Status: Onboarding Pending",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (rest.isApproved) FoodioVegGreen else FoodioOrange
                  )
                }

                Switch(
                  checked = rest.isApproved,
                  onCheckedChange = { onToggleRestaurantApproval(rest.id) },
                  colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = FoodioVegGreen)
                )
              }
            }
          }
        }
      }

      1 -> {
        // Coupons
        Column(modifier = Modifier.fillMaxSize()) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("Campaign Coupons", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Button(
              onClick = { showAddCouponDialog = true },
              colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = PureWhite)
              Spacer(modifier = Modifier.width(4.dp))
              Text("Create Coupon", color = PureWhite, fontWeight = FontWeight.Bold)
            }
          }

          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(coupons) { coupon ->
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
                  Column(modifier = Modifier.weight(1f)) {
                    Text(coupon.code, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = FoodioOrange)
                    Text(coupon.title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Min order ₹${coupon.minOrderValue.toInt()} • Max discount ₹${coupon.maxDiscount.toInt()}", fontSize = 11.sp, color = TextSecondary)
                  }

                  IconButton(onClick = { onDeleteCoupon(coupon.code) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = FoodioNonVegRed)
                  }
                }
              }
            }
          }
        }
      }

      2 -> {
        // Riders
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(deliveryPartners) { rider ->
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(rider.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                  Text("${rider.vehicle} • ${rider.phone}", fontSize = 12.sp, color = TextSecondary)
                  Text("Deliveries today: ${rider.todayDeliveries} • Earnings: ₹${rider.todayEarnings.toInt()}", fontSize = 11.sp, color = TextMuted)
                }

                Surface(
                  color = if (rider.isOnline) FoodioVegGreen.copy(alpha = 0.15f) else FoodioSurfaceGray,
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = if (rider.isOnline) "ONLINE" else "OFFLINE",
                    color = if (rider.isOnline) FoodioVegGreen else TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    if (showAddCouponDialog) {
      AddCouponDialog(
        onDismiss = { showAddCouponDialog = false },
        onAdd = { newCoupon ->
          onAddCoupon(newCoupon)
          showAddCouponDialog = false
        }
      )
    }
  }
}

@Composable
fun AddCouponDialog(
  onDismiss: () -> Unit,
  onAdd: (Coupon) -> Unit
) {
  var code by remember { mutableStateOf("") }
  var title by remember { mutableStateOf("") }
  var discountPercent by remember { mutableStateOf("25") }
  var maxDiscount by remember { mutableStateOf("120") }
  var minOrder by remember { mutableStateOf("249") }

  androidx.compose.material3.AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Create Promo Coupon", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
          value = code,
          onValueChange = { code = it.uppercase() },
          label = { Text("Coupon Code (e.g. MONSOON30)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("Offer Title") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = discountPercent,
          onValueChange = { discountPercent = it },
          label = { Text("Discount Percentage (%)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = maxDiscount,
          onValueChange = { maxDiscount = it },
          label = { Text("Max Discount Cap (₹)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = minOrder,
          onValueChange = { minOrder = it },
          label = { Text("Min Order Value (₹)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (code.isNotBlank()) {
            val coupon = Coupon(
              code = code,
              title = title.ifBlank { "$discountPercent% OFF up to ₹$maxDiscount" },
              discountPercent = discountPercent.toIntOrNull() ?: 20,
              maxDiscount = maxDiscount.toDoubleOrNull() ?: 100.0,
              minOrderValue = minOrder.toDoubleOrNull() ?: 199.0,
              description = "Special offer valid on all restaurants"
            )
            onAdd(coupon)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange)
      ) {
        Text("Create", color = PureWhite)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
