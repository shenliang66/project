package com.czw.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.czw.project.R;

/**
 * Created by xiaowei on 2015/9/7.
 */
public class Setting_FeedBack extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_feedback);
        init();
    }

    private void init() {
        findViewById(R.id.app_setting_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.setting_btn_feedback_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting_FeedBack.this,"发送成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
