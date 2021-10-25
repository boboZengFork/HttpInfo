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
class DefaultDomainDiagnose(context: Context) : BaseDiagnose<DiagnoseBean>(context) {

    override fun load(listener: ToolsListener<DiagnoseBean>?) {
        listener?.start(address)
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
                listener?.end(address,bean)
                return@launch
            }

            //dns
            bean.dnsIp = Dns.getInetAddress(address!!)

            //http
            val isInternet = Http.loadDataWithRedirects(context, Tools.getURL(address))
            bean.isInternet = isInternet

            //ping
            val pingResponse = Ping.ping(Ping.createSimplePingCommand(10, 50, address!!))
            bean.pingResponse = pingResponse
            if (!TextUtils.isEmpty(pingResponse)) {
                bean.pingIp = Ping.parseIpFromPing(pingResponse!!)
                Ping.parseLossFromPing(pingResponse, bean.pingBean)
                Ping.parseDelayFromPing(pingResponse, bean.pingBean)
                Ping.parseTtlFromPing(pingResponse, bean.pingBean)
            }
            listener?.end(address,bean)

        }
    }
}