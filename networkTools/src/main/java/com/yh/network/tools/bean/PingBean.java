package com.yh.network.tools.bean;

/**
 * Created by 谷闹年 on 2018/8/15.
 */

public class PingBean {
    private float lossRate;
    private int receive;
    private float rttAvg;
    private float rttMDev;
    private float rttMax;
    private float rttMin;
    private int ttl;
    private int error;
    private int transmitted;
    private int allTime;

    public PingBean() {
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

}
