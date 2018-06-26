package com.rqhua.demo.lib_compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Administrator on 2018/6/26.
 */

public class JavaFileCreateDemo {

    public static String builderContentFromJavaPoet(Name enclosingElementName, String clsName, String fieldName, int value, PackageElement packageName) {
        ClassName resource = ClassName.get("android.content.res", "Resources");
        ClassName mainActivity = ClassName.get(packageName.toString(), enclosingElementName.toString());
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mainActivity, "target")
                .addStatement("$T res = target.getResources()", resource)
                .addStatement("target.$L = res.getString($L)", fieldName, value)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructor)
                .build();

        return JavaFile.builder(packageName.toString(), typeSpec).build().toString();
    }

    public static void create(Filer filer, String packageName, String clsName, String content) {
        try {
            JavaFileObject javaFileObject = filer.createSourceFile(packageName + "." + clsName);
            Writer writer = javaFileObject.openWriter();
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
