package com.yh.network.tools.diagnose

import android.content.Context
import android.text.TextUtils
import androidx.annotation.RequiresPermission
import com.yh.network.tools.ToolsListener
import com.yh.network.tools.exception.AddressException
import com.yh.network.tools.response.DiagnoseResponse
import com.yh.network.tools.utils.Tools

/**
 * 诊断基类
 * @date: 2021/10/22 10:53 上午
 * @author: zengbobo
 */
open class BaseDiagnose(var context: Context) : IDiagnose {

    var address: String? = null
        set(value) {
            field = Tools.getDomain(value)
        }
    var listener: ToolsListener? = null

    fun address(address: String?): BaseDiagnose {
        this.address = Tools.getDomain(address)
        return this
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    @Throws(AddressException::class)
    open override fun load(response: DiagnoseResponse, listener: ToolsListener?) {
        this.listener = listener
        listener?.start(address)
        if (TextUtils.isEmpty(address)) {
            listener?.end(address,response)
            return throw AddressException("address不能为空或者null")
        }
    }
}