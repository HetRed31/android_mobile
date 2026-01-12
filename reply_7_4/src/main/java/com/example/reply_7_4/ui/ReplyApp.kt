package com.example.reply_7_4.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply_7_4.data.Email
import com.example.reply_7_4.data.MailboxType
import com.example.reply_7_4.ui.utils.ReplyContentType
import com.example.reply_7_4.ui.utils.ReplyNavigationType

@Composable
fun ReplyApp(
    // Параметр, который определяет ширину окна (Compact, Medium, Expanded).
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val navigationType: ReplyNavigationType // Переменная для хранения выбранного типа навигации
    val contentType: ReplyContentType // Переменная для хранения выбранного типа контента

    // Создание ViewModel. Она будет жить дольше, чем этот Composable.
    val viewModel: ReplyViewModel = viewModel()

    // Наблюдение за состоянием UI из ViewModel.
    val replyUiState = viewModel.uiState.collectAsState().value

    // Логика адаптивного дизайна: выбор UI в зависимости от ширины окна.
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            // Телефон
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION // Навигация снизу
            contentType = ReplyContentType.LIST_ONLY // Показываем только список (детали на весь экран)
        }
        WindowWidthSizeClass.Medium -> {
            //   маленький планшет
            navigationType = ReplyNavigationType.NAVIGATION_RAIL // Боковая навигационная рельса
            contentType = ReplyContentType.LIST_ONLY // Показываем только список
        }
        WindowWidthSizeClass.Expanded -> {
            // Большой планшет
            navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER // Постоянный боковой ящик
            contentType = ReplyContentType.LIST_AND_DETAIL // Список и детали показываются одновременно
        }
        else -> {
            // Значение по умолчанию (на всякий случай)
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
    }
    ReplyHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}
