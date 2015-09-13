package com.czw.project.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.czw.project.R;
import com.czw.project.util.SwitchView;

/**
 * Created by xiaowei on 2015/9/7.
 */
public class Setting_NotificateMessage extends Activity {
    private SharedPreferences notication;
    private SharedPreferences.Editor editor;
    public final static String SETTING_NOTIFICATION_EVERY="setting_everyday_nofication";
    public final static String SETTING_NOTIFICATION_ABOUT="setting_about_notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_notificate_message);
        init();
    }

    private void init() {
        notication=getPreferences(Context.MODE_PRIVATE);
        editor=notication.edit();
        findViewById(R.id.setting_notification_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       initSwitch();
    }

    private void initSwitch() {
        if(notication.getString(Setting_NotificateMessage.SETTING_NOTIFICATION_EVERY,"1").equals("0")) {
            ((SwitchView) findViewById(R.id.everyday_notification)).setState(false);
        }else{
            ((SwitchView) findViewById(R.id.everyday_notification)).setState(true);
        }
        if(notication.getString(Setting_NotificateMessage.SETTING_NOTIFICATION_ABOUT,"1").equals("0")) {
            ((SwitchView) findViewById(R.id.activity_about_notification)).setState(false);
        }else{
            ((SwitchView) findViewById(R.id.activity_about_notification)).setState(true);
        }
    }

    private void SetKey(String valuse,String key){
        editor.remove(valuse);
        editor.putString(valuse, key);
        editor.commit();
    }

    @Override
    public void finish() {
        int state=((SwitchView)findViewById(R.id.everyday_notification)).getState();
        if(state==SwitchView.STATE_SWITCH_ON){
            SetKey("setting_everyday_nofication", "1");
        }else{
            SetKey("setting_everyday_nofication", "0");
        }
        state=((SwitchView)findViewById(R.id.activity_about_notification)).getState();
        if(state==SwitchView.STATE_SWITCH_ON){
            SetKey("setting_about_notification", "1");
        }else{
            SetKey("setting_about_notification", "0");
        }
        super.finish();
    }
}
