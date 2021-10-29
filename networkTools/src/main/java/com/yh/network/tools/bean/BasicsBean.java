package com.yh.network.tools.bean;

/**
 *
 * 本机网络基本信息
 *
 * @date: 2021/10/28 3:51 下午
 * @author: zengbobo
 */
public class BasicsBean extends BaseBean{
    /**
     * 手机是否连接了网络（wifi或者移动网络）
     */
    private boolean isConnectedNet;
    /**
     * 网络类型
     */
    private String networkType;
    /**
     * 手机是有有代理
     */
    private boolean hasProxy;
    /**
     * 本地代理ip
     */
    private String proxyHost;
    /**
     * 本地代理端口
     */
    private int proxyPort = -1;
    /**
     * 本地IP
     */
    private String localIp;

    /**
     * 本地dns
     */
    private String localDns;


    public boolean isConnectedNet() {
        return isConnectedNet;
    }

    public void setConnectedNet(boolean connectedNet) {
        isConnectedNet = connectedNet;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public boolean isHasProxy() {
        return hasProxy;
    }

    public void setHasProxy(boolean hasProxy) {
        this.hasProxy = hasProxy;
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

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getLocalDns() {
        return localDns;
    }

    public void setLocalDns(String localDns) {
        this.localDns = localDns;
    }
}
