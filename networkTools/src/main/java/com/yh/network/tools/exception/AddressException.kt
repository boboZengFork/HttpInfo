package com.yh.network.tools.exception

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * 域名异常
 * @date: 2021/10/22 11:37 上午
 * @author: zengbobo
 */
class AddressException:ToolsException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    @RequiresApi(Build.VERSION_CODES.N)
    constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace)
}