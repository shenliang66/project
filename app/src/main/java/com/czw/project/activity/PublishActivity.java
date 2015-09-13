package com.czw.project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.czw.project.R;
import com.czw.project.model.TourStory;
import com.czw.project.model._User;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

public class PublishActivity extends Activity {

    private EditText edit_title, edit_content;
    private TourStory story;
    private ImageView image1,image2,image3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish);

        intView();
        story = new TourStory();
    }

    private void intView() {
        edit_title = (EditText) findViewById(R.id.id_title);
        edit_content = (EditText) findViewById(R.id.id_content);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void submit(View view) {
        _User user = BmobUser.getCurrentUser(this, _User.class);
        story.setAuthor(user);
        story.setTitle(edit_title.getText().toString());
        story.setContent(edit_content.getText().toString());
        story.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PublishActivity.this);
                builder.setTitle("提示");
                builder.setMessage("发表成功");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create().show();
            }

            @Override
            public void onFailure(int i, String s) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PublishActivity.this);
                builder.setTitle("提示");
                builder.setMessage("上传失败");
                builder.create().show();
            }
        });
    }

    public void addPicture(View view) {
        switch (view.getId()) {
            case R.id.image1:
                unloadPhoto(0);
                break;
            case R.id.image2:
                unloadPhoto(1);
                break;
            case R.id.image3:
                unloadPhoto(2);
                break;
        }
    }

    public void unloadPhoto(int requestCode) {
        Intent i = new Intent(
                Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, requestCode);
    }

    Bitmap bm = null;
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
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
            BmobFile bmobFile = new BmobFile(new File(path));

            BTPFileResponse response = BmobProFile.getInstance(this).upload(path, new UploadListener() {
                @Override
                public void onSuccess(String fileName, String url, BmobFile file) {
                    toast("上传文件成功:");
                    switch (requestCode){
                        case 0:
                            image1.setImageBitmap(bm);
                            story.setPicture1(file);
                            story.setFileName(fileName);
                            break;
                        case 1:
                            image2.setImageBitmap(bm);
                            story.setPicture2(file);
                            break;
                        case 2:
                            image3.setImageBitmap(bm);
                            story.setPicture3(file);
                            break;
                    }
                }
                @Override
                public void onProgress(int progress) {
                    toast("正在上传图片...");
                }
                @Override
                public void onError(int statuscode, String errormsg) {
                    toast("上传文件失败：" + errormsg);
                }
            });
        }
    }


    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
