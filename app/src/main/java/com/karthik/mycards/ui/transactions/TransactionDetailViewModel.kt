package com.karthik.mycards.ui.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.mycards.data.local.Transaction
import com.karthik.mycards.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    repository: CardsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val transactionId: Int = checkNotNull(savedStateHandle["transactionId"])

    // Emits the transaction (or null while loading / if not found).
    val transaction: StateFlow<Transaction?> =
        repository.getTransaction(transactionId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
}
