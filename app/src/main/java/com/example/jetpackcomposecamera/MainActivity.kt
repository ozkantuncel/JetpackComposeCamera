package com.example.jetpackcomposecamera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var directoryFile: File
    private lateinit var cameraExecutor: ExecutorService

    private var showCamera: MutableState<Boolean> = mutableStateOf(false)
    private var showPhoto: MutableState<Boolean> = mutableStateOf(false)

    private lateinit var photoUri: Uri

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        isGranted ->
        if (isGranted) {showCamera.value = true}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if(showCamera.value){
                CameraView(
                    fileDirectory = directoryFile,
                    executor = cameraExecutor,
                    onImageCaptured = ::handleImageCapture,
                    onError = {}
                )
            }
            if(showPhoto.value){
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        requestCameraPermission()
        directoryFile = getDirectoryFile()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                showCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {}

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCapture(uri: Uri) {
        showCamera.value = false
        photoUri = uri
        showPhoto.value = true
    }

    private fun getDirectoryFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.takenPhoto)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}





