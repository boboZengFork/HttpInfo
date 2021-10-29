package com.yh.network.tools.diagnose

import com.yh.network.tools.ToolsListener
import com.yh.network.tools.response.DiagnoseResponse

/**
 * 诊断类入口规范
 * @date: 2021/10/22 10:50 上午
 * @author: zengbobo
 */
interface IDiagnose {
    fun load(response: DiagnoseResponse, listener: ToolsListener?)
}