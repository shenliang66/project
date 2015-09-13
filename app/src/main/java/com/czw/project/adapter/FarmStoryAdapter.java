package com.czw.project.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.czw.project.R;
import com.czw.project.model.FarmStory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by shenliang on 2015/8/31.
 */
public class FarmStoryAdapter extends BaseAdapter {

    private List<FarmStory> farmStorys;
    private LayoutInflater inflater;
    private Context context;
    private boolean isFirstPage = true;

    public FarmStoryAdapter(Context context,List<FarmStory> list,boolean isFirstPage){
        farmStorys = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.isFirstPage = isFirstPage;
    }

    @Override
    public int getCount() {
        return farmStorys.size();
    }

    @Override
    public Object getItem(int position) {
        return farmStorys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            if(isFirstPage){
                convertView = inflater.inflate(R.layout.fragment_farmstory_list_item,null);
            }else {
                convertView = inflater.inflate(R.layout.fragment_farmstory_list_item_2,null);
            }
            viewHolder.image_picture = (SimpleDraweeView) convertView.findViewById(R.id.image_picture);
            viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            viewHolder.text_address = (TextView) convertView.findViewById(R.id.text_address);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FarmStory farmStory = farmStorys.get(position);
        if(farmStory.getPicture1() != null){
            String url = farmStory.getPicture1().getFileUrl(context);
           viewHolder.image_picture.setImageURI(Uri.parse(url));
        }
        if(farmStory.getTitle() != null){
            viewHolder.text_title.setText(farmStory.getTitle());
        }
        if(farmStory.getAddress() != null){
            viewHolder.text_address.setText(farmStory.getAddress());
        }
        return convertView;
    }

    class ViewHolder{
        SimpleDraweeView image_picture;
        TextView text_title;
        TextView text_address;
    }
}
