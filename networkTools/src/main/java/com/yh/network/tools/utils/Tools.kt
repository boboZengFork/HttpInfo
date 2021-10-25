package com.yh.network.tools.utils

import android.text.TextUtils
import java.net.MalformedURLException
import java.net.URL

/**
 * @description $
 * @date: 2021/10/21 3:38 下午
 * @author: zengbobo
 */
object Tools {

    /**
     * 转换成域名
     */
    fun getDomain(address: String?): String? {
        try {
            val url = getURL(address)
            return url?.host
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    fun getURL(address: String?):URL?{
        if (TextUtils.isEmpty(address)) {
            return null
        }
        var str = address
        if (!str!!.startsWith("http")) {
            str = "http://$address"
        } else if (str!!.startsWith("https")) {
            str = "http://" + address!!.substring(8)
        }
        try {
            return URL(str)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    fun getURLString(address: String?):String?{
        if (TextUtils.isEmpty(address)) {
            return null
        }
        var str = address
        if (!str!!.startsWith("http")) {
            str = "http://$address"
        } else if (str!!.startsWith("https")) {
            str = "http://" + address!!.substring(8)
        }
        return str
    }

    fun checkUrl(address: String?): Boolean {
        try {
            val url = URL(address)
            return true
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return false
    }
}