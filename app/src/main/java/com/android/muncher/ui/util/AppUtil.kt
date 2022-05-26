@file:Suppress("DEPRECATION")

package com.android.muncher.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Base64
import android.util.Patterns
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**Phone number validator
 * @param target : phone number
 * */
fun isValidPhoneNumber(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        if (target.length < 10 || target.length > 10) {
            false
        } else {
            Patterns.PHONE.matcher(target).matches()
        }
    }
}

/**Phone number validator
 * @param password : phone number
 * */
fun isValidPassword(password: CharSequence?): Boolean {
    return if (password == null) {
        false
    } else {
        if (password.length < 10 || password.length > 10) {
            false
        } else {
            Patterns.PHONE.matcher(password).matches()
        }
    }
}


/**User email validator
 * @param email : Email
 * */
fun emailValidator(email: String?): Boolean {
    val pattern: Pattern
    val EMAIL_PATTERN =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    pattern = Pattern.compile(EMAIL_PATTERN)
    val matcher: Matcher? = email?.let { pattern.matcher(it) }
    return matcher?.matches() ?: false
}

val timeStamp: String
    get() = Date().time.toString()

val currentDateTime: String
    get() = Calendar.getInstance().time.toString()


@SuppressLint("GetInstance")
fun encrypt(input: String?): String {
    var crypted: ByteArray? = null
    try {
        val skey = SecretKeySpec(
            Constants.EncryptKey.toByteArray(),
            "AES"
        )
        val cipher =
            Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, skey)
        if (input != null) {
            crypted = cipher.doFinal(input.toByteArray())
        } else {
            return ""
        }
    } catch (e: Exception) {
        println(e.toString())
    }


    return Base64.encodeToString(crypted, Base64.DEFAULT)
        .replace("\n", "")
}

@SuppressLint("GetInstance")
fun decrypt(input: String?): String {
    var output: ByteArray? = null
    try {
        val skey = SecretKeySpec(
            Constants.EncryptKey.toByteArray(),
            "AES"
        )
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, skey)
        output = cipher.doFinal(Base64.decode(input, Base64.DEFAULT))
    } catch (e: Exception) {
        println(e.toString())
    }
    return output?.let { String(it) } ?: ""
}


fun getCurrentDate(): String {
    val c = Calendar.getInstance()
    val day: Int = c.get(Calendar.DAY_OF_MONTH)
    val month: Int = c.get(Calendar.MONTH) + 1
    val year: Int = c.get(Calendar.YEAR)
    return "$day-$month-$year"
}


/*private suspend fun sendPicture(sessionId: UUID, p: Picture): Boolean {

        try {
            val data_part = p.data.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val data_multi_part =
                MultipartBody.Part.createFormData("picture", p.description, data_part)
            val sessionId_part =
                sessionId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val id_part = p.id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val categoryId_part =
                p.categoryId?.toString()?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val response = api.sendPicture(sessionId_part, id_part, categoryId_part, data_multi_part)
            if (!response.isSuccessful) {

                @Suppress("BlockingMethodInNonBlockingContext")
                val msg = response.errorBody()?.string() ?: textHelper(
                    R.string.error_send_picture,
                    p.id.toString()
                )
                Logger.trace(EventCode.SerializationError, msg, EventSeverity.Error)
            }
            return response.isSuccessful

        } catch (e: Exception) {

            Logger.trace(p, EventCode.SerializationError, e.localizedMessage, NotificationCompat.Action.Send, EventSeverity.Exception)
        }

        return false
    }*/


/**Check if phone network connection is available
 * @param context: application context
 * */

fun isNetworkAvailable(context: Context): Boolean {
    val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = conMan.activeNetworkInfo
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) { // connected to the internet
        Toast.makeText(context, activeNetworkInfo.typeName, Toast.LENGTH_SHORT).show()
        if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) { // connected to wifi
            return true
        } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) { // connected to the mobile provider's authData plan
            return true
        }
    }
    return (conMan.activeNetworkInfo != null && conMan.activeNetworkInfo!!.isConnected)
}

// extension function to get bitmap from assets
fun Context.assetsToBitmap(fileName:String): Bitmap?{
    return try {
        val stream = assets.open(fileName)
        BitmapFactory.decodeStream(stream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}


// extension function to convert bitmap to byte array(str)
fun Bitmap.toByteArray():ByteArray{
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG,10,this)
        return toByteArray()
    }
}

// extension function to convert byte array to bitmap
fun ByteArray.toBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this,0,size)
}




