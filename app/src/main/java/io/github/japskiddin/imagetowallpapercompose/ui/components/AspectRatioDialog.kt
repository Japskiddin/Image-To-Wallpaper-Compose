package io.github.japskiddin.imagetowallpapercompose.ui.components

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
import androidx.compose.material3.Surface
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
    Dialog(
        onDismissRequest = onDialogClose,
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
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
                        .selectableGroup()
                        .verticalScroll(rememberScrollState())
                ) {
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_4_TO_3.toString(),
                        onClick = { /*TODO*/ }
                    )
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_3_TO_4.toString(),
                        onClick = { /*TODO*/ }
                    )
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_16_TO_9.toString(),
                        onClick = { /*TODO*/ }
                    )
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_9_TO_16.toString(),
                        onClick = { /*TODO*/ }
                    )
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_18_TO_9.toString(),
                        onClick = { /*TODO*/ }
                    )
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_9_TO_18.toString(),
                        onClick = { /*TODO*/ }
                    )
                    RadioTextButton(
                        selected = false,
                        title = AspectRatio.RATIO_CUSTOM.toString(),
                        onClick = { /*TODO*/ }
                    )
                }
                Spacer(modifier = modifier.height(24.dp))
                Row(modifier = modifier.align(Alignment.End)) {
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