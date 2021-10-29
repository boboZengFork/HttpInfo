package com.yh.network.tools

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import com.yh.network.tools.diagnose.BaseDiagnose
import com.yh.network.tools.diagnose.BasicsDiagnose
import com.yh.network.tools.diagnose.DefaultDomainDiagnose
import com.yh.network.tools.diagnose.IDiagnose
import com.yh.network.tools.response.DiagnoseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @description $
 * @date: 2021/10/21 4:13 下午
 * @author: zengbobo
 */
class DiagnoseProxy(var context: Context) : IDiagnose, ToolsListener {

    companion object {
        fun newInstance(context: Context): DiagnoseProxy {
            return DiagnoseProxy(context).basics(BasicsDiagnose.newInstance(context))
        }
    }

    private var listener: ToolsListener? = null
    private val list = arrayListOf<BaseDiagnose>()
    private var basics: BaseDiagnose? = null

    @Volatile
    var count: Int = 0

    fun add(diagnose: BaseDiagnose): DiagnoseProxy {
        list.add(diagnose)
        return this
    }

    fun add(address: String?): DiagnoseProxy {
        list.add(DefaultDomainDiagnose(context).address(address))
        return this
    }

    fun basics(diagnose: BaseDiagnose?): DiagnoseProxy {
        this.basics = diagnose
        return this
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    override fun load(response: DiagnoseResponse, listener: ToolsListener?) {
        this.listener = listener
        count = list.size
        if (basics == null) {
            list.forEach {
                it.load(response, this@DiagnoseProxy)
            }
        } else {
            count++
            basics?.load(response, object : ToolsListener {
                override fun start(address: String?) {
                    this@DiagnoseProxy.start(address)
                }

                @SuppressLint("MissingPermission")
                override fun end(address: String?, response: DiagnoseResponse) {
                    this@DiagnoseProxy.end(address, response)
                    if (response.basics?.isConnectedNet == true) {
                        list.forEach {
                            it.load(response, this@DiagnoseProxy)
                        }
                    }
                }
            })
        }
    }

    @Synchronized
    override fun start(address: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            listener?.start(address)
        }
    }

    @Synchronized
    override fun end(address: String?, response: DiagnoseResponse) {
        count--
        GlobalScope.launch(Dispatchers.Main) {
            listener?.end(address, response)
        }
    }
}