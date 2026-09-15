package com.karthik.mycards.ui.cards

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karthik.mycards.data.local.Card
import com.karthik.mycards.ui.components.gradientFor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    onCardClick: (Int) -> Unit,
    onOpenChat: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cards") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Text(
                            if (isDarkTheme) "☀️" else "🌙",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onOpenChat) { Text("Ask AI") }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is CardsUiState.Loading ->
                    CircularProgressIndicator(Modifier.align(Alignment.Center))

                is CardsUiState.Success ->
                    CardsStack(cards = state.cards, onCardClick = onCardClick)
            }
        }
    }
}

/**
 * A wallet-style stack: only the top ~16% of each card peeks out; the
 * selected card sits fully visible at the front. Tap a peeking card to
 * bring it to the front; tap the front card to open its transactions.
 */
@Composable
private fun CardsStack(cards: List<Card>, onCardClick: (Int) -> Unit) {
    if (cards.isEmpty()) return

    // Stable gradient per card (by original position).
    val colorIndex = remember(cards) { cards.mapIndexed { i, c -> c.id to i }.toMap() }
    var selectedId by rememberSaveable { mutableStateOf(cards.first().id) }

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(24.dp))

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            val cardWidth = maxWidth
            val cardHeight = cardWidth / 1.586f          // real card proportions
            val peek = cardHeight * 0.16f                // ~16% of each card shows
            val stackHeight = cardHeight + peek * (cards.size - 1)

            // Non-selected cards keep their order; the selected one goes last
            // so it draws on top and is fully visible.
            val displayOrder = cards.filter { it.id != selectedId } +
                cards.first { it.id == selectedId }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(stackHeight)
            ) {
                displayOrder.forEachIndexed { slot, card ->
                    key(card.id) {
                        val y by animateDpAsState(
                            targetValue = peek * slot,
                            label = "cardOffset"
                        )
                        CreditCardView(
                            card = card,
                            colors = gradientFor(colorIndex[card.id] ?: 0),
                            onClick = {
                                if (card.id == selectedId) onCardClick(card.id)
                                else selectedId = card.id
                            },
                            modifier = Modifier.offset(y = y)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Text(
            "Tap a card to bring it to the front  •  Tap the front card to view transactions",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
private fun CreditCardView(
    card: Card,
    colors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
            .shadow(10.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(colors))
            .clickable(onClick = onClick)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top strip (this is the part that stays visible when peeking)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    card.nickname,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    card.network.uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Middle: chip + card number
            Column {
                Box(
                    modifier = Modifier
                        .size(width = 46.dp, height = 32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE9C46A))
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    "••••   ••••   ••••   ${card.last4}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    letterSpacing = 2.sp
                )
            }

            // Bottom: type + call to action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    card.type.uppercase(),
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    "VIEW  ›",
                    color = Color.White.copy(alpha = 0.95f),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
