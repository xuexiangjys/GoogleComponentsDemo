package com.xuexiang.googlecomponentsdemo.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;

import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<UserInfoEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.userInfoDao().loadAllUserInfos(),
                userInfoEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(userInfoEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<UserInfoEntity>> getUserInfos() {
        return mObservableProducts;
    }

    public LiveData<UserInfoEntity> queryUserInfo(final String loginName, final String loginPassword) {
        return mDatabase.userInfoDao().queryUserInfo(loginName, loginPassword);
    }
}
