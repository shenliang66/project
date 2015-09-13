package com.czw.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.czw.project.R;

/**
 * Created by xiaowei on 2015/9/7.
 */
public class Setting_About extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_about);
        init();
    }

    private void init() {
        findViewById(R.id.setting_aboutYNJ_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
