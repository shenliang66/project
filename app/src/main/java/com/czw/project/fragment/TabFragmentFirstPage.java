package com.czw.project.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.czw.project.R;
import com.czw.project.activity.LoginActivity;
import com.czw.project.activity.TabMainActivity;
import com.czw.project.adapter.FarmStoryAdapter;
import com.czw.project.adapter.ViewPageAdapter;
import com.czw.project.model.FarmStory;
import com.czw.project.util.TextImageButton;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentFirstPage extends Fragment implements View.OnClickListener{
    private View view;

    private ListView mListView;
    private FarmStoryAdapter mFarmStoryAdapter;
    private RelativeLayout headView;
    private List<FarmStory> mFarmStories;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mViewPager;
    private List<ImageView> mImageViews;

    private TextImageButton btn_gonglue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firstpage, container, false);
        initView();
        return view;
    }

    private void initViewPage() {
        mViewPager = (ViewPager) headView.findViewById(R.id.viewFlipper);
        mImageViews = new ArrayList<>();
        ImageView image1 = new ImageView(getContext());
        image1.setBackgroundResource(R.drawable.afternoon);
        mImageViews.add(image1);
        ImageView image2 = new ImageView(getContext());
        image2.setBackgroundResource(R.drawable.appmain);
        mImageViews.add(image2);
        ImageView image3 = new ImageView(getContext());
        image3.setBackgroundResource(R.drawable.appnew);
        mImageViews.add(image3);
        ImageView image4 = new ImageView(getContext());
        image4.setBackgroundResource(R.drawable.appnext);
        mImageViews.add(image4);
        mViewPager.setAdapter(new ViewPageAdapter(mImageViews, getContext()));
        mViewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            View[] views = new View[]{view.findViewById(R.id.dot_1), view.findViewById(R.id.dot_2),
                    view.findViewById(R.id.dot_3), view.findViewById(R.id.dot_4)};
            int oldPos = 0;
            int pos = 0;

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                pos = mViewPager.getCurrentItem();
                views[oldPos].setBackgroundResource(R.drawable.dot_blur);
                views[pos].setBackgroundResource(R.drawable.dot_focus);
                oldPos = pos;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    currentItem = (currentItem + 1) % mImageViews.size();
                    Message msg = new Message();
                    msg.arg1 = currentItem;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }
        }).start();
    }
private int currentItem;
    private Handler mHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            if(currentItem == 0){
                mViewPager.setCurrentItem(currentItem,false);
            }else {
                mViewPager.setCurrentItem(currentItem);
            }
        }
    };

    private void initView() {
        mListView = (ListView) view.findViewById(R.id.listView_firstPage);
        headView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.fragment_firstpage_head, null);
        Fresco.initialize(getContext());
        headView.findViewById(R.id.text_auto).setSelected(true);
        btn_gonglue = (TextImageButton) headView.findViewById(R.id.btn_gonglue);
        btn_gonglue.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListView();
        setSwipeRefreshLayout();
        initViewPage();
    }

    private void setListView() {
        mFarmStories = new ArrayList<>();
        TabMainActivity tabMainActivity = (TabMainActivity) getActivity();
        mFarmStories = tabMainActivity.getFarmStories();
        mFarmStoryAdapter = new FarmStoryAdapter(getContext(), mFarmStories, true);
        mListView.addHeaderView(headView);
        mListView.setAdapter(mFarmStoryAdapter);
    }

    private void setSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent toLoginActivity = new Intent(getContext(), LoginActivity.class);
        startActivity(toLoginActivity);
    }
}
