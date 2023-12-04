package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.japskiddin.imagetowallpapercompose.CropRatio
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.SettingsState

@Composable
fun CropRatioPicker(
    modifier: Modifier = Modifier,
    settingsState: SettingsState,
    onChangeCropRatio: (CropRatio) -> Unit
) {
    val cropRations = CropRatio.entries.toTypedArray()

    Text(
        text = stringResource(id = R.string.aspect_ratio),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(start = 2.dp, end = 2.dp)
    )
    Spacer(modifier = modifier.height(8.dp))
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        cropRations.forEach { cropRatio ->
            val isCustomCropRatio = cropRatio == CropRatio.RATIO_CUSTOM
            val title = if (isCustomCropRatio)
                stringResource(id = R.string.aspect_ratio_custom)
            else
                cropRatio.toString()
            OptionItem(
                title = title,
                cropRatio = cropRatio,
                isSelected = cropRatio == settingsState.cropRatio,
                onClick = {
                    onChangeCropRatio(cropRatio)
                })
        }
    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    title: String,
    cropRatio: CropRatio,
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(24.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .aspectRatio(
                        if (cropRatio == CropRatio.RATIO_CUSTOM) {
                            1f
                        } else {
                            cropRatio.width / cropRatio.height.toFloat()
                        }
                    )
                    .border(2.dp, MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
        )
    }
}