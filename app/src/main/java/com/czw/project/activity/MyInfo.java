package com.czw.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.czw.project.R;

import cn.bmob.v3.BmobUser;

public class MyInfo extends Activity {
	private ImageButton btn_back;
	private TextView text_name;
	private TextView text_tell;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_date);
		btn_back = (ImageButton) findViewById(R.id.my_userinfo_back);
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		initView();
		setInfo();
	}

	private void setInfo() {
		BmobUser user = BmobUser.getCurrentUser(this);
		if(user != null){
			text_name.setText(user.getUsername());
			text_tell.setText(user.getMobilePhoneNumber());
		}
	}

	private void initView() {
		text_name = (TextView) findViewById(R.id.text_userName);
		text_tell = (TextView) findViewById(R.id.text_tell);
	}

	public void exit(View view){
		BmobUser.logOut(this);
		Intent toLoginActivity = new Intent(this,LoginActivity.class);
		startActivity(toLoginActivity);
	}
}
