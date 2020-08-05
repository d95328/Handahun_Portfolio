package com.example.moment.activity.iot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moment.R;
import com.example.moment.activity.gmap.GoogleMapsActivity;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncPanoWrite;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pano360WriteActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 444;

    Intent intent;
    //XML 객체
    TextView pano_write_button;
    CircleImageView pano_write_profile;
    TextView pano_write_nick;
    ImageView pano_write_image;
    EditText pano_write_title;
    LinearLayout pano_write_locationButton;
    TextView pano_write_printLocation;
    EditText pano_write_comment;

    //위도 경도
    double latitude;
    double longitude;

    //가져온 이미지 이름
    String filepath;

    //DTO
    Board dto;

    //로딩바
    ProgressDialog progressDialog;

    //로그인 정보를 담은 SharedPreference
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pano360_write);

        //XML 객체 아이디
        pano_write_button = findViewById(R.id.pano_write_button);
        pano_write_profile = findViewById(R.id.pano_write_profile);
        pano_write_nick = findViewById(R.id.pano_write_nick);
        pano_write_image = findViewById(R.id.pano_write_image);
        pano_write_title = findViewById(R.id.pano_write_title);
        pano_write_locationButton = findViewById(R.id.pano_write_locationButton);
        pano_write_printLocation = findViewById(R.id.pano_write_printLocation);
        pano_write_comment = findViewById(R.id.pano_write_comment);

        //로딩바
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("게시물 저장중");

        //이미지 로드
        intent = getIntent();
        filepath = intent.getStringExtra("filepath");
        Glide.with(this).asBitmap().load(Common.SERVER_PANO_IMG+filepath).into(pano_write_image);

        //로그인 정보 취득
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        //글쓴이 정보 화면에 출력
        Glide
                .with(this)
                .load(Common.PROFILE_IMG + sharedPreferences.getString("u_profileimg",""))
                .into(pano_write_profile);
        pano_write_nick.setText(sharedPreferences.getString("u_nick",""));


        pano_write_locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Pano360WriteActivity.this, GoogleMapsActivity.class);
                startActivityForResult(intent, REQUEST_LOCATION);
            }
        });

        pano_write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pano_write_title.getText().toString().trim().equals("")){
                    Toast.makeText(Pano360WriteActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return ;
                }else if (pano_write_printLocation.getText().toString().trim().equals("")){
                    Toast.makeText(Pano360WriteActivity.this, "위치를 추가해주세요", Toast.LENGTH_SHORT).show();
                    return ;
                }else if(pano_write_comment.getText().toString().trim().equals("")){
                    Toast.makeText(Pano360WriteActivity.this, "코멘트를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return ;
                }
                dto = new Board();
                dto.setB_coment(pano_write_comment.getText().toString());
                dto.setB_title(pano_write_title.getText().toString());
                dto.setB_local(pano_write_printLocation.getText().toString());
                dto.setB_latitude(latitude);
                dto.setB_longitude(longitude);
                dto.setB_pano("Y");
                dto.setB_imgpath(filepath);
                dto.setB_userid(sharedPreferences.getString("u_userid",""));
                progressDialog.show();
                AsyncPanoWrite async = new AsyncPanoWrite(Pano360WriteActivity.this, dto, progressDialog);
                async.execute();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_LOCATION && resultCode == RESULT_OK ){
            pano_write_printLocation.setText(data.getStringExtra("location"));
            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);
        }
    }


}
