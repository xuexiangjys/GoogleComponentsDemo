# GoogleComponentsDemo
Google Architecture Components 演示程序

在MVVM框架基础上简单使用了Room、LiveData、ViewModel框架组件。

本项目使用freeline加速编译，[点击查看使用指南。](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/Freeline_README.md)

## 关于我
[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)

## 演示效果（请star支持）
![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/demo.gif)

## 何为 Google Architecture Components

   Google Architecture Components是Google在I/O大会上发布的一套应用框架库，它的使用基础是DataBinding和MVVM框架。详细介绍可参见：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/1107/8715.html

### 架构库的架构图

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/google_architecture_components.png)

### 架构库的基本组成

- Lifecycle：Android声明周期的回调，帮助我们将原先需要在onStart()等生命周期回调的代码可以分离到Activity或者Fragment之外。

- LiveData：一个数据持有类，持有数据并且这个数据可以被观察被监听，和其他Observer不同的是，它和Lifecycle是绑定的。

- ViewModel：用于实现架构中的ViewModel，同时是与Lifecycle绑定的，使用者无需担心生命周期。方便在多个Fragment之前分享数据，比如旋转屏幕后Activity会重新create，这时候使用ViewModel可以方便使用之前的数据，不需要再次请求网络数

- Room：谷歌推出的一个Sqlite ORM库，使用注解，极大简化数据库的操作。

### 框架的补充

框架补充：

- 如果不满足官方的库其实可以自己实现。比如LiveData在某些情况下可使用RxJava代替。
- 数据层官方推荐使用Room或者Realm或者其他Sqlite ORM等都可以。
- 网络请求推荐使用Retrofit。
- 各层之间的耦合推荐使用服务发现(Service Locator)或者依赖注入(DI)，推荐Dagger。

## 如何使用Google Architecture Components

### 1.在Android Studio上使用，需要在module级别的build.gradle上添加对DataBinding的支持：

```
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

### 2.配置Room schemaLocation

```
android {
    ....
    defaultConfig {
        ....
        //指定room.schemaLocation生成的文件路径
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation": "$projectDir/schemas".toString()
                ]
            }
        }
    }
}
```

### 3.配置依赖dependencies

```
dependencies {
    ....
    // Architecture Components
    implementation "android.arch.persistence.room:runtime:1.0.0-alpha1"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0-alpha1"
    implementation "android.arch.lifecycle:runtime:1.0.0-alpha1"
    implementation "android.arch.lifecycle:extensions:1.0.0-alpha1"
    annotationProcessor "android.arch.lifecycle:compiler:1.0.0-alpha1"
}

```

### 4.配置使用Room

#### 1.使用@Entity注解配置数据库的实体类

下面是用户信息数据库的实体配置：

```
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

    ....
}

```

创建后生成的数据库表如下：

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/db.png)


#### 2.构建数据库操作Dao

数据库操作Dao是一个加了@Dao注解修饰的接口，里面定义了数据库操作的方法，所有的数据库操作都是通过Sql语句实现的，查询出来的结果能够自动转化为实体对象或者LiveData对象。代码如下：

```
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

```

需要注意的是，Room最好的特性之一是如果你在主线程中执行数据库操作，app将崩溃，显示下面的信息：

```
java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
```

#### 3.构建数据库

继承RoomDatabase，并通过@Database注解进行数据库的信息配置，代码如下：

```
@Database(entities = {UserInfoEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

  //注册数据库操作Dao
  public abstract UserInfoDao userInfoDao();
}
```

细心的你可能会发现，上面的数据库实体的字段里居然出现了"Date"对象！是的，Room数据库组件支持复杂类型的存储。只需要给数据库提供一个类型转化类，通过注解@TypeConverters配置即可，@TypeConverter是注解的转化方法。下面是Date转化为时间戳的方法：

```
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

```

Room数据库的其他操作详解，可参见：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0726/8249.html


### 5.将获取到的LiveData数据源填充到ViewModel中，并进行数据变化的绑定。

LiveData 是一款基于观察者模式的可感知生命周期的核心组件。LiveData 为界面代码 （Observer）的监视对象 （Observable），当 LiveData 所持有的数据改变时，它会通知相应的界面代码进行更新。代码如下：

```
public UserInfoListViewModel(Application application) {
    super(application);

    mObservableUserInfos = new MediatorLiveData<>();
    // set by default null, until we get data from the database.
    mObservableUserInfos.setValue(null);

    LiveData<List<UserInfoEntity>> userInfos = ((DemoApp) application).getRepository().getUserInfos();

    // observe the changes of the userInfos from the database and forward them
    mObservableUserInfos.addSource(userInfos, mObservableUserInfos::setValue);
}

```

### 6.如同MVVM框架中，将ViewModel绑定到View中

XML中注册ViewModel
```
<data>
    <variable
        name="UserInfoList"
        type="com.xuexiang.googlecomponentsdemo.viewmodel.UserInfoListViewModel" />
</data>
```

Activity/Fragment中设置绑定ViewModel
```
final UserInfoListViewModel viewModel = ViewModelProviders.of(this).get(UserInfoListViewModel.class);
binding.setUserInfoList(viewModel);

```

### 7.将LiveData数据源与View的生命周期进行绑定

这样LiveData数据源便能感知View的生命周期，并进行相应的处理。同时数据发生变化时，也能对View进行通知处理。

```
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

```

以上便简单地使用了Google Architecture Components，总体来说功能比较实用，而且如果结合RxJava、Retrofit、Dagger2和ARouter后，将会更加强大。

需要了解更多有关Google Architecture Components内容的，[请点击查看](https://github.com/googlesamples/android-architecture-components)

## 更多框架演示

- [MVP](https://github.com/xuexiangjys/MyMVP)
- [MVVM](https://github.com/xuexiangjys/MyMVVM)
- [Google Architecture Components](https://github.com/xuexiangjys/GoogleComponentsDemo)