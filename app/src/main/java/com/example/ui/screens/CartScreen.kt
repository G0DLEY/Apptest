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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RemoveShoppingCart
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Address
import com.example.data.model.CartItem
import com.example.data.model.Coupon
import com.example.data.model.Restaurant
import com.example.ui.components.QuantityStepper
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
fun CartScreen(
  cartItems: List<CartItem>,
  restaurant: Restaurant?,
  appliedCoupon: Coupon?,
  specialInstructions: String,
  currentAddress: Address,
  onBackClick: () -> Unit,
  onUpdateQuantity: (Int, Int) -> Unit,
  onSpecialInstructionsChange: (String) -> Unit,
  onOpenCouponSheet: () -> Unit,
  onRemoveCoupon: () -> Unit,
  onChangeAddressClick: () -> Unit,
  onProceedToPayClick: () -> Unit,
  onBrowseRestaurantsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  if (cartItems.isEmpty()) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .background(PureWhite)
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = Icons.Default.RemoveShoppingCart,
        contentDescription = "Empty cart",
        tint = FoodioOrange.copy(alpha = 0.5f),
        modifier = Modifier.size(80.dp)
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = "Your Cart is Empty",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
      Text(
        text = "Good food is always just a few taps away. Explore restaurants to add delicious items!",
        fontSize = 14.sp,
        color = TextSecondary,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
      )
      Spacer(modifier = Modifier.height(20.dp))
      Button(
        onClick = onBrowseRestaurantsClick,
        colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth(0.7f)
          .height(48.dp)
      ) {
        Text("Explore Restaurants", fontWeight = FontWeight.Bold, color = PureWhite)
      }
    }
    return
  }

  val itemTotal = cartItems.sumOf { it.totalPrice }
  val deliveryFee = if (itemTotal > 500) 0.0 else 35.0
  val taxes = (itemTotal * 0.05).coerceAtLeast(10.0)
  val platformFee = 5.0
  val discount = if (appliedCoupon != null && itemTotal >= appliedCoupon.minOrderValue) {
    ((itemTotal * appliedCoupon.discountPercent) / 100.0).coerceAtMost(appliedCoupon.maxDiscount)
  } else 0.0
  val toPay = (itemTotal + deliveryFee + taxes + platformFee - discount).coerceAtLeast(0.0)

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(FoodioSurfaceGray)
  ) {
    // Top Bar
    Surface(
      color = PureWhite,
      shadowElevation = 2.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.testTag("btn_back_cart")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
        }
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = restaurant?.name ?: "Restaurant",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = TextPrimary
          )
          Text(
            text = restaurant?.area ?: "Foodio Delivery",
            fontSize = 12.sp,
            color = TextSecondary
          )
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Cart Items List Card
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = PureWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Ordered Items",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = TextPrimary,
              modifier = Modifier.padding(bottom = 12.dp)
            )

            cartItems.forEachIndexed { index, cartItem ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
              ) {
                Row(
                  modifier = Modifier.weight(1f),
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  verticalAlignment = Alignment.Top
                ) {
                  VegNonVegIcon(isVeg = cartItem.menuItem.isVeg, modifier = Modifier.padding(top = 4.dp))
                  Column {
                    Text(
                      text = cartItem.menuItem.name,
                      fontWeight = FontWeight.SemiBold,
                      fontSize = 14.sp,
                      color = TextPrimary
                    )
                    if (cartItem.selectedOptions.isNotEmpty()) {
                      Text(
                        text = cartItem.selectedOptions.joinToString(", ") { it.optionName },
                        fontSize = 11.sp,
                        color = TextSecondary
                      )
                    }
                    Text(
                      text = "₹${cartItem.unitPrice.toInt()}",
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                      color = TextPrimary,
                      modifier = Modifier.padding(top = 2.dp)
                    )
                  }
                }

                QuantityStepper(
                  quantity = cartItem.quantity,
                  onIncrease = { onUpdateQuantity(index, cartItem.quantity + 1) },
                  onDecrease = { onUpdateQuantity(index, cartItem.quantity - 1) }
                )
              }

              if (index < cartItems.size - 1) {
                HorizontalDivider(color = FoodioBorderGray.copy(alpha = 0.5f))
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cooking / Special instructions field
            OutlinedTextField(
              value = specialInstructions,
              onValueChange = onSpecialInstructionsChange,
              placeholder = { Text("Special cooking instructions (e.g. less spicy)") },
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodioOrange,
                unfocusedBorderColor = FoodioBorderGray
              ),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            )
          }
        }
      }

      // Offers & Coupons Card
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = PureWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray),
          modifier = Modifier.clickable { onOpenCouponSheet() }
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
              horizontalArrangement = Arrangement.spacedBy(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.LocalOffer, contentDescription = null, tint = FoodioOrange)
              Column {
                if (appliedCoupon != null) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = "${appliedCoupon.code} Applied",
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      color = FoodioVegGreen
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                      onClick = onRemoveCoupon,
                      modifier = Modifier.size(20.dp)
                    ) {
                      Icon(Icons.Default.Close, contentDescription = "Remove coupon", tint = TextMuted)
                    }
                  }
                  Text(
                    text = "You save ₹${discount.toInt()} on this order",
                    fontSize = 12.sp,
                    color = TextSecondary
                  )
                } else {
                  Text(
                    text = "Apply Coupon Code",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextPrimary
                  )
                  Text(
                    text = "Tap to view available discount coupons",
                    fontSize = 12.sp,
                    color = TextSecondary
                  )
                }
              }
            }

            Icon(
              imageVector = Icons.Default.KeyboardArrowRight,
              contentDescription = null,
              tint = TextMuted
            )
          }
        }
      }

      // Bill Breakdown Card
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
              text = "Bill Details",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = TextPrimary,
              modifier = Modifier.padding(bottom = 4.dp)
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Item Total", fontSize = 13.sp, color = TextSecondary)
              Text("₹${itemTotal.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Delivery Fee", fontSize = 13.sp, color = TextSecondary)
              if (deliveryFee == 0.0) {
                Text("FREE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FoodioVegGreen)
              } else {
                Text("₹${deliveryFee.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
              }
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Platform Fee", fontSize = 13.sp, color = TextSecondary)
              Text("₹${platformFee.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("GST and Restaurant Charges (5%)", fontSize = 13.sp, color = TextSecondary)
              Text("₹${taxes.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }

            if (discount > 0) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("Coupon Discount (${appliedCoupon?.code})", fontSize = 13.sp, color = FoodioVegGreen, fontWeight = FontWeight.Bold)
                Text("-₹${discount.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FoodioVegGreen)
              }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("TO PAY", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
              Text("₹${toPay.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = FoodioOrange)
            }
          }
        }
      }

      // Delivery Address Card
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = PureWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
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
              horizontalArrangement = Arrangement.spacedBy(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.LocationOn, contentDescription = null, tint = FoodioOrange)
              Column {
                Text(
                  text = "Deliver to: ${currentAddress.label}",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = TextPrimary
                )
                Text(
                  text = "${currentAddress.street}, ${currentAddress.city}",
                  fontSize = 12.sp,
                  color = TextSecondary,
                  maxLines = 1
                )
              }
            }

            TextButton(onClick = onChangeAddressClick) {
              Text("CHANGE", color = FoodioOrange, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Checkout Bottom Bar
    Surface(
      color = PureWhite,
      shadowElevation = 8.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text("Total to Pay", fontSize = 12.sp, color = TextSecondary)
          Text("₹${toPay.toInt()}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        }

        Button(
          onClick = onProceedToPayClick,
          colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .height(50.dp)
            .testTag("btn_proceed_checkout")
        ) {
          Text(
            text = "PROCEED TO PAY ➔",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            color = PureWhite
          )
        }
      }
    }
  }
}
