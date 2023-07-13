package com.example.jetpackcomposecamera.presentation.camera_screen

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel.CameraViewModel
import com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel.CameraViewModelFactory
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    navController: NavController,
) {
    val context = LocalContext.current

    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted

        }
    )

    val viewModel: CameraViewModel =
        viewModel(factory = CameraViewModelFactory(context.applicationContext as Application))

    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()


    val lifecycleOwner = LocalLifecycleOwner.current

    val lensFacing = CameraSelector.LENS_FACING_BACK


    val preview = Preview.Builder().build()

    val previewView = remember { PreviewView(context) }

    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(hasCamPermission) {
        launcher.launch(Manifest.permission.CAMERA)

        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }



    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        if (hasCamPermission) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = {
                    takePhoto(
                        imageCapture = imageCapture,
                        outputDirectory = getDirectoryFile(context = context),
                        executor = cameraExecutor,
                        viewModel = viewModel,
                        onPhotoSaved = {
                            CoroutineScope(Dispatchers.Main).launch {
                                navController.popBackStack()
                                navController.navigate(Screen.MainScreen.route)
                            }
                        }
                    )
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.lens_v),
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            )
        } else {
            Toast.makeText(context, "İzin verilmedi", Toast.LENGTH_SHORT).show()

            navController.popBackStack()
            navController.navigate(Screen.MainScreen.route)
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}

private fun takePhoto(
    filenameFormat: String = "yyyy-MM-dd-HH-mm-ss-SSS",
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    viewModel: CameraViewModel,
    onPhotoSaved: () -> Unit
) {

    val photoTime = SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())

    val photoFile = File(
        outputDirectory,
        "$photoTime.jpg"
    )


    viewModel.insertData(
        ImageModel(
            0,
            "Özkan",
            photoFile.nameWithoutExtension,
            photoTime,
            photoFile.path.toString()
        )
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            exception.printStackTrace()
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Log.d("DEBUG", photoFile.path)
            onPhotoSaved()
        }
    })

}

private fun getDirectoryFile(context: Context): File {
    val mediaDir = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES).firstOrNull()?.let {
        File(it, context.resources.getString(R.string.takenPhoto)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine {
        ProcessCameraProvider.getInstance(this).also { future ->
            future.addListener({
                it.resume(future.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

