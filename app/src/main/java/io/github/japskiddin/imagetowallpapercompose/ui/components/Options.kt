package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.AppTheme
import io.github.japskiddin.imagetowallpapercompose.CropRatio
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme

@Composable
fun Options(
    modifier: Modifier = Modifier,
    onChangeCropRatio: (CropRatio) -> Unit = {},
    onChangeAppTheme: (AppTheme) -> Unit = {}
) {
    val appThemes = AppTheme.entries.toTypedArray()
    val cropRations = CropRatio.entries.toTypedArray()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Text(
            text = stringResource(id = R.string.app_theme),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
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
                    onClick = {
                        onChangeAppTheme(appTheme)
                    })
            }
        }
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.aspect_ratio),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
            cropRations.forEach { cropRatio ->
                val isCustomCropRatio = cropRatio == CropRatio.RATIO_CUSTOM
                val title = if (isCustomCropRatio)
                    stringResource(id = R.string.aspect_ratio_custom)
                else
                    cropRatio.toString()
                OptionItem(
                    title = title,
                    icon = painterResource(id = R.drawable.ic_theme_night),
                    onClick = {
                        onChangeCropRatio(cropRatio)
                    })
            }
        }
    }
}

@Preview(name = "Options", showBackground = true)
@Composable
fun OptionsPreview() {
    PreviewWithTheme {
        Options()
    }
}