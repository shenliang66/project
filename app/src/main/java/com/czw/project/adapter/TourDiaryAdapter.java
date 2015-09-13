package com.czw.project.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.czw.project.R;
import com.czw.project.model.TourStory;
import com.facebook.drawee.view.SimpleDraweeView;


public class TourDiaryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<TourStory> storys;
    private Context context;
    private TourStory story;

    public TourDiaryAdapter(Context context, List<TourStory> stories) {
        this.storys = stories;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return storys.size();
    }

    @Override
    public Object getItem(int position) {
        return storys.get(position);
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
            convertView = inflater.inflate(R.layout.fragment_tourdiary_list_item, null);

            viewHolder.image_photo = (SimpleDraweeView) convertView.findViewById(R.id.image_photo);
            viewHolder.image_picture1 = (SimpleDraweeView) convertView.findViewById(R.id.image_picture1);
            viewHolder.image_picture2 = (SimpleDraweeView) convertView.findViewById(R.id.image_picture2);
            viewHolder.image_picture3 = (SimpleDraweeView) convertView.findViewById(R.id.image_picture3);
            viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            viewHolder.text_content = (TextView) convertView.findViewById(R.id.text_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        story = storys.get(position);
        if (story.getAuthor() != null) {
            if (story.getAuthor().getUsername() != null) {
                viewHolder.text_name.setText(story.getAuthor().getUsername());
            }
            if (story.getAuthor().getHeadPhoto() != null) {
                viewHolder.image_photo.setImageURI(Uri.parse(story.getAuthor().getHeadPhoto().getFileUrl(context)));
            }
        }

        if (story.getTitle() != null) {
            viewHolder.text_title.setText(story.getTitle());
        }
        if (story.getContent() != null) {
            viewHolder.text_content.setText(story.getContent());
        }
        if (story.getPicture1() != null || story.getPicture2() != null || story.getPicture3() != null) {
            if (story.getPicture1() != null) {
                viewHolder.image_picture1.setAspectRatio(1f);
                viewHolder.image_picture1.setImageURI(Uri.parse(story.getPicture1().getFileUrl(context)));
            }
            if (story.getPicture2() != null) {
                viewHolder.image_picture2.setAspectRatio(1f);
                viewHolder.image_picture2.setImageURI(Uri.parse(story.getPicture2().getFileUrl(context)));
            }
            if (story.getPicture3() != null) {
                viewHolder.image_picture3.setAspectRatio(1f);
                viewHolder.image_picture3.setImageURI(Uri.parse(story.getPicture3().getFileUrl(context)));
            }
        }
        viewHolder.text_time.setText(story.getCreatedAt().substring(0, 10));
        return convertView;
    }

    private class ViewHolder {
        SimpleDraweeView image_picture1, image_picture2, image_picture3;
        SimpleDraweeView image_photo;
        TextView text_name, text_time, text_title, text_content;
    }
}
