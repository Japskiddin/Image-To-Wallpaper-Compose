package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.japskiddin.imagetowallpapercompose.AppTheme
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.SettingsViewModel
import io.github.japskiddin.imagetowallpapercompose.ui.components.AppThemeDialog
import io.github.japskiddin.imagetowallpapercompose.ui.components.AspectRatioDialog
import io.github.japskiddin.imagetowallpapercompose.ui.components.SettingsItem
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

// TODO: add version

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ToolBar(
                onNavigateUp = onNavigateUp,
                modifier = modifier
            )
        },
        content = { innerPadding ->
            val themeState by viewModel.themeState.collectAsState()
            val aspectRatioState by viewModel.aspectRatioState.collectAsState()
            val openAspectRationDialog = remember { mutableStateOf(false) }
            val openAppThemeDialog = remember { mutableStateOf(false) }

            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                SettingsItem(
                    title = stringResource(id = R.string.aspect_ratio),
                    description = aspectRatioState.aspectRatio.toString(),
                    onClick = {
                        openAspectRationDialog.value = true
                    }
                )
                Divider(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .height(1.dp)
                )
                SettingsItem(
                    title = stringResource(id = R.string.app_theme),
                    description = stringResource(
                        id = when (themeState.theme) {
                            AppTheme.MODE_DAY -> R.string.app_theme_day
                            AppTheme.MODE_NIGHT -> R.string.app_theme_night
                            AppTheme.MODE_SYSTEM -> R.string.app_theme_system
                        }
                    ),
                    onClick = {
                        openAppThemeDialog.value = true
                    }
                )
            }

            if (openAspectRationDialog.value) {
                AspectRatioDialog(
                    aspectRatio = aspectRatioState.aspectRatio,
                    onDialogDismiss = { openAspectRationDialog.value = false },
                    onDialogConfirm = {
                        openAspectRationDialog.value = false
                        viewModel.setAspectRatio(it)
                    },
                    modifier = modifier
                )
            }
            if (openAppThemeDialog.value) {
                AppThemeDialog(
                    appTheme = themeState.theme,
                    onDialogDismiss = { openAppThemeDialog.value = false },
                    onDialogConfirm = {
                        openAppThemeDialog.value = false
                        viewModel.setAppTheme(it)
                    })
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun ToolBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.settings)) },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )
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
fun SettingsScreenPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        SettingsScreen(onNavigateUp = {})
    }
}