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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.ui.components.LiveMapTrackingView
import com.example.ui.components.OrderStatusStepper
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
fun LiveOrderTrackingScreen(
  order: Order,
  onBackClick: () -> Unit,
  onUpdateStatus: (OrderStatus) -> Unit,
  onSubmitRating: (Float, String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedRating by remember { mutableFloatStateOf(order.ratingGiven ?: 5.0f) }
  var reviewText by remember { mutableStateOf(order.reviewGiven ?: "") }
  var ratingSubmitted by remember { mutableStateOf(order.ratingGiven != null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(FoodioSurfaceGray),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top Bar
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.testTag("btn_back_tracking")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
        }
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Track Order #${order.orderNumber}",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = order.restaurantName,
            fontSize = 13.sp,
            color = TextSecondary
          )
        }
      }
    }

    // Live Animated Canvas Map
    item {
      LiveMapTrackingView(orderStatus = order.orderStatus)
    }

    // Delivery Partner Info Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(48.dp)
                  .background(FoodioOrangeContainer, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.DirectionsBike,
                  contentDescription = null,
                  tint = FoodioOrange,
                  modifier = Modifier.size(24.dp)
                )
              }

              Column {
                Text(
                  text = order.deliveryPartnerName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = TextPrimary
                )
                Text(
                  text = order.deliveryPartnerVehicle,
                  fontSize = 12.sp,
                  color = TextSecondary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = FoodioRatingAmber,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(2.dp))
                  Text(
                    text = "${order.deliveryPartnerRating} Rating",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                  )
                }
              }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              IconButton(
                onClick = { /* simulated partner call */ },
                modifier = Modifier
                  .size(40.dp)
                  .background(FoodioVegGreen.copy(alpha = 0.15f), CircleShape)
              ) {
                Icon(Icons.Default.Call, contentDescription = "Call", tint = FoodioVegGreen, modifier = Modifier.size(20.dp))
              }
              IconButton(
                onClick = { /* simulated partner chat */ },
                modifier = Modifier
                  .size(40.dp)
                  .background(FoodioOrangeContainer, CircleShape)
              ) {
                Icon(Icons.Default.Chat, contentDescription = "Chat", tint = FoodioOrange, modifier = Modifier.size(20.dp))
              }
            }
          }
        }
      }
    }

    // Order Progression Stepper
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Delivery Milestones",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          OrderStatusStepper(currentStatus = order.orderStatus)
        }
      }
    }

    // Quick Simulator Controls for the user
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FoodioOrangeContainer),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange.copy(alpha = 0.3f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "⚡ Instant Simulation Controls",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = FoodioOrange
          )
          Text(
            text = "Test every stage immediately without waiting for timers:",
            fontSize = 11.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            OutlinedButton(
              onClick = { onUpdateStatus(OrderStatus.ACCEPTED) },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
              Text("Accept", fontSize = 11.sp, color = FoodioOrange)
            }
            OutlinedButton(
              onClick = { onUpdateStatus(OrderStatus.PREPARING) },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
              Text("Cooking", fontSize = 11.sp, color = FoodioOrange)
            }
            OutlinedButton(
              onClick = { onUpdateStatus(OrderStatus.OUT_FOR_DELIVERY) },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
              Text("On Way", fontSize = 11.sp, color = FoodioOrange)
            }
            Button(
              onClick = { onUpdateStatus(OrderStatus.DELIVERED) },
              modifier = Modifier.weight(1f),
              colors = ButtonDefaults.buttonColors(containerColor = FoodioVegGreen),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
              Text("Deliver", fontSize = 11.sp, color = PureWhite)
            }
          }
        }
      }
    }

    // Rating & Feedback Card (if Delivered)
    if (order.orderStatus == OrderStatus.DELIVERED) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = PureWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = FoodioVegGreen)
              Text(
                text = "Order Delivered! How was the food?",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextPrimary
              )
            }

            if (!ratingSubmitted) {
              Spacer(modifier = Modifier.height(10.dp))
              // Star Selector
              Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
              ) {
                (1..5).forEach { star ->
                  IconButton(onClick = { selectedRating = star.toFloat() }) {
                    Icon(
                      imageVector = Icons.Default.Star,
                      contentDescription = "$star stars",
                      tint = if (star <= selectedRating) FoodioRatingAmber else FoodioBorderGray,
                      modifier = Modifier.size(32.dp)
                    )
                  }
                }
              }

              OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                placeholder = { Text("Write a quick review about the taste & delivery...") },
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
              )

              Button(
                onClick = {
                  onSubmitRating(selectedRating, reviewText)
                  ratingSubmitted = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text("Submit Review", fontWeight = FontWeight.Bold, color = PureWhite)
              }
            } else {
              Spacer(modifier = Modifier.height(8.dp))
              Surface(
                color = FoodioVegGreen.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Text(
                    text = "Thank you for your ${selectedRating.toInt()}★ rating!",
                    fontWeight = FontWeight.Bold,
                    color = FoodioVegGreen,
                    fontSize = 14.sp
                  )
                  if (reviewText.isNotBlank()) {
                    Text(
                      text = "\"$reviewText\"",
                      color = TextSecondary,
                      fontSize = 12.sp,
                      modifier = Modifier.padding(top = 4.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // Order Items Summary Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "Order Summary",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = TextPrimary
          )

          order.items.forEach { item ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "${item.quantity}x ${item.name}",
                fontSize = 13.sp,
                color = TextPrimary
              )
              Text(
                text = "₹${(item.price * item.quantity).toInt()}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
              )
            }
          }

          HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Total Paid (${order.paymentMethod})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("₹${order.totalAmount.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = FoodioOrange)
          }
        }
      }
    }
  }
}
