package com.yh.network.tools.utils

import android.content.Context
import android.text.TextUtils
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.SocketAddress
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
    fun loadHostActuatorHealth(context: Context?, host: String): Boolean {
        return loadDataWithRedirects(context, URL("http://$host$ACTUATOR_HEALTH"))
    }

    fun loadDataWithRedirects(context: Context?, url: URL?): Boolean {
        if (context == null || url == null) {
            return false
        }
        var urlConnection: HttpURLConnection? = null
        try {
            //当我们使用的是中国移动的手机网络时，下面方法可以直接获取得到10.0.0.172，80端口
            //通过andorid.net.Proxy可以获取默认的代理地址
            val host: String? = Proxy.proxyHost(context)
            //通过andorid.net.Proxy可以获取默认的代理端口
            val port: Int = Proxy.proxyPort(context)
            urlConnection = if (TextUtils.isEmpty(host) || port == -1) {
                url.openConnection() as HttpURLConnection
            } else {
                val sa: SocketAddress = InetSocketAddress(host, port)
                //定义代理，此处的Proxy是源自java.net
                val proxy = java.net.Proxy(java.net.Proxy.Type.HTTP, sa)
                //设置代理
                url.openConnection(proxy) as HttpURLConnection
            }
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
                if (!TextUtils.isEmpty(responseBody)) {
                    return true
                }
            } else if (isHttpRedirect(statusCode)) {
                val redirectUrlString = urlConnection.getHeaderField("Location")
                if (TextUtils.isEmpty(redirectUrlString)) {
                    return false
                }
                val redirectUrl = URL(url, redirectUrlString)
                try {
                    if (urlConnection != null) {
                        urlConnection.disconnect()
                    }
                } catch (e: java.lang.Exception) {
                    //ignore
                }
                if (redirectUrl != null && url.toURI() == redirectUrl.toURI()) {
                    return false
                }
                loadDataWithRedirects(context, redirectUrl)
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