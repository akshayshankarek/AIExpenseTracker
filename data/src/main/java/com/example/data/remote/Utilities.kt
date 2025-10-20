package com.example.data.remote

import com.example.data.remote.model.GeminiContent
import com.example.data.remote.model.GeminiPart
import com.example.data.remote.model.PromptType

fun getContentBasedOnPrompts(text: String, promptType: PromptType): List<GeminiContent> {
    return when (promptType) {
        PromptType.CATEGORY_SUGGESTION -> listOf(
            GeminiContent(
                parts = listOf(
                    GeminiPart("Suggest one spending category for : \"$text\" return only the category")
                )
            )
        )

        PromptType.FINAL_AMOUNT_EXTRACTION -> listOf(
            GeminiContent(
                parts = listOf(
                    GeminiPart("Extract the final payable amount from the following receipt text: --- $text --- return only the numeric value")
                )
            )
        )

        PromptType.EXTRACT_EXPENSE_DETAILS -> listOf(
            GeminiContent(
                parts = listOf(
                    GeminiPart(
                        """
                            Extract the following details from this receipt text: 
                                - Merchant or title
                                - Total amount (final payable)
                                - date (if available)
                                - Category of expense (suggest one, if not possible then "others")
                                - Try correcting the spelling of merchant string if possible
                                Return in JSON format like:
                                {"merchant":"...", "amount":"...", "date":"...", "category":"..."}
                                
                                receipt: $text
                            """.trimIndent()
                    )
                )
            )
        )
    }
}

fun normalizeCategory(category: String?): String {
    val key = category?.lowercase()?.trim() ?: return "Other"
    return when {
        foodKeywords.any { it in key } -> "Food & Drinks"
        travelKeywords.any { it in key } -> "Travel"
        shoppingKeywords.any { it in key } -> "Shopping"
        groceryKeywords.any { it in key } -> "Grocery"
        billsKeywords.any { it in key } -> "Bills"
        entertainmentKeywords.any { it in key } -> "Entertainment"
        else -> {
            "Other"
        }
    }
}

val foodKeywords = listOf(
    "food",
    "restaurant",
    "cafe",
    "dining",
    "dinner",
    "lunch",
    "breakfast",
    "brunch",
    "meal",
    "snack",
    "coffee",
    "tea",
    "drink",
    "takeout",
    "delivery",
    "bakery",
    "pizza",
    "burger",
    "sushi",
    "pasta",
    "market",
    "supermarket",
    "eats",
    "kitchen",
    "cuisine",
    "diner",
    "bistro",
    "grill",
    "bar",
    "pub",
    "juice",
    "shake",
    "ice cream",
    "dessert",
    "cake",
    "sandwich",
    "salad",
    "chicken",
    "beef",
    "fish",
    "vegetarian",
    "vegan",
    "dhaba",
    "chaat",
    "tiffin",
    "kirana",
    "ration",
    "sabzi",
    "mandi",
    "zomato",
    "swiggy",
    "bhojanalya",
    "halwai"
)

val travelKeywords = listOf(
    "travel", "flight", "airline", "hotel", "motel", "booking", "airbnb",
    "vacation", "trip", "journey", "tour", "cruise", "rental car", "uber", "lyft",
    "taxi", "train", "bus", "gas", "fuel", "station", "resort", "lodge", "expedia",
    "kayak", "fare", "ticket"
)

val shoppingKeywords = listOf(
    "shopping",
    "apparel",
    "clothing",
    "shoes",
    "accessories",
    "boutique",
    "mall",
    "store",
    "shop",
    "outlet",
    "purchase",
    "order",
    "online shopping",
    "electronics",
    "fashion",
    "retail",
    "department store",
    "amazon",
    "ebay",
    "etsy",
    "flipkart",
    "myntra",
    "ajio",
    "nykaa",
    "meesho",
    "bazaar",
    "dukaan",
    "reliance digital",
    "croma",
    "big bazaar",
    "amazon"
)

val billsKeywords = listOf(
    "bill", "payment", "utility", "utilities", "electricity", "water", "gas",
    "internet", "cable", "phone", "mobile", "subscription", "rent", "mortgage",
    "insurance", "premium", "invoice", "fee", "charge", "tax", "loan",
    "bijli", "paani", "recharge", "postpaid", "prepaid", "broadband", "dth",
    "gpay", "paytm", "phonepe", "bhim", "upi", "emi", "challan"
)

val entertainmentKeywords = listOf(
    "entertainment",
    "movie",
    "cinema",
    "theater",
    "concert",
    "show",
    "tickets",
    "event",
    "game",
    "gaming",
    "sports",
    "bar",
    "pub",
    "club",
    "music",
    "streaming",
    "netflix",
    "spotify",
    "hulu",
    "disney+",
    "youtube",
    "playstation",
    "xbox",
    "nintendo",
    "museum",
    "park",
    "recreation",
    "pvr",
    "inox",
    "bookmyshow",
    "hotstar",
    "zee5",
    "sonyliv",
    "jiosaavn",
    "gaana",
    "ipl",
    "mela"
)
val groceryKeywords = listOf(
    "grocery", "supermarket", "market", "groceries", "produce", "dairy", "meat",
    "bakery", "pantry", "beverages", "snacks", "frozen foods", "canned goods",
    "walmart", "costco", "trader joe's", "whole foods", "target"
)
