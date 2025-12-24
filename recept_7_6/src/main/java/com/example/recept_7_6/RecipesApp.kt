package com.example.recept_7_6

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recept_7_6.data.DataSource
import com.example.recept_7_6.model.RecipeCategory
import com.example.recept_7_6.ui.RecipeDetails
import com.example.recept_7_6.ui.RecipesList
import com.example.recept_7_6.ui.RecipesViewModel
import com.example.recept_7_6.ui.theme.GreetingCardTheme

@Composable
fun RecipesApp(
    viewModel: RecipesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            RecipesAppBar(
                title = stringResource(id = uiState.currentTitle),
                isShowingFirstPage = uiState.isShowingCategoryList,
                onBackButtonClick = { viewModel.navigateBack() }
            )
        }
    ) { paddingValues ->
        if (uiState.isShowingCategoryList) {
            // Screen with categories
            RecipesList(
                recipes = DataSource.getMain(),
                onClick = {
                    viewModel.navigateToListPage(it.category)
                },
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                contentPadding = paddingValues,
            )
        } else if (uiState.selectedRecipe != null) {
            // Details screen
            RecipeDetails(
                selectedRecipe = uiState.selectedRecipe!!,
                contentPadding = paddingValues,
                onBackPressed = {
                    viewModel.navigateBack()
                }
            )
        } else {
            // Screen with recipes from a category
            val category = RecipeCategory.entries.find { it.titleResourceId == uiState.currentTitle }
            if (category != null) {
                RecipesList(
                    recipes = DataSource.getRecipesForCategory(category),
                    onClick = {
                        viewModel.selectRecipe(it)
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    contentPadding = paddingValues,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
    isShowingFirstPage: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        navigationIcon = if (!isShowingFirstPage) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        } else {
            { Box {} }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun AppPreview() {
    GreetingCardTheme {
        RecipesApp()
    }
}