package com.czw.project.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shenliang on 2015/9/10.
 */
public class TextImageButton extends LinearLayout {

    private ImageView mImageView;
    private TextView mTextView;

    /* 构造函数 */
    public TextImageButton(Context context) {
        this(context,null);
    }

    public TextImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        //通过系统的按钮样式初始化父布局
        //这样会设置clickable属性和按钮背景来匹配当前的主题
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        //创建子视图
        mImageView = new ImageView(context,attrs,defStyleAttr);
        mTextView = new TextView(context,attrs,defStyleAttr);
        //创建子视图的LayoutParams 为wrap_content并居中显示
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //添加视图
        this.addView(mImageView, params);
        mTextView.setPadding(0,10,0,0);
        this.addView(mTextView,params);

    }

    /* 访问器 */
    public void setText(CharSequence text){
        //设置文字
        mTextView.setText(text);
    }

    public void setImageResource(int resID){
        //设置图片
        mImageView.setImageResource(resID);
    }

    public void setImageDrawable(Drawable imageDrawable){
        //设置图片
        mImageView.setImageDrawable(imageDrawable);
    }
}
