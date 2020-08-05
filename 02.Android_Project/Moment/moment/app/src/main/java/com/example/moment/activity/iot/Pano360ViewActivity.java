package com.example.moment.activity.iot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.moment.R;
import com.example.moment.common.Common;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

public class Pano360ViewActivity extends AppCompatActivity {

    String filepath;
    VrPanoramaView vrPanoramaView;
    VrPanoramaView.Options options;

    Button pano_back;
    Button pano_write;

    Intent intent;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pano360_view);

        //외부장치로 촬영한 사진 이름을 가지고 옴
        intent = getIntent();
        filepath = intent.getStringExtra("filepath");

        //파노라마 XML 객체
        vrPanoramaView = findViewById(R.id.vrPanoramaView);

        //버튼 XML 객체
        pano_back = findViewById(R.id.pano_back);
        pano_write = findViewById(R.id.pano_write);

        //확인 취소 창을 띄워줄 버튼
        builder = new AlertDialog.Builder(this, R.style.IotAlertDialogStyle);

        //파노라마 사진 띄우는 형식을 설정할 옵션
        options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        //파노라마 사진 띄우기
        Glide
                .with(this)
                .asBitmap()
                .error(R.drawable.photosphere)
                .load(Common.SERVER_PANO_IMG+filepath)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        vrPanoramaView.loadImageFromBitmap(resource,options);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        //iot 기기연동 화면으로 이동
        pano_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton_dialog();
            }
        });
        //글작성 화면으로 이동
        pano_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeButton_dialog();
            }
        });
    }
    private void backButton_dialog(){
        builder.setTitle("알림 !").setMessage("다시 뒤로 돌아가시겠습니까?");
        //확인을 클릭시 뒤로 돌아감
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                intent = new Intent(Pano360ViewActivity.this, External_device.class);
                startActivity(intent);
            }
        });

        //취소를 누를시 다이얼로그 제거
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
    private void writeButton_dialog(){
        builder.setTitle("알림 !").setMessage("이 사진으로 글을 작성 하시겠습니까?");
        //확인을 클릭시 글 작성 페이지로 이동
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                intent = new Intent(Pano360ViewActivity.this, Pano360WriteActivity.class);
                intent.putExtra("filepath",filepath);
                startActivity(intent);
                finish();
            }
        });
        //취소를 누를시 다이얼로그 제거
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

}
