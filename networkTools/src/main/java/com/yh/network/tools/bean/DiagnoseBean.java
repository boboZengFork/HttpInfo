package com.yh.network.tools.bean;

/**
 * 域名诊断结果实体类
 * @date: 2021/10/22 2:14 下午
 * @author: zengbobo
 */
public class DiagnoseBean {
    /**
     * 需要诊断地址
     */
    private String address;
    /**
     * ping 服务器
     */
    private PingBean pingBean;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PingBean getPingBean() {
        return pingBean;
    }

    public void setPingBean(PingBean pingBean) {
        this.pingBean = pingBean;
    }
}
