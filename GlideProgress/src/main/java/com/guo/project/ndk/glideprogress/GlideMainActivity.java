package com.guo.project.ndk.glideprogress;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

public class GlideMainActivity extends AppCompatActivity {

    String url = "http://att.bbs.duowan.com/forum/201811/13/220639dyy4ieiks4newyo0.jpg";
    String url2 = "http://vtime-seaweedfs.vargoyun.cn/2/03ebe7259400";

    ProgressImageView image;
    ProgressImageView image2;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_main);
        image = (ProgressImageView) findViewById(R.id.image);
        image2 = (ProgressImageView) findViewById(R.id.image_2);
    }

    public void loadImage(View view) {

        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                image.setProgress(100,progress);
            }
        });
        ProgressInterceptor.addListener(url2, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                image2.setProgress(100,progress);
            }
        });

        Glide.with(this)//
                .load(url)//
                .diskCacheStrategy(DiskCacheStrategy.NONE)//
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//
                .into(new GlideDrawableImageViewTarget(image) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        ProgressInterceptor.removeListener(url);
                    }
                });//
        Glide.with(this)//
                .load(url2)//
                .diskCacheStrategy(DiskCacheStrategy.NONE)//
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//
                .into(new GlideDrawableImageViewTarget(image2) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        ProgressInterceptor.removeListener(url2);
                    }
                });//



    }


}
