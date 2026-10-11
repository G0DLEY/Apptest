package com.example.data.repository

import com.example.data.model.Address
import com.example.data.model.CartCustomizationSelection
import com.example.data.model.CartItem
import com.example.data.model.Coupon
import com.example.data.model.DeliveryPartner
import com.example.data.model.MenuItem
import com.example.data.model.Order
import com.example.data.model.OrderItemSnapshot
import com.example.data.model.OrderStatus
import com.example.data.model.Restaurant
import com.example.data.model.Review
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodioRepository(
  private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
  private val _restaurants = MutableStateFlow<List<Restaurant>>(SeedData.sampleRestaurants)
  val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

  private val _menuItems = MutableStateFlow<Map<String, List<MenuItem>>>(SeedData.sampleMenuItems)
  val menuItems: StateFlow<Map<String, List<MenuItem>>> = _menuItems.asStateFlow()

  private val _currentUser = MutableStateFlow<UserProfile>(SeedData.sampleUser)
  val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

  private val _currentRole = MutableStateFlow<UserRole>(UserRole.CUSTOMER)
  val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

  private val _isLoggedIn = MutableStateFlow<Boolean>(true)
  val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

  private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
  val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

  private val _appliedCoupon = MutableStateFlow<Coupon?>(null)
  val appliedCoupon: StateFlow<Coupon?> = _appliedCoupon.asStateFlow()

  private val _specialInstructions = MutableStateFlow<String>("")
  val specialInstructions: StateFlow<String> = _specialInstructions.asStateFlow()

  private val _orders = MutableStateFlow<List<Order>>(emptyList())
  val orders: StateFlow<List<Order>> = _orders.asStateFlow()

  private val _activeOrderId = MutableStateFlow<String?>(null)
  val activeOrderId: StateFlow<String?> = _activeOrderId.asStateFlow()

  private val _deliveryPartners = MutableStateFlow<List<DeliveryPartner>>(SeedData.sampleDeliveryPartners)
  val deliveryPartners: StateFlow<List<DeliveryPartner>> = _deliveryPartners.asStateFlow()

  private val _coupons = MutableStateFlow<List<Coupon>>(SeedData.sampleCoupons)
  val coupons: StateFlow<List<Coupon>> = _coupons.asStateFlow()

  private val _reviews = MutableStateFlow<List<Review>>(SeedData.sampleReviews)
  val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

  private val _favorites = MutableStateFlow<Set<String>>(setOf("rest_1", "rest_4"))
  val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

  // Track auto-progression job for live simulation
  private var liveTrackingJob: Job? = null

  init {
    // Seed one previous order in history
    val initialOrder = Order(
      id = "ord_prev_1",
      orderNumber = "FO-94821",
      customerId = SeedData.sampleUser.id,
      customerName = SeedData.sampleUser.name,
      customerPhone = SeedData.sampleUser.phone,
      deliveryAddress = SeedData.sampleAddresses.first(),
      restaurantId = "rest_4",
      restaurantName = "Rameshwaram Pure Veg Cafe",
      restaurantArea = "Indiranagar",
      items = listOf(
        OrderItemSnapshot("m4_1", "Ghee Podi Masala Dosa", 135.0, 2, isVeg = true),
        OrderItemSnapshot("m4_6", "Authentic Degree Filter Coffee", 45.0, 2, isVeg = true)
      ),
      itemTotal = 360.0,
      deliveryFee = 25.0,
      taxes = 18.0,
      platformFee = 5.0,
      discount = 50.0,
      totalAmount = 358.0,
      couponCodeApplied = "WELCOME50",
      paymentMethod = "UPI",
      orderStatus = OrderStatus.DELIVERED,
      createdAt = System.currentTimeMillis() - 86400000L * 2,
      ratingGiven = 5.0f,
      reviewGiven = "Superb ghee flavour, fresh and crisp!"
    )
    _orders.value = listOf(initialOrder)
  }

  // --- Auth & Profile ---
  fun setRole(role: UserRole) {
    _currentRole.value = role
  }

  fun login(phone: String, name: String) {
    val updated = _currentUser.value.copy(
      phone = phone,
      name = if (name.isNotBlank()) name else "Food Lover"
    )
    _currentUser.value = updated
    _isLoggedIn.value = true
  }

  fun logout() {
    _isLoggedIn.value = false
  }

  fun setSelectedAddress(addressId: String) {
    val current = _currentUser.value
    _currentUser.value = current.copy(selectedAddressId = addressId)
  }

  fun addAddress(address: Address) {
    val current = _currentUser.value
    val updatedList = current.addresses + address
    _currentUser.value = current.copy(
      addresses = updatedList,
      selectedAddressId = address.id
    )
  }

  fun getSelectedAddress(): Address {
    val user = _currentUser.value
    return user.addresses.find { it.id == user.selectedAddressId }
      ?: user.addresses.firstOrNull()
      ?: Address(
        id = "default",
        type = com.example.data.model.AddressType.HOME,
        label = "Home",
        street = "12th Main Road, Indiranagar",
        city = "Bengaluru"
      )
  }

  fun toggleFavorite(restaurantId: String) {
    val current = _favorites.value.toMutableSet()
    if (current.contains(restaurantId)) {
      current.remove(restaurantId)
    } else {
      current.add(restaurantId)
    }
    _favorites.value = current
  }

  // --- Cart Operations ---
  fun addToCart(
    item: MenuItem,
    selectedOptions: List<CartCustomizationSelection> = emptyList(),
    specialInstructions: String = ""
  ) {
    val currentList = _cartItems.value.toMutableList()
    // If the cart has items from another restaurant, reset cart to new restaurant
    val firstItem = currentList.firstOrNull()
    if (firstItem != null && firstItem.menuItem.restaurantId != item.restaurantId) {
      currentList.clear()
    }

    // Check if exact item + customizations already exists
    val existingIndex = currentList.indexOfFirst {
      it.menuItem.id == item.id && it.selectedOptions == selectedOptions
    }

    if (existingIndex >= 0) {
      val existing = currentList[existingIndex]
      currentList[existingIndex] = existing.copy(quantity = existing.quantity + 1)
    } else {
      currentList.add(
        CartItem(
          menuItem = item,
          quantity = 1,
          selectedOptions = selectedOptions,
          specialInstructions = specialInstructions
        )
      )
    }
    _cartItems.value = currentList
  }

  fun updateCartQuantity(cartIndex: Int, newQuantity: Int) {
    val currentList = _cartItems.value.toMutableList()
    if (cartIndex in currentList.indices) {
      if (newQuantity <= 0) {
        currentList.removeAt(cartIndex)
      } else {
        val item = currentList[cartIndex]
        currentList[cartIndex] = item.copy(quantity = newQuantity)
      }
      _cartItems.value = currentList
      if (currentList.isEmpty()) {
        _appliedCoupon.value = null
      }
    }
  }

  fun getItemQuantityInCart(itemId: String): Int {
    return _cartItems.value.filter { it.menuItem.id == itemId }.sumOf { it.quantity }
  }

  fun setSpecialInstructions(instructions: String) {
    _specialInstructions.value = instructions
  }

  fun applyCoupon(coupon: Coupon): Boolean {
    val itemTotal = _cartItems.value.sumOf { it.totalPrice }
    if (itemTotal >= coupon.minOrderValue) {
      _appliedCoupon.value = coupon
      return true
    }
    return false
  }

  fun removeCoupon() {
    _appliedCoupon.value = null
  }

  fun clearCart() {
    _cartItems.value = emptyList()
    _appliedCoupon.value = null
    _specialInstructions.value = ""
  }

  fun getCartRestaurant(): Restaurant? {
    val firstItem = _cartItems.value.firstOrNull() ?: return null
    return _restaurants.value.find { it.id == firstItem.menuItem.restaurantId }
  }

  // --- Orders & Checkout ---
  fun placeOrder(paymentMethod: String): Order {
    val items = _cartItems.value
    val restaurant = getCartRestaurant() ?: _restaurants.value.first()
    val user = _currentUser.value
    val address = getSelectedAddress()

    val itemTotal = items.sumOf { it.totalPrice }
    val deliveryFee = if (itemTotal > 500) 0.0 else 35.0
    val taxes = (itemTotal * 0.05).coerceAtLeast(10.0)
    val platformFee = 5.0
    val coupon = _appliedCoupon.value
    var discount = 0.0

    if (coupon != null && itemTotal >= coupon.minOrderValue) {
      discount = ((itemTotal * coupon.discountPercent) / 100.0).coerceAtMost(coupon.maxDiscount)
    }

    val totalAmount = (itemTotal + deliveryFee + taxes + platformFee - discount).coerceAtLeast(0.0)
    val orderId = "ord_" + System.currentTimeMillis()
    val orderNumber = "FO-" + (10000..99999).random()

    val snapshots = items.map {
      val customText = it.selectedOptions.joinToString(", ") { opt -> opt.optionName }
      OrderItemSnapshot(
        itemId = it.menuItem.id,
        name = it.menuItem.name,
        price = it.unitPrice,
        quantity = it.quantity,
        isVeg = it.menuItem.isVeg,
        customizationsText = customText
      )
    }

    val partner = _deliveryPartners.value.filter { it.isOnline }.randomOrNull()
      ?: _deliveryPartners.value.first()

    val newOrder = Order(
      id = orderId,
      orderNumber = orderNumber,
      customerId = user.id,
      customerName = user.name,
      customerPhone = user.phone,
      deliveryAddress = address,
      restaurantId = restaurant.id,
      restaurantName = restaurant.name,
      restaurantArea = restaurant.area,
      items = snapshots,
      itemTotal = itemTotal,
      deliveryFee = deliveryFee,
      taxes = taxes,
      platformFee = platformFee,
      discount = discount,
      totalAmount = totalAmount,
      couponCodeApplied = coupon?.code,
      paymentMethod = paymentMethod,
      paymentStatus = "PAID",
      orderStatus = OrderStatus.PLACED,
      prepTimeMinutes = 20,
      deliveryPartnerName = partner.name,
      deliveryPartnerPhone = partner.phone,
      deliveryPartnerVehicle = partner.vehicle,
      deliveryPartnerRating = partner.rating,
      createdAt = System.currentTimeMillis()
    )

    _orders.value = listOf(newOrder) + _orders.value
    _activeOrderId.value = orderId
    clearCart()

    // Start realistic simulated progression through stages
    startOrderProgressionSimulation(orderId)

    return newOrder
  }

  private fun startOrderProgressionSimulation(orderId: String) {
    liveTrackingJob?.cancel()
    liveTrackingJob = scope.launch {
      // Step 1: Placed -> Accepted after 4 seconds
      delay(4000)
      updateOrderStatus(orderId, OrderStatus.ACCEPTED)

      // Step 2: Accepted -> Preparing after 6 seconds
      delay(6000)
      updateOrderStatus(orderId, OrderStatus.PREPARING)

      // Step 3: Preparing -> Out for delivery after 8 seconds
      delay(8000)
      updateOrderStatus(orderId, OrderStatus.OUT_FOR_DELIVERY)

      // Step 4: Out for delivery -> Delivered after 15 seconds
      delay(15000)
      updateOrderStatus(orderId, OrderStatus.DELIVERED)
    }
  }

  fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
    _orders.value = _orders.value.map { order ->
      if (order.id == orderId) {
        order.copy(orderStatus = newStatus)
      } else {
        order
      }
    }
  }

  fun rateOrder(orderId: String, rating: Float, review: String) {
    _orders.value = _orders.value.map { order ->
      if (order.id == orderId) {
        order.copy(ratingGiven = rating, reviewGiven = review)
      } else {
        order
      }
    }
    // Also save into reviews
    val order = _orders.value.find { it.id == orderId }
    if (order != null && review.isNotBlank()) {
      val newReview = Review(
        id = "rev_" + System.currentTimeMillis(),
        restaurantId = order.restaurantId,
        userName = _currentUser.value.name,
        rating = rating,
        comment = review,
        relativeTime = "Just now"
      )
      _reviews.value = listOf(newReview) + _reviews.value
    }
  }

  fun setActiveOrder(orderId: String?) {
    _activeOrderId.value = orderId
  }

  fun getOrderById(orderId: String): Order? {
    return _orders.value.find { it.id == orderId }
  }

  // --- Restaurant Partner Dashboard Actions ---
  fun toggleRestaurantOpen(restaurantId: String) {
    _restaurants.value = _restaurants.value.map {
      if (it.id == restaurantId) it.copy(isOpen = !it.isOpen) else it
    }
  }

  fun updateMenuItemAvailability(restaurantId: String, itemId: String, isAvailable: Boolean) {
    val currentMap = _menuItems.value.toMutableMap()
    val list = currentMap[restaurantId]?.map {
      if (it.id == itemId) it.copy(isAvailable = isAvailable) else it
    } ?: emptyList()
    currentMap[restaurantId] = list
    _menuItems.value = currentMap
  }

  fun addMenuItem(restaurantId: String, item: MenuItem) {
    val currentMap = _menuItems.value.toMutableMap()
    val list = currentMap[restaurantId]?.toMutableList() ?: mutableListOf()
    list.add(item)
    currentMap[restaurantId] = list
    _menuItems.value = currentMap
  }

  fun editMenuItem(restaurantId: String, updatedItem: MenuItem) {
    val currentMap = _menuItems.value.toMutableMap()
    val list = currentMap[restaurantId]?.map {
      if (it.id == updatedItem.id) updatedItem else it
    } ?: emptyList()
    currentMap[restaurantId] = list
    _menuItems.value = currentMap
  }

  // --- Delivery Partner Portal Actions ---
  fun toggleDeliveryOnline(partnerId: String) {
    _deliveryPartners.value = _deliveryPartners.value.map {
      if (it.id == partnerId) it.copy(isOnline = !it.isOnline) else it
    }
  }

  fun deliveryPartnerCompleteOrder(partnerId: String, orderId: String) {
    updateOrderStatus(orderId, OrderStatus.DELIVERED)
    _deliveryPartners.value = _deliveryPartners.value.map {
      if (it.id == partnerId) {
        it.copy(
          todayDeliveries = it.todayDeliveries + 1,
          todayEarnings = it.todayEarnings + 65.0,
          activeOrderId = null
        )
      } else it
    }
  }

  // --- Admin Dashboard Actions ---
  fun toggleRestaurantApproval(restaurantId: String) {
    _restaurants.value = _restaurants.value.map {
      if (it.id == restaurantId) it.copy(isApproved = !it.isApproved) else it
    }
  }

  fun addCoupon(coupon: Coupon) {
    _coupons.value = listOf(coupon) + _coupons.value
  }

  fun deleteCoupon(code: String) {
    _coupons.value = _coupons.value.filter { it.code != code }
  }
}
