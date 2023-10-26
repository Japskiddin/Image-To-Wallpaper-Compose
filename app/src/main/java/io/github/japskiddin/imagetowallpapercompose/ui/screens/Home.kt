package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.canhub.cropper.CropImageView
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.ui.components.MenuButton
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

// https://www.geeksforgeeks.org/android-jetpack-compose-implement-dark-mode/
// https://stackoverflow.com/questions/69495413/jetpack-compose-force-switch-night-notnight-resources
// https://github.com/google-developer-training/basic-android-kotlin-compose-training-cupcake/blob/navigation/app/src/main/java/com/example/cupcake/ui/OrderViewModel.kt

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
            MenuButton(
                icon = R.drawable.ic_wallpaper,
                desc = R.string.set_wallpaper,
                onClick = {/*TODO*/ })
            MenuButton(
                icon = R.drawable.ic_gallery,
                desc = R.string.select_image,
                onClick = {/*TODO*/ })
        }
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