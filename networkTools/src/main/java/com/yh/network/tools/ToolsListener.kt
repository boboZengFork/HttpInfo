package com.yh.network.tools

import com.yh.network.tools.response.DiagnoseResponse

/**
 *  域名诊断监听
 * @time 2021/10/22 10:52 上午
 * @author zengbobo
 */
interface ToolsListener {
    /**
     * 开始诊断
     *
     * @param address
     */
    fun start(address: String?)

    /**
     * 结束诊断
     *
     * @param address
     * @param response
     */
    fun end(address: String?, response: DiagnoseResponse)
}