package com.yh.network.tools.exception

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @description $
 * @date: 2021/10/22 11:37 上午
 * @author: zengbobo
 */
open class ToolsException : Exception{

    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)

    @RequiresApi(api = Build.VERSION_CODES.N)
    constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace)
}