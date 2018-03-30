package com.xuexiang.googlecomponentsdemo.viewmodel.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * 感知生命周期的数据
 * @author xuexiang
 * @date 2018/3/30 上午10:28
 */
public abstract class LifecycleData<T> implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    abstract void startUp();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    abstract void cleanup();
}
