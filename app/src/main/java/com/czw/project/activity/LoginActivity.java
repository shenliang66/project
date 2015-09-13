package com.czw.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.czw.project.R;
import com.czw.project.model._User;
import com.czw.project.util.CircleImageView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends Activity {

    private CircleImageView headPhoto;

    private EditText edit_name;
    private EditText edit_password;

    private BmobUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        user = BmobUser.getCurrentUser(this);
        if(user != null){
            Intent toSignActivity = new Intent(LoginActivity.this, TabMainActivity.class);
            startActivity(toSignActivity);
            finish();
        }

        initView();
        setHeadPhoto();
    }

    private void setHeadPhoto() {
        FileInputStream in = null;
        BufferedInputStream bin = null;
        try {
            in = openFileInput("headPhoto.png");
            bin = new BufferedInputStream(in);
            Bitmap bitmap = BitmapFactory.decodeStream(bin);
            in.close();
            bin.close();
            headPhoto.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        edit_name = (EditText) findViewById(R.id.edit_userName);
        edit_password = (EditText) findViewById(R.id.edit_password);
        headPhoto = (CircleImageView) findViewById(R.id.id_image_photo);

        if(user != null){
            edit_name.setText(user.getUsername());
        }
    }

    public void sign(View view) {
        Intent toSignActivity = new Intent(this, SignActivityFirst.class);
        startActivity(toSignActivity);
    }

    public void findPassword(View view) {
        Toast.makeText(this, "功能未完善", Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        String name = edit_name.getText().toString();
        String password = edit_password.getText().toString();

        BmobUser.loginByAccount(this, name, password, new LogInListener<_User>() {

            @Override
            public void done(_User user, BmobException e) {
                // TODO Auto-generated method stub
                if (user != null) {
                    Intent toSignActivity = new Intent(LoginActivity.this, TabMainActivity.class);
                    startActivity(toSignActivity);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private long lastClickTime;
    @Override
    public void onBackPressed() {
        if (lastClickTime <= 0) {
            Toast.makeText(this, "在按一次后退键退出应用程序", Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        } else {
            long currentClickTime = System.currentTimeMillis();
            if (currentClickTime - lastClickTime < 1000) {
                finish();
            } else {
                Toast.makeText(this, "在按一次后退键退出应用程序", Toast.LENGTH_SHORT).show();
                lastClickTime = currentClickTime;
            }
        }
    }

}
