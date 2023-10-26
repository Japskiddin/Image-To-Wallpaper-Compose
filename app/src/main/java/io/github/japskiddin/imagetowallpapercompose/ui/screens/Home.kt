package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun HomeScreen(onSettingsClick: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            HomeToolBar(
                onSettingsClick = onSettingsClick,
                modifier = modifier
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            )
            {
                AndroidView(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    factory = { context ->
                        CropImageView(context).apply {
                            guidelines = CropImageView.Guidelines.ON
                        }
                    })
                Menu(modifier = modifier)
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun Menu(modifier: Modifier = Modifier) {
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

// TODO: add shadow

@Composable
fun HomeToolBar(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(id = R.string.settings),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        modifier = modifier
    )
}

@Preview(
    name = "Home Light mode",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "Home Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    ImageToWallpaperTheme {
        HomeScreen(onSettingsClick = {})
    }
}