package com.karthik.mycards.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.mycards.data.repository.AiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(val text: String, val fromUser: Boolean)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isSending: Boolean = false
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val aiRepository: AiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun send(question: String) {
        val trimmed = question.trim()
        if (trimmed.isEmpty() || _uiState.value.isSending) return

        // Show the user's message immediately, and mark "sending".
        _uiState.update {
            it.copy(
                messages = it.messages + ChatMessage(trimmed, fromUser = true),
                isSending = true
            )
        }

        viewModelScope.launch {
            val reply = try {
                aiRepository.ask(trimmed)
            } catch (e: Exception) {
                "Sorry, I couldn't reach the assistant. Is the proxy running? (${e.message})"
            }
            _uiState.update {
                it.copy(
                    messages = it.messages + ChatMessage(reply, fromUser = false),
                    isSending = false
                )
            }
        }
    }
}
