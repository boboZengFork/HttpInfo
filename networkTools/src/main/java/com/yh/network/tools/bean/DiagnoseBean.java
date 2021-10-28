package com.yh.network.tools.bean;

/**
 * @description $
 * @date: 2021/10/22 2:14 下午
 * @author: zengbobo
 */
public class DiagnoseBean {
    /**
     * 需要诊断地址
     */
    private String diagnoseAddress;

    /**
     * 手机是否连接了网络（wifi或者移动网络）
     */
    private boolean isConnectedNet;

    /**
     * 是否可以通过url访问
     */
    private boolean isConnectedUrl;

    /**
     * 是否可以通过url访问 actuator/health
     */
    private boolean isConnectedUrlActuatorHealth;

    /**
     * 网络类型
     */
    private String networkType;

    /**
     * 手机是有有代理
     */
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

    /**
     * ping数据解析
     */
    private PingBean pingBean;

    /**
     * api获取ip
     */
    private String dnsIp;

    /**
     * ping操作获取ip
     */
    private String pingIp;

    /**
     * 是否可以ping
     */
    private boolean enablePing;

    public String getDiagnoseAddress() {
        return diagnoseAddress;
    }

    public void setDiagnoseAddress(String diagnoseAddress) {
        this.diagnoseAddress = diagnoseAddress;
    }

    public boolean isConnectedNet() {
        return isConnectedNet;
    }

    public void setConnectedNet(boolean connectedNet) {
        isConnectedNet = connectedNet;
    }

    public boolean isConnectedUrl() {
        return isConnectedUrl;
    }

    public void setConnectedUrl(boolean connectedUrl) {
        isConnectedUrl = connectedUrl;
    }

    public boolean isConnectedUrlActuatorHealth() {
        return isConnectedUrlActuatorHealth;
    }

    public void setConnectedUrlActuatorHealth(boolean connectedUrlActuatorHealth) {
        isConnectedUrlActuatorHealth = connectedUrlActuatorHealth;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
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

    public boolean isEnablePing() {
        return enablePing;
    }

    public void setEnablePing(boolean enablePing) {
        this.enablePing = enablePing;
    }
}
