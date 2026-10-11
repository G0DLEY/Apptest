package com.example.data.model

enum class UserRole {
  CUSTOMER,
  RESTAURANT_PARTNER,
  DELIVERY_PARTNER,
  ADMIN
}

enum class AddressType {
  HOME,
  WORK,
  OTHER
}

data class Address(
  val id: String,
  val type: AddressType,
  val label: String,
  val street: String,
  val city: String,
  val landmark: String = "",
  val isDefault: Boolean = false
)

data class UserProfile(
  val id: String,
  val name: String,
  val phone: String,
  val email: String,
  val addresses: List<Address>,
  val selectedAddressId: String
)

data class CustomizationOption(
  val id: String,
  val name: String,
  val price: Double
)

data class CustomizationGroup(
  val id: String,
  val title: String,
  val isRequired: Boolean,
  val maxSelection: Int = 1,
  val options: List<CustomizationOption>
)

data class MenuItem(
  val id: String,
  val restaurantId: String,
  val name: String,
  val description: String,
  val price: Double,
  val originalPrice: Double = price,
  val category: String,
  val isVeg: Boolean,
  val isBestseller: Boolean = false,
  val isAvailable: Boolean = true,
  val rating: Float = 4.3f,
  val ratingCount: Int = 120,
  val customizationGroups: List<CustomizationGroup> = emptyList()
)

data class Restaurant(
  val id: String,
  val name: String,
  val cuisineType: String,
  val rating: Float,
  val ratingCount: Int,
  val deliveryTimeMinutes: Int,
  val distanceKm: Double,
  val costForTwo: Double,
  val isVegOnly: Boolean = false,
  val isOpen: Boolean = true,
  val isPromoted: Boolean = false,
  val offerText: String = "",
  val area: String,
  val address: String,
  val tags: List<String> = emptyList(),
  val isApproved: Boolean = true
)

data class CartCustomizationSelection(
  val groupTitle: String,
  val optionName: String,
  val price: Double
)

data class CartItem(
  val menuItem: MenuItem,
  val quantity: Int,
  val selectedOptions: List<CartCustomizationSelection> = emptyList(),
  val specialInstructions: String = ""
) {
  val unitPrice: Double
    get() = menuItem.price + selectedOptions.sumOf { it.price }

  val totalPrice: Double
    get() = unitPrice * quantity
}

data class Coupon(
  val code: String,
  val title: String,
  val discountPercent: Int,
  val maxDiscount: Double,
  val minOrderValue: Double,
  val description: String
)

enum class OrderStatus(val label: String, val stepIndex: Int) {
  PLACED("Order Placed", 0),
  ACCEPTED("Accepted by Restaurant", 1),
  PREPARING("Food is Preparing", 2),
  OUT_FOR_DELIVERY("Out for Delivery", 3),
  DELIVERED("Delivered", 4),
  CANCELLED("Cancelled", -1)
}

data class OrderItemSnapshot(
  val itemId: String,
  val name: String,
  val price: Double,
  val quantity: Int,
  val isVeg: Boolean,
  val customizationsText: String = ""
)

data class Order(
  val id: String,
  val orderNumber: String,
  val customerId: String,
  val customerName: String,
  val customerPhone: String,
  val deliveryAddress: Address,
  val restaurantId: String,
  val restaurantName: String,
  val restaurantArea: String,
  val items: List<OrderItemSnapshot>,
  val itemTotal: Double,
  val deliveryFee: Double,
  val taxes: Double,
  val platformFee: Double = 5.0,
  val discount: Double = 0.0,
  val totalAmount: Double,
  val couponCodeApplied: String? = null,
  val paymentMethod: String, // "UPI", "CREDIT_CARD", "COD", "WALLET"
  val paymentStatus: String = "PAID",
  val orderStatus: OrderStatus = OrderStatus.PLACED,
  val prepTimeMinutes: Int = 25,
  val deliveryPartnerName: String = "Rahul Sharma",
  val deliveryPartnerPhone: String = "+91 98765 43210",
  val deliveryPartnerVehicle: String = "Honda Activa (KA-01-EQ-4089)",
  val deliveryPartnerRating: Float = 4.8f,
  val createdAt: Long = System.currentTimeMillis(),
  val deliveryEstimateMinutes: Int = 32,
  val ratingGiven: Float? = null,
  val reviewGiven: String? = null
)

data class DeliveryPartner(
  val id: String,
  val name: String,
  val phone: String,
  val vehicle: String,
  val rating: Float,
  val isOnline: Boolean,
  val activeOrderId: String? = null,
  val todayDeliveries: Int = 8,
  val todayEarnings: Double = 1240.0
)

data class Review(
  val id: String,
  val restaurantId: String,
  val userName: String,
  val rating: Float,
  val comment: String,
  val relativeTime: String
)
