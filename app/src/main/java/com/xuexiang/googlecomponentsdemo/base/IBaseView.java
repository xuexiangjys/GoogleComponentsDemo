package com.xuexiang.googlecomponentsdemo.base;

/**
 * @author xuexiang
 * @date 2018/3/14 下午1:30
 */
public interface IBaseView {

    /**
     * 开始加载
     *
     * @param loadType 加载的类型 0：第一次记载 1：下拉刷新 2：上拉加载更多
     */
    void loadStart(int loadType);

    /**
     * 加载完成
     */
    void loadComplete();

    /**
     * 加载失败
     *
     * @param message
     */
    void loadFailure(String message);

}
