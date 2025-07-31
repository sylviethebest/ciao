//import android.content.Context
//import android.net.Uri
//import android.provider.OpenableColumns
////import okhttp3.*
////import okhttp3.MediaType.Companion.toMediaType
////import okhttp3.RequestBody.Companion.asRequestBody
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//
//fun uploadVideoToIIS(context: Context, uri: Uri) {
//    val contentResolver = context.contentResolver
//
//    // 파일 이름 얻기
//    var fileName = "video.mp4"
//    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
//        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//        if (cursor.moveToFirst()) {
//            fileName = cursor.getString(nameIndex)
//        }
//    }
//
//    // Uri → 임시 File 변환
//    val inputStream = contentResolver.openInputStream(uri)
//    val tempFile = File(context.cacheDir, fileName)
//    FileOutputStream(tempFile).use { outputStream ->
//        inputStream?.copyTo(outputStream)
//    }
//
//    // OkHttp Multipart Request 생성
//    val requestBody = MultipartBody.Builder()
//        .setType(MultipartBody.FORM)
//        .addFormDataPart("file", tempFile.name, tempFile.asRequestBody("video/mp4".toMediaType()))
//        .build()
//
//    val request = Request.Builder()
//        .url("http://192.168.45.108/uploads/") // <-- 실제 IIS 주소
//        .post(requestBody)
//        .build()
//
//    val client = OkHttpClient()
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            e.printStackTrace()
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            println("Upload response: ${response.code}, ${response.body?.string()}")
//        }
//    })
//}
