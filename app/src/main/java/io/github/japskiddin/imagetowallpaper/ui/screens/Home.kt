package io.github.japskiddin.imagetowallpaper.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.canhub.cropper.CropImageView
import io.github.japskiddin.imagetowallpaper.R
import io.github.japskiddin.imagetowallpaper.ui.theme.ImageToWallpaperTheme

// https://www.geeksforgeeks.org/android-jetpack-compose-implement-dark-mode/
// https://stackoverflow.com/questions/69495413/jetpack-compose-force-switch-night-notnight-resources

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    )
    {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { context ->
                CropImageView(context).apply {
                    guidelines = CropImageView.Guidelines.ON
                }
            })
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            HomeButton(
                icon = R.drawable.ic_wallpaper,
                desc = R.string.set_wallpaper,
                onClick = {/*TODO*/ })
            HomeButton(
                icon = R.drawable.ic_gallery,
                desc = R.string.select_image,
                onClick = {/*TODO*/ })
        }
    }
}

@Composable
fun HomeButton(icon: Int, desc: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = desc),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
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
fun HomeScreenPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        HomeScreen()
    }
}

@Preview
@Composable
fun HomeButtonPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        HomeButton(
            icon = R.drawable.ic_gallery,
            desc = R.string.select_image,
            onClick = {})
    }
}