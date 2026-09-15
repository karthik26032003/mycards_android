package com.karthik.mycards.data

import com.karthik.mycards.data.local.Card
import com.karthik.mycards.data.local.Transaction

/**
 * Seed data loaded into the database on first launch, so the app has
 * something to display without a real backend.
 */
object MockData {

    val cards = listOf(
        Card(id = 1, nickname = "Everyday Rewards", last4 = "4821", network = "Visa", type = "Credit"),
        Card(id = 2, nickname = "Travel Platinum", last4 = "1093", network = "Mastercard", type = "Credit"),
        Card(id = 3, nickname = "Salary Account", last4 = "7756", network = "Visa", type = "Debit"),
    )

    val transactions = listOf(
        // ---- Card 1: Everyday Rewards ----
        Transaction(cardId = 1, merchant = "Starbucks", category = "Dining", amount = 6.75, date = "2026-07-14"),
        Transaction(cardId = 1, merchant = "Whole Foods", category = "Groceries", amount = 82.40, date = "2026-07-12"),
        Transaction(cardId = 1, merchant = "Shell", category = "Fuel", amount = 45.10, date = "2026-07-09"),
        Transaction(cardId = 1, merchant = "Domino's Pizza", category = "Dining", amount = 23.50, date = "2026-07-05"),
        Transaction(cardId = 1, merchant = "Amazon", category = "Shopping", amount = 129.99, date = "2026-06-28"),
        Transaction(cardId = 1, merchant = "Netflix", category = "Subscriptions", amount = 15.49, date = "2026-06-25"),

        // ---- Card 2: Travel Platinum ----
        Transaction(cardId = 2, merchant = "United Airlines", category = "Travel", amount = 412.00, date = "2026-07-11"),
        Transaction(cardId = 2, merchant = "Marriott Hotels", category = "Travel", amount = 268.30, date = "2026-07-10"),
        Transaction(cardId = 2, merchant = "Uber", category = "Travel", amount = 27.85, date = "2026-07-08"),
        Transaction(cardId = 2, merchant = "The Cheesecake Factory", category = "Dining", amount = 64.20, date = "2026-07-03"),
        Transaction(cardId = 2, merchant = "Duty Free", category = "Shopping", amount = 96.00, date = "2026-06-30"),

        // ---- Card 3: Salary Account ----
        Transaction(cardId = 3, merchant = "Electric Company", category = "Bills", amount = 88.15, date = "2026-07-13"),
        Transaction(cardId = 3, merchant = "Trader Joe's", category = "Groceries", amount = 54.70, date = "2026-07-07"),
        Transaction(cardId = 3, merchant = "Gym Membership", category = "Health", amount = 39.99, date = "2026-07-01"),
        Transaction(cardId = 3, merchant = "Spotify", category = "Subscriptions", amount = 10.99, date = "2026-06-27"),
    )
}
