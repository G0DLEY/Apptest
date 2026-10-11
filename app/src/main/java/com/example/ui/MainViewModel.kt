package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Address
import com.example.data.model.CartCustomizationSelection
import com.example.data.model.Coupon
import com.example.data.model.MenuItem
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.Restaurant
import com.example.data.model.UserRole
import com.example.data.repository.FoodioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
  val repository: FoodioRepository = FoodioRepository()
) : ViewModel() {

  val restaurants = repository.restaurants
  val menuItems = repository.menuItems
  val currentUser = repository.currentUser
  val currentRole = repository.currentRole
  val isLoggedIn = repository.isLoggedIn
  val cartItems = repository.cartItems
  val appliedCoupon = repository.appliedCoupon
  val specialInstructions = repository.specialInstructions
  val orders = repository.orders
  val activeOrderId = repository.activeOrderId
  val deliveryPartners = repository.deliveryPartners
  val coupons = repository.coupons
  val reviews = repository.reviews
  val favorites = repository.favorites

  // Navigation & Sub-screen states
  private val _customerTab = MutableStateFlow(0) // 0: Home, 1: Search, 2: Orders, 3: Profile
  val customerTab: StateFlow<Int> = _customerTab.asStateFlow()

  private val _selectedRestaurant = MutableStateFlow<Restaurant?>(null)
  val selectedRestaurant: StateFlow<Restaurant?> = _selectedRestaurant.asStateFlow()

  private val _selectedOrder = MutableStateFlow<Order?>(null)
  val selectedOrder: StateFlow<Order?> = _selectedOrder.asStateFlow()

  private val _isViewingCart = MutableStateFlow(false)
  val isViewingCart: StateFlow<Boolean> = _isViewingCart.asStateFlow()

  // Modals & Sheets
  private val _showAddressSheet = MutableStateFlow(false)
  val showAddressSheet: StateFlow<Boolean> = _showAddressSheet.asStateFlow()

  private val _showRoleSwitcher = MutableStateFlow(false)
  val showRoleSwitcher: StateFlow<Boolean> = _showRoleSwitcher.asStateFlow()

  private val _showAuthDialog = MutableStateFlow(false)
  val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

  private val _showCouponSheet = MutableStateFlow(false)
  val showCouponSheet: StateFlow<Boolean> = _showCouponSheet.asStateFlow()

  private val _showPaymentSandbox = MutableStateFlow(false)
  val showPaymentSandbox: StateFlow<Boolean> = _showPaymentSandbox.asStateFlow()

  private val _snackbarMessage = MutableStateFlow<String?>(null)
  val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

  fun setCustomerTab(tabIndex: Int) {
    _customerTab.value = tabIndex
    _selectedRestaurant.value = null
    _selectedOrder.value = null
    _isViewingCart.value = false
  }

  fun setRole(role: UserRole) {
    repository.setRole(role)
    _selectedRestaurant.value = null
    _selectedOrder.value = null
    _isViewingCart.value = false
  }

  fun selectRestaurant(restaurant: Restaurant?) {
    _selectedRestaurant.value = restaurant
  }

  fun selectOrderForTracking(order: Order?) {
    _selectedOrder.value = order
  }

  fun setViewingCart(viewing: Boolean) {
    _isViewingCart.value = viewing
  }

  fun setShowAddressSheet(show: Boolean) {
    _showAddressSheet.value = show
  }

  fun setShowRoleSwitcher(show: Boolean) {
    _showRoleSwitcher.value = show
  }

  fun setShowAuthDialog(show: Boolean) {
    _showAuthDialog.value = show
  }

  fun setShowCouponSheet(show: Boolean) {
    _showCouponSheet.value = show
  }

  fun setShowPaymentSandbox(show: Boolean) {
    _showPaymentSandbox.value = show
  }

  fun showMessage(msg: String) {
    _snackbarMessage.value = msg
  }

  fun clearMessage() {
    _snackbarMessage.value = null
  }

  // Action delegations
  fun addToCart(item: MenuItem, options: List<CartCustomizationSelection> = emptyList(), instructions: String = "") {
    repository.addToCart(item, options, instructions)
    showMessage("Added ${item.name} to cart")
  }

  fun updateCartQuantity(index: Int, qty: Int) {
    repository.updateCartQuantity(index, qty)
  }

  fun setSpecialInstructions(inst: String) {
    repository.setSpecialInstructions(inst)
  }

  fun applyCoupon(coupon: Coupon) {
    val applied = repository.applyCoupon(coupon)
    if (applied) {
      showMessage("Coupon ${coupon.code} applied successfully!")
    } else {
      showMessage("Minimum order of ₹${coupon.minOrderValue.toInt()} required for this coupon")
    }
  }

  fun removeCoupon() {
    repository.removeCoupon()
    showMessage("Coupon removed")
  }

  fun proceedPayment(method: String) {
    val order = repository.placeOrder(method)
    _showPaymentSandbox.value = false
    _isViewingCart.value = false
    _selectedRestaurant.value = null
    _selectedOrder.value = order
    showMessage("Order #${order.orderNumber} placed successfully!")
  }

  fun rateOrder(orderId: String, rating: Float, review: String) {
    repository.rateOrder(orderId, rating, review)
    showMessage("Thank you for your rating!")
  }

  fun reorder(order: Order) {
    val rest = repository.restaurants.value.find { it.id == order.restaurantId }
    val dishes = repository.menuItems.value[order.restaurantId] ?: emptyList()
    order.items.forEach { snapshot ->
      val menuItem = dishes.find { it.id == snapshot.itemId }
      if (menuItem != null) {
        repository.addToCart(menuItem)
      }
    }
    _isViewingCart.value = true
    showMessage("Items added to cart from previous order")
  }

  fun toggleFavorite(restaurantId: String) {
    repository.toggleFavorite(restaurantId)
  }

  fun selectAddress(addressId: String) {
    repository.setSelectedAddress(addressId)
  }

  fun addAddress(address: Address) {
    repository.addAddress(address)
    showMessage("New address '${address.label}' added")
  }

  fun login(phone: String, name: String) {
    repository.login(phone, name)
    showMessage("Welcome back, $name!")
  }

  fun logout() {
    repository.logout()
    showMessage("Signed out successfully")
  }

  // Restaurant Partner Actions
  fun toggleRestaurantOpen(restaurantId: String) {
    repository.toggleRestaurantOpen(restaurantId)
  }

  fun toggleItemAvailability(restaurantId: String, itemId: String, isAvailable: Boolean) {
    repository.updateMenuItemAvailability(restaurantId, itemId, isAvailable)
  }

  fun addMenuItem(restaurantId: String, item: MenuItem) {
    repository.addMenuItem(restaurantId, item)
    showMessage("Added ${item.name} to menu")
  }

  fun updateOrderStatus(orderId: String, status: OrderStatus) {
    repository.updateOrderStatus(orderId, status)
    showMessage("Order status updated to: ${status.label}")
  }

  // Delivery Partner Actions
  fun toggleDeliveryOnline(partnerId: String) {
    repository.toggleDeliveryOnline(partnerId)
  }

  fun deliveryPickupOrder(orderId: String) {
    repository.updateOrderStatus(orderId, OrderStatus.OUT_FOR_DELIVERY)
    showMessage("Order picked up! Navigate to customer drop")
  }

  fun deliveryCompleteOrder(partnerId: String, orderId: String) {
    repository.deliveryPartnerCompleteOrder(partnerId, orderId)
    showMessage("Order delivered successfully! ₹65 credited to earnings")
  }

  // Admin Actions
  fun toggleRestaurantApproval(restaurantId: String) {
    repository.toggleRestaurantApproval(restaurantId)
  }

  fun addCouponAdmin(coupon: Coupon) {
    repository.addCoupon(coupon)
    showMessage("Created coupon ${coupon.code}")
  }

  fun deleteCouponAdmin(code: String) {
    repository.deleteCoupon(code)
    showMessage("Removed coupon $code")
  }
}
