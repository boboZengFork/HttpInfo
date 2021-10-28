package com.yh.network.tools.diagnose

import android.content.Context
import android.text.TextUtils
import androidx.annotation.RequiresPermission
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
    companion object {
        fun newInstance(context: Context, address: String?): BaseDiagnose<DiagnoseBean> {
            return DefaultDomainDiagnose(context).address(address)
        }
    }



    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
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
            bean.isConnectedNet = Net.isNetworkAvailable(context)
            if (!bean.isConnectedNet) {
                listener?.end(address, bean)
                return@launch
            }

            //networkType
            bean.networkType = Net.networkType(context)

            //dns
            bean.dnsIp = Dns.getInetAddress(address!!)

            //http
            bean.isConnectedUrl = Http.loadDataWithRedirects(context, Tools.getURL(address))
            bean.isConnectedUrlActuatorHealth =
                Http.loadHostActuatorHealth(context, Tools.getDomain(address))

            //ping
            val pingResponse = Ping.ping(Ping.createSimplePingCommand(200, 500, address!!))
//            bean.pingResponse = pingResponse
            if (!TextUtils.isEmpty(pingResponse)) {
                bean.isEnablePing = true
                bean.pingIp = Ping.parseIpFromPing(pingResponse!!)
                Ping.parseLossFromPing(pingResponse, bean.pingBean)
                Ping.parseDelayFromPing(pingResponse, bean.pingBean)
                Ping.parseTtlFromPing(pingResponse, bean.pingBean)
            } else {
                bean.isEnablePing = false
            }
            listener?.end(address, bean)

        }
    }
}