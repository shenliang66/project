package com.czw.project.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.czw.project.R;
import com.czw.project.activity.LoginActivity;
import com.czw.project.activity.PublishActivity;
import com.czw.project.activity.TabMainActivity;
import com.czw.project.activity.TourStoryMoreInfo;
import com.czw.project.adapter.TourDiaryAdapter;
import com.czw.project.model.TourStory;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class TabFragmentTourDiary extends Fragment implements View.OnClickListener {

    private View view;

    private ListView listView;
    private List<TourStory> stories = new ArrayList<>();
    private BmobQuery<TourStory> bmobQuery = new BmobQuery<>();
    private TourDiaryAdapter adapter;
    private BmobUser user;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout headView;
    private RadioGroup mRadioGroup;

    private TabMainActivity mTabMainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tourdiary, container, false);
        setSwipeRefreshLayout();

        return view;
    }

    private void setSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stories.clear();
                addDatas();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addDatas();
        initView();
        initListView();
        initViewFlipper();
        initRadioGroup();
    }

    private void initRadioGroup() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_1:
                        break;
                    case R.id.btn_2:
                        break;
                    case R.id.btn_3:
                        break;
                }
            }
        });
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.list_story);
        adapter = new TourDiaryAdapter(getContext(), stories);
        listView.addHeaderView(headView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tag","adjklajd");
                Intent toTourStoryMoreActivity = new Intent(getContext(), TourStoryMoreInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("story", stories.get(position-1));
                toTourStoryMoreActivity.putExtras(bundle);
                startActivity(toTourStoryMoreActivity);
            }
        });
    }

    private void addDatas() {
        mTabMainActivity = (TabMainActivity) getActivity();
        stories.addAll(mTabMainActivity.getTourStories());
    }

    private void initView() {
        headView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.fragment_tourdiary_head,null);
        mRadioGroup = (RadioGroup) headView.findViewById(R.id.id_radio_group);
    }

    private void initViewFlipper() {
        final ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        viewFlipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            View[] views = new View[]{view.findViewById(R.id.dot_1), view.findViewById(R.id.dot_2), view.findViewById(R.id.dot_3), view.findViewById(R.id.dot_4)};
            int oldPos = 1;

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int pos = viewFlipper.getDisplayedChild();
                views[pos].setBackgroundResource(R.drawable.dot_focus);
                views[oldPos].setBackgroundResource(R.drawable.dot_blur);
                oldPos = pos;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_writeYouji) {
            user = BmobUser.getCurrentUser(getContext());
            if(user != null){
                Intent toPublishActivity = new Intent(getContext(), PublishActivity.class);
                startActivity(toPublishActivity);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("请登陆后发布");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.create().show();
            }

        }
    }
}
