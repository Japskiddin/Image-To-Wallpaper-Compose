package io.github.japskiddin.imagetowallpapercompose

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.japskiddin.imagetowallpapercompose.ui.components.ToolBar
import io.github.japskiddin.imagetowallpapercompose.ui.screens.HomeScreen
import io.github.japskiddin.imagetowallpapercompose.ui.screens.SettingsScreen
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

sealed class Screen(val route: Route, @StringRes val title: Int) {
    data object Home : Screen(Route.HOME, R.string.app_name)
    data object Settings : Screen(Route.SETTINGS, R.string.settings)

    enum class Route {
        HOME,
        SETTINGS
    }
}

@Composable
fun ImageToWallpaperApp() {
    ImageToWallpaperTheme {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentScreen = when (backStackEntry?.destination?.route) {
                        Screen.Route.HOME.name -> Screen.Home
                        Screen.Route.SETTINGS.name -> Screen.Settings
                        else -> Screen.Home
                    }
                    ToolBar(
                        screen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        onSettingsClick = { navController.navigate(Screen.Settings.route.name) },
                        navigateUp = { navController.navigateUp() })
                },
                content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route.name) {
                            HomeScreen()
                        }
                        composable(route = Screen.Settings.route.name) {
                            SettingsScreen()
                        }
                    }
                }
            )
        }
    }
}

@Preview(
    name = "App Light mode",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "App Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppScreenPreview() {
    ImageToWallpaperTheme {
        ImageToWallpaperApp()
    }
}