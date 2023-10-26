package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.data.repository.AppTheme
import io.github.japskiddin.imagetowallpapercompose.data.repository.AspectRatio
import io.github.japskiddin.imagetowallpapercompose.data.repository.DEFAULT_PREFERENCE
import io.github.japskiddin.imagetowallpapercompose.data.repository.SettingsRepository
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            repository = SettingsRepository(
                LocalContext.current
            )
        )
    )
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        val appPreferences by viewModel.appPreferences.collectAsState(DEFAULT_PREFERENCE)
        SettingsItem(
            title = stringResource(id = R.string.aspect_ratio),
            description = appPreferences.aspectRatio.toString(),
            onClick = {

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
                id = when (appPreferences.appTheme) {
                    AppTheme.MODE_DAY -> R.string.app_theme_day
                    AppTheme.MODE_NIGHT -> R.string.app_theme_night
                    AppTheme.MODE_AUTO -> R.string.app_theme_auto
                }
            ),
            onClick = {

            }
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

// https://stackoverflow.com/questions/68852110/show-custom-alert-dialog-in-jetpack-compose

@Composable
fun AspectRationDialog(
    aspectRatio: AspectRatio,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            modifier = modifier
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_aspect_ratio),
                        contentDescription = stringResource(id = R.string.aspect_ratio)
                    )
                    Text(text = stringResource(id = R.string.aspect_ratio))
                    Spacer(modifier = Modifier.height(24.dp))
                    Row {
                        TextButton(onClick = { openDialog.value = false }) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AspectRatioDialogPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        AspectRationDialog(
            aspectRatio = AspectRatio.RATIO_4_TO_3,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsItemPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        SettingsItem(title = "Title", description = "Description", onClick = {})
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