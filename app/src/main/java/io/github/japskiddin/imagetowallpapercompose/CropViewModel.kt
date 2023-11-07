package io.github.japskiddin.imagetowallpapercompose

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CropViewModel : ViewModel() {
    private val _imageUriState = MutableStateFlow<Uri?>(null)
    val imageUriState: StateFlow<Uri?> = _imageUriState

    fun setImageUri(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _imageUriState.update { uri }
        }
    }
}