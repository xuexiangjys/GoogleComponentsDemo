package com.xuexiang.googlecomponentsdemo.model;

/**
 * @author xuexiang
 * @date 2018/3/14 下午1:35
 */
public interface IViewModel<V> {

    <T extends IViewModel> T attachV(V view);

    void detachV();

}
