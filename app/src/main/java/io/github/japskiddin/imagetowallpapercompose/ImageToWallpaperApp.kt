package io.github.japskiddin.imagetowallpapercompose

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.japskiddin.imagetowallpapercompose.ui.components.AppThemePicker
import io.github.japskiddin.imagetowallpapercompose.ui.components.CropRatioPicker
import io.github.japskiddin.imagetowallpapercompose.ui.components.Menu
import io.github.japskiddin.imagetowallpapercompose.ui.components.ToolBar
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme
import io.github.japskiddin.imagetowallpapercompose.utils.PreviewWithTheme
import io.github.japskiddin.imagetowallpapercompose.utils.hasStoragePermission
import io.github.japskiddin.imagetowallpapercompose.utils.openFile
import io.github.japskiddin.imagetowallpapercompose.utils.requestStoragePermission
import io.github.japskiddin.imagetowallpapercompose.utils.updateWallpaper
import io.moyuru.cropify.Cropify
import io.moyuru.cropify.CropifyOption
import io.moyuru.cropify.rememberCropifyState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-4-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-navigation#6
// https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#7

@Composable
fun ImageToWallpaperApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel()
) {
    val settingsState by viewModel.settingsState.collectAsState()

    ImageToWallpaperTheme(appTheme = settingsState.theme) {
        val context = LocalContext.current
        val backgroundColor = MaterialTheme.colorScheme.background
        viewModel.setCropifyOptionBackground(backgroundColor)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            val imageUri by viewModel.imageUri.collectAsState()
            val cropifyOption by viewModel.cropifyOption.collectAsState()

            val openDocumentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument(),
                onResult = { uri -> uri?.let { viewModel.setImageUri(it) } }
            )
            val getContentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri -> uri?.let { viewModel.setImageUri(it) } }
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
                if (hasStoragePermission(context)) {
                    openFile(context, openDocumentLauncher, getContentLauncher)
                } else {
                    requestStoragePermission(context, requestPermissionLauncher)
                }
            }

            ImageToWallpaperAppContent(
                modifier = modifier,
                cropifyOption = cropifyOption,
                imageUri = imageUri,
                settingsState = settingsState,
                onSelectImageClick = onSelectImageClick,
                onChangeCropRatio = {
                    viewModel.setCropRatio(it)
                },
                onChangeAppTheme = { viewModel.setAppTheme(it) }
            )
        }
    }
}

@Composable
fun ImageToWallpaperAppContent(
    modifier: Modifier = Modifier,
    cropifyOption: CropifyOption = CropifyOption(),
    settingsState: SettingsState = SettingsState(),
    imageUri: Uri? = null,
    onSelectImageClick: () -> Unit,
    onChangeCropRatio: (CropRatio) -> Unit,
    onChangeAppTheme: (AppTheme) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val cropifyState = rememberCropifyState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var wallpaperType by remember { mutableStateOf(WallpaperType.HOME) }

    if (showBottomSheet) {
        BottomSheet(
            onDismiss = { showBottomSheet = false },
            settingsState = settingsState,
            onChangeCropRatio = onChangeCropRatio,
            onChangeAppTheme = onChangeAppTheme
        )
    }

    Scaffold(
        topBar = {
            ToolBar(
                onOptionsClick = { showBottomSheet = true },
                modifier = modifier
            )
        },
        content = {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
            )
            {
                if (imageUri == null) {
                    Spacer(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    Cropify(
                        uri = imageUri,
                        state = cropifyState,
                        option = cropifyOption,
                        onImageCropped = { croppedBitmap ->
                            coroutineScope.launch(Dispatchers.IO) {
                                val result = updateWallpaper(
                                    context,
                                    croppedBitmap.asAndroidBitmap(),
                                    wallpaperType
                                )

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        if (result) {
                                            R.string.wallpaper_success
                                        } else {
                                            R.string.err_set_wallpaper
                                        },
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
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

                Menu(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp),
                    onSelectImageClick = onSelectImageClick,
                    onSetWallpaper = {
                        wallpaperType = it
                        cropifyState.crop()
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    settingsState: SettingsState,
    onDismiss: () -> Unit,
    onChangeCropRatio: (CropRatio) -> Unit,
    onChangeAppTheme: (AppTheme) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, bottom = 16.dp, start = 14.dp, end = 14.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            AppThemePicker(
                modifier = modifier,
                settingsState = settingsState,
                onChangeAppTheme = onChangeAppTheme
            )
            Spacer(modifier = modifier.height(16.dp))
            CropRatioPicker(
                modifier = modifier,
                settingsState = settingsState,
                onChangeCropRatio = onChangeCropRatio
            )
        }
    }
}

@Preview(
    name = "App Light mode",
    showBackground = true
)
@Preview(
    name = "App Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun AppScreenPreview() {
    PreviewWithTheme {
        ImageToWallpaperAppContent(
            onChangeCropRatio = {},
            onChangeAppTheme = {},
            onSelectImageClick = {}
        )
    }
}