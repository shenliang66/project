package com.czw.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.czw.project.R;

public class LocateCity extends Activity {
	private Button ok, canel;
	private TextView address;
	private Spinner province, city, county;
	private String[] str_province = { "北京市", "天津市", "上海市", "重庆市", "河北省", "河南省",
			"云南省", "辽宁省", "黑龙江省", "湖南省", "安徽省", "山东省", "新疆省", "江苏省", "浙江省",
			"江西省", "湖北省", "广西省", "甘肃省", "山西省", "内蒙古", "陕西省", "吉林省", "福建省",
			"贵州省", "广东省", "青海省" };
	private  ArrayAdapter<String>  adapter_province;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_city);
		init();
	}

	private void init() {
		ok = (Button) findViewById(R.id.location_city_ok);
		canel = (Button) findViewById(R.id.location_city_canel);
		address = (TextView) findViewById(R.id.tv_address);
		address.setText(getIntent().getStringExtra("mylocate"));
		province = (Spinner) findViewById(R.id.spinner_province);
		city = (Spinner) findViewById(R.id.spinner_city);
		county = (Spinner) findViewById(R.id.spinner_county);
		adapter_province = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, str_province);
		province.setAdapter(adapter_province);
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		canel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
