/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.googlecomponentsdemo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.xuexiang.googlecomponentsdemo.DemoApp;
import com.xuexiang.googlecomponentsdemo.db.DataRepository;
import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;


public class UserInfoViewModel extends AndroidViewModel {

    private final LiveData<UserInfoEntity> mObservableUserInfo;

    public ObservableField<UserInfoEntity> userInfo = new ObservableField<>();


    public UserInfoViewModel(@NonNull Application application, DataRepository repository,
                             final String loginName, final String loginPassword) {
        super(application);

        mObservableUserInfo = repository.queryUserInfo(loginName, loginPassword);
    }

    public LiveData<UserInfoEntity> getObservableUserInfo() {
        return mObservableUserInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo.set(userInfo);
    }

    /**
     * A creator is used to inject the userInfo ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the userInfo ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String LoginName;

        private final String LoginPassword;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, final String loginName, final String loginPassword) {
            mApplication = application;
            LoginName = loginName;
            LoginPassword = loginPassword;
            mRepository = ((DemoApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserInfoViewModel(mApplication, mRepository, LoginName, LoginPassword);
        }
    }
}
