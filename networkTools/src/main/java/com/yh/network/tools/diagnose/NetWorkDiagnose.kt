package com.yh.network.tools.diagnose

import android.content.Context
import android.text.TextUtils
import com.yh.network.tools.ToolsListener
import com.yh.network.tools.bean.DiagnoseBean
import com.yh.network.tools.bean.PingBean
import com.yh.network.tools.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @description $
 * @date: 2021/10/22 10:53 上午
 * @author: zengbobo
 */
class NetWorkDiagnose private constructor(context: Context) : BaseDiagnose<DiagnoseBean>(context) {

    companion object {
        const val NETWORK_ADDRESS = "www.baidu.com"
        fun newInstance(context: Context): NetWorkDiagnose {
            return NetWorkDiagnose(context).address(NETWORK_ADDRESS) as NetWorkDiagnose
        }
    }

    override fun load(listener: ToolsListener<DiagnoseBean>?) {
        super.load(listener)
        val bean = DiagnoseBean().apply {
            pingBean = PingBean()
            diagnoseAddress = address
        }
        if (TextUtils.isEmpty(address)) {
            listener?.end(address, bean)
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            //net
            val networkAvailable = Net.isNetworkAvailable(context)
            bean.isConnectionNet = networkAvailable
            if (!networkAvailable) {
                listener?.end(address, bean)
                return@launch
            }
            //proxy
            bean.apply {
                proxyHost = Proxy.proxyHost(context)
                proxyPort = Proxy.proxyPort(context)
                isHasProxy = !TextUtils.isEmpty(proxyHost) && proxyPort != -1
            }

            //http
            val isInternet = Http.loadDataWithRedirects(context, Tools.getURL(address))
            bean.isInternet = isInternet

            //ping
            val pingResponse = Ping.ping(Ping.createSimplePingCommand(10, 50, address!!))
            bean.pingResponse = pingResponse
            if (!TextUtils.isEmpty(pingResponse)) {
                val ip = Ping.parseIpFromPing(pingResponse!!)
                Ping.parseLossFromPing(pingResponse, bean.pingBean)
                Ping.parseDelayFromPing(pingResponse, bean.pingBean)
                Ping.parseTtlFromPing(pingResponse, bean.pingBean)
                bean.apply {
                    pingIp = ip
                }
            }

            listener?.end(address, bean)
        }
    }
}