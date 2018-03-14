package com.xuexiang.googlecomponentsdemo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.googlecomponentsdemo.util.ToastUtil;

/**
 * 基础BindingFragment
 *
 * @author xuexiang
 * @date 2018/3/14 下午2:24
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected T binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayoutId(), container, false);
        initArgs();
        bindViews();
        initListeners();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化参数
     */
    protected void initArgs() {

    }

    /**
     * 绑定ViewModel
     */
    protected abstract void bindViews();

    /**
     * 初始化监听
     */
    protected void initListeners() {

    }

    protected void runOnUiThread(Runnable action) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(action);
        }
    }

    protected void toast(String msg) {
        ToastUtil.showToast(msg);
    }
}
