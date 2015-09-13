package com.czw.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.czw.project.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class SignActivityFirst extends Activity {

    private EditText edit_PhoneNum;
    private EditText edit_yanzhengma;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_first);


        initView();

    }

    private void initView() {
        edit_PhoneNum = (EditText) findViewById(R.id.edit_mobilePhoneNumber);
        edit_yanzhengma = (EditText) findViewById(R.id.edit_yanzhengma);
    }

    public void next(View view){
        BmobSMS.verifySmsCode(this, phoneNum, edit_yanzhengma.getText().toString(), new VerifySMSCodeListener() {
            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                if (ex == null) {//短信验证码已验证成功
                    Log.i("bmob", "验证通过");
                    Intent toNextActivity = new Intent(SignActivityFirst.this, SignNextActivity.class);
                    toNextActivity.putExtra("phoneNum", phoneNum);
                    startActivity(toNextActivity);
                } else {
                    Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                    Toast.makeText(SignActivityFirst.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*Intent toNextActivity = new Intent(SignActivityFirst.this, SignNextActivity.class);
        toNextActivity.putExtra("phoneNum", phoneNum);
        startActivity(toNextActivity);*/
    }

    public void verification(View view){
        phoneNum = edit_PhoneNum.getText().toString();
        BmobSMS.requestSMSCode(this, phoneNum, "模板名称",new RequestSMSCodeListener() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                // TODO Auto-generated method stub
                if(ex==null){//验证码发送成功
                    Log.i("bmob", "短信id："+smsId);//用于查询本次短信发送详情
                    Toast.makeText(SignActivityFirst.this,"验证码发送成功，十分钟内有效",Toast.LENGTH_LONG).show();
                }else {
                    Log.i("bmob" , ex.toString());
            }
            }
        });
    }

}
