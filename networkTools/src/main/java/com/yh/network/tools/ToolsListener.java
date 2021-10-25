package com.yh.network.tools;

/**
 * @description 
 * @time 2021/10/22 10:52 上午
 * @author zengbobo
 */
public interface ToolsListener<T> {
    void start(String address);

    void end(String address,T bean);
}
