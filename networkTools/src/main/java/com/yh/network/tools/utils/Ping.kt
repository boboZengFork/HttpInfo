package com.yh.network.tools.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * @description $
 * @date: 2021/10/21 11:02 上午
 * @author: zengbobo
 */
object Ping {
    fun createSimplePingCommand(params: String): String? {
        return String.format("/system/bin/ping %s", params)
    }

    fun createSimplePingCommand(
        arrayParams: List<Any>,
        formatStr: String
    ): String? {
        return String.format("/system/bin/ping $formatStr", arrayParams)
    }

    fun createSimplePingCommand(count: Int, timeout: Int, domain: String): String? {
        val arrayOfObject = arrayOfNulls<Any>(3)
        arrayOfObject[0] = count
        arrayOfObject[1] = timeout
        arrayOfObject[2] = domain
        return String.format("/system/bin/ping -c %d -w %d %s", *arrayOfObject)
    }

    /**
     *
     * *************网络不通 ****************
     * 返回：空字符
     * *************网络正常 域名不通 ****************
    PING glzxsit.yonghui.cn (10.251.69.19) 56(84) bytes of data.

    --- glzxsit.yonghui.cn ping statistics ---
    32 packets transmitted, 0 received, 100% packet loss, time 31018ms

     * *************网络正常(vpn不通也可以) 域名通 ****************
    PING www.a.shifen.com (36.152.44.96) 56(84) bytes of data.
    64 bytes from 36.152.44.96: icmp_seq=1 ttl=53 time=47.8 ms
    64 bytes from 36.152.44.96: icmp_seq=2 ttl=53 time=47.3 ms
    64 bytes from 36.152.44.96: icmp_seq=3 ttl=53 time=45.0 ms
    64 bytes from 36.152.44.96: icmp_seq=4 ttl=53 time=54.3 ms
    64 bytes from 36.152.44.96: icmp_seq=5 ttl=53 time=51.5 ms
    64 bytes from 36.152.44.96: icmp_seq=6 ttl=53 time=40.9 ms
    64 bytes from 36.152.44.96: icmp_seq=7 ttl=53 time=53.5 ms
    64 bytes from 36.152.44.96: icmp_seq=8 ttl=53 time=48.2 ms
    64 bytes from 36.152.44.96: icmp_seq=9 ttl=53 time=45.5 ms
    64 bytes from 36.152.44.96: icmp_seq=10 ttl=53 time=44.9 ms

    --- www.a.shifen.com ping statistics ---
    10 packets transmitted, 10 received, 0% packet loss, time 9013ms
    rtt min/avg/max/mdev = 40.973/47.954/54.369/3.979 ms

     * *************end ****************
     * 执行ping命令
     */
    fun ping(command: String): String? {
        var process: Process? = null
        var inputStream: InputStream? = null
        var reader: BufferedReader? = null
        try {
            process = Runtime.getRuntime().exec(command)
            inputStream = process.inputStream
            reader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuilder()
            var line: String?
            while (null != reader.readLine().also { line = it }) {
                sb.append(line)
                sb.append("\n")
            }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
            inputStream?.close()
            process?.destroy()
        }
        return null
    }

    /**
     * PING www.a.shifen.com (36.152.44.96) 56(84) bytes of data.
     * 解析服务器ip
     */
    fun parseIpFromPing(paramString: String): String? {
        try {
            val bool = paramString.contains("ping")
            var localObject: String? = null
            if (bool) {
                val i = paramString.indexOf("(")
                val j = paramString.indexOf(")")
                localObject = paramString.substring(i + 1, j)
            }
            return localObject
        } catch (localException: Exception) {
            localException.printStackTrace()
        }
        return null
    }

    /**
     *   10 packets transmitted, 10 received, 0% packet loss, time 9013ms
     */
    fun parseLossFromPing(paramString: String): Map<String, Any> {
        var map = hashMapOf<String, Any>()
        try {
            if (paramString.contains("statistics")) {
                val str1 = paramString.substring(
                    1 + paramString.indexOf(
                        "\n",
                        paramString.indexOf("statistics")
                    )
                )
                val arrayOfString = str1.substring(0, str1.indexOf("\n")).split(", ").toTypedArray()
                for (str in arrayOfString) {
                    if (str.contains(" packets transmitted")) {
                        map.put(
                            "transmitted",
                            str.substring(
                                0,
                                str.indexOf(" packets transmitted")
                            ).toInt()
                        )
                    }
                    if (str.contains(" received")) {
                        map.put("received", str.substring(0, str.indexOf(" received")).toInt())
                    }
                    if (str.contains(" errors")) {
                        map.put("errors", str.substring(0, str.indexOf(" errors")).toInt())
                    }
                    if (str.contains(" packet loss")) {
                        map.put("loss", str.substring(0, str.indexOf("%")).toFloat())
                    }
                    if (str.contains("time")) {
                        map.put(
                            "time",
                            str.substring(
                                str.lastIndexOf("e") + 2,
                                str.indexOf("ms")
                            ).toInt()
                        )
                    }
                }
            }
        } catch (localException: java.lang.Exception) {
            localException.printStackTrace()
        }

        return map
    }
}