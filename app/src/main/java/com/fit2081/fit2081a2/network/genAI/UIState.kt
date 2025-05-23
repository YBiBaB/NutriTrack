package com.fit2081.fit2081a2.network.genAI

sealed interface UiState {
    object Initial : UiState
    object Loading : UiState
    data class Success(val outputString: String) : UiState
    data class Error(val errorMessage: String) : UiState
}