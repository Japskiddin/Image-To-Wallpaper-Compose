package io.github.japskiddin.imagetowallpapercompose

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.imagetowallpapercompose.ui.components.MenuButton
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme
import io.github.japskiddin.imagetowallpapercompose.utils.hasPermission
import io.github.japskiddin.imagetowallpapercompose.utils.openFile
import io.github.japskiddin.imagetowallpapercompose.utils.requestPermission
import io.moyuru.cropify.AspectRatio
import io.moyuru.cropify.Cropify
import io.moyuru.cropify.CropifyOption
import io.moyuru.cropify.CropifyState
import io.moyuru.cropify.rememberCropifyState

// https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-4-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-navigation#6
// https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#7

@Composable
fun ImageToWallpaperApp(
    modifier: Modifier = Modifier,
    cropViewModel: CropViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    ImageToWallpaperTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            val backgroundColor = MaterialTheme.colorScheme.background
            val aspectRatioState by settingsViewModel.aspectRatioState.collectAsState()
            val cropifyState = rememberCropifyState()
            val cropifyOption = remember {
                mutableStateOf(
                    CropifyOption(
                        backgroundColor = backgroundColor,
                        frameAspectRatio = AspectRatio(
                            aspectRatioState.aspectRatio.width,
                            aspectRatioState.aspectRatio.height
                        ),
                        maskColor = backgroundColor
                    )
                )
            }

            val imageUri by cropViewModel.imageUriState.collectAsState()

            val openDocumentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument(),
                onResult = { uri -> uri?.let { cropViewModel.setImageUri(it) } }
            )
            val getContentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri -> uri?.let { cropViewModel.setImageUri(it) } }
            )
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (isGranted) {
                        openFile(context, openDocumentLauncher, getContentLauncher)
                    } else {
                        Toast.makeText(
                            context,
                            R.string.err_permission_not_granted,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            val onSelectImageClick = {
                if (hasPermission(context)) {
                    openFile(context, openDocumentLauncher, getContentLauncher)
                } else {
                    requestPermission(context, requestPermissionLauncher)
                }
            }

            ImageToWallpaperAppContent(
                imageUri = imageUri,
                cropifyState = cropifyState,
                cropifyOption = cropifyOption.value,
                modifier = modifier,
                onSelectImageClick = onSelectImageClick
            )
        }
    }
}

@Composable
fun ImageToWallpaperAppContent(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    cropifyState: CropifyState = CropifyState(),
    cropifyOption: CropifyOption = CropifyOption(),
    onSelectImageClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ToolBar(
                onSettingsClick = { },
                modifier = modifier
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            )
            {
                imageUri?.let {
                    Cropify(
                        uri = it,
                        state = cropifyState,
                        option = cropifyOption,
                        onImageCropped = {},
                        onFailedToLoadImage = {
                            Toast.makeText(
                                context,
                                R.string.err_load_image,
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                Spacer(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
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
    name = "App Light mode",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "App Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppScreenPreview() {
    val appTheme = if (isSystemInDarkTheme()) AppTheme.MODE_NIGHT else AppTheme.MODE_DAY
    ImageToWallpaperTheme(
        appTheme = appTheme
    ) {
        ImageToWallpaperAppContent()
    }
}