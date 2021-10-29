package com.yh.network.tools.diagnose

import android.content.Context
import android.text.TextUtils
import androidx.annotation.RequiresPermission
import com.yh.network.tools.ToolsListener
import com.yh.network.tools.bean.BasicsBean
import com.yh.network.tools.bean.DiagnoseBean
import com.yh.network.tools.bean.PingBean
import com.yh.network.tools.response.DiagnoseResponse
import com.yh.network.tools.utils.Dns
import com.yh.network.tools.utils.Net
import com.yh.network.tools.utils.Ping
import com.yh.network.tools.utils.Proxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @description $
 * @date: 2021/10/22 10:53 上午
 * @author: zengbobo
 */
class BasicsDiagnose private constructor(context: Context) : BaseDiagnose(context) {

    companion object {
        fun newInstance(context: Context): BasicsDiagnose {
            return BasicsDiagnose(context).address("BasicsDiagnose") as BasicsDiagnose
        }
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    override fun load(response: DiagnoseResponse, listener: ToolsListener?) {
        super.load(response, listener)
        GlobalScope.launch(Dispatchers.IO) {
            with(response) {
                basics = BasicsBean().apply {
                    //net
                    isConnectedNet = Net.isNetworkAvailable(context)
                }

                if (!basics.isConnectedNet) {
                    listener?.end(address, response)
                    return@launch
                }
                basics.apply {
                    //networkType
                    networkType = Net.networkType(context)

                    //proxy
                    proxyHost = Proxy.proxyHost(context)
                    proxyPort = Proxy.proxyPort(context)
                    isHasProxy = !TextUtils.isEmpty(proxyHost) && proxyPort != -1

                    //dns
                    localIp = Net.getClientIp()
                    localDns = Dns.readDnsServers(context)?.get(0)
                }
                //127.0.0.1
                localHost = DiagnoseBean().apply {
                    address = "127.0.0.1"
                    val pingResponse = Ping.ping(Ping.createSimplePingCommand(200, 500, address))
                    pingBean = PingBean().apply {
                        if (!TextUtils.isEmpty(pingResponse)) {
                            this.isEnablePing = true
                            this.pingIp = Ping.parseIpFromPing(pingResponse!!)
                            Ping.parseLossFromPing(pingResponse, this)
                            Ping.parseDelayFromPing(pingResponse, this)
                            Ping.parseTtlFromPing(pingResponse, this)
                        } else {
                            this.isEnablePing = false
                        }
                    }
                }

                //本地ip
                localIp = DiagnoseBean().apply {
                    address = basics.localIp
                    val pingResponse = Ping.ping(Ping.createSimplePingCommand(200, 500, address))
                    pingBean = PingBean().apply {
                        if (!TextUtils.isEmpty(pingResponse)) {
                            this.isEnablePing = true
                            this.pingIp = Ping.parseIpFromPing(pingResponse!!)
                            Ping.parseLossFromPing(pingResponse, this)
                            Ping.parseDelayFromPing(pingResponse, this)
                            Ping.parseTtlFromPing(pingResponse, this)
                        } else {
                            this.isEnablePing = false
                        }
                    }
                }

                //本地dns
                localDns = DiagnoseBean().apply {
                    address = basics.localDns
                    val pingResponse = Ping.ping(Ping.createSimplePingCommand(200, 500, address))
                    pingBean = PingBean().apply {
                        if (!TextUtils.isEmpty(pingResponse)) {
                            this.isEnablePing = true
                            this.pingIp = Ping.parseIpFromPing(pingResponse!!)
                            Ping.parseLossFromPing(pingResponse, this)
                            Ping.parseDelayFromPing(pingResponse, this)
                            Ping.parseTtlFromPing(pingResponse, this)
                        } else {
                            this.isEnablePing = false
                        }
                    }
                }
            }
            listener?.end(address, response)
        }
    }
}