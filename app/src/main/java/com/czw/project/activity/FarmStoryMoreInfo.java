package com.czw.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

import com.czw.project.R;
import com.czw.project.model.FarmStory;
import com.czw.project.util.WebImageView;

public class FarmStoryMoreInfo extends Activity {
    FarmStory farmStory;
    private ViewFlipper viewFlipper;
    private WebImageView image1, image2, image3, image4;
    private WebImageView headPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_story_more_info);

        initObject();

        Intent intent = getIntent();
        farmStory = (FarmStory) intent.getSerializableExtra("farmStory");

        initView();
        setViewFlipper();
        loadInformation();
    }

    private void loadInformation() {
        if(farmStory.getPicture4() != null){
            image1.setImageUrl(farmStory.getPicture4().getFileUrl(this));
        }
        if(farmStory.getPicture3() != null){
            image2.setImageUrl(farmStory.getPicture3().getFileUrl(this));
        }
        if(farmStory.getPicture2() != null){
            image3.setImageUrl(farmStory.getPicture2().getFileUrl(this));
        }
        if(farmStory.getPicture1() != null){
            image4.setImageUrl(farmStory.getPicture1().getFileUrl(this));
        }
        if(farmStory.getHeadPhoto() != null){
            headPhoto.setImageUrl(farmStory.getHeadPhoto().getFileUrl(this));
        }
    }

    private void setViewFlipper() {
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            float statX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        statX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float offsetX = event.getX() - statX;
                        if (offsetX > 5) {
                            viewFlipper.showPrevious();
                        }
                        if (offsetX < -5) {
                            viewFlipper.showNext();
                        }
                        break;
                }
                return true;
            }
        });
        viewFlipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            View[] views = new View[]{findViewById(R.id.dot_1), findViewById(R.id.dot_2), findViewById(R.id.dot_3), findViewById(R.id.dot_4)};

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int id = viewFlipper.getDisplayedChild();
                for (int i = 0; i < views.length; i++) {
                    if (id == i) {
                        views[i].setBackgroundResource(R.drawable.dot_focus);
                    } else {
                        views[i].setBackgroundResource(R.drawable.dot_blur);
                    }
                }
            }
        });
    }

    private void initView() {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        image1 = (WebImageView) findViewById(R.id.image1);
        image2 = (WebImageView) findViewById(R.id.image2);
        image3 = (WebImageView) findViewById(R.id.image3);
        image4 = (WebImageView) findViewById(R.id.image4);
        headPhoto = (WebImageView) findViewById(R.id.image_photo);
    }

    private void initObject() {
        farmStory = new FarmStory();
    }

}
