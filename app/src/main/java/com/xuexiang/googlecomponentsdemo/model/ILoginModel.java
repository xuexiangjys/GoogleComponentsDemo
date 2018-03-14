package com.xuexiang.googlecomponentsdemo.model;

import com.xuexiang.googlecomponentsdemo.viewmodel.LoginModel;

/**
 * @author xuexiang
 * @date 2018/1/5 下午2:48
 */
public interface ILoginModel<T> extends IViewModel<T> {

    /**
     * 登录
     * @param loginModel
     */
    void login(LoginModel loginModel);

    /**
     * 注册
     * @param loginModel
     */
    void register(LoginModel loginModel);

}
