package com.rqhua.demo.lib_compile;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.rqhua.demo.lib_annotations.FieldAnnotation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by Administrator on 2018/6/26.
 */
public class ProssorTest extends AbstractProcessor {
    private Elements mElementUtils;
    private Types mTypeUtils;
    private Filer mFiler;
    private Messager mMessages;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
        mTypeUtils = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
        mMessages = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();

        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(FieldAnnotation.class);
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
        return SourceVersion.RELEASE_8;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(FieldAnnotation.class)) {
            if (element.getKind() != ElementKind.FIELD) {
                continue;
            }
            Element enclosingElement = element.getEnclosingElement();
            Name enclosingElementName = enclosingElement.getSimpleName();
            String clsName = enclosingElementName.toString() + Constants.fileEnd;

            String fieldName = element.getSimpleName().toString();
            int value = element.getAnnotation(FieldAnnotation.class).value();

            PackageElement packageName = mElementUtils.getPackageOf(element);

            String content = JavaFileCreateDemo.builderContentFromJavaPoet(enclosingElementName, clsName, fieldName, value, packageName);
            mMessages.printMessage(Diagnostic.Kind.NOTE, "+++++++++++++++++++\n" + content + "\n+++++++++++++++++++");
            JavaFileCreateDemo.create(mFiler, packageName.toString(), clsName, content);
            mMessages.printMessage(Diagnostic.Kind.NOTE, "创建完成 \n+++++++++++++++++++");

            /*try {
                JavaFileObject javaFileObject = mFiler.createSourceFile(packageName + "." + clsName);
                Writer writer = javaFileObject.openWriter();
                writer.write(content);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        return false;
    }

    private String getContentFromJava(String clsname, String fieldName, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.rqhua.demo.annotationdemo;\n\n");
        sb.append("import android.app.Activity;\n\n");
        sb.append("public class " + clsname + "{\n\n");
        sb.append("public " + clsname + "(Activity target) {\n");
//        sb.append("    Resources res = target.getResources();\n");
//        sb.append("target.find= res.getString(").append(id).append(");\n");
        sb.append("}\n");
        sb.append("}");
        return sb.toString();
    }
}