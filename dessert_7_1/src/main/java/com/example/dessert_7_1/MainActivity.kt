package com.example.dessert_7_1

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dessert_7_1.ui.theme.DessertClickerTheme

// Фрагмент кода из MainActivity.kt

class MainActivity : ComponentActivity() {
    // 1. Жизненный цикл Activity: onCreate - точка входа
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Логирование для отслеживания жизненного цикла (учебная цель)
        Log.d(TAG, "onCreate Called")
        // Включает отображение контента под системными панелями (статус-бар, навигационная панель)
        enableEdgeToEdge()
        // Устанавливает Jetpack Compose UI в качестве контента Activity
        setContent {
            DessertClickerTheme {
                Surface(
                    // Modifier для заполнения всего доступного пространства
                    modifier = Modifier
                        .fillMaxSize()
                        // Учитывает отступы для статус-бара, чтобы контент не перекрывался им
                        .statusBarsPadding(),
                ) {
                    // Запуск корневой Composable-функции приложения
                    DessertClickerApp()
                }
            }
        }
    }

    // 2. Методы жизненного цикла Activity:
    // Все эти методы переопределены для демонстрации и логирования.
    // ViewModel, в отличие от Activity, переживает изменения конфигурации (например, поворот экрана).
    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(TAG, "onRestart Called")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy Called")
    }
}


private fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
    // Создание Intent с действием ACTION_SEND (отправить)
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        // Добавление текста для обмена, используя строковый ресурс
        putExtra(
            Intent.EXTRA_TEXT,
            intentContext.getString(R.string.share_text, dessertsSold, revenue)
        )
        // Указание типа контента (простой текст)
        type = "text/plain"
    }

    // Создание  пользователь мог выбрать приложение для обмена
    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        // Запуск Intent
        intentContext.startActivity(shareIntent)
    } catch (e: ActivityNotFoundException) {
        // Обработка случая, когда нет приложения, способного обработать Intent
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}

// 4. Корневая Composable-функция (Интеграция с ViewModel)
@Composable
private fun DessertClickerApp(
    // Создаем экземпляр DessertViewModel
    // ViewModel переживет рекомпозицию и изменения конфигурации.
    viewModel: DessertViewModel = viewModel()
) {
    // Наблюдаем за изменениями в uiState StateFlow из ViewModel.
    // 'collectAsState()' преобразует StateFlow в State<T>, который вызывает рекомпозицию.
    val uiState by viewModel.uiState.collectAsState()

    // Передача состояния (State) вниз и обработчика событий (Event) вниз
    DessertClickerApp(
        uiState = uiState, // Состояние: данные для отображения
        // Событие: лямбда-функция, которая вызывает onDessertClicked в ViewModel.
        onDessertClicked = viewModel::onDessertClicked
    )
}

// 5. Composable-функция, принимающая состояние и события (UDF)
@Composable
private fun DessertClickerApp(
    // Получаем текущее состояние пользовательского интерфейса (доход, проданные десерты и т.д.)
    uiState: DessertUiState,
    // Получаем функцию для обработки клика по десерту
    onDessertClicked: () -> Unit
) {
    // Scaffold предоставляет базовую структуру UI (AppBar, Content, FloatingActionButton и т.д.)
    Scaffold(
        topBar = {
            // Получаем Context для запуска Intent (Side Effect)
            val intentContext = LocalContext.current
            AppBar(
                onShareButtonClicked = {
                    shareSoldDessertsInformation(
                        intentContext = intentContext,
                        // Используем данные о проданных десертах и доходе из uiState для отправки
                        dessertsSold = uiState.dessertsSold,
                        revenue = uiState.revenue
                    )
                }
            )
        }
    ) { contentPadding ->
        // Основной экран приложения
        DessertClickerScreen(
            // Передаем данные о доходе и проданных десертах в дочернюю компонуемую функцию
            revenue = uiState.revenue,
            dessertsSold = uiState.dessertsSold,
            dessertImageId = uiState.currentDessertImageId,
            onDessertClicked = onDessertClicked, // Передача обработчика клика
            modifier = Modifier.padding(contentPadding)
        )
    }
}

// 6. Composable-функция для верхней панели (AppBar)
@Composable
private fun AppBar(
    onShareButtonClicked: () -> Unit, // Лямбда-функция для обработки нажатия кнопки "Поделиться"
    modifier: Modifier = Modifier
) {
    // Row для горизонтального расположения элементов
    Row(
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.SpaceBetween, // Располагает элементы по краям
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Текст заголовка приложения
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
        // Кнопка "Поделиться"
        IconButton(
            onClick = onShareButtonClicked, // Вызывает переданную лямбда-функцию
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
        ) {
            Icon(
                imageVector = Icons.Filled.Share, // Использование встроенной иконки
                contentDescription = stringResource(R.string.share),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

// 7. Composable-функция для основного экрана
@Composable
fun DessertClickerScreen(
    revenue: Int,
    dessertsSold: Int,
    @DrawableRes dessertImageId: Int, // Аннотация указывает, что это ID ресурса Drawable
    onDessertClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Box позволяет накладывать элементы друг на друга
    Box(modifier = modifier) {
        // Фоновое изображение
        Image(
            painter = painterResource(R.drawable.bakery_back),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Column {
            // Контейнер для изображения десерта
            Box(
                modifier = Modifier
                    .weight(1f) // Занимает все оставшееся вертикальное пространство
                    .fillMaxWidth(),
            ) {
                // Изображение десерта
                Image(
                    painter = painterResource(dessertImageId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.image_size))
                        .height(dimensionResource(R.dimen.image_size))
                        .align(Alignment.Center)
                        // Точка, где происходит событие клика, которое передается в ViewModel
                        .clickable { onDessertClicked() },
                    contentScale = ContentScale.Crop,
                )
            }
            // Панель с информацией о транзакциях
            TransactionInfo(
                revenue = revenue,
                dessertsSold = dessertsSold,
                modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
            )
        }
    }
}

// 8. Вспомогательные Composable-функции для отображения информации
@Composable
private fun TransactionInfo(
    revenue: Int,
    dessertsSold: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DessertsSoldInfo(
            dessertsSold = dessertsSold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
        RevenueInfo(
            revenue = revenue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
private fun RevenueInfo(revenue: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.total_revenue),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = "$${revenue}",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun DessertsSoldInfo(dessertsSold: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.dessert_sold),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = dessertsSold.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DessertClickerAppPreview() {
    DessertClickerTheme {
        DessertClickerApp(
            uiState = DessertUiState(),
            onDessertClicked = {}
        )
    }
}

