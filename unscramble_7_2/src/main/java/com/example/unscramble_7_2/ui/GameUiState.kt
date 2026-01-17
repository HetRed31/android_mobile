package com.example.unscramble_7_2.ui

/**
 * Data class that represents the game UI state
 */
data class GameUiState(
    val currentScrambledWord: String = "",
    val currentWordCount: Int = 1,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false
)
