@file:OptIn(ExperimentalMaterial3Api::class)
package com.intel.ciao
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.CameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toBitmap as drawableToBitmap
import androidx.compose.ui.Alignment
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.graphics.Color

import android.os.Environment
import androidx.camera.core.Preview
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.compose.LocalLifecycleOwner
//import uploadVideoToIIS
import java.io.File


val CAMERAX_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO,
)

@Composable
fun VideoScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isRecording = remember { mutableStateOf(false) }

    val preview = remember { Preview.Builder().build() }
    val qualitySelector = QualitySelector.from(Quality.HIGHEST)
    val recorder = remember {
        Recorder.Builder()
            .setQualitySelector(qualitySelector)
            .build()
    }
    val videoCapture = remember {
        VideoCapture.withOutput(recorder)
    }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    var recording: Recording? by remember { mutableStateOf(null) }
    val activity = context as? Activity

    LaunchedEffect(true) {
        val cameraProvider = cameraProviderFuture.get()

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            videoCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    // navigation bar 숨기기 (최초 1회만 실행)
    LaunchedEffect(Unit) {
        activity?.let {
            it.window.setDecorFitsSystemWindows(false)
            WindowInsetsControllerCompat(it.window, it.window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.width(400.dp).height(700.dp),
        )

        Button(
            onClick = {
                if (!isRecording.value) {
                    val videoFile = File(
                        context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
                        "video_${System.currentTimeMillis()}.mp4"
                    )
                    val outputOptions = FileOutputOptions.Builder(videoFile).build()

                    recording = videoCapture.output
                        .prepareRecording(context, outputOptions)
                        .start(ContextCompat.getMainExecutor(context)) { event ->
                            if (event is VideoRecordEvent.Finalize && !event.hasError()) {
                                Log.d("Video", "Saved: ${videoFile.absolutePath}")
                            }
                        }

                    isRecording.value = true
                } else {
                    recording?.stop()
                    recording = null
                    isRecording.value = false
                    navController.navigate("submit")
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRecording.value) Color.Red else Color.Green
            )
        ) {
            Text(text = if (isRecording.value) "Stop" else "Record")
        }
    }
}

