package com.yh.network.tools.bean;

/**
 * ping获取到的数据实体类
 *
 * @author
 * @time
 */
public class PingBean extends BaseBean {

    /**
     * 是否可以ping
     */
    private boolean enablePing;

    /**
     * ping操作获取ip
     */
    private String pingIp;
    /**
     * 发送包
     */
    private int transmitted;

    /**
     * 接受包
     */
    private int receive;
    /**
     * 丢包率：百分比%
     */
    private float lossRate;
    /**
     * 平均rtt（ms）
     */
    private float rttAvg;
    /**
     * 算数平均偏差rtt（ms）
     */
    private float rttMDev;
    /**
     * 最大rtt（ms）
     */
    private float rttMax;
    /**
     * 最小rtt（ms）
     */
    private float rttMin;
    /**
     * 生存时间（ms）
     */
    private int ttl;
    /**
     * 执行结果：-1 失败，200 成功
     */
    private int error;
    /**
     * 总消耗时间（ms）
     */
    private int allTime;

    public PingBean() {
    }

    public boolean isEnablePing() {
        return enablePing;
    }

    public void setEnablePing(boolean enablePing) {
        this.enablePing = enablePing;
    }

    public int getAllTime() {
        return allTime;
    }

    public void setAllTime(int allTime) {
        this.allTime = allTime;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getTransmitted() {
        return transmitted;
    }

    public void setTransmitted(int count) {
        this.transmitted = count;
    }

    public void setLossRate(float lossRate) {
        this.lossRate = lossRate;
    }

    public void setReceive(int receive) {
        this.receive = receive;
    }

    public void setRttAvg(float rttAvg) {
        this.rttAvg = rttAvg;
    }

    public void setRttMDev(float rttMDev) {
        this.rttMDev = rttMDev;
    }

    public void setRttMax(float rttMax) {
        this.rttMax = rttMax;
    }

    public void setRttMin(float rttMin) {
        this.rttMin = rttMin;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public float getLossRate() {
        return this.lossRate;
    }

    public int getReceive() {
        return this.receive;
    }

    public float getRttAvg() {
        return this.rttAvg;
    }

    public float getRttMDev() {
        return this.rttMDev;
    }

    public float getRttMax() {
        return this.rttMax;
    }

    public float getRttMin() {
        return this.rttMin;
    }

    public int getTtl() {
        return this.ttl;
    }

    public String getPingIp() {
        return pingIp;
    }

    public void setPingIp(String pingIp) {
        this.pingIp = pingIp;
    }
}
