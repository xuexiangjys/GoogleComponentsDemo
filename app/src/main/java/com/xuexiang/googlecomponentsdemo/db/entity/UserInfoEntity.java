package com.xuexiang.googlecomponentsdemo.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * @author xuexiang
 * @date 2018/3/13 下午5:01
 */
@Entity(tableName = "_UserInfo")
public class UserInfoEntity {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    /**
     * 登录名
     */
    @ColumnInfo(name = "name")
    private String LoginName;
    /**
     * 登录密码
     */
    @ColumnInfo(name = "password")
    private String LoginPassword;
    /**
     * 别名
     */
    private String Alias;
    /**
     * 年龄
     */
    private int Age;
    /**
     * 性别
     */
    private String Gender;
    /**
     * 出生日期
     */
    private Date BirthDay;

    /**
     * 签名
     */
    private String Signature = "这个家伙很懒，什么也没留下～～";

    public UserInfoEntity() {}

    public UserInfoEntity(String loginName, String loginPassword, String alias, int age, String gender, Date birthDay) {
        LoginName = loginName;
        LoginPassword = loginPassword;
        Alias = alias;
        Age = age;
        Gender = gender;
        BirthDay = birthDay;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getLoginPassword() {
        return LoginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        LoginPassword = loginPassword;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Date getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(Date birthDay) {
        BirthDay = birthDay;
    }

    public String getSignature() {
        return Signature;
    }
    public void setSignature(String signature) {
        Signature = signature;
    }

}
