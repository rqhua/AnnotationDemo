package com.rqhua.demo.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rqhua.demo.lib_annotation_demo.AnnotationDemo;
import com.rqhua.demo.lib_annotations.FieldAnnotation;

public class MainActivity extends AppCompatActivity {
    @FieldAnnotation(R.string.app_name)
    String resid;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnotationDemo.attach(this);
        Log.d(TAG, "onCreate: " + resid);
    }
}
