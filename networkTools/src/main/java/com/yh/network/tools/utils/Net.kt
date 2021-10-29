package com.yh.network.tools.utils

import android.content.Context
import android.net.*
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

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

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    fun networkType(context: Context): String? {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager != null) {
            val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
                return "WIFI"
            }
        }
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return "Other"
        val networkType = telephonyManager.networkType
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
            TelephonyManager.NETWORK_TYPE_LTE -> "4G"
            else -> "Other"
        }
    }

    /**
     * 本地IP
     *
     * @return
     */
    fun getClientIp(): String? {
        val localIp = ""
        try {
            val localEnumeration: Enumeration<*>? = NetworkInterface.getNetworkInterfaces()
            if (localEnumeration != null) {
                while (localEnumeration.hasMoreElements()) {
                    val localEnumerationNew: Enumeration<*> =
                        (localEnumeration.nextElement() as NetworkInterface).inetAddresses
                    while (localEnumerationNew.hasMoreElements()) {
                        val inetAddress = localEnumerationNew.nextElement() as InetAddress
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address && !inetAddress.isLinkLocalAddress()) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return localIp
    }

}