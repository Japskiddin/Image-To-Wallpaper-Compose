package io.github.japskiddin.imagetowallpapercompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.japskiddin.imagetowallpapercompose.R

@Composable
fun Menu(modifier: Modifier = Modifier, onSelectImageClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        MenuButton(
            icon = R.drawable.ic_wallpaper,
            desc = R.string.set_wallpaper,
            onClick = {/*TODO*/ })
        MenuButton(
            icon = R.drawable.ic_gallery,
            desc = R.string.select_image,
            onClick = onSelectImageClick
        )
    }
}