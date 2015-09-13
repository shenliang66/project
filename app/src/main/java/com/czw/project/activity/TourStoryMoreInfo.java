package com.czw.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.czw.project.R;
import com.czw.project.adapter.TourDiaryMoreInfoAdapter;
import com.czw.project.model.Comment;
import com.czw.project.model.TourStory;
import com.czw.project.model._User;
import com.czw.project.util.WebImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class TourStoryMoreInfo extends Activity {

    private ListView list_commnet;
    List<Comment> commnets;
    private Comment comment;
    private TourDiaryMoreInfoAdapter adapter;
    private TourStory story;
    private View headView;
    private View headView2;

    private EditText edit_reply;
    private WebImageView image1, image2, image3, authorHead;
    private TextView text_name, text_title, text_content, text_time;

    private _User author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tour_story_more_info);
        headView = LayoutInflater.from(this).inflate(R.layout.list_item_story, null);
        headView2 = LayoutInflater.from(this).inflate(R.layout.list_item_story_moreinfo_head, null);
        Intent intent = getIntent();
        story = (TourStory) intent.getSerializableExtra("story");

        initView();
        addData();
    }

    private void addData() {
        commnets = new ArrayList<>();
        text_name.setText(story.getAuthor().getUsername());
        text_content.setText(story.getContent());
        text_title.setText(story.getTitle());
        text_time.setText(story.getCreatedAt());
        if (story.getAuthor().getHeadPhoto() != null) {
            authorHead.setImageUrl(story.getAuthor().getHeadPhoto().getFileUrl(this));
        }
        if (story.getPicture1() != null) {
            image1.setImageUrl(story.getPicture1().getFileUrl(this));
        }
        if (story.getPicture2() != null) {
            image2.setImageUrl(story.getPicture2().getFileUrl(this));
        }
        if (story.getPicture3() != null) {
            image3.setImageUrl(story.getPicture3().getFileUrl(this));
        }

        comment = new Comment();
        BmobQuery<Comment> bmobQuery = new BmobQuery<>();
        bmobQuery.include("author,tourStory");

        bmobQuery.addWhereEqualTo("tourStory",new BmobPointer(story));
        bmobQuery.findObjects(this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                commnets.addAll(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(int i, String s) {
                commnets.add(new Comment());
            }
        });
        adapter = new TourDiaryMoreInfoAdapter(this, commnets);
        list_commnet.addHeaderView(headView);
        list_commnet.addHeaderView(headView2);
        list_commnet.setAdapter(adapter);
    }

    private void initView() {
        list_commnet = (ListView) findViewById(R.id.id_list_comments);
        image1 = (WebImageView) headView2.findViewById(R.id.image_1);
        image2 = (WebImageView) headView2.findViewById(R.id.image_2);
        image3 = (WebImageView) headView2.findViewById(R.id.image_3);
        authorHead = (WebImageView) headView.findViewById(R.id.image_photo);
        edit_reply = (EditText) findViewById(R.id.edit_reply);
        text_name = (TextView) headView.findViewById(R.id.text_name);
        text_title = (TextView) headView.findViewById(R.id.text_title);
        text_content = (TextView) headView.findViewById(R.id.text_content);
        text_time = (TextView) headView.findViewById(R.id.text_time);
    }

    public void reply(View view) {
        author = _User.getCurrentUser(this,_User.class);
        String reply = edit_reply.getText().toString();
        comment.setAuthor(author);
        comment.setContent(reply);
        comment.setTourStory(story);
        comment.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                toast("回复成功");
                edit_reply.setText("");
            }

            @Override
            public void onFailure(int i, String s) {
                toast("回复失败");
            }
        });
    }

    public void refresh(View view) {
        commnets.clear();
        Toast.makeText(this, "正在更新", Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        onBackPressed();
    }

    public void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

}
