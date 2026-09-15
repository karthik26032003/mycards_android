package com.karthik.mycards.ui.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.mycards.data.local.Card
import com.karthik.mycards.data.local.Transaction
import com.karthik.mycards.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface TransactionsUiState {
    data object Loading : TransactionsUiState
    data class Success(
        val card: Card?,
        val transactions: List<Transaction>
    ) : TransactionsUiState
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    repository: CardsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // The navigation argument "cardId" is delivered via SavedStateHandle.
    private val cardId: Int = checkNotNull(savedStateHandle["cardId"])

    // combine merges two live streams (the card + its transactions) into one UI state.
    val uiState: StateFlow<TransactionsUiState> =
        combine(
            repository.getCard(cardId),
            repository.getTransactionsForCard(cardId)
        ) { card, transactions ->
            TransactionsUiState.Success(card, transactions) as TransactionsUiState
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TransactionsUiState.Loading
        )
}
