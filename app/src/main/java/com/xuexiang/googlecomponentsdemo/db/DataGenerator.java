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

package com.xuexiang.googlecomponentsdemo.db;


import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;
import com.xuexiang.googlecomponentsdemo.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    public static List<UserInfoEntity> generateUserInfos() {
        List<UserInfoEntity> userInfos = new ArrayList<>();
        userInfos.add(new UserInfoEntity("xuexiang", "12345", "翔", 24, "男", DateUtils.string2Date("1994-08-06")));
        userInfos.add(new UserInfoEntity("xuexiangjys", "123456", "翔", 24, "男", DateUtils.string2Date("1994-08-06")));
        userInfos.add(new UserInfoEntity("baocui", "4321", "鲍鱼", 25, "女", DateUtils.string2Date("1993-10-06")));
        userInfos.add(new UserInfoEntity("xuexin", "123456", "欣", 30, "女", DateUtils.string2Date("1988-08-09")));
        return userInfos;
    }

}
