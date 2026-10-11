package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Address
import com.example.data.model.AddressType
import com.example.data.model.CartCustomizationSelection
import com.example.data.model.Coupon
import com.example.data.model.CustomizationOption
import com.example.data.model.MenuItem
import com.example.data.model.OrderStatus
import com.example.data.model.UserRole
import com.example.ui.theme.FoodioBorderGray
import com.example.ui.theme.FoodioNonVegRed
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
fun VegNonVegIcon(isVeg: Boolean, modifier: Modifier = Modifier) {
  val color = if (isVeg) FoodioVegGreen else FoodioNonVegRed
  Box(
    modifier = modifier
      .size(16.dp)
      .border(1.5.dp, color, RoundedCornerShape(3.dp))
      .padding(3.dp),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(6.dp)
        .background(color, CircleShape)
    )
  }
}

@Composable
fun RatingBadge(rating: Float, count: Int? = null, modifier: Modifier = Modifier) {
  val bgColor = if (rating >= 4.0f) FoodioVegGreen else FoodioRatingAmber
  Surface(
    color = bgColor,
    shape = RoundedCornerShape(6.dp),
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
      Text(
        text = String.format("%.1f", rating),
        color = PureWhite,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
      )
      Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null,
        tint = PureWhite,
        modifier = Modifier.size(11.dp)
      )
      if (count != null) {
        Text(
          text = " ($count)",
          color = PureWhite.copy(alpha = 0.9f),
          fontSize = 10.sp
        )
      }
    }
  }
}

@Composable
fun QuantityStepper(
  quantity: Int,
  onIncrease: () -> Unit,
  onDecrease: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange),
    color = PureWhite,
    shadowElevation = 2.dp,
    modifier = modifier.testTag("quantity_stepper")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
      IconButton(
        onClick = onDecrease,
        modifier = Modifier
          .size(28.dp)
          .testTag("btn_decrease_qty")
      ) {
        Icon(
          imageVector = Icons.Default.Remove,
          contentDescription = "Decrease",
          tint = FoodioOrange,
          modifier = Modifier.size(16.dp)
        )
      }

      Text(
        text = "$quantity",
        color = FoodioOrange,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 8.dp)
      )

      IconButton(
        onClick = onIncrease,
        modifier = Modifier
          .size(28.dp)
          .testTag("btn_increase_qty")
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Increase",
          tint = FoodioOrange,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}

@Composable
fun LiveMapTrackingView(
  orderStatus: OrderStatus,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "map_anim")
  val progressAnim by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3500, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "bike_progress"
  )

  val pulseAnim by infiniteTransition.animateFloat(
    initialValue = 8f,
    targetValue = 24f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_halo"
  )

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF5)),
    modifier = modifier
      .fillMaxWidth()
      .height(200.dp)
  ) {
    Box(modifier = Modifier.fillMaxWidth()) {
      Canvas(modifier = Modifier.matchParentSize()) {
        val width = size.width
        val height = size.height

        // Background city grid blocks
        drawRect(
          color = Color(0xFFDEE5EE),
          topLeft = Offset(width * 0.08f, height * 0.12f),
          size = androidx.compose.ui.geometry.Size(width * 0.35f, height * 0.32f)
        )
        drawRect(
          color = Color(0xFFDEE5EE),
          topLeft = Offset(width * 0.55f, height * 0.15f),
          size = androidx.compose.ui.geometry.Size(width * 0.38f, height * 0.30f)
        )
        drawRect(
          color = Color(0xFFDEE5EE),
          topLeft = Offset(width * 0.15f, height * 0.58f),
          size = androidx.compose.ui.geometry.Size(width * 0.40f, height * 0.32f)
        )

        // Road network lines
        val roadPaint = Color(0xFFFFFFFF)
        val roadStroke = 26f
        drawLine(roadPaint, Offset(0f, height * 0.48f), Offset(width, height * 0.48f), roadStroke)
        drawLine(roadPaint, Offset(width * 0.48f, 0f), Offset(width * 0.48f, height), roadStroke)
        drawLine(roadPaint, Offset(width * 0.15f, 0f), Offset(width * 0.85f, height), roadStroke * 0.8f)

        // Delivery Route Path
        val startPoint = Offset(width * 0.2f, height * 0.35f) // Restaurant
        val bendPoint1 = Offset(width * 0.48f, height * 0.48f)
        val endPoint = Offset(width * 0.8f, height * 0.72f) // Customer Home

        val routePath = Path().apply {
          moveTo(startPoint.x, startPoint.y)
          lineTo(bendPoint1.x, bendPoint1.y)
          lineTo(endPoint.x, endPoint.y)
        }

        // Draw dotted route
        drawPath(
          path = routePath,
          color = FoodioOrange,
          style = Stroke(
            width = 6f,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 15f), 0f)
          )
        )

        // Restaurant Marker (Start)
        drawCircle(
          color = Color(0xFF1F2937),
          radius = 14f,
          center = startPoint
        )
        drawCircle(
          color = PureWhite,
          radius = 6f,
          center = startPoint
        )

        // Customer Home Marker (End)
        drawCircle(
          color = FoodioVegGreen.copy(alpha = 0.25f),
          radius = pulseAnim,
          center = endPoint
        )
        drawCircle(
          color = FoodioVegGreen,
          radius = 16f,
          center = endPoint
        )
        drawCircle(
          color = PureWhite,
          radius = 6f,
          center = endPoint
        )

        // Courier Location based on OrderStatus
        val courierPos = when (orderStatus) {
          OrderStatus.PLACED, OrderStatus.ACCEPTED -> startPoint
          OrderStatus.PREPARING -> startPoint
          OrderStatus.OUT_FOR_DELIVERY -> {
            // Animate along line
            val t = progressAnim
            if (t < 0.5f) {
              val subT = t / 0.5f
              Offset(
                startPoint.x + (bendPoint1.x - startPoint.x) * subT,
                startPoint.y + (bendPoint1.y - startPoint.y) * subT
              )
            } else {
              val subT = (t - 0.5f) / 0.5f
              Offset(
                bendPoint1.x + (endPoint.x - bendPoint1.x) * subT,
                bendPoint1.y + (endPoint.y - bendPoint1.y) * subT
              )
            }
          }
          OrderStatus.DELIVERED -> endPoint
          OrderStatus.CANCELLED -> startPoint
        }

        // Draw Courier Bike Marker
        drawCircle(
          color = FoodioOrange.copy(alpha = 0.3f),
          radius = pulseAnim * 1.2f,
          center = courierPos
        )
        drawCircle(
          color = FoodioOrange,
          radius = 18f,
          center = courierPos
        )
        drawCircle(
          color = PureWhite,
          radius = 8f,
          center = courierPos
        )
      }

      // Map Badges overlay
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = PureWhite.copy(alpha = 0.95f),
          shadowElevation = 3.dp
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .background(FoodioVegGreen, CircleShape)
            )
            Text(
              text = if (orderStatus == OrderStatus.DELIVERED) "Arrived at destination" else "Live GPS tracking",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(20.dp),
          color = FoodioOrange,
          shadowElevation = 3.dp
        ) {
          Text(
            text = when (orderStatus) {
              OrderStatus.PLACED -> "Est. 30 mins"
              OrderStatus.ACCEPTED -> "Est. 28 mins"
              OrderStatus.PREPARING -> "Est. 22 mins"
              OrderStatus.OUT_FOR_DELIVERY -> "Arriving in 8 mins"
              OrderStatus.DELIVERED -> "Delivered"
              OrderStatus.CANCELLED -> "Cancelled"
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = PureWhite,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          )
        }
      }
    }
  }
}

@Composable
fun OrderStatusStepper(
  currentStatus: OrderStatus,
  modifier: Modifier = Modifier
) {
  val steps = listOf(
    OrderStatus.PLACED to "Order Placed",
    OrderStatus.ACCEPTED to "Accepted",
    OrderStatus.PREPARING to "Food Preparing",
    OrderStatus.OUT_FOR_DELIVERY to "Out for Delivery",
    OrderStatus.DELIVERED to "Delivered"
  )

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    steps.forEachIndexed { index, (status, label) ->
      val isCompleted = currentStatus.stepIndex > status.stepIndex
      val isCurrent = currentStatus.stepIndex == status.stepIndex
      val isPending = currentStatus.stepIndex < status.stepIndex

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        // Step indicator circle
        Box(
          modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(
              when {
                isCompleted -> FoodioVegGreen
                isCurrent -> FoodioOrange
                else -> FoodioSurfaceGray
              }
            )
            .border(
              1.dp,
              if (isPending) FoodioBorderGray else Color.Transparent,
              CircleShape
            ),
          contentAlignment = Alignment.Center
        ) {
          if (isCompleted) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = PureWhite,
              modifier = Modifier.size(16.dp)
            )
          } else {
            Text(
              text = "${index + 1}",
              color = if (isCurrent) PureWhite else TextMuted,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
            color = when {
              isCurrent -> FoodioOrange
              isCompleted -> TextPrimary
              else -> TextMuted
            }
          )
          if (isCurrent) {
            Text(
              text = when (status) {
                OrderStatus.PLACED -> "Restaurant is reviewing order details"
                OrderStatus.ACCEPTED -> "Chef is assigned, kitchen warming up"
                OrderStatus.PREPARING -> "Dishes are cooking fresh in the kitchen"
                OrderStatus.OUT_FOR_DELIVERY -> "Delivery partner is on the way to you"
                OrderStatus.DELIVERED -> "Enjoy your delicious hot meal!"
                else -> ""
              },
              fontSize = 12.sp,
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizationBottomSheet(
  item: MenuItem,
  onDismiss: () -> Unit,
  onAddToCart: (List<CartCustomizationSelection>, String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val selectedOptions = remember { mutableStateMapOf<String, CustomizationOption>() }
  var specialInstructions by remember { mutableStateOf("") }

  // Auto-select first option for required groups
  remember(item) {
    item.customizationGroups.forEach { group ->
      if (group.isRequired && group.options.isNotEmpty()) {
        selectedOptions[group.id] = group.options.first()
      }
    }
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = PureWhite
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            VegNonVegIcon(isVeg = item.isVeg)
            Text(
              text = item.name,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
          Text(
            text = "₹${item.price.toInt()}",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
        }
      }

      HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

      item.customizationGroups.forEach { group ->
        Text(
          text = group.title,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary,
          modifier = Modifier.padding(vertical = 6.dp)
        )

        group.options.forEach { option ->
          val isSelected = selectedOptions[group.id]?.id == option.id
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                selectedOptions[group.id] = option
              }
              .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              RadioButton(
                selected = isSelected,
                onClick = { selectedOptions[group.id] = option },
                colors = RadioButtonDefaults.colors(selectedColor = FoodioOrange)
              )
              Text(
                text = option.name,
                fontSize = 14.sp,
                color = TextPrimary
              )
            }
            if (option.price > 0) {
              Text(
                text = "+₹${option.price.toInt()}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = FoodioOrange
              )
            }
          }
        }
        Spacer(modifier = Modifier.height(8.dp))
      }

      val extraPrice = selectedOptions.values.sumOf { it.price }
      val totalPrice = item.price + extraPrice

      Button(
        onClick = {
          val selections = selectedOptions.map { (groupId, option) ->
            val group = item.customizationGroups.find { it.id == groupId }
            CartCustomizationSelection(
              groupTitle = group?.title ?: "",
              optionName = option.name,
              price = option.price
            )
          }
          onAddToCart(selections, specialInstructions)
          onDismiss()
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp)
          .height(50.dp)
          .testTag("btn_add_customized_item"),
        colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Add Item", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PureWhite)
          Text("₹${totalPrice.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = PureWhite)
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponSelectionBottomSheet(
  coupons: List<Coupon>,
  currentTotal: Double,
  onDismiss: () -> Unit,
  onApplyCoupon: (Coupon) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = PureWhite
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      Text(
        text = "Available Offers & Coupons",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
      Text(
        text = "Select an offer to get instant discounts on this order",
        fontSize = 12.sp,
        color = TextSecondary,
        modifier = Modifier.padding(bottom = 12.dp)
      )

      coupons.forEach { coupon ->
        val isApplicable = currentTotal >= coupon.minOrderValue
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = FoodioSurfaceGray),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Surface(
                color = FoodioOrangeContainer,
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = coupon.code,
                  color = FoodioOrange,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
              }
              Text(
                text = coupon.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextPrimary,
                modifier = Modifier.padding(top = 4.dp)
              )
              Text(
                text = coupon.description,
                fontSize = 11.sp,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
              )
            }

            Button(
              onClick = {
                if (isApplicable) {
                  onApplyCoupon(coupon)
                  onDismiss()
                }
              },
              enabled = isApplicable,
              colors = ButtonDefaults.buttonColors(
                containerColor = FoodioOrange,
                disabledContainerColor = FoodioBorderGray
              ),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.padding(start = 12.dp)
            ) {
              Text(
                text = if (isApplicable) "APPLY" else "₹${coupon.minOrderValue.toInt()} min",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isApplicable) PureWhite else TextMuted
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RazorpaySandboxDialog(
  totalAmount: Double,
  onDismiss: () -> Unit,
  onPaymentSuccess: (String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var selectedMethod by remember { mutableStateOf("UPI") }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = PureWhite
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.Payment, contentDescription = null, tint = FoodioOrange)
            Text(
              text = "Razorpay Sandbox Gateway",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
          Text(
            text = "Total to Pay: ₹${totalAmount.toInt()}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = FoodioOrange
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
        }
      }

      HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

      val paymentOptions = listOf(
        "UPI" to "Instant UPI (GPay / PhonePe / Paytm)",
        "CREDIT_CARD" to "Credit / Debit Card (Visa, Mastercard, RuPay)",
        "WALLET" to "Foodio Wallet / Amazon Pay",
        "COD" to "Cash On Delivery (Pay upon arrival)"
      )

      paymentOptions.forEach { (key, title) ->
        val isSelected = selectedMethod == key
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) FoodioOrangeContainer else FoodioSurfaceGray
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) FoodioOrange else FoodioBorderGray
          ),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedMethod = key }
            .padding(vertical = 4.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            RadioButton(
              selected = isSelected,
              onClick = { selectedMethod = key },
              colors = RadioButtonDefaults.colors(selectedColor = FoodioOrange)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = title,
              fontSize = 14.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = TextPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          onPaymentSuccess(selectedMethod)
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("btn_confirm_pay")
      ) {
        Text(
          text = if (selectedMethod == "COD") "Place COD Order (₹${totalAmount.toInt()})" else "Pay ₹${totalAmount.toInt()} (Test Sandbox)",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = PureWhite
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSelectionSheet(
  addresses: List<Address>,
  selectedAddressId: String,
  onDismiss: () -> Unit,
  onSelectAddress: (String) -> Unit,
  onAddNewAddress: (Address) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var showAddForm by remember { mutableStateOf(false) }
  var newLabel by remember { mutableStateOf("My Place") }
  var newStreet by remember { mutableStateOf("") }
  var newLandmark by remember { mutableStateOf("") }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = PureWhite
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      Text(
        text = "Select Delivery Address",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      if (!showAddForm) {
        Spacer(modifier = Modifier.height(12.dp))
        addresses.forEach { address ->
          val isSelected = address.id == selectedAddressId
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isSelected) FoodioOrangeContainer else FoodioSurfaceGray
            ),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isSelected) FoodioOrange else FoodioBorderGray
            ),
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                onSelectAddress(address.id)
                onDismiss()
              }
              .padding(vertical = 4.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = if (isSelected) FoodioOrange else TextMuted,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = address.label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextPrimary
                  )
                  if (isSelected) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "(Selected)",
                      fontSize = 11.sp,
                      color = FoodioOrange,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
                Text(
                  text = "${address.street}, ${address.city}",
                  fontSize = 12.sp,
                  color = TextSecondary,
                  maxLines = 2,
                  overflow = TextOverflow.Ellipsis
                )
                if (address.landmark.isNotBlank()) {
                  Text(
                    text = "Landmark: ${address.landmark}",
                    fontSize = 11.sp,
                    color = TextMuted
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
          onClick = { showAddForm = true },
          colors = ButtonDefaults.buttonColors(containerColor = FoodioSurfaceGray),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Add, contentDescription = null, tint = FoodioOrange)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Add New Address", color = FoodioOrange, fontWeight = FontWeight.Bold)
          }
        }
      } else {
        // Quick add address form
        Spacer(modifier = Modifier.height(12.dp))
        androidx.compose.material3.OutlinedTextField(
          value = newLabel,
          onValueChange = { newLabel = it },
          label = { Text("Label (e.g. Friends Flat, Office)") },
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.OutlinedTextField(
          value = newStreet,
          onValueChange = { newStreet = it },
          label = { Text("Flat / House No. & Street Name") },
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.OutlinedTextField(
          value = newLandmark,
          onValueChange = { newLandmark = it },
          label = { Text("Nearby Landmark") },
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          TextButton(
            onClick = { showAddForm = false },
            modifier = Modifier.weight(1f)
          ) {
            Text("Back")
          }
          Button(
            onClick = {
              if (newStreet.isNotBlank()) {
                val created = Address(
                  id = "addr_" + System.currentTimeMillis(),
                  type = AddressType.OTHER,
                  label = newLabel,
                  street = newStreet,
                  city = "Bengaluru",
                  landmark = newLandmark
                )
                onAddNewAddress(created)
                onDismiss()
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
            modifier = Modifier.weight(1f)
          ) {
            Text("Save Address", color = PureWhite, fontWeight = FontWeight.Bold)
          }
        }
      }
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSwitcherDialog(
  currentRole: UserRole,
  onDismiss: () -> Unit,
  onRoleSelected: (UserRole) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = PureWhite
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      Text(
        text = "Switch User Role / Portal",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
      Text(
        text = "Experience all 4 actors of the food delivery ecosystem seamlessly",
        fontSize = 12.sp,
        color = TextSecondary,
        modifier = Modifier.padding(bottom = 12.dp)
      )

      val roles = listOf(
        UserRole.CUSTOMER to ("Customer App" to "Browse 10 restaurants, customize dishes, cart, checkout, live map tracking"),
        UserRole.RESTAURANT_PARTNER to ("Restaurant Partner" to "Manage Meghana Biryani menu, stock toggles, accept orders with prep time"),
        UserRole.DELIVERY_PARTNER to ("Delivery Partner" to "Toggle online/offline, view pickup/drop tasks, complete delivery & earnings"),
        UserRole.ADMIN to ("Admin Dashboard" to "View platform GMV, order volume, restaurant approvals & coupon controls")
      )

      roles.forEach { (role, info) ->
        val isSelected = currentRole == role
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) FoodioOrangeContainer else FoodioSurfaceGray
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) FoodioOrange else FoodioBorderGray
          ),
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              onRoleSelected(role)
              onDismiss()
            }
            .padding(vertical = 4.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            RadioButton(
              selected = isSelected,
              onClick = {
                onRoleSelected(role)
                onDismiss()
              },
              colors = RadioButtonDefaults.colors(selectedColor = FoodioOrange)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = info.first,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (isSelected) FoodioOrange else TextPrimary
              )
              Text(
                text = info.second,
                fontSize = 12.sp,
                color = TextSecondary
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
