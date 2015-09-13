package com.czw.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.czw.project.R;
import com.czw.project.model.Comment;
import com.czw.project.util.WebImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/8/30.
 */
public class TourDiaryMoreInfoAdapter extends BaseAdapter {
    List<Comment> list_comment;
    private LayoutInflater inflater;
    private Comment comment;
    private Context context;

    public TourDiaryMoreInfoAdapter(Context context, List<Comment> storys){
        list_comment = storys;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list_comment.size();
    }

    @Override
    public Object getItem(int position) {
        return list_comment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_story_moreinfo, null);

            viewHolder.image_photo = (WebImageView) convertView.findViewById(R.id.id_image_photo);
            viewHolder.text_name = (TextView) convertView.findViewById(R.id.id_text_name);
            viewHolder.text_content = (TextView) convertView.findViewById(R.id.id_text_content);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        comment = list_comment.get(position);
        if(comment.getAuthor().getUsername() != null){
            viewHolder.text_name.setText(comment.getAuthor().getUsername());
        }
        if(comment.getContent() != null){
            viewHolder.text_content.setText(comment.getContent());
        }
        if(comment.getAuthor().getHeadPhoto() != null){
            viewHolder.image_photo.setImageUrl(comment.getAuthor().getHeadPhoto().getFileUrl(context));
        }
        viewHolder.text_time.setText(comment.getCreatedAt());
        return convertView;
    }

    private class ViewHolder {
        WebImageView image_photo;
        TextView text_name, text_content,text_time;
    }
}
