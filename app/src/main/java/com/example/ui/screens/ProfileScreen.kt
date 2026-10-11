package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.ui.theme.FoodioBorderGray
import com.example.ui.theme.FoodioOrange
import com.example.ui.theme.FoodioOrangeContainer
import com.example.ui.theme.FoodioSurfaceGray
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
  userProfile: UserProfile,
  currentRole: UserRole,
  isLoggedIn: Boolean,
  onOpenRoleSwitcher: () -> Unit,
  onOpenAddresses: () -> Unit,
  onOpenAuthDialog: () -> Unit,
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  var notificationsEnabled by remember { mutableStateOf(true) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(FoodioSurfaceGray)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Profile Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(FoodioOrangeContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = FoodioOrange,
                modifier = Modifier.size(32.dp)
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = userProfile.name,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = TextPrimary
              )
              Text(
                text = userProfile.phone,
                fontSize = 13.sp,
                color = TextSecondary
              )
              Text(
                text = userProfile.email,
                fontSize = 12.sp,
                color = TextMuted
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Current Role Pill + Switch Portal Button
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = FoodioOrangeContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, FoodioOrange.copy(alpha = 0.5f)),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenRoleSwitcher() }
              .testTag("btn_profile_switch_role")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = FoodioOrange)
                Column {
                  Text(
                    text = "Current View: ${currentRole.name.replace("_", " ")}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodioOrange
                  )
                  Text(
                    text = "Tap to switch to Restaurant, Delivery or Admin",
                    fontSize = 11.sp,
                    color = TextSecondary
                  )
                }
              }
              Icon(Icons.Default.ChevronRight, contentDescription = null, tint = FoodioOrange)
            }
          }
        }
      }
    }

    // Account Options
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
      ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
          // Addresses
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenAddresses() }
              .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Icon(Icons.Default.LocationOn, contentDescription = null, tint = FoodioOrange)
              Column {
                Text("Saved Delivery Addresses", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("${userProfile.addresses.size} addresses saved", fontSize = 12.sp, color = TextSecondary)
              }
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
          }

          HorizontalDivider(color = FoodioBorderGray.copy(alpha = 0.5f))

          // Notifications toggle
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Icon(Icons.Default.Notifications, contentDescription = null, tint = FoodioOrange)
              Column {
                Text("Order Status Notifications", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Live alerts for order preparation & delivery", fontSize = 12.sp, color = TextSecondary)
              }
            }
            Switch(
              checked = notificationsEnabled,
              onCheckedChange = { notificationsEnabled = it },
              colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = FoodioOrange)
            )
          }

          HorizontalDivider(color = FoodioBorderGray.copy(alpha = 0.5f))

          // Phone / OTP Login
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenAuthDialog() }
              .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Icon(Icons.Default.Phone, contentDescription = null, tint = FoodioOrange)
              Column {
                Text("Phone & OTP Authentication", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(if (isLoggedIn) "Verified as ${userProfile.phone}" else "Sign in / Register", fontSize = 12.sp, color = TextSecondary)
              }
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
          }
        }
      }
    }

    // App Information & Policies
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, FoodioBorderGray)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Foodio Delivery MVP",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = TextPrimary
          )
          Text(
            text = "Version 1.0.0 • Connected to local reactive state with real-time multi-actor flow",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.padding(top = 2.dp)
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedButton(
            onClick = onLogout,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(if (isLoggedIn) "Sign Out" else "Sign In", color = FoodioOrange, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
