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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.data.repository.AspectRatio
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

// https://stackoverflow.com/questions/68852110/show-custom-alert-dialog-in-jetpack-compose

@Composable
fun AspectRatioDialog(
    onDialogClose: () -> Unit,
    aspectRatio: AspectRatio,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val aspectRatios = AspectRatio.entries.toTypedArray()

    Dialog(
        onDismissRequest = onDialogClose,
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
                painter = painterResource(id = R.drawable.ic_aspect_ratio),
                contentDescription = stringResource(id = R.string.aspect_ratio),
                modifier = modifier.size(24.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.aspect_ratio),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = modifier.height(16.dp))
            Column(
                modifier = modifier
                    .weight(1f, false)
                    .selectableGroup()
                    .verticalScroll(rememberScrollState())
            ) {
                aspectRatios.forEach { aspectRatio ->
                    val isCustomAspectRatio = aspectRatio == AspectRatio.RATIO_CUSTOM
                    val title = if (isCustomAspectRatio)
                        stringResource(id = R.string.aspect_ratio_custom)
                    else
                        aspectRatio.toString()
                    RadioTextButton(
                        selected = false,
                        title = title,
                        onClick = { /*TODO*/ },
                        modifier = modifier
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            Row(
                modifier = modifier.align(Alignment.End)
            ) {
                TextButton(onClick = onDialogClose) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    )
                }
                TextButton(onClick = onDialogClose) {
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
fun AspectRatioDialogPreview() {
    ImageToWallpaperTheme {
        AspectRatioDialog(
            onDialogClose = {},
            aspectRatio = AspectRatio.RATIO_4_TO_3,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}