package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.AppTheme
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.SettingsState

@Composable
fun AppThemePicker(
    modifier: Modifier = Modifier,
    settingsState: SettingsState,
    onChangeAppTheme: (AppTheme) -> Unit
) {
    val appThemes = AppTheme.entries.toTypedArray()

    Text(
        text = stringResource(id = R.string.app_theme),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(start = 2.dp, end = 2.dp)
    )
    Spacer(modifier = modifier.height(8.dp))
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        appThemes.forEach { appTheme ->
            val title = when (appTheme) {
                AppTheme.MODE_DAY -> R.string.app_theme_day
                AppTheme.MODE_NIGHT -> R.string.app_theme_night
                else -> R.string.app_theme_system
            }
            val icon = when (appTheme) {
                AppTheme.MODE_DAY -> R.drawable.ic_theme_day
                AppTheme.MODE_NIGHT -> R.drawable.ic_theme_night
                else -> R.drawable.ic_theme_auto
            }
            OptionItem(
                title = stringResource(id = title),
                icon = painterResource(id = icon),
                isSelected = appTheme == settingsState.theme,
                onClick = {
                    onChangeAppTheme(appTheme)
                })
        }
    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 2.dp, end = 2.dp)
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