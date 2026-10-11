package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.UserRole
import com.example.ui.MainViewModel
import com.example.ui.components.AddressSelectionSheet
import com.example.ui.components.CouponSelectionBottomSheet
import com.example.ui.components.PhoneOtpAuthDialog
import com.example.ui.components.RazorpaySandboxDialog
import com.example.ui.components.RoleSwitcherDialog
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CustomerHomeScreen
import com.example.ui.screens.DeliveryPartnerScreen
import com.example.ui.screens.LiveOrderTrackingScreen
import com.example.ui.screens.OrdersHistoryScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RestaurantDetailScreen
import com.example.ui.screens.RestaurantPartnerScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.theme.FoodioOrange
import com.example.ui.theme.FoodioOrangeContainer
import com.example.ui.theme.FoodioSurfaceGray
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        FoodioApp()
      }
    }
  }
}

@Composable
fun FoodioApp(
  viewModel: MainViewModel = viewModel()
) {
  val restaurants by viewModel.restaurants.collectAsStateWithLifecycle()
  val allMenuItems by viewModel.menuItems.collectAsStateWithLifecycle()
  val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
  val currentRole by viewModel.currentRole.collectAsStateWithLifecycle()
  val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
  val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
  val appliedCoupon by viewModel.appliedCoupon.collectAsStateWithLifecycle()
  val specialInstructions by viewModel.specialInstructions.collectAsStateWithLifecycle()
  val orders by viewModel.orders.collectAsStateWithLifecycle()
  val deliveryPartners by viewModel.deliveryPartners.collectAsStateWithLifecycle()
  val coupons by viewModel.coupons.collectAsStateWithLifecycle()
  val favorites by viewModel.favorites.collectAsStateWithLifecycle()

  val customerTab by viewModel.customerTab.collectAsStateWithLifecycle()
  val selectedRestaurant by viewModel.selectedRestaurant.collectAsStateWithLifecycle()
  val selectedOrder by viewModel.selectedOrder.collectAsStateWithLifecycle()
  val isViewingCart by viewModel.isViewingCart.collectAsStateWithLifecycle()

  val showAddressSheet by viewModel.showAddressSheet.collectAsStateWithLifecycle()
  val showRoleSwitcher by viewModel.showRoleSwitcher.collectAsStateWithLifecycle()
  val showAuthDialog by viewModel.showAuthDialog.collectAsStateWithLifecycle()
  val showCouponSheet by viewModel.showCouponSheet.collectAsStateWithLifecycle()
  val showPaymentSandbox by viewModel.showPaymentSandbox.collectAsStateWithLifecycle()
  val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(snackbarMessage) {
    snackbarMessage?.let {
      snackbarHostState.showSnackbar(it)
      viewModel.clearMessage()
    }
  }

  val selectedAddress = currentUser.addresses.find { it.id == currentUser.selectedAddressId }
    ?: currentUser.addresses.first()

  // Calculate cart total to pay for sandbox dialog
  val itemTotal = cartItems.sumOf { it.totalPrice }
  val deliveryFee = if (itemTotal > 500) 0.0 else 35.0
  val taxes = (itemTotal * 0.05).coerceAtLeast(10.0)
  val discount = if (appliedCoupon != null && itemTotal >= appliedCoupon!!.minOrderValue) {
    ((itemTotal * appliedCoupon!!.discountPercent) / 100.0).coerceAtMost(appliedCoupon!!.maxDiscount)
  } else 0.0
  val totalPayAmount = (itemTotal + deliveryFee + taxes + 5.0 - discount).coerceAtLeast(0.0)

  // Active orders count for badge
  val activeOrdersCount = orders.count {
    it.orderStatus != com.example.data.model.OrderStatus.DELIVERED &&
      it.orderStatus != com.example.data.model.OrderStatus.CANCELLED
  }

  val showBottomNav = currentRole == UserRole.CUSTOMER &&
    selectedRestaurant == null &&
    selectedOrder == null &&
    !isViewingCart

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      if (showBottomNav) {
        NavigationBar(
          containerColor = PureWhite,
          tonalElevation = 8.dp,
          modifier = Modifier
            .navigationBarsPadding()
            .testTag("customer_bottom_nav")
        ) {
          NavigationBarItem(
            selected = customerTab == 0,
            onClick = { viewModel.setCustomerTab(0) },
            icon = {
              Icon(
                if (customerTab == 0) Icons.Default.Home else Icons.Outlined.Home,
                contentDescription = "Home"
              )
            },
            label = { Text("Home", fontWeight = if (customerTab == 0) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = FoodioOrange,
              selectedTextColor = FoodioOrange,
              indicatorColor = FoodioOrangeContainer
            ),
            modifier = Modifier.testTag("nav_tab_home")
          )

          NavigationBarItem(
            selected = customerTab == 1,
            onClick = { viewModel.setCustomerTab(1) },
            icon = {
              Icon(
                if (customerTab == 1) Icons.Default.Search else Icons.Outlined.Search,
                contentDescription = "Search"
              )
            },
            label = { Text("Search", fontWeight = if (customerTab == 1) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = FoodioOrange,
              selectedTextColor = FoodioOrange,
              indicatorColor = FoodioOrangeContainer
            ),
            modifier = Modifier.testTag("nav_tab_search")
          )

          NavigationBarItem(
            selected = customerTab == 2,
            onClick = { viewModel.setCustomerTab(2) },
            icon = {
              if (activeOrdersCount > 0) {
                BadgedBox(
                  badge = {
                    Badge(containerColor = FoodioOrange) {
                      Text("$activeOrdersCount", color = PureWhite, fontSize = 10.sp)
                    }
                  }
                ) {
                  Icon(
                    if (customerTab == 2) Icons.Default.History else Icons.Outlined.History,
                    contentDescription = "Orders"
                  )
                }
              } else {
                Icon(
                  if (customerTab == 2) Icons.Default.History else Icons.Outlined.History,
                  contentDescription = "Orders"
                )
              }
            },
            label = { Text("Orders", fontWeight = if (customerTab == 2) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = FoodioOrange,
              selectedTextColor = FoodioOrange,
              indicatorColor = FoodioOrangeContainer
            ),
            modifier = Modifier.testTag("nav_tab_orders")
          )

          NavigationBarItem(
            selected = customerTab == 3,
            onClick = { viewModel.setCustomerTab(3) },
            icon = {
              Icon(
                if (customerTab == 3) Icons.Default.Person else Icons.Outlined.Person,
                contentDescription = "Profile"
              )
            },
            label = { Text("Profile", fontWeight = if (customerTab == 3) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = FoodioOrange,
              selectedTextColor = FoodioOrange,
              indicatorColor = FoodioOrangeContainer
            ),
            modifier = Modifier.testTag("nav_tab_profile")
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentRole) {
        UserRole.CUSTOMER -> {
          when {
            selectedOrder != null -> {
              BackHandler {
                viewModel.selectOrderForTracking(null)
              }
              // Sync active order with repository orders state
              val currentOrderState = orders.find { it.id == selectedOrder!!.id } ?: selectedOrder!!
              LiveOrderTrackingScreen(
                order = currentOrderState,
                onBackClick = { viewModel.selectOrderForTracking(null) },
                onUpdateStatus = { newStatus ->
                  viewModel.updateOrderStatus(selectedOrder!!.id, newStatus)
                },
                onSubmitRating = { rating, review ->
                  viewModel.rateOrder(selectedOrder!!.id, rating, review)
                }
              )
            }

            isViewingCart -> {
              BackHandler {
                viewModel.setViewingCart(false)
              }
              val cartRestaurant = viewModel.repository.getCartRestaurant()
              CartScreen(
                cartItems = cartItems,
                restaurant = cartRestaurant,
                appliedCoupon = appliedCoupon,
                specialInstructions = specialInstructions,
                currentAddress = selectedAddress,
                onBackClick = { viewModel.setViewingCart(false) },
                onUpdateQuantity = { index, qty -> viewModel.updateCartQuantity(index, qty) },
                onSpecialInstructionsChange = { viewModel.setSpecialInstructions(it) },
                onOpenCouponSheet = { viewModel.setShowCouponSheet(true) },
                onRemoveCoupon = { viewModel.removeCoupon() },
                onChangeAddressClick = { viewModel.setShowAddressSheet(true) },
                onProceedToPayClick = { viewModel.setShowPaymentSandbox(true) },
                onBrowseRestaurantsClick = {
                  viewModel.setViewingCart(false)
                  viewModel.setCustomerTab(0)
                }
              )
            }

            selectedRestaurant != null -> {
              BackHandler {
                viewModel.selectRestaurant(null)
              }
              val itemsForRestaurant = allMenuItems[selectedRestaurant!!.id] ?: emptyList()
              RestaurantDetailScreen(
                restaurant = selectedRestaurant!!,
                menuItems = itemsForRestaurant,
                cartItems = cartItems,
                isFavorite = favorites.contains(selectedRestaurant!!.id),
                onBackClick = { viewModel.selectRestaurant(null) },
                onToggleFavorite = { viewModel.toggleFavorite(selectedRestaurant!!.id) },
                onAddToCart = { item, options, instructions ->
                  viewModel.addToCart(item, options, instructions)
                },
                onUpdateQuantity = { itemId, newQty ->
                  val index = cartItems.indexOfFirst { it.menuItem.id == itemId }
                  if (index >= 0) {
                    viewModel.updateCartQuantity(index, newQty)
                  }
                },
                onViewCartClick = { viewModel.setViewingCart(true) }
              )
            }

            else -> {
              when (customerTab) {
                0 -> CustomerHomeScreen(
                  restaurants = restaurants,
                  favorites = favorites,
                  currentAddress = selectedAddress,
                  currentRole = currentRole,
                  cartItems = cartItems,
                  onAddressClick = { viewModel.setShowAddressSheet(true) },
                  onRoleClick = { viewModel.setShowRoleSwitcher(true) },
                  onSearchClick = { viewModel.setCustomerTab(1) },
                  onRestaurantClick = { viewModel.selectRestaurant(it) },
                  onToggleFavorite = { viewModel.toggleFavorite(it) },
                  onViewCartClick = { viewModel.setViewingCart(true) }
                )

                1 -> SearchScreen(
                  restaurants = restaurants,
                  allMenuItems = allMenuItems,
                  onRestaurantClick = { viewModel.selectRestaurant(it) },
                  onAddToCart = { viewModel.addToCart(it) }
                )

                2 -> OrdersHistoryScreen(
                  orders = orders,
                  onOrderClick = { viewModel.selectOrderForTracking(it) },
                  onReorderClick = { viewModel.reorder(it) },
                  onBrowseFoodClick = { viewModel.setCustomerTab(0) }
                )

                3 -> ProfileScreen(
                  userProfile = currentUser,
                  currentRole = currentRole,
                  isLoggedIn = isLoggedIn,
                  onOpenRoleSwitcher = { viewModel.setShowRoleSwitcher(true) },
                  onOpenAddresses = { viewModel.setShowAddressSheet(true) },
                  onOpenAuthDialog = { viewModel.setShowAuthDialog(true) },
                  onLogout = {
                    if (isLoggedIn) viewModel.logout() else viewModel.setShowAuthDialog(true)
                  }
                )
              }
            }
          }
        }

        UserRole.RESTAURANT_PARTNER -> {
          BackHandler {
            viewModel.setRole(UserRole.CUSTOMER)
          }
          val activeRest = restaurants.find { it.id == "rest_1" } ?: restaurants.first()
          val restDishes = allMenuItems[activeRest.id] ?: emptyList()
          RestaurantPartnerScreen(
            restaurant = activeRest,
            menuItems = restDishes,
            orders = orders,
            onToggleOpen = { viewModel.toggleRestaurantOpen(activeRest.id) },
            onToggleItemAvailability = { itemId, avail ->
              viewModel.toggleItemAvailability(activeRest.id, itemId, avail)
            },
            onAddNewItem = { viewModel.addMenuItem(activeRest.id, it) },
            onUpdateOrderStatus = { orderId, status ->
              viewModel.updateOrderStatus(orderId, status)
            },
            onSwitchRole = { viewModel.setShowRoleSwitcher(true) }
          )
        }

        UserRole.DELIVERY_PARTNER -> {
          BackHandler {
            viewModel.setRole(UserRole.CUSTOMER)
          }
          val activeDriver = deliveryPartners.first()
          DeliveryPartnerScreen(
            partner = activeDriver,
            orders = orders,
            onToggleOnline = { viewModel.toggleDeliveryOnline(activeDriver.id) },
            onPickupOrder = { viewModel.deliveryPickupOrder(it) },
            onCompleteDelivery = { viewModel.deliveryCompleteOrder(activeDriver.id, it) },
            onSwitchRole = { viewModel.setShowRoleSwitcher(true) }
          )
        }

        UserRole.ADMIN -> {
          BackHandler {
            viewModel.setRole(UserRole.CUSTOMER)
          }
          AdminScreen(
            restaurants = restaurants,
            deliveryPartners = deliveryPartners,
            orders = orders,
            coupons = coupons,
            onToggleRestaurantApproval = { viewModel.toggleRestaurantApproval(it) },
            onAddCoupon = { viewModel.addCouponAdmin(it) },
            onDeleteCoupon = { viewModel.deleteCouponAdmin(it) },
            onSwitchRole = { viewModel.setShowRoleSwitcher(true) }
          )
        }
      }
    }
  }

  // Modals & BottomSheets
  if (showAddressSheet) {
    AddressSelectionSheet(
      addresses = currentUser.addresses,
      selectedAddressId = currentUser.selectedAddressId,
      onDismiss = { viewModel.setShowAddressSheet(false) },
      onSelectAddress = { viewModel.selectAddress(it) },
      onAddNewAddress = { viewModel.addAddress(it) }
    )
  }

  if (showRoleSwitcher) {
    RoleSwitcherDialog(
      currentRole = currentRole,
      onDismiss = { viewModel.setShowRoleSwitcher(false) },
      onRoleSelected = { viewModel.setRole(it) }
    )
  }

  if (showCouponSheet) {
    CouponSelectionBottomSheet(
      coupons = coupons,
      currentTotal = itemTotal,
      onDismiss = { viewModel.setShowCouponSheet(false) },
      onApplyCoupon = { viewModel.applyCoupon(it) }
    )
  }

  if (showPaymentSandbox) {
    RazorpaySandboxDialog(
      totalAmount = totalPayAmount,
      onDismiss = { viewModel.setShowPaymentSandbox(false) },
      onPaymentSuccess = { method -> viewModel.proceedPayment(method) }
    )
  }

  if (showAuthDialog) {
    PhoneOtpAuthDialog(
      initialPhone = currentUser.phone,
      initialName = currentUser.name,
      onDismiss = { viewModel.setShowAuthDialog(false) },
      onLoginSuccess = { phone, name -> viewModel.login(phone, name) }
    )
  }
}
