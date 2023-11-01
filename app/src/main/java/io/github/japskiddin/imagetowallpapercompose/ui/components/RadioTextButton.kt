package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

@Composable
fun RadioTextButton(
    selected: Boolean,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioTextButtonPreview() {
    ImageToWallpaperTheme {
        RadioTextButton(selected = true, title = "Text", onClick = { })
    }
}