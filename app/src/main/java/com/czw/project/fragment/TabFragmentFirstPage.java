package com.czw.project.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.czw.project.R;
import com.czw.project.activity.TabMainActivity;
import com.czw.project.adapter.FarmStoryAdapter;
import com.czw.project.adapter.ViewPageAdapter;
import com.czw.project.model.FarmStory;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TabFragmentFirstPage extends Fragment {
    private View view;
    private ViewPager pager;
    private List<View> list;
    private List<ImageView> imageViewsList;
    private int currentItem = 0;


    private ListView mListView;
    private FarmStoryAdapter mFarmStoryAdapter;
    private LinearLayout headView;
    private List<FarmStory> mFarmStories;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            pager.setCurrentItem(currentItem);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firstpage, container, false);
        initView();
        initViewPage();
        return view;
    }

    private void initView() {
        mListView = (ListView) view.findViewById(R.id.listView_firstPage);
        headView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.fragment_firstpage_head, null);
        Fresco.initialize(getContext());
        headView.findViewById(R.id.text_auto).setSelected(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListView();
        setSwipeRefreshLayout();
    }

    private void setListView() {
        mFarmStories = new ArrayList<>();
        TabMainActivity tabMainActivity = (TabMainActivity) getActivity();
        mFarmStories = tabMainActivity.getFarmStories();
        mFarmStoryAdapter = new FarmStoryAdapter(getContext(), mFarmStories,true);
        mListView.addHeaderView(headView);
        mListView.setAdapter(mFarmStoryAdapter);
    }


    private void initViewPage() {
        int[] imageUrl = new int[]{R.drawable.afternoon, R.drawable.appmain,
                R.drawable.appnext, R.drawable.appnew};
        list = new ArrayList<>();
        imageViewsList = new ArrayList<>();
        for (int i = 0; i < imageUrl.length; i++) {
            ImageView imageView = new ImageView(getActivity()
                    .getApplicationContext());
            imageView.setImageResource(imageUrl[i]);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageViewsList.add(imageView);
        }
        list.add(headView.findViewById(R.id.v_dot0));
        list.add(headView.findViewById(R.id.v_dot1));
        list.add(headView.findViewById(R.id.v_dot2));
        list.add(headView.findViewById(R.id.v_dot3));
        ViewPageAdapter adapter = new ViewPageAdapter(imageViewsList, getContext());
        pager = (ViewPager) headView.findViewById(R.id.viewPager);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new MyPageChangeListener());
    }


    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;

        public void onPageSelected(int position) {
            currentItem = position;
            list.get(oldPosition).setBackgroundResource(R.drawable.dot_blur);
            list.get(position).setBackgroundResource(R.drawable.dot_focus);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    @Override
    public void onStart() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
                TimeUnit.SECONDS);
        super.onStart();
    }

    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (pager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
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
}
