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
import android.arch.lifecycle.MediatorLiveData;

import com.xuexiang.googlecomponentsdemo.DemoApp;
import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;

import java.util.List;

public class UserInfoListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<UserInfoEntity>> mObservableUserInfos;

    public UserInfoListViewModel(Application application) {
        super(application);

        mObservableUserInfos = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableUserInfos.setValue(null);

        LiveData<List<UserInfoEntity>> userInfos = ((DemoApp) application).getRepository().getUserInfos();

        // observe the changes of the userInfos from the database and forward them
        mObservableUserInfos.addSource(userInfos, mObservableUserInfos::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<UserInfoEntity>> getUserInfos() {
        return mObservableUserInfos;
    }
}
