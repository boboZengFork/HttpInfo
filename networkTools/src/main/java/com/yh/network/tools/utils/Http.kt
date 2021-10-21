package com.yh.network.tools.utils

import android.text.TextUtils
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @description $
 * @date: 2021/10/21 2:28 下午
 * @author: zengbobo
 */
object Http {
    private const val DEFAULT_TIMEOUT = 5 * 1000
    private const val ACTUATOR_HEALTH = "/actuator/health"

    private fun isHttpOk(statusCode: Int): Boolean {
        return statusCode / 100 == 2
    }

    private fun isHttpRedirect(statusCode: Int): Boolean {
        return statusCode / 100 == 3
    }

    /**
     * 验证是否可以访问服务器（/actuator/health）
     */
    fun loadHostActuatorHealth(host: String): Boolean {
        var urlConnection: HttpURLConnection? = null
        try {
            var url = URL(host + ACTUATOR_HEALTH)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.doInput = true
            urlConnection.useCaches = false
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = DEFAULT_TIMEOUT
            urlConnection.readTimeout = DEFAULT_TIMEOUT
            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            urlConnection.connect()
            val statusCode = urlConnection.responseCode
            if (isHttpOk(statusCode)) {
                var responseBody = getStreamForSuccessfulRequest(urlConnection, true)
                if (!TextUtils.isEmpty(responseBody) && responseBody!!.contains("UP")) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                urlConnection?.disconnect()
            } catch (e: Exception) {
            }
        }
        return false
    }

    private fun getStreamForSuccessfulRequest(
        httpURLConnection: HttpURLConnection,
        isSuccess: Boolean
    ): String? {
        var inputStream: InputStream? = null
        try {
            val stringBuilder = StringBuilder()
            val buf = ByteArray(1024)
            inputStream =
                if (isSuccess) httpURLConnection.inputStream else httpURLConnection.errorStream
            var n: Int
            while (inputStream.read(buf).also { n = it } != -1) {
                stringBuilder.append(String(buf, 0, n))
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: java.lang.Exception) {

            }
        }

        return null
    }
}