package com.yh.network.tools.bean;

/**
 * @description $
 * @date: 2021/10/22 2:14 下午
 * @author: zengbobo
 */
public class DiagnoseBean {
    /**
     * 手机是否连接了网络（wifi或者移动网络）
     */
    private boolean isConnectionNet;
    /**
     * 手机是否连接互联网
     */
    private boolean isInternet;
    /**
     * 需要诊断地址
     */
    private String diagnoseAddress;
    private boolean HasProxy;
    /**
     * 本地代理ip
     */
    private String proxyHost;
    /**
     * 本地代理端口
     */
    private int proxyPort = -1;
    /**
     * ping返回的数据
     */
    private String pingResponse;
    private PingBean pingBean;
    private String dnsIp;
    private String pingIp;

    public boolean isConnectionNet() {
        return isConnectionNet;
    }

    public void setConnectionNet(boolean connectionNet) {
        isConnectionNet = connectionNet;
    }

    public boolean isInternet() {
        return isInternet;
    }

    public void setInternet(boolean internet) {
        isInternet = internet;
    }

    public String getDiagnoseAddress() {
        return diagnoseAddress;
    }

    public void setDiagnoseAddress(String diagnoseAddress) {
        this.diagnoseAddress = diagnoseAddress;
    }

    public boolean isHasProxy() {
        return HasProxy;
    }

    public void setHasProxy(boolean hasProxy) {
        HasProxy = hasProxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getPingResponse() {
        return pingResponse;
    }

    public void setPingResponse(String pingResponse) {
        this.pingResponse = pingResponse;
    }

    public PingBean getPingBean() {
        return pingBean;
    }

    public void setPingBean(PingBean pingBean) {
        this.pingBean = pingBean;
    }

    public String getDnsIp() {
        return dnsIp;
    }

    public void setDnsIp(String dnsIp) {
        this.dnsIp = dnsIp;
    }

    public String getPingIp() {
        return pingIp;
    }

    public void setPingIp(String pingIp) {
        this.pingIp = pingIp;
    }
}
