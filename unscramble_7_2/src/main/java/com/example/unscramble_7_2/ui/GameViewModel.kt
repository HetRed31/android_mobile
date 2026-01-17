package com.example.unscramble_7_2.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble_7_2.data.MAX_NO_OF_WORDS
import com.example.unscramble_7_2.data.SCORE_INCREASE
import com.example.unscramble_7_2.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



class GameViewModel : ViewModel() {

    // 1. Хранение состояния UI (StateFlow)

    private val _uiState = MutableStateFlow(GameUiState())

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // 2. Хранение ввода пользователя (Compose State)
    var userGuess by mutableStateOf("")
        private set

    // 3. Внутреннее состояние игры
    private lateinit var currentWord: String // Правильное слово для текущего раунда
    private var usedWords: MutableSet<String> = mutableSetOf() // Набор слов, которые уже были использованы


    init {
        resetGame() // Начинаем игру сразу при создании ViewModel
    }

    // 5. Логика выбора и перемешивания слова
    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random() // Выбираем случайное слово

        // Проверка на повтор: если слово уже использовалось, выбираем новое (рекурсия)
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord) // Добавляем слово в использованные
            shuffleCurrentWord(currentWord) // Перемешиваем и возвращаем
        }
    }

    // 6. Логика перемешивания букв
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()

        // Гарантия: цикл повторяет перемешивание, пока слово не будет отличаться от оригинала
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    // 7. Сброс игры
    fun resetGame() {
        usedWords.clear() // Очищаем список использованных слов

        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    // 8. Обновление ввода пользователя
    // Вызывается при каждом нажатии клавиши в поле ввода
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    // 9. Проверка ответа пользователя
    fun checkUserGuess() {

        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore) // Ответ правильный: обновляем состояние игры
        } else {
            // Ответ неправильный: устанавливаем флаг ошибки в UI State
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("") // Очищаем поле ввода после проверки
    }

    // 10. Обновление состояния игры (после правильного ответа или пропуска)
    private fun updateGameState(updatedScore: Int) {
        // Проверка: достигнуто ли максимальное количество слов (конец игры)
        if (usedWords.size == MAX_NO_OF_WORDS){
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true // Устанавливаем флаг конца игры
                )
            }
        } else{
            // Игра продолжается: выбираем новое слово и обновляем счетчики
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(), // Увеличиваем счетчик слов
                    score = updatedScore
                )
            }
        }
    }

    // 11. Пропуск слова
    fun skipWord() {
        // Обновляем состояние, но передаем текущий счет (без увеличения)
        updateGameState(_uiState.value.score)
        updateUserGuess("") // Очищаем поле ввода
    }
}
