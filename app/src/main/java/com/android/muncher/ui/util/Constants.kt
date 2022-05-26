package com.android.muncher.ui.util

interface Constants {

     interface Url {
        companion object {
            const val login = "$baseUrl/auth/login"
            const val register = "$baseUrl/auth/registration"
            const val reset = baseUrl + "reset"
            const val forgot = baseUrl + "forgot"
            const val uploadImg = "$baseUrl/user/upload-img"
            const val refreshToken = "$baseUrl/auth/refresh-token/"
        }
    }

    companion object {
        const val baseUrl = "http://addes.xyz/testing/api/"
        const val EncryptKey = "6753598754127645"
        const val tokenKey = "rEDeMptIon"
        val limitedDeviceInfo = StringBuffer()
        const val KeyToken = "jH4kB2aUDXF2OVp" //"t3Qo7xfdH1";

        const val ERROR_CODE = "0"
        const val SUCCESS_CODE = "1"
        const val SUCCESS_MSG_CODE = "2"
        const val AUTH_FAILURE_CODE = "-1"
    }

    interface FragmentTags {
        companion object {
            const val TAG = "Login Fragment"

        }
    }
}