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

package com.xuexiang.googlecomponentsdemo;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuexiang.googlecomponentsdemo.db.AppDatabase;
import com.xuexiang.googlecomponentsdemo.db.DataRepository;
import com.xuexiang.googlecomponentsdemo.util.AppExecutors;
import com.xuexiang.googlecomponentsdemo.util.ToastUtil;

/**
 * Android Application class. Used for accessing singletons.
 */
public class DemoApp extends Application {

    private static AppExecutors mAppExecutors;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        ToastUtil.init(this);
        mAppExecutors = new AppExecutors();

        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    public static AppDatabase getDatabase() {
        return AppDatabase.getInstance(sContext, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
