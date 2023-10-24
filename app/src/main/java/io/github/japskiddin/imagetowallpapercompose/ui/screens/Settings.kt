package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        val aspectRatio by viewModel.aspectRatio.collectAsState()
        SettingsItem(
            title = stringResource(id = R.string.aspect_ratio),
            description = aspectRatio.toString()
        )
    }
}

@Composable
fun SettingsItem(title: String, description: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = title)
        Text(text = description)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsItemPreview() {
    ImageToWallpaperTheme {
        SettingsItem(title = "Title", description = "Description")
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
fun SettingsScreenPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        SettingsScreen()
    }
}