/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.googlecomponentsdemo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xuexiang.googlecomponentsdemo.util.ToastUtil;

/**
 * 基础BindingActivity
 * @author xuexiang
 * @date 2018/3/14 下午2:24
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initArgs();
        bindViews();
        initListeners();
    }

    @Override
    protected void onDestroy() {
        binding.unbind();
        super.onDestroy();
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


    protected void toast(String msg) {
        ToastUtil.showToast(msg);
    }
}