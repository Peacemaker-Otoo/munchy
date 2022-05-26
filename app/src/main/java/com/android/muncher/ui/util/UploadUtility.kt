package com.android.muncher.ui.util

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadUtility(private var activity: Activity) {

    var dialog: ProgressDialog? = null
    var serverURL: String = "https://handyopinion.com/tutorials/UploadToServer.php"
    private var serverUploadDirectoryPath: String = "https://handyopinion.com/tutorials/uploads/"
    private val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }

    fun uploadFile(sourceFileUri: Uri, uploadedFileName: String? = null) {
        val pathFromUri = URIPathHelper().getPath(activity, sourceFileUri)
        uploadFile(File(pathFromUri!!), uploadedFileName)
    }

    private fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile)
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = uploadedFileName ?: sourceFile.name
            toggleProgressDialog(this, true)
            try {
                val requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "uploaded_file",
                        fileName,
                        sourceFile.asRequestBody(mimeType.toMediaTypeOrNull())
                    )
                    .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()
                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload", "success, path: $serverUploadDirectoryPath$fileName")
                    showToast(
                        this,
                        "File uploaded successfully at $serverUploadDirectoryPath$fileName"
                    )
                } else {
                    Log.e("File upload", "failed")
                    showToast(this, "File uploading failed")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast(this, "File uploading failed")
            }
            toggleProgressDialog(this, false)
        }.start()
    }

    // url = file path or whatever suitable URL you want.
    private fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    companion object {
        fun showToast(uploadUtility: UploadUtility, message: String) {
            uploadUtility.activity.runOnUiThread {
                Toast.makeText(uploadUtility.activity, message, Toast.LENGTH_LONG).show()
            }
        }

        fun toggleProgressDialog(uploadUtility: UploadUtility, show: Boolean) {
            uploadUtility.activity.runOnUiThread {
                if (show) {
                    uploadUtility.dialog =
                        ProgressDialog.show(uploadUtility.activity, "", "Uploading file...", true)
                } else {
                    uploadUtility.dialog?.dismiss()
                }
            }
        }
    }

}