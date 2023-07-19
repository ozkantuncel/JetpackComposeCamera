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
import android.content.pm.PackageManager
import android.os.Environment
import com.example.jetpackcomposecamera.presentation.common.dialog.JCCAlertDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.presentation.camera_screen.viewmodel.CameraViewModel
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
fun CameraViewScreen(
    navController: NavController,
) {
    val context = LocalContext.current

    // lazy delegate ile bir CameraViewModel nesnesi oluşturuyor.
    // ViewModelProvider ile context'i bir ViewModelStoreOwner olarak kullanarak CameraViewModel nesnesini elde ediyoruz.
    val viewModel: CameraViewModel by lazy {
        ViewModelProvider(context as ViewModelStoreOwner).get(CameraViewModel::class.java)
    }

    val errorDialogState = remember { mutableStateOf(false) }
    val errorTitle = remember { mutableStateOf("") }
    val errorMsg = remember { mutableStateOf("") }

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

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }


    if (hasCamPermission) {
        CameraViewPage(
            navController = navController,
            context = context,
            errorDialogState = errorDialogState,
            errorTitle = errorTitle,
            errorMsg = errorMsg,
            addImage = {
                viewModel.insertData(it)
            }
        )
    } else {
        /* JCCAlertDialog(
             openTheDialog = errorDialogState,
             content = {},
             title = "İzin",
             message = "Kamera izni verilmedi"
         )*/
    }
}

@Composable
fun CameraViewPage(
    navController: NavController,
    context: Context,
    errorDialogState: MutableState<Boolean>,
    errorTitle: MutableState<String>,
    errorMsg: MutableState<String>,
    addImage: (ImageModel) -> Unit
) {
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

    LaunchedEffect(lensFacing) {
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

    if (errorDialogState.value) {
        JCCAlertDialog(
            openTheDialog = errorDialogState,
            content = {},
            title = errorTitle.value,
            message = errorMsg.value
        )
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {

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
                    addImage = {
                        addImage(it)
                    },
                    onError = { imageCaptureError ->
                        errorDialogState.value = true
                        errorTitle.value = "Camera Sorunu"
                        errorMsg.value = imageCaptureError.message.toString()
                    },
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
    addImage: (ImageModel) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onPhotoSaved: () -> Unit
) {
    val photoTime = SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())

    val photoFile = File(
        outputDirectory,
        "$photoTime.jpg"
    )

    addImage(
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
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
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

