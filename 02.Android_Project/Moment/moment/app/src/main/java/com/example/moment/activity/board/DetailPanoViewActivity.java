package com.example.moment.activity.board;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.moment.R;
import com.example.moment.common.Common;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

public class DetailPanoViewActivity extends AppCompatActivity {
    String imgpath;
    VrPanoramaView detail_vrPanoramaView;
    VrPanoramaView.Options options;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pano_view);

        detail_vrPanoramaView = findViewById(R.id.detail_vrPanoramaView);
        //이미지 정보 가져옴
        intent = getIntent();
        imgpath = intent.getStringExtra("imgpath");
        Log.i("detailPano: ",imgpath);
        //파노라마 사진 띄우는 형식을 설정할 옵션
        options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        //파노라마 사진 띄우기
        Glide
                .with(this)
                .asBitmap()
                .error(R.drawable.photosphere)
                .load(imgpath)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        detail_vrPanoramaView.loadImageFromBitmap(resource,options);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }
}
