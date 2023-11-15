package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.japskiddin.imagetowallpapercompose.AppTheme
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme

// https://stackoverflow.com/questions/68852110/show-custom-alert-dialog-in-jetpack-compose

@Composable
fun AppThemeDialog(
    appTheme: AppTheme,
    onDialogDismiss: () -> Unit,
    onDialogConfirm: (appTheme: AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    val appThemes = AppTheme.entries.toTypedArray()
    val selectedAppTheme = remember { mutableStateOf(appTheme) }

    Dialog(
        onDismissRequest = { onDialogDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_theme_night),
                contentDescription = stringResource(id = R.string.app_theme),
                modifier = modifier.size(24.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_theme),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = modifier.height(16.dp))
            Column(
                modifier = modifier
                    .weight(1f, false)
                    .selectableGroup()
                    .verticalScroll(rememberScrollState())
            ) {
                appThemes.forEach { appTheme ->
                    val title = when (appTheme) {
                        AppTheme.MODE_DAY -> R.string.app_theme_day
                        AppTheme.MODE_NIGHT -> R.string.app_theme_night
                        else -> R.string.app_theme_system
                    }
                    RadioTextButton(
                        checked = selectedAppTheme.value == appTheme,
                        title = stringResource(id = title),
                        onClick = { selectedAppTheme.value = appTheme },
                        modifier = modifier
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            Row(
                modifier = modifier.align(Alignment.End)
            ) {
                TextButton(onClick = { onDialogDismiss() }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    )
                }
                TextButton(onClick = { onDialogConfirm(selectedAppTheme.value) }) {
                    Text(
                        text = stringResource(id = R.string.select),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppThemeDialogPreview() {
    PreviewWithTheme {
        AppThemeDialog(
            appTheme = AppTheme.MODE_DAY,
            onDialogDismiss = {},
            onDialogConfirm = {}
        )
    }
}