package com.xuexiang.googlecomponentsdemo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.xuexiang.googlecomponentsdemo.db.entity.UserInfoEntity;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/3/13 下午5:09
 */
@Dao
public interface UserInfoDao {

    @Query("SELECT * FROM _UserInfo")
    LiveData<List<UserInfoEntity>> loadAllUserInfos();

    @Query("SELECT * FROM _UserInfo where name = :loginName and password = :loginPassword")
    LiveData<UserInfoEntity> queryUserInfo(String loginName, String loginPassword);

    /**
     * 同步操作
     *
     * @param loginName
     * @param loginPassword
     * @return
     */
    @Query("SELECT * FROM _UserInfo where name = :loginName and password = :loginPassword")
    List<UserInfoEntity> queryUserInfoSync(String loginName, String loginPassword);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserInfoEntity> userInfos);
}
