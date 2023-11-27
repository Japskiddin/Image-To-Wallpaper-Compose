package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(percent = 20))
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    Color.Transparent
                }
            )
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            modifier = modifier.size(24.dp)
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Preview(name = "Option item unselected")
@Composable
fun OptionItemPreviewUnselected() {
    PreviewWithTheme {
        OptionItem(
            title = stringResource(id = R.string.app_theme_night),
            icon = painterResource(id = R.drawable.ic_theme_night)
        )
    }
}

@Preview(name = "Option item selected")
@Composable
fun OptionItemPreviewSelected() {
    PreviewWithTheme {
        OptionItem(
            title = stringResource(id = R.string.app_theme_night),
            icon = painterResource(id = R.drawable.ic_theme_night),
            isSelected = true
        )
    }
}