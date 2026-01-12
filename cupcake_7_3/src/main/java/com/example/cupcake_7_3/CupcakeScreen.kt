package com.example.cupcake_7_3

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake_7_3.data.DataSource
import com.example.cupcake_7_3.data.OrderUiState
import com.example.cupcake_7_3.ui.OrderSummaryScreen
import com.example.cupcake_7_3.ui.OrderViewModel
import com.example.cupcake_7_3.ui.SelectOptionScreen
import com.example.cupcake_7_3.ui.StartOrderScreen

/**
 * enum: Определяет все маршруты (экраны) для навигации.
 */
enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

/**
 * Компонент TopAppBar: Отображает заголовок и кнопку "Назад".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            // Кнопка "Назад" отображается, только если возможна навигация назад.
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Главный компонент приложения: Управляет ViewModel и Навигацией.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeApp(
    // ViewModel создается здесь и будет жить до тех пор, пока живет Activity.
    viewModel: OrderViewModel = viewModel(),
    // NavController управляет стеком экранов.
    navController: NavHostController = rememberNavController()
) {
    // Получаем текущий экран для обновления заголовка TopAppBar.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                // Проверяем, есть ли предыдущий экран в стеке.
                canNavigateBack = navController.previousBackStackEntry != null,
                // navigateUp() возвращает на предыдущий экран.
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        // Наблюдаем за состоянием заказа из ViewModel.
        // uiState содержит все данные (количество, вкус, цена).
        val uiState by viewModel.uiState.collectAsState()

        // NavHost: Контейнер, который переключает Composable-экраны.
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name, // Точка входа
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // ЭКРАН 1: Start (Выбор количества)
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        // 1. Обновляем ViewModel: сохраняем выбранное количество.
                        viewModel.setQuantity(it)
                        // 2. Навигация: переходим на экран выбора вкуса.
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            // ЭКРАН 2: Flavor (Выбор вкуса)
            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    // subtotal берется из uiState (ViewModel) и автоматически обновляется.
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController) // Отмена заказа
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    // Обновляем ViewModel при выборе вкуса.
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            // ЭКРАН 3: Pickup (Выбор даты)
            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price, // Цена снова берется из uiState.
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    // Обновляем ViewModel при выборе даты.
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            // ЭКРАН 4: Summary (Сводка заказа)
            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current
                OrderSummaryScreen(
                    // Передаем весь объект состояния для отображения всех деталей.
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        // Вызов функции для отправки заказа через Intent.
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

/**
 * Вспомогательная функция: Сбрасывает состояние заказа и возвращает на стартовый экран.
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder() // Сброс всех данных в ViewModel.
    // popBackStack: Очищает стек навигации до экрана Start.
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

/**
 * Вспомогательная функция: Создает Intent для отправки деталей заказа.
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Создание неявного Intent для отправки текста (например, через почту или мессенджер).
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}
