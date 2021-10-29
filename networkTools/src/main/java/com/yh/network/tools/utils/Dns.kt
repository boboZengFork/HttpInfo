package com.yh.network.tools.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.lang.reflect.Method
import java.net.InetAddress
import java.util.*

/**
 * @description $
 * @date: 2021/10/21 1:51 下午
 * @author: zengbobo
 */
object Dns {

    /**
     * dns解析，需要在有网的时候才能调用成功
     */
    fun getInetAddress(host: String): String? {
        try {
            val inetAddress = InetAddress.getByName(host)
            return inetAddress.hostAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 拿到本地的两个DNS服务器
     *
     * @param context
     * @return
     */
    fun readDnsServers(context: Context?): Array<String>? {
        var dnsServers: Array<String>? =
            readDnsServersFromConnectionManager(context)
        if (dnsServers.isNullOrEmpty()) {
            dnsServers = readDnsServersFromSystemProperties()
            if (dnsServers.isNullOrEmpty()) {
                dnsServers = readDnsServersFromCommand()
            }
        }
        return dnsServers
    }

    private fun readDnsServersFromConnectionManager(context: Context?): Array<String>? {
        val dnsServers = LinkedList<String>()
        if (Build.VERSION.SDK_INT >= 21 && context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                for (network in connectivityManager.allNetworks) {
                    val networkInfo = connectivityManager.getNetworkInfo(network)
                    if (networkInfo!!.type == activeNetworkInfo!!.type) {
                        val lp = connectivityManager.getLinkProperties(network)
                        for (addr in lp!!.dnsServers) {
                            if (!TextUtils.isEmpty(addr.hostAddress)) {
                                dnsServers.add(addr.hostAddress)
                            }
                        }
                    }
                }
            }
        }
        return if (dnsServers.isEmpty()) null else dnsServers.toTypedArray()
    }


    private val DNS_SERVER_PROPERTIES = arrayOf("net.dns1", "net.dns2", "net.dns3", "net.dns4")

    private fun readDnsServersFromSystemProperties(): Array<String> {
        SystemProperties.init()
        val dnsServers = LinkedList<String>()
        for (property in DNS_SERVER_PROPERTIES) {
            var server: String? = SystemProperties.get(property, "")
            if (server != null && server.isNotEmpty()) {
                try {
                    val ip = InetAddress.getByName(server) ?: continue
                    server = ip.hostAddress
                    if (server == null || server.isEmpty()) {
                        continue
                    }
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                    continue
                }
                dnsServers.add(server)
            }
        }
        return dnsServers.toTypedArray()
    }

    private object SystemProperties {
        private var isReflectInited = false
        fun init() {
            if (!isReflectInited) {
                isReflectInited = true
                try {
                    val cls = Class.forName("android.os.SystemProperties")
                    setPropertyMethod = cls.getDeclaredMethod(
                        "set", *arrayOf<Class<*>>(
                            String::class.java,
                            String::class.java
                        )
                    )
                    setPropertyMethod?.isAccessible = true
                    getPropertyMethod = cls.getDeclaredMethod(
                        "get", *arrayOf<Class<*>>(
                            String::class.java,
                            String::class.java
                        )
                    )
                    getPropertyMethod?.isAccessible = true
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
        }

        private var getPropertyMethod: Method? = null
        operator fun get(property: String?, defaultValue: String?): String? {
            var propertyValue = defaultValue
            if (getPropertyMethod != null) {
                try {
                    propertyValue =
                        getPropertyMethod!!.invoke(null, property, defaultValue) as String
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
            return propertyValue
        }

        private var setPropertyMethod: Method? = null
        operator fun set(property: String?, value: String?) {
            if (setPropertyMethod != null) {
                try {
                    setPropertyMethod!!.invoke(null, property, value)
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
        }
    }

    private fun readDnsServersFromWifiManager(context: Context): Array<String?>? {
        val dnsServers = LinkedList<String>()
        try {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (wifiManager == null || !wifiManager.isWifiEnabled) {
                return arrayOfNulls(0)
            }
            val dhcpInfo = wifiManager.dhcpInfo
            if (dhcpInfo != null) {
                if (dhcpInfo.dns1 != 0) {
                    dnsServers.add(intToIp(dhcpInfo.dns1))
                }
                if (dhcpInfo.dns2 != 0) {
                    dnsServers.add(intToIp(dhcpInfo.dns2))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (dnsServers.isEmpty()) arrayOfNulls(0) else dnsServers.toTypedArray()
    }

    private fun intToIp(paramInt: Int): String {
        return ((paramInt and 0xFF).toString() + "." + (0xFF and paramInt shr 8) + "." + (0xFF and paramInt shr 16) + "."
                + (0xFF and paramInt shr 24))
    }


    private fun readDnsServersFromCommand(): Array<String>? {
        val dnsServers = LinkedList<String>()
        try {
            val process = Runtime.getRuntime().exec("getprop")
            val inputStream = process.inputStream
            val lnr = LineNumberReader(InputStreamReader(inputStream))
            var line: String? = null
            while (lnr.readLine().also { line = it } != null) {
                val split = line!!.indexOf("]: [")
                if (split == -1) {
                    continue
                }
                val property = line!!.substring(1, split)
                var value = line!!.substring(split + 4, line!!.length - 1)
                if (property.endsWith(".dns")
                    || property.endsWith(".dns1")
                    || property.endsWith(".dns2")
                    || property.endsWith(".dns3")
                    || property.endsWith(".dns4")
                ) {
                    val ip = InetAddress.getByName(value) ?: continue
                    value = ip.hostAddress
                    if (value == null) {
                        continue
                    }
                    if (value.length == 0) {
                        continue
                    }
                    dnsServers.add(value)
                }
            }
        } catch (e: IOException) {
        }
        return if (dnsServers.isEmpty()) null else dnsServers.toTypedArray<String>()
    }
}