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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Order
import com.example.data.model.OrderStatus
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
fun OrdersHistoryScreen(
  orders: List<Order>,
  onOrderClick: (Order) -> Unit,
  onReorderClick: (Order) -> Unit,
  onBrowseFoodClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(PureWhite)
  ) {
    Text(
      text = "Your Orders (${orders.size})",
      fontSize = 20.sp,
      fontWeight = FontWeight.ExtraBold,
      color = TextPrimary,
      modifier = Modifier.padding(16.dp)
    )

    if (orders.isEmpty()) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.History,
          contentDescription = null,
          tint = FoodioOrange.copy(alpha = 0.5f),
          modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "No Orders Yet",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Text(
          text = "Whenever you place an order, you will find its full history and live tracking details right here.",
          fontSize = 13.sp,
          color = TextSecondary,
          textAlign = androidx.compose.ui.text.style.TextAlign.Center,
          modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
          onClick = onBrowseFoodClick,
          colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Order Something Delicious", color = PureWhite, fontWeight = FontWeight.Bold)
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        items(orders, key = { it.id }) { order ->
          val isActive = order.orderStatus != OrderStatus.DELIVERED && order.orderStatus != OrderStatus.CANCELLED

          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOrderClick(order) }
              .testTag("order_item_${order.id}")
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = order.restaurantName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary
                  )
                  Text(
                    text = order.restaurantArea,
                    fontSize = 12.sp,
                    color = TextSecondary
                  )
                  Text(
                    text = "Order #${order.orderNumber}",
                    fontSize = 11.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                  )
                }

                Surface(
                  color = if (isActive) FoodioOrangeContainer else FoodioVegGreen.copy(alpha = 0.15f),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = order.orderStatus.label,
                    color = if (isActive) FoodioOrange else FoodioVegGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }

              HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

              // Items summary
              order.items.forEach { item ->
                Text(
                  text = "${item.quantity} x ${item.name}",
                  fontSize = 13.sp,
                  color = TextSecondary,
                  modifier = Modifier.padding(vertical = 2.dp)
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Total: ₹${order.totalAmount.toInt()}",
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 15.sp,
                  color = TextPrimary
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  if (isActive) {
                    Button(
                      onClick = { onOrderClick(order) },
                      colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
                      shape = RoundedCornerShape(8.dp),
                      modifier = Modifier.testTag("btn_track_order_${order.id}")
                    ) {
                      Text("Track Live", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }
                  } else {
                    OutlinedButton(
                      onClick = { onReorderClick(order) },
                      shape = RoundedCornerShape(8.dp),
                      colors = ButtonDefaults.outlinedButtonColors(contentColor = FoodioOrange),
                      border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange)
                    ) {
                      Text("Reorder", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    if (order.ratingGiven != null) {
                      Surface(
                        color = FoodioRatingAmber.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                      ) {
                        Row(
                          modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                          Icon(Icons.Default.Star, contentDescription = null, tint = FoodioRatingAmber, modifier = Modifier.size(14.dp))
                          Text("${order.ratingGiven?.toInt()}★", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FoodioRatingAmber)
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
