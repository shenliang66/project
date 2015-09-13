package com.czw.project.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.czw.project.activity.AppSetting;
import com.czw.project.activity.MyInfo;
import com.czw.project.R;
import com.czw.project.activity.TabMainActivity;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;

public class TabFragmentUser extends Fragment {
	private LinearLayout btn_user_ji;
	private ImageView userHead;
	private ImageView setting;
	private TextView text_name;

	private View view;

	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
		view = inflater.inflate(R.layout.fragment_user, null);
		return view;
    }  
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		userHead = (ImageView) view.findViewById(R.id.user_info);
		userHead.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity().getApplicationContext(),MyInfo.class);
				startActivity(intent);
			}
		});
		setting =  (ImageView) getActivity().findViewById(R.id.app_setting);
		setting.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity().getApplicationContext(),AppSetting.class);
				startActivity(intent);
			}
			
		});
		super.onActivityCreated(savedInstanceState);

		initView();
		setMyInfo();
	}

	private void initView() {
		text_name = (TextView) view.findViewById(R.id.text_userName);
	}

	private void setMyInfo() {
		BmobUser user = BmobUser.getCurrentUser(getContext());
		if(user != null){
			setHeadPhoto();
			text_name.setText(user.getUsername());
		}
	}

	private void setHeadPhoto() {
		FileInputStream in = null;
		BufferedInputStream bin = null;
		try {
			in = getContext().openFileInput("headPhoto.png");
			bin = new BufferedInputStream(in);
			Bitmap bitmap = BitmapFactory.decodeStream(bin);
			in.close();
			bin.close();
			userHead.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
