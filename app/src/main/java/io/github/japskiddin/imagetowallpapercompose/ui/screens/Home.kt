package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.canhub.cropper.CropImageView
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.ui.components.MenuButton
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme
import io.github.japskiddin.imagetowallpapercompose.utils.getBitmapFromUri
import io.github.japskiddin.imagetowallpapercompose.utils.getScreenHeight
import io.github.japskiddin.imagetowallpapercompose.utils.getScreenWidth
import io.github.japskiddin.imagetowallpapercompose.utils.hasPermission
import io.github.japskiddin.imagetowallpapercompose.utils.openFile
import io.github.japskiddin.imagetowallpapercompose.utils.requestPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// https://www.geeksforgeeks.org/android-jetpack-compose-implement-dark-mode/
// https://stackoverflow.com/questions/69495413/jetpack-compose-force-switch-night-notnight-resources
// https://github.com/google-developer-training/basic-android-kotlin-compose-training-cupcake/blob/navigation/app/src/main/java/com/example/cupcake/ui/OrderViewModel.kt

// https://developer.android.com/jetpack/compose/performance/bestpractices
// https://paulallies.medium.com/clean-architecture-in-the-flavour-of-jetpack-compose-dd4b0016f815
// https://developer.android.com/jetpack/compose/architecture
// https://jonas-rodehorst.dev/posts/how-to-structure-your-jetpack-compose-project
// https://medium.com/@takahirom/jetpack-compose-state-guideline-494d467b6e76
// https://medium.com/ibtech/state-management-in-jetpack-compose-7530e8490d3d
// https://habr.com/ru/articles/350068/
// https://habr.com/ru/articles/514786/
// https://startandroid.ru/ru/courses/compose/30-course/compose/672-urok-9-remember.html
// https://startandroid.ru/ru/courses/compose/30-course/compose/673-urok-10-remember-mutablestateof.html
// https://startandroid.ru/ru/courses/kotlin.html
// https://metanit.com/kotlin/jetpack/5.2.php

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ToolBar(
                onSettingsClick = onSettingsClick,
                modifier = modifier
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            )
            {
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()

                val imageUri = remember { mutableStateOf<Uri?>(null) }
                val image = remember { mutableStateOf<Bitmap?>(null) }

                val openDocumentLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenDocument(),
                    onResult = { imageUri.value = it }
                )
                val getContentLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { imageUri.value = it }
                )
                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (isGranted) {
                            openFile(context, openDocumentLauncher, getContentLauncher)
                        } else {
                            Toast.makeText(
                                context.applicationContext,
                                R.string.err_permission_not_granted,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )

                imageUri.value?.let {
                    coroutineScope.launch(Dispatchers.IO) {
                        image.value =
                            getBitmapFromUri(context, it, getScreenWidth(), getScreenHeight())
                    }
                }

                AndroidView(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    factory = {
                        CropImageView(it).apply {
                            guidelines = CropImageView.Guidelines.ON
                            setOnCropImageCompleteListener { _view, result ->
//                                getCropResult(result)
                            }
                        }
                    },
                    update = { view ->
//                        image.value?.let { bitmap ->
//                            view.setImageBitmap(bitmap)
//                        }
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
                        onClick = {
                            if (hasPermission(context)) {
                                openFile(context, openDocumentLauncher, getContentLauncher)
                            } else {
                                requestPermission(context, requestPermissionLauncher)
                            }
                        })
                }
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun ToolBar(
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
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
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