package com.intel.ciao

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.os.Handler
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.camera.core.*
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors


@Composable
fun PhotoScreen(navController: androidx.navigation.NavController) {
    val color_background = Color(0XFFFFFAF3)
    val color_button = Color(0XFFFFECD2)
    val color_logo = Color(0XFF73AE19)
    val color_buttontext = Color(0XFF9E6C3A)
    val color_blue = Color(0XFF2D87E2)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(color_background),
        verticalArrangement = Arrangement.Center,

        ) {
        Column (
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraApp()
        }
    }
}


@Composable
fun CameraApp(viewModel: CameraViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "camera") {
        composable("camera") { CameraScreen(navController, viewModel) }
        composable("result") { ResultScreen(navController, viewModel) }
        composable("submit") { SubmitScreen(navController) }
    }
}
@Composable
fun CameraScreen(navController: NavHostController, vm: CameraViewModel,) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context).apply { implementationMode = PreviewView.ImplementationMode.COMPATIBLE } }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
        vm.permissionsGranted = perms[Manifest.permission.CAMERA] == true && perms[Manifest.permission.RECORD_AUDIO] == true
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
    }

    if (!vm.permissionsGranted) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("카메라 권한이 필요합니다.")
        }
        return
    }

    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    var recording by remember { mutableStateOf<Recording?>(null) }
    var isRecording by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        val recorder = Recorder.Builder().setQualitySelector(QualitySelector.from(Quality.HIGHEST)).build()
        val video = VideoCapture.withOutput(recorder)
        val image = ImageCapture.Builder().build()

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview, video, image
        )

        imageCapture = image
        videoCapture = video
    }

    Box(Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val file = File(context.externalMediaDirs.first(), "IMG_${System.currentTimeMillis()}.jpg")
                val options = ImageCapture.OutputFileOptions.Builder(file).build()
                imageCapture?.takePicture(
                    options,
                    Executors.newSingleThreadExecutor(),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exception: ImageCaptureException) {
                            Toast.makeText(context, "사진 저장 실패", Toast.LENGTH_SHORT).show()
                        }

                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            vm.capturedFile = file
                            uploadToServer(file, context,
                                onSuccess = {
                                    vm.serverResponseText = it
                                    // ✅ 기존: navController.navigate("result")
                                    navController.navigate("submit") // ✅ 변경!
                                },
                                onError = {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                )

            }) {
                Text("📷 사진")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                val video = videoCapture ?: return@Button
                if (isRecording) {
                    recording?.stop()
                    recording = null
                    isRecording = false
                } else {
                    val mediaStoreOutput = MediaStoreOutputOptions.Builder(
                        context.contentResolver,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    ).build()

                    recording = video.output.prepareRecording(context, mediaStoreOutput)
                        .apply {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED
                            ) withAudioEnabled()
                        }
                        .start(ContextCompat.getMainExecutor(context)) { event ->
                            when (event) {
                                is VideoRecordEvent.Start -> {
                                    isRecording = true
                                }

                                is VideoRecordEvent.Finalize -> {
                                    isRecording = false
                                    recording = null

                                    val uri = event.outputResults.outputUri
                                    Log.d("Camera", "녹화 완료: $uri")

                                    try {
                                        // uri → 실제 파일로 복사
                                        val inputStream = context.contentResolver.openInputStream(uri)
                                        val tempFile = File.createTempFile("video", ".mp4", context.cacheDir)
                                        inputStream?.use { input ->
                                            tempFile.outputStream().use { output ->
                                                input.copyTo(output)
                                            }
                                        }

                                        vm.capturedFile = tempFile

                                        uploadToServer(tempFile, context,
                                            onSuccess = {
                                                vm.serverResponseText = it
                                                navController.navigate("submit") // ✅ 성공 시 submit 화면으로 이동
                                            },
                                            onError = {
                                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                            }
                                        )

                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        Toast.makeText(context, "동영상 처리 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                }
            }) {
                Text(if (isRecording) "🛑 중지" else "🎥 동영상")
            }
        }
    }
}
fun uploadToServer(file: File, context: Context, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://d621d1a10b02.ngrok-free.app/api/ocr/license-plate")
        .post(
            MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name, file.asRequestBody("application/octet-stream".toMediaTypeOrNull()))
                .build()
        ).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            android.os.Handler(context.mainLooper).post {
                onError("서버 연결 실패: ${e.message}")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            android.os.Handler(context.mainLooper).post {
                if (response.isSuccessful) {
                    onSuccess(response.body?.string().orEmpty())
                } else {
                    onError("서버 오류: ${response.code}")
                }
            }
        }
    })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavHostController, vm: CameraViewModel) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("결과") }, navigationIcon = {
            IconButton(onClick = {
                vm.clear()
                navController.popBackStack()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
            }
        })
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            vm.capturedFile?.let { Text("파일명: ${it.name}") }
            Spacer(Modifier.height(16.dp))
            Text("서버 응답:\n${vm.serverResponseText}")
        }
    }
}
class CameraViewModel : ViewModel() {
    var permissionsGranted by mutableStateOf(false)
    var capturedFile by mutableStateOf<File?>(null)
    var serverResponseText by mutableStateOf("")

    fun clear() {
        capturedFile = null
        serverResponseText = ""
    }
}
