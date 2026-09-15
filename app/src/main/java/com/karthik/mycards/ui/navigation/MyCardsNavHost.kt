package com.karthik.mycards.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.karthik.mycards.ui.cards.CardsScreen
import com.karthik.mycards.ui.chat.ChatScreen
import com.karthik.mycards.ui.transactions.TransactionDetailScreen
import com.karthik.mycards.ui.transactions.TransactionsScreen

/**
 * The navigation graph. Each screen is a "destination" with a route.
 * Routes carry arguments (e.g. the tapped card's id) in the path.
 */
@Composable
fun MyCardsNavHost(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "cards") {

        // Screen 1: the card list
        composable("cards") {
            CardsScreen(
                onCardClick = { cardId -> navController.navigate("transactions/$cardId") },
                onOpenChat = { navController.navigate("chat") },
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }

        // Screen 2: transactions for a card
        composable(
            route = "transactions/{cardId}",
            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
        ) {
            TransactionsScreen(
                onTransactionClick = { txId -> navController.navigate("detail/$txId") },
                onBack = { navController.popBackStack() }
            )
        }

        // Screen 3: a single transaction's detail
        composable(
            route = "detail/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) {
            TransactionDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Screen 4: the AI assistant
        composable("chat") {
            ChatScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
