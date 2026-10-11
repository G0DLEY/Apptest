package com.example.data.repository

import com.example.data.model.Address
import com.example.data.model.AddressType
import com.example.data.model.Coupon
import com.example.data.model.CustomizationGroup
import com.example.data.model.CustomizationOption
import com.example.data.model.DeliveryPartner
import com.example.data.model.MenuItem
import com.example.data.model.Restaurant
import com.example.data.model.Review
import com.example.data.model.UserProfile

object SeedData {

  val sampleAddresses = listOf(
    Address(
      id = "addr_1",
      type = AddressType.HOME,
      label = "Home",
      street = "Flat 402, Sunshine Heights, 12th Main, Indiranagar",
      city = "Bengaluru",
      landmark = "Near Metro Pillar 84",
      isDefault = true
    ),
    Address(
      id = "addr_2",
      type = AddressType.WORK,
      label = "Work",
      street = "Tower B, 5th Floor, Global Tech Park, Bellandur",
      city = "Bengaluru",
      landmark = "Opposite Central Mall",
      isDefault = false
    ),
    Address(
      id = "addr_3",
      type = AddressType.OTHER,
      label = "Gym / Studio",
      street = "FitZone Hub, 100 Feet Road, Koramangala 4th Block",
      city = "Bengaluru",
      landmark = "Behind Starbucks",
      isDefault = false
    )
  )

  val sampleUser = UserProfile(
    id = "user_101",
    name = "Aman Verma",
    phone = "+91 98765 12340",
    email = "aman.verma@example.com",
    addresses = sampleAddresses,
    selectedAddressId = "addr_1"
  )

  val sampleCoupons = listOf(
    Coupon(
      code = "WELCOME50",
      title = "50% OFF up to ₹100",
      discountPercent = 50,
      maxDiscount = 100.0,
      minOrderValue = 199.0,
      description = "Valid on all orders above ₹199 for fresh food lovers"
    ),
    Coupon(
      code = "FOODIO20",
      title = "20% OFF up to ₹150",
      discountPercent = 20,
      maxDiscount = 150.0,
      minOrderValue = 299.0,
      description = "Save 20% on top rated restaurants in your neighborhood"
    ),
    Coupon(
      code = "FREEDEL",
      title = "Free Delivery (Save ₹40)",
      discountPercent = 100,
      maxDiscount = 40.0,
      minOrderValue = 149.0,
      description = "Zero delivery charges on your lunch & dinner cravings"
    ),
    Coupon(
      code = "FEAST100",
      title = "Flat ₹100 OFF",
      discountPercent = 25,
      maxDiscount = 100.0,
      minOrderValue = 449.0,
      description = "Great for parties and family meals above ₹449"
    )
  )

  val sampleDeliveryPartners = listOf(
    DeliveryPartner(
      id = "dp_1",
      name = "Rahul Sharma",
      phone = "+91 98765 43210",
      vehicle = "Honda Activa (KA-01-EQ-4089)",
      rating = 4.8f,
      isOnline = true,
      todayDeliveries = 9,
      todayEarnings = 1420.0
    ),
    DeliveryPartner(
      id = "dp_2",
      name = "Vikram Patil",
      phone = "+91 98222 34567",
      vehicle = "Bajaj Pulsar 150 (KA-05-MH-2031)",
      rating = 4.9f,
      isOnline = true,
      todayDeliveries = 12,
      todayEarnings = 1890.0
    ),
    DeliveryPartner(
      id = "dp_3",
      name = "Santosh Kumar",
      phone = "+91 97111 89012",
      vehicle = "TVS Jupiter (KA-03-JJ-9912)",
      rating = 4.7f,
      isOnline = false,
      todayDeliveries = 6,
      todayEarnings = 950.0
    )
  )

  val sampleRestaurants = listOf(
    Restaurant(
      id = "rest_1",
      name = "Meghana Biryani & Kebabs",
      cuisineType = "Biryani, Andhra, North Indian",
      rating = 4.6f,
      ratingCount = 4280,
      deliveryTimeMinutes = 28,
      distanceKm = 2.4,
      costForTwo = 550.0,
      isVegOnly = false,
      isOpen = true,
      isPromoted = true,
      offerText = "50% OFF up to ₹100 with WELCOME50",
      area = "Indiranagar",
      address = "544, CMH Road, Near Indiranagar Metro Station",
      tags = listOf("Biryani", "Spicy", "Bestseller")
    ),
    Restaurant(
      id = "rest_2",
      name = "Truffles Gourmet Burgers",
      cuisineType = "American, Burgers, Shakes, Cafe",
      rating = 4.5f,
      ratingCount = 3890,
      deliveryTimeMinutes = 24,
      distanceKm = 1.8,
      costForTwo = 450.0,
      isVegOnly = false,
      isOpen = true,
      isPromoted = true,
      offerText = "Free Delivery on orders above ₹149",
      area = "Koramangala",
      address = "93/A, 4th B Cross, 5th Block, Koramangala",
      tags = listOf("Burgers", "Fast Food", "Top Rated")
    ),
    Restaurant(
      id = "rest_3",
      name = "Pizza Bellissimo Artisanal",
      cuisineType = "Italian, Woodfired Pizza, Pasta",
      rating = 4.4f,
      ratingCount = 2150,
      deliveryTimeMinutes = 32,
      distanceKm = 3.1,
      costForTwo = 600.0,
      isVegOnly = false,
      isOpen = true,
      isPromoted = false,
      offerText = "20% OFF with FOODIO20",
      area = "Defence Colony",
      address = "77/1, 100 Feet Road, Defence Colony, Indiranagar",
      tags = listOf("Pizza", "Italian", "Gourmet")
    ),
    Restaurant(
      id = "rest_4",
      name = "Rameshwaram Pure Veg Cafe",
      cuisineType = "South Indian, Dosa, Filter Coffee",
      rating = 4.8f,
      ratingCount = 6120,
      deliveryTimeMinutes = 20,
      distanceKm = 1.2,
      costForTwo = 250.0,
      isVegOnly = true,
      isOpen = true,
      isPromoted = true,
      offerText = "Pure Veg Delight",
      area = "Indiranagar",
      address = "2984, 12th Main Rd, HAL 2nd Stage, Indiranagar",
      tags = listOf("Pure Veg", "South Indian", "Breakfast", "Fast Delivery")
    ),
    Restaurant(
      id = "rest_5",
      name = "Wok & Roll Asian Street",
      cuisineType = "Chinese, Dimsums, Thai Noodles",
      rating = 4.3f,
      ratingCount = 1740,
      deliveryTimeMinutes = 35,
      distanceKm = 3.6,
      costForTwo = 480.0,
      isVegOnly = false,
      isOpen = true,
      isPromoted = false,
      offerText = "Flat ₹100 OFF with FEAST100",
      area = "Domlur",
      address = "18/2, Old Airport Road, Domlur Village",
      tags = listOf("Chinese", "Dimsums", "Noodles")
    ),
    Restaurant(
      id = "rest_6",
      name = "Haldiram's Sweets & Chaat",
      cuisineType = "North Indian, Chaat, Indian Sweets",
      rating = 4.5f,
      ratingCount = 2950,
      deliveryTimeMinutes = 25,
      distanceKm = 2.0,
      costForTwo = 350.0,
      isVegOnly = true,
      isOpen = true,
      isPromoted = false,
      offerText = "Festive Sweet Specials",
      area = "MG Road",
      address = "Brigade Road Junction, Off MG Road",
      tags = listOf("Pure Veg", "Chaat", "Desserts", "Thali")
    ),
    Restaurant(
      id = "rest_7",
      name = "The Belgian Waffle & Shakes",
      cuisineType = "Desserts, Waffles, Thickshakes",
      rating = 4.7f,
      ratingCount = 3300,
      deliveryTimeMinutes = 22,
      distanceKm = 1.5,
      costForTwo = 300.0,
      isVegOnly = true,
      isOpen = true,
      isPromoted = false,
      offerText = "Buy 2 Get 10% Off",
      area = "Koramangala",
      address = "14, Jyoti Nivas College Rd, 5th Block",
      tags = listOf("Waffles", "Desserts", "Late Night")
    ),
    Restaurant(
      id = "rest_8",
      name = "Green Protein & Salad Bowls",
      cuisineType = "Healthy Food, Mediterranean, Salads",
      rating = 4.6f,
      ratingCount = 1290,
      deliveryTimeMinutes = 30,
      distanceKm = 2.8,
      costForTwo = 500.0,
      isVegOnly = false,
      isOpen = true,
      isPromoted = false,
      offerText = "Healthy Fuel - Low Carb",
      area = "Indiranagar",
      address = "104, Double Road, Indiranagar",
      tags = listOf("Healthy", "Low Carb", "Keto", "High Protein")
    ),
    Restaurant(
      id = "rest_9",
      name = "Faasos Signature Wraps",
      cuisineType = "Rolls, Fast Food, Mughlai",
      rating = 4.2f,
      ratingCount = 2650,
      deliveryTimeMinutes = 26,
      distanceKm = 2.2,
      costForTwo = 380.0,
      isVegOnly = false,
      isOpen = true,
      isPromoted = false,
      offerText = "Extra Roll at ₹99",
      area = "Ulsoor",
      address = "32, Kensington Road, Ulsoor",
      tags = listOf("Rolls", "Quick Bites", "Value Meals")
    ),
    Restaurant(
      id = "rest_10",
      name = "Chai Point & Snacks",
      cuisineType = "Beverages, Fast Food, Bakery",
      rating = 4.4f,
      ratingCount = 2800,
      deliveryTimeMinutes = 18,
      distanceKm = 1.0,
      costForTwo = 200.0,
      isVegOnly = true,
      isOpen = true,
      isPromoted = false,
      offerText = "Hot Chai in 20 Mins",
      area = "Indiranagar",
      address = "100 Feet Rd, Near 12th Main Signal",
      tags = listOf("Chai", "Snacks", "Breakfast", "Quick")
    )
  )

  val sampleMenuItems: Map<String, List<MenuItem>> = mapOf(
    // 1. Meghana Biryani
    "rest_1" to listOf(
      MenuItem(
        id = "m1_1",
        restaurantId = "rest_1",
        name = "Meghana Special Chicken Biryani",
        description = "Aromatic basmati rice cooked with secret Andhra spices and tender boneless spiced chicken pieces.",
        price = 330.0,
        originalPrice = 360.0,
        category = "Biryani",
        isVeg = false,
        isBestseller = true,
        customizationGroups = listOf(
          CustomizationGroup(
            id = "size_1",
            title = "Choose Portion Size",
            isRequired = true,
            options = listOf(
              CustomizationOption("opt_reg", "Regular (Serves 1-2)", 0.0),
              CustomizationOption("opt_jumbo", "Jumbo Pack (Serves 2-3)", 120.0)
            )
          ),
          CustomizationGroup(
            id = "addons_1",
            title = "Add-ons & Sides",
            isRequired = false,
            maxSelection = 3,
            options = listOf(
              CustomizationOption("opt_egg", "Boiled Egg (2 pcs)", 30.0),
              CustomizationOption("opt_raita", "Extra Mint Raita", 25.0),
              CustomizationOption("opt_salan", "Spicy Mirchi Salan", 35.0)
            )
          )
        )
      ),
      MenuItem(
        id = "m1_2",
        restaurantId = "rest_1",
        name = "Hyderabadi Mutton Dum Biryani",
        description = "Slow cooked long grain rice infused with saffron, ghee, and succulent baby mutton chunks.",
        price = 420.0,
        originalPrice = 450.0,
        category = "Biryani",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m1_3",
        restaurantId = "rest_1",
        name = "Paneer Tikka Dum Biryani",
        description = "Marinated fresh cottage cheese cubes layered with fragrant dum rice and fried crisp onions.",
        price = 280.0,
        category = "Biryani",
        isVeg = true,
        isBestseller = false
      ),
      MenuItem(
        id = "m1_4",
        restaurantId = "rest_1",
        name = "Andhra Chilli Chicken",
        description = "Crispy green chilli tossed chicken with curry leaves and Andhra style crushed peppercorn.",
        price = 290.0,
        category = "Starters",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m1_5",
        restaurantId = "rest_1",
        name = "Chicken 65 Dry",
        description = "South Indian specialty deep fried chicken chunks seasoned with red chili and yoghurt marinade.",
        price = 270.0,
        category = "Starters",
        isVeg = false
      ),
      MenuItem(
        id = "m1_6",
        restaurantId = "rest_1",
        name = "Guntur Paneer Dry",
        description = "Fiery Guntur red chilli spiced cottage cheese tossed with capsicum and fresh coriander.",
        price = 250.0,
        category = "Starters",
        isVeg = true
      ),
      MenuItem(
        id = "m1_7",
        restaurantId = "rest_1",
        name = "Butter Naan (2 pcs)",
        description = "Traditional clay oven baked flatbread brushed with clarified golden butter.",
        price = 90.0,
        category = "Breads",
        isVeg = true
      ),
      MenuItem(
        id = "m1_8",
        restaurantId = "rest_1",
        name = "Butter Chicken Masala",
        description = "Roasted tandoori chicken cooked in rich silky tomato, cashew cream gravy.",
        price = 310.0,
        category = "Curries",
        isVeg = false
      ),
      MenuItem(
        id = "m1_9",
        restaurantId = "rest_1",
        name = "Gulab Jamun (2 pcs)",
        description = "Warm golden milk dough dumplings steeped in cardamom and saffron sugar syrup.",
        price = 80.0,
        category = "Desserts",
        isVeg = true
      ),
      MenuItem(
        id = "m1_10",
        restaurantId = "rest_1",
        name = "Sweet Lassi (300ml)",
        description = "Thick churned creamy yogurt drink flavored with rose water and pistachios.",
        price = 70.0,
        category = "Beverages",
        isVeg = true
      )
    ),

    // 2. Truffles
    "rest_2" to listOf(
      MenuItem(
        id = "m2_1",
        restaurantId = "rest_2",
        name = "All American Cheese Burger",
        description = "Juicy flame grilled chicken patty topped with melted cheddar, caramelized onions, pickles & secret sauce.",
        price = 240.0,
        originalPrice = 270.0,
        category = "Burgers",
        isVeg = false,
        isBestseller = true,
        customizationGroups = listOf(
          CustomizationGroup(
            id = "cheese_2",
            title = "Customize Cheese & Patty",
            isRequired = false,
            options = listOf(
              CustomizationOption("c_single", "Extra Cheese Slice", 30.0),
              CustomizationOption("c_double", "Make it Double Patty", 80.0)
            )
          ),
          CustomizationGroup(
            id = "fries_2",
            title = "Add Fries Combo",
            isRequired = false,
            options = listOf(
              CustomizationOption("f_peri", "Peri Peri Fries", 65.0),
              CustomizationOption("f_salted", "Classic Salted Fries", 50.0)
            )
          )
        )
      ),
      MenuItem(
        id = "m2_2",
        restaurantId = "rest_2",
        name = "Crispy Veg Crunch Burger",
        description = "Crispy crumbed spiced potato and pea patty with iceberg lettuce and spicy mayo.",
        price = 180.0,
        category = "Burgers",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m2_3",
        restaurantId = "rest_2",
        name = "Lamb Sloppy Joe",
        description = "Minced spiced lamb with melted mozzarella inside a toasted brioche sesame bun.",
        price = 290.0,
        category = "Burgers",
        isVeg = false
      ),
      MenuItem(
        id = "m2_4",
        restaurantId = "rest_2",
        name = "Peri Peri Chicken Wings (6 pcs)",
        description = "Crispy chicken wings glazed in spicy tangy Portuguese peri-peri glaze.",
        price = 220.0,
        category = "Sides",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m2_5",
        restaurantId = "rest_2",
        name = "Loaded Cheese Fries",
        description = "Golden french fries smothered with warm liquid cheddar and jalapeño bits.",
        price = 160.0,
        category = "Sides",
        isVeg = true
      ),
      MenuItem(
        id = "m2_6",
        restaurantId = "rest_2",
        name = "Ferrero Rocher Thick Shake",
        description = "Decadent hazelnut chocolate thickshake crowned with whipped cream and crushed nuts.",
        price = 195.0,
        category = "Shakes",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m2_7",
        restaurantId = "rest_2",
        name = "Oreo Cookie Milkshake",
        description = "Crushed oreo cookies blended with creamy vanilla ice cream.",
        price = 170.0,
        category = "Shakes",
        isVeg = true
      ),
      MenuItem(
        id = "m2_8",
        restaurantId = "rest_2",
        name = "Grilled Chicken Salad",
        description = "Fresh crisp greens, cherry tomatoes, grilled chicken strips and vinaigrette dressing.",
        price = 230.0,
        category = "Healthy",
        isVeg = false
      ),
      MenuItem(
        id = "m2_9",
        restaurantId = "rest_2",
        name = "New York Style Cheesecake",
        description = "Silky smooth Philadelphia cream cheese cake slice with blueberry compote.",
        price = 180.0,
        category = "Desserts",
        isVeg = true
      ),
      MenuItem(
        id = "m2_10",
        restaurantId = "rest_2",
        name = "Classic Cold Coffee",
        description = "Freshly brewed espresso shaken with milk, sugar and vanilla scoop.",
        price = 130.0,
        category = "Beverages",
        isVeg = true
      )
    ),

    // 3. Pizza Bellissimo
    "rest_3" to listOf(
      MenuItem(
        id = "m3_1",
        restaurantId = "rest_3",
        name = "Margherita Gourmet Woodfired",
        description = "San Marzano tomato sauce, fresh buffalo mozzarella, fresh basil, and extra virgin olive oil.",
        price = 360.0,
        category = "Pizzas",
        isVeg = true,
        isBestseller = true,
        customizationGroups = listOf(
          CustomizationGroup(
            id = "crust_3",
            title = "Crust Type",
            isRequired = true,
            options = listOf(
              CustomizationOption("c_thin", "Artisan Thin Crust", 0.0),
              CustomizationOption("c_cheese", "Cheese Burst Crust", 80.0)
            )
          )
        )
      ),
      MenuItem(
        id = "m3_2",
        restaurantId = "rest_3",
        name = "Pepperoni & Smoked Bacon Feast",
        description = "Imported pork pepperoni, smoked bacon bits, jalapeños, and layered aged mozzarella.",
        price = 490.0,
        originalPrice = 540.0,
        category = "Pizzas",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m3_3",
        restaurantId = "rest_3",
        name = "Spicy Paneer Makhani Pizza",
        description = "Tandoori spiced paneer cubes, capsicum, red paprika, and creamy makhani sauce base.",
        price = 390.0,
        category = "Pizzas",
        isVeg = true
      ),
      MenuItem(
        id = "m3_4",
        restaurantId = "rest_3",
        name = "BBQ Chicken Supreme Pizza",
        description = "Smoky barbecue chicken, red onions, charred bell peppers, and mozzarella cheese.",
        price = 440.0,
        category = "Pizzas",
        isVeg = false
      ),
      MenuItem(
        id = "m3_5",
        restaurantId = "rest_3",
        name = "Creamy Alfredo Penne Pasta",
        description = "Penne tossed in creamy garlic parmesan sauce with black olives and fresh herbs.",
        price = 320.0,
        category = "Pasta",
        isVeg = true
      ),
      MenuItem(
        id = "m3_6",
        restaurantId = "rest_3",
        name = "Stuffed Garlic Breadsticks",
        description = "Warm buttery breadsticks filled with molten cheese, sweet corn and jalapeños.",
        price = 160.0,
        category = "Sides",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m3_7",
        restaurantId = "rest_3",
        name = "Classic Italian Tiramisu",
        description = "Espresso soaked ladyfingers layered with whipped mascarpone cream and cocoa powder.",
        price = 210.0,
        category = "Desserts",
        isVeg = true
      ),
      MenuItem(
        id = "m3_8",
        restaurantId = "rest_3",
        name = "Peach Iced Tea (400ml)",
        description = "Refreshing chilled brewed black tea infused with real peach nectar.",
        price = 110.0,
        category = "Beverages",
        isVeg = true
      ),
      MenuItem(
        id = "m3_9",
        restaurantId = "rest_3",
        name = "Fiery Peri Peri Chicken Wings",
        description = "Tender baked and fried wings in hot bell pepper glaze.",
        price = 240.0,
        category = "Sides",
        isVeg = false
      ),
      MenuItem(
        id = "m3_10",
        restaurantId = "rest_3",
        name = "Four Cheese Quattro Formaggi",
        description = "Mozzarella, Gorgonzola, Cheddar, and Parmesan with cracked black pepper.",
        price = 450.0,
        category = "Pizzas",
        isVeg = true
      )
    ),

    // 4. Rameshwaram Cafe
    "rest_4" to listOf(
      MenuItem(
        id = "m4_1",
        restaurantId = "rest_4",
        name = "Ghee Podi Masala Dosa",
        description = "Signature golden crispy dosa smeared with pure desi ghee and fiery gun powder, stuffed with spiced potato.",
        price = 135.0,
        category = "Dosas",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m4_2",
        restaurantId = "rest_4",
        name = "Ghee Button Idli with Sambar (14 pcs)",
        description = "Mini fluffy steamed idlis soaked in piping hot aromatic drumstick sambar and pure ghee.",
        price = 110.0,
        category = "Idlis",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m4_3",
        restaurantId = "rest_4",
        name = "Medu Vada with Chutney (2 pcs)",
        description = "Crispy deep fried lentil doughnuts served with fresh coconut and tomato chutney.",
        price = 70.0,
        category = "Snacks",
        isVeg = true
      ),
      MenuItem(
        id = "m4_4",
        restaurantId = "rest_4",
        name = "Ghee Open Butter Masala Dosa",
        description = "Open face thick sponge dosa smothered with white butter, red chutney and potato bhaji.",
        price = 140.0,
        category = "Dosas",
        isVeg = true
      ),
      MenuItem(
        id = "m4_5",
        restaurantId = "rest_4",
        name = "Bisibelebath with Boondi",
        description = "Traditional Karnataka style spicy lentil rice dish with vegetables and crunchy boondi on top.",
        price = 115.0,
        category = "Rice Bowls",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m4_6",
        restaurantId = "rest_4",
        name = "Authentic Degree Filter Coffee",
        description = "Freshly brewed chicory coffee with frothy hot boiled milk in traditional brass style.",
        price = 45.0,
        category = "Beverages",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m4_7",
        restaurantId = "rest_4",
        name = "Ghee Kesari Bath (Pineapple)",
        description = "Mouth melting semolina dessert prepared with ghee, pineapple chunks and roasted cashews.",
        price = 75.0,
        category = "Desserts",
        isVeg = true
      ),
      MenuItem(
        id = "m4_8",
        restaurantId = "rest_4",
        name = "Podi Thatte Idli (1 large)",
        description = "Giant plate shaped steamed idli topped with spicy gun powder and hot melted ghee.",
        price = 85.0,
        category = "Idlis",
        isVeg = true
      ),
      MenuItem(
        id = "m4_9",
        restaurantId = "rest_4",
        name = "Upma (Khara Bath)",
        description = "Wholesome tempered semolina with roasted mustard, curry leaves and green vegetables.",
        price = 65.0,
        category = "Snacks",
        isVeg = true
      ),
      MenuItem(
        id = "m4_10",
        restaurantId = "rest_4",
        name = "Curd Rice with Pomegranate",
        description = "Cooling spiced tempered curd rice garnished with sweet pomegranate jewels.",
        price = 95.0,
        category = "Rice Bowls",
        isVeg = true
      )
    ),

    // 5. Wok & Roll
    "rest_5" to listOf(
      MenuItem(
        id = "m5_1",
        restaurantId = "rest_5",
        name = "Steamed Chicken Dimsums (6 pcs)",
        description = "Delicate thin translucent wrappers packed with minced chicken, spring onions, served with spicy chili oil.",
        price = 210.0,
        category = "Dimsums",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m5_2",
        restaurantId = "rest_5",
        name = "Crispy Chilli Paneer Dry",
        description = "Wok tossed cottage cheese cubes with bell peppers, garlic and dark soy glaze.",
        price = 230.0,
        category = "Starters",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m5_3",
        restaurantId = "rest_5",
        name = "Hakka Noodles with Vegetables",
        description = "Wok tossed wheat noodles with julienned cabbage, carrots, bell peppers and mild spices.",
        price = 180.0,
        category = "Noodles",
        isVeg = true
      ),
      MenuItem(
        id = "m5_4",
        restaurantId = "rest_5",
        name = "Chicken Schezwan Fried Rice",
        description = "Long grain rice tossed with chicken egg shreds and fiery homemade schezwan sauce.",
        price = 220.0,
        category = "Rice",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m5_5",
        restaurantId = "rest_5",
        name = "Thai Green Curry with Jasmine Rice",
        description = "Fragrant lemongrass and coconut milk curry with exotic vegetables and steamed rice.",
        price = 310.0,
        category = "Curries",
        isVeg = true
      ),
      MenuItem(
        id = "m5_6",
        restaurantId = "rest_5",
        name = "Crispy Honey Chilli Potatoes",
        description = "Finger cut potatoes fried crisp and tossed in sweet and spicy sesame honey glaze.",
        price = 170.0,
        category = "Starters",
        isVeg = true
      ),
      MenuItem(
        id = "m5_7",
        restaurantId = "rest_5",
        name = "Chicken Manchurian Gravy",
        description = "Succulent chicken meatballs in savoury coriander garlic ginger dark gravy.",
        price = 240.0,
        category = "Main Course",
        isVeg = false
      ),
      MenuItem(
        id = "m5_8",
        restaurantId = "rest_5",
        name = "Truffle Mushroom Momos (6 pcs)",
        description = "Exotic shiitake and button mushrooms in truffle oil dressing.",
        price = 240.0,
        category = "Dimsums",
        isVeg = true
      ),
      MenuItem(
        id = "m5_9",
        restaurantId = "rest_5",
        name = "Darsaan with Vanilla Ice Cream",
        description = "Crispy fried honey glazed wonton noodles served with chilled vanilla scoop.",
        price = 140.0,
        category = "Desserts",
        isVeg = true
      ),
      MenuItem(
        id = "m5_10",
        restaurantId = "rest_5",
        name = "Lemon Mint Cooler",
        description = "Zesty crushed mint, fresh lime juice and club soda.",
        price = 90.0,
        category = "Beverages",
        isVeg = true
      )
    ),

    // 6. Haldiram's Sweets & Chaat
    "rest_6" to listOf(
      MenuItem(
        id = "m6_1",
        restaurantId = "rest_6",
        name = "Special Raj Kachori",
        description = "Crispy giant kachori stuffed with bhalla, boiled potatoes, sprout lentils, sweet yogurt and tangy chutneys.",
        price = 140.0,
        category = "Chaat",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m6_2",
        restaurantId = "rest_6",
        name = "Chole Bhature (2 pcs)",
        description = "Piping hot fluffy bhaturas served with spicy Amritsari chole, pickled onions and green chili.",
        price = 175.0,
        category = "Meals",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m6_3",
        restaurantId = "rest_6",
        name = "Pani Puri Platter (8 pcs)",
        description = "Crispy semolina puris served with spicy mint water, sweet tamarind chutney and potato chickpea filling.",
        price = 90.0,
        category = "Chaat",
        isVeg = true
      ),
      MenuItem(
        id = "m6_4",
        restaurantId = "rest_6",
        name = "Pav Bhaji (Butter Loaded)",
        description = "Mashed vegetable curry cooked on tawa with secret spices, served with 2 buttery toasted pavs.",
        price = 150.0,
        category = "Meals",
        isVeg = true
      ),
      MenuItem(
        id = "m6_5",
        restaurantId = "rest_6",
        name = "Rasmalai (2 pcs)",
        description = "Soft cottage cheese patties soaked in saffron and cardamom flavored reduced milk.",
        price = 110.0,
        category = "Sweets",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m6_6",
        restaurantId = "rest_6",
        name = "Kaju Katli (250g Box)",
        description = "Premium diamond cut cashew fudge decorated with edible silver vark.",
        price = 280.0,
        category = "Sweets",
        isVeg = true
      ),
      MenuItem(
        id = "m6_7",
        restaurantId = "rest_6",
        name = "Dahi Bhalla Papdi Chaat",
        description = "Lentil dumplings soaked in chilled sweet yogurt topped with crispy papdis and cumin.",
        price = 120.0,
        category = "Chaat",
        isVeg = true
      ),
      MenuItem(
        id = "m6_8",
        restaurantId = "rest_6",
        name = "Deluxe North Indian Thali",
        description = "Dal makhani, paneer butter masala, mixed veg, jeera rice, 2 rotis, raita, gulab jamun.",
        price = 260.0,
        category = "Meals",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m6_9",
        restaurantId = "rest_6",
        name = "Kesar Pista Badam Milk",
        description = "Chilled thick milk enriched with crushed almonds, pistachios and saffron strands.",
        price = 95.0,
        category = "Beverages",
        isVeg = true
      ),
      MenuItem(
        id = "m6_10",
        restaurantId = "rest_6",
        name = "Motichoor Ladoo (4 pcs)",
        description = "Tiny gram flour pearls fried in pure ghee and bound in fragrant sugar syrup.",
        price = 120.0,
        category = "Sweets",
        isVeg = true
      )
    ),

    // 7. Belgian Waffle
    "rest_7" to listOf(
      MenuItem(
        id = "m7_1",
        restaurantId = "rest_7",
        name = "Death By Chocolate Waff-wich",
        description = "Dark chocolate waffle layered with melted Belgian dark & milk chocolate, topped with choco chips.",
        price = 170.0,
        category = "Waffles",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m7_2",
        restaurantId = "rest_7",
        name = "Nutella Butter Waff-wich",
        description = "Warm crispy golden waffle generously smothered with imported Nutella hazelnut spread.",
        price = 185.0,
        category = "Waffles",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m7_3",
        restaurantId = "rest_7",
        name = "Red Velvet Naked Waffle",
        description = "Red velvet waffle with white chocolate filling and cream cheese drizzle.",
        price = 160.0,
        category = "Waffles",
        isVeg = true
      ),
      MenuItem(
        id = "m7_4",
        restaurantId = "rest_7",
        name = "Belgian Chocolate Thickshake",
        description = "Rich decadent dark chocolate thickshake blended with chocolate truffle cream.",
        price = 160.0,
        category = "Shakes",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m7_5",
        restaurantId = "rest_7",
        name = "Almond Butter & Honey Waffle",
        description = "Golden crisp waffle with roasted almond flakes and organic honey.",
        price = 150.0,
        category = "Waffles",
        isVeg = true
      ),
      MenuItem(
        id = "m7_6",
        restaurantId = "rest_7",
        name = "Strawberry Cream Mini Pancakes (8 pcs)",
        description = "Bite sized fluffy warm dutch pancakes drizzled with strawberry compote and cream.",
        price = 140.0,
        category = "Pancakes",
        isVeg = true
      ),
      MenuItem(
        id = "m7_7",
        restaurantId = "rest_7",
        name = "KitKat Crunch Waffle",
        description = "Crispy crushed kitkat pieces layered with milk chocolate and white chocolate swirls.",
        price = 165.0,
        category = "Waffles",
        isVeg = true
      ),
      MenuItem(
        id = "m7_8",
        restaurantId = "rest_7",
        name = "Cold Coffee with Vanilla Ice Cream",
        description = "Creamy thick iced coffee float with a scoop of vanilla ice cream.",
        price = 130.0,
        category = "Beverages",
        isVeg = true
      ),
      MenuItem(
        id = "m7_9",
        restaurantId = "rest_7",
        name = "Cookie & Cream Mini Pancakes",
        description = "Mini pancakes sprinkled with oreo crumbs and melted white chocolate.",
        price = 145.0,
        category = "Pancakes",
        isVeg = true
      ),
      MenuItem(
        id = "m7_10",
        restaurantId = "rest_7",
        name = "Lotus Biscoff Waff-wich",
        description = "Crisp waffle coated in caramelized Lotus Biscoff spread and biscuit crumbs.",
        price = 195.0,
        category = "Waffles",
        isVeg = true,
        isBestseller = true
      )
    ),

    // 8. Green Protein
    "rest_8" to listOf(
      MenuItem(
        id = "m8_1",
        restaurantId = "rest_8",
        name = "Grilled Peri Peri Paneer Salad Bowl",
        description = "High protein bowl with grilled paneer, quinoa, avocado, edamame, lettuce and lemon dressing.",
        price = 280.0,
        category = "Bowls",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m8_2",
        restaurantId = "rest_8",
        name = "Herb Roasted Chicken & Quinoa Bowl",
        description = "Tender rosemary roasted chicken breast with brown rice, broccoli, walnuts and balsamic reduction.",
        price = 330.0,
        category = "Bowls",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m8_3",
        restaurantId = "rest_8",
        name = "Greek Feta & Falafel Salad",
        description = "Crispy baked chickpea falafels, cucumber, kalamata olives, cherry tomatoes and greek feta.",
        price = 260.0,
        category = "Salads",
        isVeg = true
      ),
      MenuItem(
        id = "m8_4",
        restaurantId = "rest_8",
        name = "Avocado Toast on Sourdough (2 pcs)",
        description = "Toasted artisan sourdough with smashed Hass avocado, chili flakes, and toasted pumpkin seeds.",
        price = 240.0,
        category = "Toast & Bites",
        isVeg = true
      ),
      MenuItem(
        id = "m8_5",
        restaurantId = "rest_8",
        name = "Cold Pressed Detox Green Juice",
        description = "Green apple, cucumber, celery, spinach, ginger, and lemon cold pressed elixir.",
        price = 150.0,
        category = "Juices",
        isVeg = true
      ),
      MenuItem(
        id = "m8_6",
        restaurantId = "rest_8",
        name = "Blueberry Protein Smoothie (30g Protein)",
        description = "Wild blueberries, Greek yogurt, almond milk, and whey protein isolate.",
        price = 220.0,
        category = "Smoothies",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m8_7",
        restaurantId = "rest_8",
        name = "Smoked Salmon & Poached Egg Bowl",
        description = "Norwegian smoked salmon, organic greens, capers, quinoa and mustard seed glaze.",
        price = 450.0,
        category = "Bowls",
        isVeg = false
      ),
      MenuItem(
        id = "m8_8",
        restaurantId = "rest_8",
        name = "Keto Cauliflower Rice Stir Fry",
        description = "Grated cauliflower sauteed with mushrooms, bell peppers, sesame oil and tofu cubes.",
        price = 250.0,
        category = "Keto",
        isVeg = true
      ),
      MenuItem(
        id = "m8_9",
        restaurantId = "rest_8",
        name = "Chia Seed Coconut Pudding",
        description = "Superfood chia seeds steeped in coconut milk topped with mango cubes and pomegranate.",
        price = 160.0,
        category = "Desserts",
        isVeg = true
      ),
      MenuItem(
        id = "m8_10",
        restaurantId = "rest_8",
        name = "Ginger Turmeric Immunity Shot",
        description = "Concentrated raw organic turmeric root, ginger, black pepper and orange juice.",
        price = 80.0,
        category = "Juices",
        isVeg = true
      )
    ),

    // 9. Faasos
    "rest_9" to listOf(
      MenuItem(
        id = "m9_1",
        restaurantId = "rest_9",
        name = "Classic Chicken Tikka Wrap",
        description = "Charcoal roasted spiced chicken tikka wrapped in a handmade laccha paratha with mint sauce.",
        price = 210.0,
        category = "Wraps",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m9_2",
        restaurantId = "rest_9",
        name = "Double Egg Cheese Jumbo Roll",
        description = "Twin farm eggs cooked with golden cheese, onions and special spice mix inside a crispy paratha.",
        price = 160.0,
        category = "Wraps",
        isVeg = false,
        isBestseller = true
      ),
      MenuItem(
        id = "m9_3",
        restaurantId = "rest_9",
        name = "Paneer Signature Melt Wrap",
        description = "Soft paneer cubes tossed in creamy tikka masala with molten cheese sauce.",
        price = 190.0,
        category = "Wraps",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m9_4",
        restaurantId = "rest_9",
        name = "Smokey BBQ Chicken Roll",
        description = "Barbecue shredded chicken with crunchy onions and smoked chili mayo.",
        price = 220.0,
        category = "Wraps",
        isVeg = false
      ),
      MenuItem(
        id = "m9_5",
        restaurantId = "rest_9",
        name = "Cheesy Corn & Salsa Wrap",
        description = "Sweet American corn mixed with Mexican salsa and cheddar cheese.",
        price = 170.0,
        category = "Wraps",
        isVeg = true
      ),
      MenuItem(
        id = "m9_6",
        restaurantId = "rest_9",
        name = "Crispy French Fries with Dip",
        description = "Golden salted potato fries served with spiced cheesy jalapeño dip.",
        price = 99.0,
        category = "Sides",
        isVeg = true
      ),
      MenuItem(
        id = "m9_7",
        restaurantId = "rest_9",
        name = "Mutton Boti Kebab Wrap",
        description = "Tender marinated slow cooked mutton chunks rolled in a flaky roomali bread.",
        price = 290.0,
        category = "Wraps",
        isVeg = false
      ),
      MenuItem(
        id = "m9_8",
        restaurantId = "rest_9",
        name = "Warm Chocolate Lava Cake",
        description = "Gooey molten chocolate center inside a warm moist sponge cake.",
        price = 110.0,
        category = "Desserts",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m9_9",
        restaurantId = "rest_9",
        name = "Masala Lemonade (300ml)",
        description = "Refreshing chilled sparkling lemonade flavored with roasted cumin and rock salt.",
        price = 70.0,
        category = "Beverages",
        isVeg = true
      ),
      MenuItem(
        id = "m9_10",
        restaurantId = "rest_9",
        name = "Fiery Chicken Meatballs (8 pcs)",
        description = "Bite sized chicken meatballs tossed in hot sriracha seasoning.",
        price = 160.0,
        category = "Sides",
        isVeg = false
      )
    ),

    // 10. Chai Point
    "rest_10" to listOf(
      MenuItem(
        id = "m10_1",
        restaurantId = "rest_10",
        name = "Ginger Elaichi Dum Chai (Flask 500ml)",
        description = "Slow simmered hot tea made with fresh hand crushed ginger and aromatic green cardamom. Serves 4.",
        price = 140.0,
        category = "Chai",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m10_2",
        restaurantId = "rest_10",
        name = "Bun Maska with Jam",
        description = "Warm sweet bun generously spread with chilled Amul butter and mixed fruit jam.",
        price = 65.0,
        category = "Snacks",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m10_3",
        restaurantId = "rest_10",
        name = "Crispy Paneer Pakoda (6 pcs)",
        description = "Spiced gram flour batter coated paneer fritters served with spicy mint chutney.",
        price = 120.0,
        category = "Snacks",
        isVeg = true
      ),
      MenuItem(
        id = "m10_4",
        restaurantId = "rest_10",
        name = "Samosa with Chutney (2 pcs)",
        description = "Golden crisp pastry filled with spicy mashed potato, green peas and whole spices.",
        price = 60.0,
        category = "Snacks",
        isVeg = true,
        isBestseller = true
      ),
      MenuItem(
        id = "m10_5",
        restaurantId = "rest_10",
        name = "Masala Maggi Noodles",
        description = "Classic street style Maggi cooked with butter, onions, tomatoes and extra tastemaker.",
        price = 90.0,
        category = "Snacks",
        isVeg = true
      ),
      MenuItem(
        id = "m10_6",
        restaurantId = "rest_10",
        name = "Masala Chai (Single Cup)",
        description = "Traditional Indian spiced milk tea infused with clove, cinnamon, ginger, and cardamom.",
        price = 40.0,
        category = "Chai",
        isVeg = true
      ),
      MenuItem(
        id = "m10_7",
        restaurantId = "rest_10",
        name = "Banana Walnut Dry Cake Slice",
        description = "Freshly baked tea cake slice rich with ripe banana purée and roasted walnuts.",
        price = 85.0,
        category = "Bakery",
        isVeg = true
      ),
      MenuItem(
        id = "m10_8",
        restaurantId = "rest_10",
        name = "Poha with Roasted Peanuts",
        description = "Fluffy tempered beaten rice with mustard, turmeric, green chili and crunchy peanuts.",
        price = 80.0,
        category = "Breakfast",
        isVeg = true
      ),
      MenuItem(
        id = "m10_9",
        restaurantId = "rest_10",
        name = "Filter Coffee (Flask 500ml)",
        description = "South Indian piping hot filter coffee in heat retaining flask. Serves 4.",
        price = 160.0,
        category = "Coffee",
        isVeg = true
      ),
      MenuItem(
        id = "m10_10",
        restaurantId = "rest_10",
        name = "Vada Pav (2 pcs)",
        description = "Mumbai style spicy potato batata vada in pav with dry garlic chutney and fried green chili.",
        price = 90.0,
        category = "Snacks",
        isVeg = true,
        isBestseller = true
      )
    )
  )

  val sampleReviews = listOf(
    Review("r_1", "rest_1", "Priya Sharma", 5.0f, "Best biryani in town! The rice was fragrant and chicken pieces were so tender. Delivery was super quick too.", "2 hours ago"),
    Review("r_2", "rest_1", "Karthik R.", 4.5f, "Loved the Andhra spice level. Authentic flavors and the packaging was completely spill-proof.", "Yesterday"),
    Review("r_3", "rest_2", "Sneha Roy", 5.0f, "All American cheese burger is an absolute beast! Crispy fries and thickshake made my day.", "3 days ago"),
    Review("r_4", "rest_4", "Anand K.", 5.0f, "Crispiest Podi Dosa ever. The pure ghee aroma is unbeatable, 10/10 would order daily.", "Just now")
  )
}
