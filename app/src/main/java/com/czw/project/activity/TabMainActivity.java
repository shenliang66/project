package com.czw.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.czw.project.R;
import com.czw.project.fragment.TabFragmentFarmStory;
import com.czw.project.fragment.TabFragmentFirstPage;
import com.czw.project.fragment.TabFragmentTourDiary;
import com.czw.project.fragment.TabFragmentUser;
import com.czw.project.model.FarmStory;
import com.czw.project.model.TourStory;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class TabMainActivity extends FragmentActivity implements Runnable {
    private RadioGroup myTabRg = null;
    private long lastClickTime;

    private ViewPager mViewPager;
    private List<Fragment> fragments;

    public List<FarmStory> getFarmStories() {
        return mFarmStories;
    }

    private List<FarmStory> mFarmStories;

    public List<TourStory> getTourStories() {
        return mTourStories;
    }

    private List<TourStory> mTourStories;

    private RelativeLayout mActionBar;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private TextView mLocation;
    private BDLocation mBDLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.application_load);
        SDKInitializer.initialize(getApplicationContext());
        Bmob.initialize(this, "c41cec5bf04044a1da77ffa4756cf646");
        Fresco.initialize(this);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        new Thread(this).start();
    }

    int i = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            i++;
            if (i == 2) {
                setContentView(R.layout.tab_main);
                initView();
                initLocation();
                mLocationClient.start();
            }
        }
    };

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mLocationClient.setLocOption(option);
    }

    private void initView() {
        mActionBar = (RelativeLayout) findViewById(R.id.action_bar);
        mLocation = (TextView) findViewById(R.id.local_name);
        TabFragmentFirstPage firstPage = new TabFragmentFirstPage();
        TabFragmentFarmStory farmStory = new TabFragmentFarmStory();
        TabFragmentTourDiary tourDiary = new TabFragmentTourDiary();
        TabFragmentUser user = new TabFragmentUser();

        mViewPager = (ViewPager) findViewById(R.id.id_viewPage);
        fragments = new ArrayList<>();
        fragments.add(firstPage);
        fragments.add(farmStory);
        fragments.add(tourDiary);
        fragments.add(user);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tabFristPage:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.tabFarmStory:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.tabTourDiary:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.tabUser:
                        mViewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideActionbar(false);
                switch (position) {
                    case 0:
                        myTabRg.check(R.id.tabFristPage);
                        break;
                    case 1:
                        myTabRg.check(R.id.tabFarmStory);
                        break;
                    case 2:
                        myTabRg.check(R.id.tabTourDiary);
                        break;
                    case 3:
                        myTabRg.check(R.id.tabUser);
                        hideActionbar(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

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

    @Override
    public void run() {
        mFarmStories = new ArrayList<>();
        BmobQuery<FarmStory> farmStoryBmobQuery = new BmobQuery<>();
        farmStoryBmobQuery.findObjects(this, new FindListener<FarmStory>() {
            @Override
            public void onSuccess(List<FarmStory> object) {
                mFarmStories.addAll(object);
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onError(int code, String msg) {
                mHandler.sendEmptyMessage(0);
            }
        });
        BmobQuery<TourStory> tourStoryBmobQuery = new BmobQuery<>();
        mTourStories = new ArrayList<>();
        tourStoryBmobQuery.include("author");
        tourStoryBmobQuery.findObjects(this, new FindListener<TourStory>() {
            @Override
            public void onSuccess(List<TourStory> list) {
                mTourStories.addAll(list);
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onError(int i, String s) {
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    /* 隐藏actionbar */
    public void hideActionbar(boolean isHide) {
        if (isHide) {
            mActionBar.setVisibility(View.GONE);
        } else {
            mActionBar.setVisibility(View.VISIBLE);
        }
    }

    /*显示当前位置*/
    public void showPosition(View view) {
        Intent toPositionAvtivity = new Intent(TabMainActivity.this, PositionActivity.class);
        toPositionAvtivity.putExtra("city",mLocation.getText().toString());
        startActivityForResult(toPositionAvtivity, 0);
        overridePendingTransition(R.anim.up_in,R.anim.no);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            mLocation.setText(data.getStringExtra("city"));
        }
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mBDLocation = location;
            if (location.getAddrStr() != null) {
                mLocation.setText(location.getAddrStr().substring(5, 7));
            }
        }
    }
}
