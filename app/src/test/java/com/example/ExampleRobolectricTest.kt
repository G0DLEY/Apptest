package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.OrderStatus
import com.example.data.model.UserRole
import com.example.data.repository.FoodioRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun readStringFromContext() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Foodio", appName)
  }

  @Test
  fun testSeedDataHasTenRestaurants() {
    val repo = FoodioRepository()
    assertEquals(10, repo.restaurants.value.size)
  }

  @Test
  fun testCartAndOrderCreationFlow() {
    val repo = FoodioRepository()
    val restaurant = repo.restaurants.value.first()
    val dishes = repo.menuItems.value[restaurant.id] ?: emptyList()
    assertTrue(dishes.isNotEmpty())

    val firstDish = dishes.first()
    repo.addToCart(firstDish)
    assertEquals(1, repo.cartItems.value.size)
    assertEquals(firstDish.price, repo.cartItems.value.first().totalPrice, 0.01)

    // Apply coupon WELCOME50
    val welcomeCoupon = repo.coupons.value.find { it.code == "WELCOME50" }
    assertNotNull(welcomeCoupon)
    repo.applyCoupon(welcomeCoupon!!)

    // Place order
    val placedOrder = repo.placeOrder("UPI")
    assertEquals(OrderStatus.PLACED, placedOrder.orderStatus)
    assertTrue(repo.cartItems.value.isEmpty())
    assertEquals(placedOrder.id, repo.activeOrderId.value)
  }

  @Test
  fun testRoleSwitching() {
    val repo = FoodioRepository()
    assertEquals(UserRole.CUSTOMER, repo.currentRole.value)

    repo.setRole(UserRole.RESTAURANT_PARTNER)
    assertEquals(UserRole.RESTAURANT_PARTNER, repo.currentRole.value)

    repo.setRole(UserRole.DELIVERY_PARTNER)
    assertEquals(UserRole.DELIVERY_PARTNER, repo.currentRole.value)

    repo.setRole(UserRole.ADMIN)
    assertEquals(UserRole.ADMIN, repo.currentRole.value)
  }
}
