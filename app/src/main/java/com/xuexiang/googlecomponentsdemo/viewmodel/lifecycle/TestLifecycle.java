package com.xuexiang.googlecomponentsdemo.viewmodel.lifecycle;

import com.xuexiang.googlecomponentsdemo.util.ToastUtil;

/**
 * @author xuexiang
 * @date 2018/3/30 上午10:47
 */
public class TestLifecycle<T> extends LifecycleData<T> {
    private T mData;

    public TestLifecycle(T data) {
        mData = data;
    }

    @Override
    void startUp() {
        ToastUtil.showToast("startUp:" + mData.getClass().getSimpleName());
    }

    @Override
    void cleanup() {
        ToastUtil.showToast("cleanup" + mData.getClass().getSimpleName());
    }
}
