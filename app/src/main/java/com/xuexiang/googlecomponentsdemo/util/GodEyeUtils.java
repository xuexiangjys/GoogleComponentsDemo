package com.xuexiang.googlecomponentsdemo.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.xuexiang.googlecomponentsdemo.BuildConfig;

import java.util.Map;

import cn.hikyson.android.godeye.toolbox.crash.CrashFileProvider;
import cn.hikyson.android.godeye.toolbox.rxpermission.RxPermissions;
import cn.hikyson.godeye.core.GodEye;
import cn.hikyson.godeye.core.helper.PermissionRequest;
import cn.hikyson.godeye.core.internal.modules.battery.Battery;
import cn.hikyson.godeye.core.internal.modules.battery.BatteryContextImpl;
import cn.hikyson.godeye.core.internal.modules.cpu.Cpu;
import cn.hikyson.godeye.core.internal.modules.cpu.CpuContextImpl;
import cn.hikyson.godeye.core.internal.modules.crash.Crash;
import cn.hikyson.godeye.core.internal.modules.fps.Fps;
import cn.hikyson.godeye.core.internal.modules.fps.FpsContextImpl;
import cn.hikyson.godeye.core.internal.modules.leakdetector.LeakContextImpl2;
import cn.hikyson.godeye.core.internal.modules.leakdetector.LeakDetector;
import cn.hikyson.godeye.core.internal.modules.memory.Heap;
import cn.hikyson.godeye.core.internal.modules.memory.Pss;
import cn.hikyson.godeye.core.internal.modules.memory.PssContextImpl;
import cn.hikyson.godeye.core.internal.modules.memory.Ram;
import cn.hikyson.godeye.core.internal.modules.memory.RamContextImpl;
import cn.hikyson.godeye.core.internal.modules.pageload.Pageload;
import cn.hikyson.godeye.core.internal.modules.pageload.PageloadContextImpl;
import cn.hikyson.godeye.core.internal.modules.sm.Sm;
import cn.hikyson.godeye.core.internal.modules.sm.SmContextImpl;
import cn.hikyson.godeye.core.internal.modules.thread.ThreadContextImpl;
import cn.hikyson.godeye.core.internal.modules.thread.ThreadDump;
import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadLock;
import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadLockContextImpl;
import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadlockDefaultThreadFilter;
import cn.hikyson.godeye.core.internal.modules.traffic.Traffic;
import cn.hikyson.godeye.core.internal.modules.traffic.TrafficContextImpl;
import cn.hikyson.godeye.monitor.GodEyeMonitor;
import io.reactivex.Observable;

/**
 * 天眼工具类
 *
 * run adb forward tcp:5390 tcp:5390, then open http://localhost:5390/.
 *
 * @author xuexiang
 * @date 2018/3/30 下午5:31
 */
public class GodEyeUtils {

    /**
     * 初始化
     * @param application
     */
    public static void init(Application application) {
        GodEyeUtils.initAppInfo(application);
        GodEyeUtils.install(application);
    }

    /**
     * 开始监测
     * @param context
     */
    public static void start(Context context) {
        GodEyeMonitor.work(context);
    }

    /**
     * 停止监测
     */
    public static void stop() {
        GodEyeMonitor.shutDown();
    }

    /**
     * 初始化信息
     * @param context
     */
    public static void initAppInfo(final Context context) {
        GodEyeMonitor.injectAppInfoConext(new GodEyeMonitor.AppInfoConext() {
            @Override
            public Context getContext() {
                return context;
            }

            @Override
            public Map<String, Object> getAppInfo() {
                Map<String, Object> appInfo = new ArrayMap<>();
                appInfo.put("ApplicationID", BuildConfig.APPLICATION_ID);
                appInfo.put("VersionName", BuildConfig.VERSION_NAME);
                appInfo.put("VersionCode", BuildConfig.VERSION_CODE);
                appInfo.put("BuildType", BuildConfig.BUILD_TYPE);
                return appInfo;
            }
        });
    }

    /**
     * 应用监听装载
     * @param application
     */
    public static void install(Application application) {
        GodEye.instance().install(Cpu.class, new CpuContextImpl())
                .install(Battery.class, new BatteryContextImpl(application))
                .install(Fps.class, new FpsContextImpl(application))
                .install(Heap.class, Long.valueOf(2000))
                .install(Pss.class, new PssContextImpl(application))
                .install(Ram.class, new RamContextImpl(application))
                .install(Sm.class, new SmContextImpl(application, 1000, 300, 800))
                .install(Traffic.class, new TrafficContextImpl())
                .install(Crash.class, new CrashFileProvider(application))
                .install(ThreadDump.class, new ThreadContextImpl())
                .install(DeadLock.class, new DeadLockContextImpl(GodEye.instance().getModule(ThreadDump.class).subject(), new DeadlockDefaultThreadFilter()))
                .install(Pageload.class, new PageloadContextImpl(application))
                .install(LeakDetector.class, new LeakContextImpl2(application, new PermissionRequest() {
                    @Override
                    public Observable<Boolean> dispatchRequest(Activity activity, String... permissions) {
                        return new RxPermissions(activity).request(permissions);
                    }
                }));
    }

}
