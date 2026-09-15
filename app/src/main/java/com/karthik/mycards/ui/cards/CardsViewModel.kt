package com.karthik.mycards.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.mycards.data.local.Card
import com.karthik.mycards.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * All the states the cards screen can be in.
 * A sealed interface forces the UI to handle EVERY case explicitly.
 */
sealed interface CardsUiState {
    data object Loading : CardsUiState
    data class Success(val cards: List<Card>) : CardsUiState
}

/**
 * The screen's "brain". @HiltViewModel + @Inject = Hilt supplies the
 * repository automatically. It exposes a StateFlow the UI observes.
 */
@HiltViewModel
class CardsViewModel @Inject constructor(
    private val repository: CardsRepository
) : ViewModel() {

    // Turn the repository's live Card stream into UI state.
    // stateIn keeps the latest value cached so the screen has data instantly.
    val uiState: StateFlow<CardsUiState> =
        repository.getAllCards()
            .map<List<Card>, CardsUiState> { CardsUiState.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CardsUiState.Loading
            )

    init {
        // Load the sample data the first time the app runs.
        viewModelScope.launch { repository.seedIfEmpty() }
    }
}
