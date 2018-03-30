package com.xuexiang.googlecomponentsdemo.view;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuexiang.googlecomponentsdemo.R;
import com.xuexiang.googlecomponentsdemo.base.BaseActivity;
import com.xuexiang.googlecomponentsdemo.databinding.ActivityLoginBinding;
import com.xuexiang.googlecomponentsdemo.util.GodEyeUtils;
import com.xuexiang.googlecomponentsdemo.viewmodel.LoginModel;

@Route(path = "/ui/login")
public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements ILoginView{

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 绑定ViewModel
     */
    @Override
    protected void bindViews() {
        GodEyeUtils.start(this);
        binding.setLoginModel(new LoginModel().attachV(this));
    }

    /**
     * 界面退出
     */
    @Override
    public void onFinished() {
        finish();
    }
}
