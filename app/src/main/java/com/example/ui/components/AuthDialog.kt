package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun PhoneOtpAuthDialog(
  initialPhone: String,
  initialName: String,
  onDismiss: () -> Unit,
  onLoginSuccess: (phone: String, name: String) -> Unit
) {
  var phone by remember { mutableStateOf(if (initialPhone.isNotBlank()) initialPhone else "+91 98765 12340") }
  var name by remember { mutableStateOf(if (initialName.isNotBlank()) initialName else "Aman Verma") }
  var otpSent by remember { mutableStateOf(false) }
  var otpInput by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    containerColor = PureWhite,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.Phone, contentDescription = null, tint = FoodioOrange)
          Text(
            text = if (!otpSent) "Login / Sign Up" else "Verify 4-digit OTP",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
        }
      }
    },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (!otpSent) {
          Text(
            text = "Enter your mobile number to get an instant OTP for login and order updates.",
            fontSize = 13.sp,
            color = TextSecondary
          )

          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your Full Name") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FoodioOrange,
              unfocusedBorderColor = FoodioBorderGray
            ),
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FoodioOrange,
              unfocusedBorderColor = FoodioBorderGray
            ),
            modifier = Modifier.fillMaxWidth()
          )
        } else {
          Surface(
            color = FoodioOrangeContainer,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(Icons.Default.Security, contentDescription = null, tint = FoodioOrange)
              Text(
                text = "OTP sent to $phone\n(Hint: Enter 1234 or any 4 digits)",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = FoodioOrange
              )
            }
          }

          OutlinedTextField(
            value = otpInput,
            onValueChange = { if (it.length <= 4) otpInput = it },
            label = { Text("Enter 4-Digit OTP") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FoodioOrange,
              unfocusedBorderColor = FoodioBorderGray
            ),
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    },
    confirmButton = {
      if (!otpSent) {
        Button(
          onClick = {
            if (phone.isNotBlank()) {
              otpSent = true
              otpInput = "1234"
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = FoodioOrange),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("btn_send_otp")
        ) {
          Text("Send OTP", fontWeight = FontWeight.Bold, color = PureWhite)
        }
      } else {
        Button(
          onClick = {
            onLoginSuccess(phone, name)
            onDismiss()
          },
          colors = ButtonDefaults.buttonColors(containerColor = FoodioVegGreen),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("btn_verify_otp")
        ) {
          Text("Verify & Continue", fontWeight = FontWeight.Bold, color = PureWhite)
        }
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel", color = TextSecondary)
      }
    }
  )
}
