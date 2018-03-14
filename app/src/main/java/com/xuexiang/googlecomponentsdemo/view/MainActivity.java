package com.xuexiang.googlecomponentsdemo.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuexiang.googlecomponentsdemo.R;
import com.xuexiang.googlecomponentsdemo.base.BaseActivity;
import com.xuexiang.googlecomponentsdemo.databinding.ActivityMainBinding;
import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;
import com.xuexiang.googlecomponentsdemo.model.OnItemClickListener;
import com.xuexiang.googlecomponentsdemo.util.ToastUtil;
import com.xuexiang.googlecomponentsdemo.view.adapter.UserInfoAdapter;
import com.xuexiang.googlecomponentsdemo.viewmodel.UserInfoListViewModel;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/3/14 下午6:51
 */
@Route(path = "/ui/main")
public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnItemClickListener<UserInfoEntity> {

    private UserInfoAdapter mUserInfoAdapter;

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 绑定ViewModel
     */
    @Override
    protected void bindViews() {
        final UserInfoListViewModel viewModel = ViewModelProviders.of(this).get(UserInfoListViewModel.class);
        binding.setUserInfoList(viewModel);

        mUserInfoAdapter = new UserInfoAdapter(this);
        binding.userinfosList.setAdapter(mUserInfoAdapter);

        viewModel.getUserInfos().observe(this, new Observer<List<UserInfoEntity>>() {
            @Override
            public void onChanged(@Nullable List<UserInfoEntity> userInfoEntityList) {
                if (userInfoEntityList != null) {
                    binding.setIsLoading(false);
                    mUserInfoAdapter.setUserInfoList(userInfoEntityList);
                } else {
                    binding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                binding.executePendingBindings();
            }
        });
    }

    @Override
    public void onItemClick(UserInfoEntity userInfoEntity) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ToastUtil.showToast("显示" + userInfoEntity.getAlias() + "的信息");
        }
    }
}
