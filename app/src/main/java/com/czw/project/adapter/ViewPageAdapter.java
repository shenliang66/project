package com.czw.project.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class ViewPageAdapter extends PagerAdapter {
	private List<ImageView> pagerImageList;
    private Context context;
    public ViewPageAdapter(List<ImageView> pagerImageList,Context context) {
    	this.context=context;
    	this.pagerImageList=pagerImageList;
	}
	@Override
	public int getCount() {
		return pagerImageList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(pagerImageList.get(position));
	}
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(pagerImageList.get(position));
		return pagerImageList.get(position);
	}
}
    




