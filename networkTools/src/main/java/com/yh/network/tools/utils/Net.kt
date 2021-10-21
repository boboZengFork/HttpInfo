package com.yh.network.tools.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @description $
 * @date: 2021/10/20 2:04 下午
 * @author: zengbobo
 */
object Net {

    /**
     * 手机连接网络状态；
     */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        var mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        var mWiFiNetworkInfo = mConnectivityManager?.activeNetworkInfo
        return mWiFiNetworkInfo?.isConnected == true
    }


}