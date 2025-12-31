package com.example.recept_7_6.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
    ) {
        Column {
            // 1. НАЗВАНИЕ БЛЮДА
            Text(
                text = stringResource(selectedRecipe.titleResourceId),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
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
            onBackPressed = { }
        )
    }
}
