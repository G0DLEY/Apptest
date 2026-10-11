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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DeliveryPartner
import com.example.data.model.Order
import com.example.data.model.OrderStatus
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
fun DeliveryPartnerScreen(
  partner: DeliveryPartner,
  orders: List<Order>,
  onToggleOnline: () -> Unit,
  onPickupOrder: (String) -> Unit,
  onCompleteDelivery: (String) -> Unit,
  onSwitchRole: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Orders available or assigned to delivery partner
  val pendingPickups = orders.filter {
    it.orderStatus == OrderStatus.PREPARING || it.orderStatus == OrderStatus.ACCEPTED
  }
  val activeDeliveries = orders.filter {
    it.orderStatus == OrderStatus.OUT_FOR_DELIVERY
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
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .background(FoodioOrangeContainer, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.DirectionsBike, contentDescription = null, tint = FoodioOrange)
            }
            Column {
              Text(partner.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
              Text(partner.vehicle, fontSize = 12.sp, color = TextSecondary)
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

        Spacer(modifier = Modifier.height(12.dp))

        // Duty Toggle Card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (partner.isOnline) FoodioVegGreen.copy(alpha = 0.12f) else FoodioSurfaceGray
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (partner.isOnline) FoodioVegGreen else FoodioBorderGray
          )
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .background(if (partner.isOnline) FoodioVegGreen else TextMuted, CircleShape)
              )
              Text(
                text = if (partner.isOnline) "You are ONLINE (Receiving Orders)" else "You are OFFLINE",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (partner.isOnline) FoodioVegGreen else TextSecondary
              )
            }

            Switch(
              checked = partner.isOnline,
              onCheckedChange = { onToggleOnline() },
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
          Text("Today's Earnings", fontSize = 11.sp, color = TextSecondary)
          Text("₹${partner.todayEarnings.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = FoodioOrange)
        }
      }

      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Trips Completed", fontSize = 11.sp, color = TextSecondary)
          Text("${partner.todayDeliveries}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        }
      }

      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("Rider Rating", fontSize = 11.sp, color = TextSecondary)
          Text("${partner.rating} ★", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = FoodioVegGreen)
        }
      }
    }

    // Task List
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      if (!partner.isOnline) {
        item {
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Icon(Icons.Default.PowerSettingsNew, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
              Spacer(modifier = Modifier.height(8.dp))
              Text("Go Online to Receive Orders", fontWeight = FontWeight.Bold, fontSize = 15.sp)
              Text("Toggle the switch above to start earning ₹65/delivery", fontSize = 12.sp, color = TextSecondary)
            }
          }
        }
      } else {
        // Active in-transit delivery
        if (activeDeliveries.isNotEmpty()) {
          item {
            Text(
              text = "In Delivery (${activeDeliveries.size})",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp,
              color = FoodioOrange
            )
          }

          items(activeDeliveries) { order ->
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.5.dp, FoodioOrange)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("Delivering Order #${order.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                  Text("Payout: ₹65", fontWeight = FontWeight.Bold, color = FoodioVegGreen, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Drop Location
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Icon(Icons.Default.LocationOn, contentDescription = null, tint = FoodioOrange)
                  Column {
                    Text("Customer Drop: ${order.customerName}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("${order.deliveryAddress.street}, ${order.deliveryAddress.city}", fontSize = 12.sp, color = TextSecondary)
                  }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                  onClick = { onCompleteDelivery(order.id) },
                  colors = ButtonDefaults.buttonColors(containerColor = FoodioVegGreen),
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_complete_delivery_${order.id}")
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PureWhite)
                    Text("Delivered to Customer (Collect ₹${order.totalAmount.toInt()})", fontWeight = FontWeight.Bold, color = PureWhite)
                  }
                }
              }
            }
          }
        }

        // Available pickups at restaurant
        if (pendingPickups.isNotEmpty()) {
          item {
            Text(
              text = "Ready for Pickup (${pendingPickups.size})",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp,
              color = TextPrimary
            )
          }

          items(pendingPickups) { order ->
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("Pickup at ${order.restaurantName}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                  Text("Trip: ~2.4 km", fontSize = 12.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Icon(Icons.Default.Store, contentDescription = null, tint = FoodioOrange)
                  Text("${order.restaurantArea} (Order #${order.orderNumber})", fontSize = 13.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                  onClick = { onPickupOrder(order.id) },
                  colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(Icons.Default.Navigation, contentDescription = null, tint = PureWhite)
                    Text("Picked Up from Kitchen ➔", fontWeight = FontWeight.Bold, color = PureWhite)
                  }
                }
              }
            }
          }
        }

        if (activeDeliveries.isEmpty() && pendingPickups.isEmpty()) {
          item {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text("No Pending Deliveries", fontWeight = FontWeight.Bold, fontSize = 16.sp)
              Text("Place an order from the Customer app to see pickup alerts here!", fontSize = 12.sp, color = TextSecondary)
            }
          }
        }
      }
    }
  }
}
