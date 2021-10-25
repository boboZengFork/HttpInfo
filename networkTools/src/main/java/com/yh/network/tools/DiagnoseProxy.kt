package com.yh.network.tools

import android.content.Context
import com.yh.network.tools.diagnose.BaseDiagnose
import com.yh.network.tools.diagnose.IDiagnose
import com.yh.network.tools.diagnose.NetWorkDiagnose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @description $
 * @date: 2021/10/21 4:13 下午
 * @author: zengbobo
 */
class DiagnoseProxy : IDiagnose<Any>, ToolsListener<Any> {

    companion object {
        fun newInstance(context: Context): DiagnoseProxy {
            return DiagnoseProxy().add(NetWorkDiagnose.newInstance(context))
        }
    }

    private var listener: ToolsListener<Any>? = null
    private val list = arrayListOf<BaseDiagnose<Any>>()

    fun add(diagnose: BaseDiagnose<*>): DiagnoseProxy {
        list.add(diagnose as BaseDiagnose<Any>)
        return this
    }

    override fun load(listener: ToolsListener<Any>?) {
        this.listener = listener
        list.forEach {
            it.load(this)
        }
    }

    override fun start(address: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            listener?.start(address)
        }
    }

    override fun end(address: String?, bean: Any?) {
        GlobalScope.launch(Dispatchers.Main) {
            listener?.end(address, bean)
        }
    }
}