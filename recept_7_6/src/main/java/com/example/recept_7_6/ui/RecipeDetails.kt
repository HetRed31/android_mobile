package com.example.recept_7_6.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recept_7_6.R
import com.example.recept_7_6.data.DataSource
import com.example.recept_7_6.model.Recipe
import com.example.recept_7_6.ui.theme.GreetingCardTheme

@Composable
fun RecipeDetails(
    selectedRecipe: Recipe,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
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
            // 1. НАЗВАНИЕ БЛЮДА (НОВЫЙ ЗАГОЛОВОК)
            Text(
                text = stringResource(selectedRecipe.titleResourceId),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface, // <-- Цвет текста для светлого фона
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal),
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
            )

            // 2. ИЗОБРАЖЕНИЕ
            Box {
                Image(
                    painter = painterResource(selectedRecipe.imageResourceId),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                // УДАЛЯЕМ ГРАДИЕНТ И ТЕКСТ, КОТОРЫЕ БЫЛИ ПОВЕРХ КАРТИНКИ
            }

            // 3. ОПИСАНИЕ БЛЮДА
            Text(
                text = stringResource(selectedRecipe.detailsResourceId),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}

@Preview
@Composable
fun DetailsPreview() {
    GreetingCardTheme {
        RecipeDetails(
            selectedRecipe = DataSource.defaultRecipe,
            onBackPressed = { },
            contentPadding = PaddingValues(),
        )
    }
}
