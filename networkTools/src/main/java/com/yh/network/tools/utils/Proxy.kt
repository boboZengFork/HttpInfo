package com.yh.network.tools.utils

import android.content.Context
import android.net.Proxy
import android.os.Build
import android.text.TextUtils

/**
 * @description $
 * @date: 2021/10/21 2:29 下午
 * @author: zengbobo
 */
object Proxy {

    /**
     * 判断设备 是否使用代理上网
     */
    fun hasProxy(context: Context?): Boolean {
        var proxyAddress: String?
        var proxyPort: Int?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            proxyAddress = System.getProperty("http.proxyHost")
            var portStr = System.getProperty("http.proxyPort")
            proxyPort = (portStr ?: "-1").toInt()
        } else {
            proxyAddress = Proxy.getHost(context)
            proxyPort = Proxy.getPort(context)
        }
        return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
    }

    /**
     * 代理地址
     */
    fun proxyHost(context: Context?): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            System.getProperty("http.proxyHost")
        } else {
            Proxy.getHost(context)
        }
    }

    /**
     * 代理端口
     */
    fun proxyPort(context: Context?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val portStr = System.getProperty("http.proxyPort")
            (portStr ?: "-1").toInt()
        } else {
            Proxy.getPort(context)
        }
    }
}