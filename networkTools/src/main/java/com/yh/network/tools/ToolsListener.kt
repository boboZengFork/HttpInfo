package com.yh.network.tools

import com.yh.network.tools.constans.DiagnoseType
import com.yh.network.tools.response.DiagnoseResponse

/**
 * @description
 * @time 2021/10/22 10:52 上午
 * @author zengbobo
 */
interface ToolsListener {
    fun start(address: String?)
    fun end(address: String?, response: DiagnoseResponse)
}