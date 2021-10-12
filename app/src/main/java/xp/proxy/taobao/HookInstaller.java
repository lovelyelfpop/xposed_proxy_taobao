package xp.proxy.taobao;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookInstaller implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        hookTB(loadPackageParam.packageName, loadPackageParam.classLoader);
    }

    public void hookTB(final String package_name, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(XposedHelpers.findClassIfExists("mtopsdk.mtop.global.SwitchConfig", classLoader), "isGlobalSpdySwitchOpen", new Object[]{new XC_MethodHook() {

            @Override
            public void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                log(" " + package_name + "开启抓包->");
                param.setResult(false);
            }
        }});
    }

    public void log(Object logStr) {
        if (logStr != null) {
            XposedBridge.log("淘宝系app_ " + logStr.toString());
            return;
        }
        XposedBridge.log("淘宝系app_ " + logStr);
    }
}