package com.yh.network.tools.utils

import java.net.InetAddress

/**
 * @description $
 * @date: 2021/10/21 1:51 下午
 * @author: zengbobo
 */
object Dns {

    /**
     * dns解析，需要在有网的时候才能调用成功
     */
    fun getInetAddress(host:String):String?{
        try {
            val inetAddress = InetAddress.getByName(host)
            return inetAddress.hostAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}