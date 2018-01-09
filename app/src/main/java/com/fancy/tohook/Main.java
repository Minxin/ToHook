package com.fancy.tohook;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Created by fancy on 2018/1/8.
 */



public class Main implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName!=null){
            String packstring=lpparam.packageName.toString();
            if (packstring.contains("com.fancy.tobeenhook") || packstring.indexOf("com.fancy.tobeenhook")!=-1) {
                XposedBridge.log(String.format("HOOK app: %s", lpparam.packageName));



                XposedHelpers.findAndHookMethod("com.fancy.tobeenhook.MainActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {


                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        Class clazz = param.thisObject.getClass();
                        XposedBridge.log("XXX class name:"+clazz.getName());

                        Field field = clazz.getDeclaredField("btn");
                        Field field2=clazz.getDeclaredField("edit");//控件变量名

                        field.setAccessible(true);
                        field2.setAccessible(true);

                        Button btn = (Button) field.get(param.thisObject);
                        EditText edt=(EditText) field2.get(param.thisObject);
                        edt.setText("BeenHook");
                        btn.setEnabled(true);


                    }
                });
            }
        }


    }
}