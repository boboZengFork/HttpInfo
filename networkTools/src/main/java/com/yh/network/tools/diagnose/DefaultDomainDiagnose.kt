package com.yh.network.tools.diagnose

import android.content.Context
import android.text.TextUtils
import androidx.annotation.RequiresPermission
import com.yh.network.tools.ToolsListener
import com.yh.network.tools.bean.DiagnoseBean
import com.yh.network.tools.bean.PingBean
import com.yh.network.tools.response.DiagnoseResponse
import com.yh.network.tools.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 默认实现域名诊断
 * @date: 2021/10/22 10:53 上午
 * @author: zengbobo
 */
open class DefaultDomainDiagnose(context: Context) : BaseDiagnose(context) {
    companion object {
        fun newInstance(context: Context, address: String?): BaseDiagnose {
            return DefaultDomainDiagnose(context).address(address)
        }
    }


    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    override fun load(response: DiagnoseResponse, listener: ToolsListener?) {
        super.load(response, listener)
        if (TextUtils.isEmpty(address)) {
            listener?.end(address, response)
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            with(response) {
                val mDiagnoseBean = DiagnoseBean().apply {
                    this.address = this@DefaultDomainDiagnose.address
                    pingBean = PingBean().apply {
                        //ping
                        val pingResponse =
                            Ping.ping(Ping.createSimplePingCommand(20, 50, address!!))
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
                response.data.add(mDiagnoseBean)
            }
            listener?.end(address, response)
        }
    }
}