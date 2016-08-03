package com.tan.pulltorefreshandloadmore.sample.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-01
 * Time: 18:07
 */
public class BaseActivity extends AppCompatActivity {

    public Context context;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=this;
    }

    public <T extends View> T getView(int resId) {
        return (T) findViewById(resId);
    }
}
