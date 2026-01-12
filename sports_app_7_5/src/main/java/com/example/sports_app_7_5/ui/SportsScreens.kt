package com.example.sports_app_7_5.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sports_app_7_5.R
import com.example.sports_app_7_5.data.LocalSportsDataProvider
import com.example.sports_app_7_5.model.Sport
import com.example.sports_app_7_5.ui.theme.SportsTheme
import com.example.sports_app_7_5.utils.SportsContentType



@Composable
fun SportsApp(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val viewModel: SportsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    //  (АДАПТИВНОСТЬ) ===
    // Определяем тип контента (макета) на основе ширины окна (windowSize)
    val contentType = when (windowSize) {
        // Если ширина экрана "Компактная" или "Средняя" (телефоны, небольшие планшеты)
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium -> SportsContentType.ListOnly // -> Используем однопанельный макет

        // Если ширина экрана "Расширенная" (большие планшеты)
        WindowWidthSizeClass.Expanded -> SportsContentType.ListAndDetail // -> Используем двухпанельный макет
        else -> SportsContentType.ListOnly
    }


    Scaffold(
        topBar = {
            SportsAppBar(
                isShowingListPage = uiState.isShowingListPage,
                // Логика возврата: при нажатии на кнопку "Назад" в TopBar вызывается navigateToListPage()
                onBackButtonClick = { viewModel.navigateToListPage() },
                windowSize = windowSize
            )
        }
    ) { innerPadding ->
        // === НАЧАЛО ВЫБОРА МАКЕТА ===
        if (contentType == SportsContentType.ListAndDetail) {
            // 1. Двухпанельный макет (для Expanded/планшетов)
            SportsListAndDetail(
                sports = uiState.sportsList,
                selectedSport = uiState.currentSport,
                onClick = {
                    // При нажатии на элемент в двухпанельном режиме просто обновляем детали справа
                    viewModel.updateCurrentSport(it)
                },
                onBackPressed = onBackPressed,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // 2. Однопанельный макет (для Compact/Medium/телефонов)
            if (uiState.isShowingListPage) {
                // Если флаг isShowingListPage == true, показываем СПИСОК
                SportsList(
                    sports = uiState.sportsList,
                    onClick = {
                        // === НАЧАЛО ЛОГИКИ НАВИГАЦИИ (ПРОВАЛИВАНИЕ ДАЛЬШЕ) ===
                        viewModel.updateCurrentSport(it) // 1. Обновляем выбранный вид спорта в ViewModel
                        viewModel.navigateToDetailPage() // 2. Устанавливаем флаг isShowingListPage = false, что вызывает перерисовку на SportsDetail
                        // === КОНЕЦ ЛОГИКИ НАВИГАЦИИ (ПРОВАЛИВАНИЕ ДАЛЬШЕ) ===
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    contentPadding = innerPadding,
                )
            } else {
                // Если флаг isShowingListPage == false, показываем ДЕТАЛИ
                SportsDetail(
                    selectedSport = uiState.currentSport,
                    contentPadding = innerPadding,
                    onBackPressed = {
                        // === НАЧАЛО ЛОГИКИ НАВИГАЦИИ (ВОЗВРАТ) ===
                        viewModel.navigateToListPage() // Устанавливает флаг isShowingListPage = true, что вызывает перерисовку на SportsList
                        // === КОНЕЦ ЛОГИКИ НАВИГАЦИИ (ВОЗВРАТ) ===
                    }
                )
            }
        }
        // === КОНЕЦ ВЫБОРА МАКЕТА ===
    }
}

/**
 * Composable, который отображает верхнюю панель и кнопку "Назад", если возможна навигация назад.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    // Определяем, нужно ли показывать кнопку "Назад" (только в однопанельном режиме на экране деталей)
    val isShowingDetailPage = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage
    TopAppBar(
        title = {
            Text(
                text =
                    if (isShowingDetailPage) {
                        stringResource(R.string.detail_fragment_label)
                    } else {
                        stringResource(R.string.list_fragment_label)
                    },
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = if (isShowingDetailPage) {
            {
                // === ЛОГИКА НАВИГАЦИИ (ВОЗВРАТ ЧЕРЕЗ TOPBAR) ===
                IconButton(onClick = onBackButtonClick) { // onBackButtonClick вызывает viewModel.navigateToListPage()
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
                // === КОНЕЦ ЛОГИКИ НАВИГАЦИИ (ВОЗВРАТ ЧЕРЕЗ TOPBAR) ===
            }
        } else {
            { Box {} }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SportsListItem(
    sport: Sport,
    onItemClick: (Sport) -> Unit,
    modifier: Modifier = Modifier
) {
    // === НАЧАЛО СТРУКТУРЫ КАРТОЧКИ ===
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(sport) } // Обработчик нажатия на карточку
    ) {
        Row( // Горизонтальный контейнер для всего содержимого карточки
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            // 1. Изображение (SportsListImageItem) - занимает фиксированную ширину
            SportsListImageItem(
                sport = sport,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            // 2. Колонка с текстовым контентом - занимает оставшееся место
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f) // Позволяет колонке занять всю оставшуюся ширину
            ) {
                // Название спорта
                Text(
                    text = stringResource(sport.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                // Подзаголовок
                Text(
                    text = stringResource(sport.subtitleResourceId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(Modifier.weight(1f)) // Отталкивает нижнюю строку к низу колонки
                Row { // Нижняя строка с деталями
                    // Количество игроков
                    Text(
                        text = pluralStringResource(
                            R.plurals.player_count_caption,
                            sport.playerCount,
                            sport.playerCount
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f)) // Отталкивает олимпийский статус вправо
                    if (sport.olympic) {
                        // Олимпийский статус
                        Text(
                            text = stringResource(R.string.olympic_caption),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
    // === КОНЕЦ СТРУКТУРЫ КАРТОЧКИ ===
}

@Composable
private fun SportsListImageItem(sport: Sport, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(sport.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun SportsList(
    sports: List<Sport>,
    onClick: (Sport) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(sports, key = { sport -> sport.id }) { sport ->
            SportsListItem(
                sport = sport,
                onItemClick = onClick // Передаем обработчик нажатия из SportsApp
            )
        }
    }
}

@Composable
private fun SportsDetail(
    selectedSport: Sport,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    // === ЛОГИКА НАВИГАЦИИ (ВОЗВРАТ ЧЕРЕЗ СИСТЕМНУЮ КНОПКУ "НАЗАД") ===
    BackHandler {
        onBackPressed() // Вызывает viewModel.navigateToListPage()
    }
    // === КОНЕЦ ЛОГИКИ НАВИГАЦИИ (ВОЗВРАТ ЧЕРЕЗ СИСТЕМНУЮ КНОПКУ "НАЗАД") ===
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box {
                Box {
                    Image(
                        painter = painterResource(selectedSport.sportsImageBanner),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedSport.titleResourceId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(
                            text = pluralStringResource(
                                R.plurals.player_count_caption,
                                selectedSport.playerCount,
                                selectedSport.playerCount
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(R.string.olympic_caption),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                    }
                }
            }
            Text(
                text = stringResource(selectedSport.sportDetails),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}

@Composable
private fun SportsListAndDetail(
    sports: List<Sport>,
    selectedSport: Sport,
    onClick: (Sport) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // === ДВУХПАНЕЛЬНЫЙ МАКЕТ (для Expanded/планшетов) ===
    Row(
        modifier = modifier
    ) {
        SportsList( // Список слева (занимает 2/5 ширины)
            sports = sports,
            onClick = onClick,
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
        SportsDetail( // Детали справа (занимает 3/5 ширины)
            selectedSport = selectedSport,
            modifier = Modifier.weight(3f),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            onBackPressed = onBackPressed,
        )
    }
    // === КОНЕЦ ДВУХПАНЕЛЬНОГО МАКЕТА ===
}

@Preview
@Composable
fun SportsListItemPreview() {
    SportsTheme {
        SportsListItem(
            sport = LocalSportsDataProvider.defaultSport,
            onItemClick = {}
        )
    }
}

@Preview
@Composable
fun SportsListPreview() {
    SportsTheme {
        Surface {
            SportsList(
                sports = LocalSportsDataProvider.getSportsData(),
                onClick = {},
            )
        }
    }
}

@Preview(device = Devices.TABLET)
@Composable
fun SportsListAndDetailsPreview() {
    SportsTheme {
        Surface {
            SportsListAndDetail(
                sports = LocalSportsDataProvider.getSportsData(),
                selectedSport = LocalSportsDataProvider.getSportsData().getOrElse(0) {
                    LocalSportsDataProvider.defaultSport
                },
                onClick = {},
                onBackPressed = {},
            )
        }
    }
}
