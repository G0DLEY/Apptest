# foodio — Food Delivery App (Swiggy-inspired MVP)

Foodio is a complete, modern food delivery Android application built using **Kotlin** and **Jetpack Compose (Material 3)**. It is architected for seamless multi-role testing, allowing immediate simulation of the entire food delivery ecosystem: **Customer**, **Restaurant Partner**, **Delivery Partner**, and **Admin**.

---

## 📱 Features by User Role

### 1. Customer Experience
- **Authentication**: Phone number login with instant 4-digit OTP verification simulation.
- **Location & Addresses**: Multi-address management (Home, Work, Other) with immediate delivery address selection.
- **Home Feed**:
  - Top delivery address bar and real-time Role Switcher pill.
  - Interactive search bar with instant query suggestions.
  - Promotional hero banner highlighting discounts (e.g. `WELCOME50`).
  - Cuisine categories: Biryani, Burgers, Pizza, South Indian, Chinese, Desserts, Healthy, Rolls, Chai.
  - Quick filters: *Pure Veg*, *Rating 4.0+*, *Fast Delivery (<25 min)*, *Great Offers*.
  - Restaurant cards displaying ratings, estimated delivery times, distance, cost for two, and offer badges.
- **Restaurant Menu & Customizations**:
  - Grouped menu categories with Veg / Non-Veg indicators and Bestseller tags.
  - Customization bottom sheet (portion size, extra cheese, fries, side add-ons).
  - Floating bottom Cart bar with item counts and price summary.
- **Search Screen**:
  - Live search across all 10 restaurants and 100+ dishes with instant match chips.
- **Cart & Bill Breakdown**:
  - Quantity steppers (`+` / `-`), special cooking instructions input.
  - Coupon discount engine (`WELCOME50`, `FOODIO20`, `FREEDEL`, `FEAST100`).
  - Item total, delivery fee (free above ₹500), GST & restaurant charges (5%), and platform fee (₹5).
- **Payment Sandbox (Razorpay / UPI)**:
  - Realistic payment gateway simulator supporting UPI (GPay, PhonePe, Paytm), Credit/Debit Cards, Wallets, and Cash on Delivery (COD).
- **Live Order Tracking**:
  - Canvas-rendered animated GPS route map with dynamic road paths, pulsing restaurant marker, animated courier bike marker, and customer home destination pin.
  - Milestone stepper: `Placed` → `Accepted` → `Preparing` → `Out for Delivery` → `Delivered`.
  - Delivery partner card with vehicle details, rating, and call/chat actions.
  - Instant Simulation Controls to fast-forward order milestones without waiting.
  - Star rating (1-5★) and written review feedback submission upon delivery.
- **Order History & Reorder**:
  - Order receipts with timestamps, status pills, and one-tap "Reorder" button.

### 2. Restaurant Partner Dashboard
- **Kitchen Status**: One-tap toggle between "OPEN for orders" and "Kitchen CLOSED".
- **Real-Time Orders**:
  - Incoming orders with customer details, dishes ordered, and "Accept (20m)" or "Reject".
  - Active kitchen preparation tracker with "Handover to Delivery Partner" action.
- **Menu Management**:
  - In-stock / Out-of-stock availability toggles for each dish.
  - "Add Dish" dialog to create new menu items with category, price, and veg/non-veg status.
- **Daily Performance**: Real-time gross revenue, order volume, and average preparation time.

### 3. Delivery Partner Portal
- **Duty Toggle**: Instant Online / Offline duty switcher.
- **Pickup & Drop Assignments**:
  - Pickup alert cards showing restaurant location, order number, and distance.
  - "Picked Up from Kitchen" navigation handover.
  - Customer drop details with address and "Delivered to Customer (Collect ₹...)" action.
- **Earnings Tracker**: Today's delivery count, rider rating, and cumulative trip payouts (₹65/trip).

### 4. Central Admin Console
- **Platform Analytics**: Total GMV (Gross Merchandise Value), 15% platform commissions, and active partner counts.
- **Restaurant Onboarding**: Approve or pause restaurant listings in real-time.
- **Campaign Coupons**: Create new promotional coupons with discount percentage, cap, and minimum order thresholds; delete expired codes.
- **Rider Roster**: Live visibility over all delivery partners, duty status, and daily metrics.

---

## 🗄️ Database & Domain Schema

```
UserProfile {
  id: String
  name: String
  phone: String
  email: String
  addresses: List<Address>
  selectedAddressId: String
}

Address {
  id: String
  type: HOME | WORK | OTHER
  label: String
  street: String
  city: String
  landmark: String
}

Restaurant {
  id: String
  name: String
  cuisineType: String
  rating: Float
  ratingCount: Int
  deliveryTimeMinutes: Int
  distanceKm: Double
  costForTwo: Double
  isVegOnly: Boolean
  isOpen: Boolean
  offerText: String
  area: String
  isApproved: Boolean
}

MenuItem {
  id: String
  restaurantId: String
  name: String
  description: String
  price: Double
  originalPrice: Double
  category: String
  isVeg: Boolean
  isBestseller: Boolean
  isAvailable: Boolean
  customizationGroups: List<CustomizationGroup>
}

CartItem {
  menuItem: MenuItem
  quantity: Int
  selectedOptions: List<CartCustomizationSelection>
  specialInstructions: String
}

Order {
  id: String
  orderNumber: String
  customerId: String
  customerName: String
  deliveryAddress: Address
  restaurantId: String
  restaurantName: String
  items: List<OrderItemSnapshot>
  itemTotal: Double
  deliveryFee: Double
  taxes: Double
  platformFee: Double
  discount: Double
  totalAmount: Double
  orderStatus: PLACED | ACCEPTED | PREPARING | OUT_FOR_DELIVERY | DELIVERED | CANCELLED
  deliveryPartnerName: String
  deliveryPartnerVehicle: String
  ratingGiven: Float?
}
```

---

## 🔌 Backend REST API Specification (Phase 2 Ready)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/auth/otp-request` | Request 4-digit SMS OTP |
| `POST` | `/api/v1/auth/otp-verify` | Verify OTP and return JWT session |
| `GET` | `/api/v1/restaurants` | List restaurants with filters & sorting |
| `GET` | `/api/v1/restaurants/:id/menu` | Fetch restaurant menu catalog |
| `POST` | `/api/v1/cart/apply-coupon` | Validate & calculate coupon savings |
| `POST` | `/api/v1/orders/checkout` | Create order & initiate Razorpay order ID |
| `GET` | `/api/v1/orders/:id/track` | Live order location & milestone status |
| `PATCH`| `/api/v1/restaurant/orders/:id` | Update kitchen preparation status |
| `PATCH`| `/api/v1/delivery/orders/:id` | Update rider pickup & drop status |
| `POST` | `/api/v1/admin/coupons` | Provision platform promo campaigns |

---

## 🚀 Running & Testing

1. Launch the app in Android Studio or streaming emulator.
2. Tap the **Role: Customer ▾** pill in the top bar or inside **Profile** to switch between **Customer**, **Restaurant Partner**, **Delivery Partner**, and **Admin**.
3. Place an order in **Customer** mode, switch to **Restaurant Partner** to accept it, switch to **Delivery Partner** to pick up and deliver, and watch **Admin** analytics update in real time!
