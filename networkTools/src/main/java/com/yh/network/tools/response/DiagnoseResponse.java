package com.yh.network.tools.response;

import com.yh.network.tools.bean.BasicsBean;
import com.yh.network.tools.bean.DiagnoseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @description $
 * @date: 2021/10/28 4:32 下午
 * @author: zengbobo
 */
public class DiagnoseResponse {
    /**
     * 本机网络基本信息
     */
    private BasicsBean basics;
    /**
     * 127.0.0.1
     */
    private DiagnoseBean localHost;
    /**
     * 本地ip
     */
    private DiagnoseBean localIp;
    /**
     * 本地dns
     */
    private DiagnoseBean localDns;
    /**
     * 指定参照服务器：如www.baidu.com
     */
    private DiagnoseBean special;
    /**
     * 服务器
     */
    private List<DiagnoseBean> data = new ArrayList();

    public BasicsBean getBasics() {
        return basics;
    }

    public void setBasics(BasicsBean basics) {
        this.basics = basics;
    }

    public DiagnoseBean getLocalHost() {
        return localHost;
    }

    public void setLocalHost(DiagnoseBean localHost) {
        this.localHost = localHost;
    }

    public DiagnoseBean getLocalIp() {
        return localIp;
    }

    public void setLocalIp(DiagnoseBean localIp) {
        this.localIp = localIp;
    }

    public DiagnoseBean getLocalDns() {
        return localDns;
    }

    public void setLocalDns(DiagnoseBean localDns) {
        this.localDns = localDns;
    }

    public DiagnoseBean getSpecial() {
        return special;
    }

    public void setSpecial(DiagnoseBean special) {
        this.special = special;
    }

    public List<DiagnoseBean> getData() {
        return data;
    }

    public void setData(List<DiagnoseBean> data) {
        this.data = data;
    }
}
