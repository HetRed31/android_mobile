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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.recept_7_6.data.DataSource
import com.example.recept_7_6.model.RecipeCategory
import com.example.recept_7_6.navigation.RecipesRoute
import com.example.recept_7_6.ui.RecipeDetails
import com.example.recept_7_6.ui.RecipesList
import com.example.recept_7_6.ui.RecipesViewModel
import com.example.recept_7_6.ui.theme.GreetingCardTheme

@Composable
fun RecipesApp(
    navController: NavHostController = rememberNavController(),
    viewModel: RecipesViewModel = viewModel()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    
    // Определяем заголовок и видимость кнопки "Назад" на основе текущего маршрута
    val isShowingFirstPage = currentRoute?.contains("CategoryList") == true
    val currentTitle = when {
        currentRoute?.contains("CategoryList") == true -> stringResource(R.string.app_name)
        currentRoute?.contains("RecipeList") == true -> {
            // Извлекаем название категории из маршрута
            backStackEntry?.arguments?.getString("categoryName")?.let { categoryName ->
                RecipeCategory.entries.find { it.name == categoryName }?.let { category ->
                    stringResource(category.titleResourceId)
                }
            } ?: stringResource(R.string.app_name)
        }
        currentRoute?.contains("RecipeDetails") == true -> {
            // Извлекаем ID рецепта и находим его название
            backStackEntry?.arguments?.getInt("recipeId")?.let { recipeId ->
                DataSource.recipes.find { it.id == recipeId }?.let { recipe ->
                    stringResource(recipe.titleResourceId)
                }
            } ?: stringResource(R.string.app_name)
        }
        else -> stringResource(R.string.app_name)
    }

    Scaffold(
        topBar = {
            RecipesAppBar(
                title = currentTitle,
                isShowingFirstPage = isShowingFirstPage,
                onBackButtonClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = RecipesRoute.CategoryList,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Экран со списком категорий
            composable<RecipesRoute.CategoryList> {
                RecipesList(
                    recipes = DataSource.getMain(),
                    onClick = { recipe ->
                        navController.navigate(RecipesRoute.RecipeList(recipe.category.name))
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                )
            }

            // Экран со списком рецептов категории
            composable<RecipesRoute.RecipeList> { backStackEntry ->
                val route: RecipesRoute.RecipeList = backStackEntry.toRoute()
                val category = RecipeCategory.entries.find { it.name == route.categoryName }
                
                if (category != null) {
                    RecipesList(
                        recipes = DataSource.getRecipesForCategory(category),
                        onClick = { recipe ->
                            navController.navigate(RecipesRoute.RecipeDetails(recipe.id))
                        },
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    )
                }
            }

            // Экран с деталями рецепта
            composable<RecipesRoute.RecipeDetails> { backStackEntry ->
                val route: RecipesRoute.RecipeDetails = backStackEntry.toRoute()
                val recipe = DataSource.recipes.find { it.id == route.recipeId }
                
                if (recipe != null) {
                    RecipeDetails(
                        selectedRecipe = recipe,
                        onBackPressed = { navController.navigateUp() }
                    )
                }
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
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White
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
