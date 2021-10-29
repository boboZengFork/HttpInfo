package com.yh.network.tools.constans

/**
 * 诊断数据类型
 *
 * @property des
 * @constructor Create empty Diagnose type
 */
enum class DiagnoseType(val des: String) {
    BASIC("本机网络基本信息"),
    SERVER("服务器"),
    LOCALHOST("127.0.0.1"),
    LOCALIP("本地ip"),
    LOCALDNS("本地dns"),
    SPECIAL("指定参照服务器：如www.baidu.com")
}