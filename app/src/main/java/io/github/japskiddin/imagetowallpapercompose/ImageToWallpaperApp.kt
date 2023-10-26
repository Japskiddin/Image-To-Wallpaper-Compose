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

const val ROUTE_HOME = "home"
const val ROUTE_SETTINGS = "settings"

sealed class Screen(val route: String, @StringRes val title: Int) {
    data object Home : Screen(ROUTE_HOME, R.string.app_name)
    data object Settings : Screen(ROUTE_SETTINGS, R.string.settings)
}

@Composable
fun ImageToWallpaperApp(
    modifier: Modifier = Modifier,
) {
    ImageToWallpaperTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            Scaffold(
                topBar = {
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentScreen = when (backStackEntry?.destination?.route) {
                        ROUTE_HOME -> Screen.Home
                        ROUTE_SETTINGS -> Screen.Settings
                        else -> Screen.Home
                    }
                    ToolBar(
                        screen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        onSettingsClick = { navController.navigate(Screen.Settings.route) },
                        navigateUp = { navController.navigateUp() })
                },
                content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeScreen()
                        }
                        composable(route = Screen.Settings.route) {
                            SettingsScreen()
                        }
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppScreenPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        ImageToWallpaperApp()
    }
}