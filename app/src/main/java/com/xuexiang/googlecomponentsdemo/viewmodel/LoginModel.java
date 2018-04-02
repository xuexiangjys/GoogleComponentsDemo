package com.xuexiang.googlecomponentsdemo.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuexiang.googlecomponentsdemo.BR;
import com.xuexiang.googlecomponentsdemo.DemoApp;
import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;
import com.xuexiang.googlecomponentsdemo.model.ILoginModel;
import com.xuexiang.googlecomponentsdemo.util.ToastUtil;
import com.xuexiang.googlecomponentsdemo.view.ILoginView;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/3/14 上午11:36
 */
public class LoginModel extends BaseObservable implements ILoginModel<ILoginView> {

    /**
     * 登录名
     */
    private String LoginName = "";
    /**
     * 登录密码
     */
    private String LoginPassword = "";

    private ILoginView mILoginView;

    @Override
    public LoginModel attachV(ILoginView view) {
        mILoginView = view;
        return this;
    }

    @Override
    public void detachV() {
        mILoginView = null;
    }

    @Bindable
    public String getLoginName() {
        return LoginName;
    }

    public LoginModel setLoginName(String loginName) {
        LoginName = loginName;
        notifyPropertyChanged(BR.loginName);
        return this;
    }

    @Bindable
    public String getLoginPassword() {
        return LoginPassword;
    }

    public LoginModel setLoginPassword(String loginPassword) {
        LoginPassword = loginPassword;
        notifyPropertyChanged(BR.loginPassword);
        return this;
    }

    /**
     * 登录
     *
     * @param loginModel
     */
    @Override
    public void login(final LoginModel loginModel) {
        DemoApp.getAppExecutors().diskIO().execute(new Runnable() {  //数据库的操作必须放在子线程中
            @Override
            public void run() {
                List<UserInfoEntity> lists =  DemoApp.getDatabase().userInfoDao().queryUserInfoSync(loginModel.getLoginName(), loginModel.getLoginPassword());
                if (lists.size() > 0) {
                    ARouter.getInstance().build("/ui/main").navigation();
                    if (mILoginView != null) {
                        mILoginView.onFinished();
                    }
                } else {
                    ToastUtil.showToast("用户名或者密码错误!");
                }
            }
        });
    }

    /**
     * 注册
     *
     * @param loginModel
     */
    @Override
    public void register(LoginModel loginModel) {
        ToastUtil.showToast("点击注册按钮，用户名：" + loginModel.getLoginName() + "，密码：" + loginModel.getLoginPassword());
    }

}
