package com.rqhua.demo.lib_annotation_demo;

import android.app.Activity;

import com.rqhua.demo.lib_compile.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2018/6/26.
 */

public class AnnotationDemo {

    public static void attach(Activity target) {
        // TODO: 2018/6/26 调用生成的类
        // 获取 activity 的 Class 对象
        Class<? extends Activity> targetCls = target.getClass();
        // 获取 activity 的名字
        String clsName = targetCls.getName();
        try {
            // 通过 activity 的 ClassLoader 加载生成的类
            Class<?> generatedCls = targetCls.getClassLoader().loadClass(clsName + Constants.fileEnd);
            // 找到构造方法
            Constructor<?> constructor = generatedCls.getConstructor(targetCls);
            if (constructor != null) {
                // 创建对象
                constructor.newInstance(target);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}