package com.czw.project.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.czw.project.R;
import com.czw.project.activity.FarmStoryMoreInfo;
import com.czw.project.activity.TabMainActivity;
import com.czw.project.adapter.FarmStoryAdapter;
import com.czw.project.model.FarmStory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class TabFragmentFarmStory extends Fragment {

    private View view;

    private ListView list_farmStory;
    private List<FarmStory> farmStories;
    private FarmStoryAdapter adapter;
    private FarmStory farmStory;

    private LinearLayout headView;
    private SimpleDraweeView image1,image2,image3,image4;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_farmstory, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        addData();
        setListener();
        setSwipeRefreshLayout();
    }

    private void setListener() {
        list_farmStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), FarmStoryMoreInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("farmStory", farmStories.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void addData() {
        farmStory = new FarmStory();
        farmStories = new ArrayList<>();

        TabMainActivity activity = (TabMainActivity) getActivity();
        farmStories = activity.getFarmStories();
        adapter = new FarmStoryAdapter(getContext(), farmStories,false);
        list_farmStory.addHeaderView(headView);
        list_farmStory.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(farmStories.size() >= 4){
            image1.setImageURI(Uri.parse(farmStories.get(0).getPicture1().getFileUrl(getContext())));
            image2.setImageURI(Uri.parse(farmStories.get(1).getPicture1().getFileUrl(getContext())));
            image3.setImageURI(Uri.parse(farmStories.get(2).getPicture1().getFileUrl(getContext())));
            image4.setImageURI(Uri.parse(farmStories.get(3).getPicture1().getFileUrl(getContext())));
        }
    }

    private void initView() {
        list_farmStory = (ListView) view.findViewById(R.id.list_farmStory);
        headView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.fragment_farmstory_head, null);
        image1 = (SimpleDraweeView) headView.findViewById(R.id.image_1);
        image2 = (SimpleDraweeView) headView.findViewById(R.id.image_2);
        image3 = (SimpleDraweeView) headView.findViewById(R.id.image_3);
        image4 = (SimpleDraweeView) headView.findViewById(R.id.image_4);
        image1.setAspectRatio(2f);
        image2.setAspectRatio(2f);
        image3.setAspectRatio(2f);
        image4.setAspectRatio(2f);
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
