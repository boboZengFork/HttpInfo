package com.yh.network.tools.diagnose

import android.content.Context
import android.text.TextUtils
import com.yh.network.tools.ToolsListener
import com.yh.network.tools.exception.AddressException
import com.yh.network.tools.utils.Tools

/**
 * @description $
 * @date: 2021/10/22 10:53 上午
 * @author: zengbobo
 */
open class BaseDiagnose<T>(var context: Context) : IDiagnose<T> {

    var address: String? = null
        set(value) {
            field = Tools.getDomain(value)
        }
    var listener: ToolsListener<T>? = null

    fun address(address: String?): BaseDiagnose<T> {
        this.address = Tools.getDomain(address)
        return this
    }

    @Throws(AddressException::class)
    open override fun load(listener: ToolsListener<T>?) {
        this.listener = listener
        listener?.start(address)
        if (TextUtils.isEmpty(address)) {
            listener?.end(address,null)
            return throw AddressException("address不能为空或者null")
        }
    }
}