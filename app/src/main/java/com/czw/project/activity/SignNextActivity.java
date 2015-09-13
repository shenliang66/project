package com.czw.project.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.czw.project.R;
import com.czw.project.model._User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class SignNextActivity extends Activity {

    private EditText edit_userName;
    private EditText edit_password;
    private EditText edit_passwordAgain;

    private ImageView photo;
    private String headPhotoUrl;
    private _User user;
    private String phoneNum;

    private BmobFile bmobFile;

    private static final int RESULT_LOAD_IMAGE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_next);

        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");

        user = new _User();
        initView();
    }

    private void initView() {
        edit_userName = (EditText) findViewById(R.id.edit_userName);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_passwordAgain = (EditText) findViewById(R.id.edit_passwordAgain);
        photo = (ImageView) findViewById(R.id.image_photo);
    }

    public void sign(View view) {
        String name = edit_userName.getText().toString();
        String password = edit_password.getText().toString();
        String passwordAgain = edit_passwordAgain.getText().toString();

        if (name.equals("")) {
            toast("用户名不能为空");
            return;
        }
        if (password.equals(passwordAgain) && !password.equals("")) {
            user.setUsername(name);
            user.setPassword(password);
            user.setMobilePhoneNumber(phoneNum);
            user.setMobilePhoneNumberVerified(true);
            user.setHeadPhoto(bmobFile);
            user.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    toast("注册成功:");
                    finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    toast("注册失败:" + msg);
                }
            });

            return;
        }
        toast("请确认密码输入一致！");
    }

    public void setPhoto(View view) {
        Intent i = new Intent(
                Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    Bitmap bm = null;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            ContentResolver resolver = getContentResolver();
            Uri originalUri = data.getData();
            try {
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            Log.i("path", path);
            unloadPhoto(path);

            saveToSdCard(bm);
        }
    }

    private void saveToSdCard(Bitmap bitmap) {
        FileOutputStream out;
        try {
            out = openFileOutput("headPhoto.png", MODE_PRIVATE);
            out.write(Bitmap2Bytes(bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public void unloadPhoto(String path) {
        bmobFile = new BmobFile(new File(path));
        /*bmobFile.uploadblock(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                //bmobFile.getUrl()---返回的上传文件的地址（不带域名）
                //bmobFile.getFileUrl(context)--返回的上传文件的完整地址（带域名）
                toast("上传文件成功:");
                headPhotoUrl = bmobFile.getFileUrl(SignNextActivity.this);
                photo.setImageBitmap(bm);
            }

            @Override
            public void onProgress(Integer value) {
                // TODO Auto-generated method stub
                // 返回的上传进度（百分比）
                toast("正在上传图片...");
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("上传文件失败：" + msg);
            }
        });*/


        BTPFileResponse response = BmobProFile.getInstance(this).upload(path, new UploadListener() {
            @Override
            public void onSuccess(String fileName,String url,BmobFile file) {
                Log.i("bmob","文件上传成功："+fileName+",可访问的文件地址："+file.getUrl());
                toast("上传文件成功:");
                photo.setImageBitmap(bm);
                bmobFile = file;
                // TODO Auto-generated method stub
                // fileName ：文件名（带后缀），这个文件名是唯一的，开发者需要记录下该文件名，方便后续下载或者进行缩略图的处理
                // url        ：文件地址
                // file        :BmobFile文件类型，`V3.4.1版本`开始提供，用于兼容新旧文件服务。
            }

            @Override
            public void onProgress(int progress) {
                // TODO Auto-generated method stub
                Log.i("bmob","onProgress :"+progress);
                toast("正在上传图片...");
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                Log.i("bmob","文件上传失败："+errormsg);
                toast("上传文件失败：" + errormsg);
            }
        });
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
