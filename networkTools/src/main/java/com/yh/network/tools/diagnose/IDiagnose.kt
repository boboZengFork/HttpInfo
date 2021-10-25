package com.yh.network.tools.diagnose

import com.yh.network.tools.ToolsListener

/**
 * @description $
 * @date: 2021/10/22 10:50 上午
 * @author: zengbobo
 */
interface IDiagnose<T> {
    fun load(listener: ToolsListener<T>?)
}