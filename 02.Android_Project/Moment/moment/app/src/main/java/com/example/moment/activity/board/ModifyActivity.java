package com.example.moment.activity.board;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.moment.R;
import com.example.moment.activity.gmap.GoogleMapsActivity;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardDetail;
import com.example.moment.trancefer.AsyncBoardModify;
import com.example.moment.trancefer.AsyncUserModify;

import java.io.File;
import java.net.URL;

public class ModifyActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 555;

    String result;

    Board dto;
    ImageView modifyImageView;
    ImageView modifyProfile;

    TextView modifyNick;
    TextView modifyLocation;
    TextView modifyTitle;
    TextView modifyComent;

    SharedPreferences sharedPreferences;

    Toolbar toolbar;

    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_modify);

        //툴바생성 & 뒤로가기버튼
        toolbar = findViewById(R.id.toolbar_modify);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        modifyImageView = findViewById(R.id.modifyImageView);
        modifyProfile = findViewById(R.id.modifyProfile);
        modifyNick = findViewById(R.id.modifyNick);
        modifyLocation = findViewById(R.id.modifyLocal);
        modifyTitle = findViewById(R.id.modifyTitle);
        modifyComent = findViewById(R.id.modifyComent);

        // 구글맵 인텐트
        modifyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMaps = new Intent(ModifyActivity.this, GoogleMapsActivity.class);
                startActivityForResult(intentMaps, REQUEST_LOCATION);
            }
        });

        final Intent intent = getIntent();
        String no = String.valueOf(intent.getIntExtra("b_no", 0));
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        //Detail 정보 불러오기
        try {
            //유저 아이디를 받아옴.
            String b_userid = sharedPreferences.getString("u_userid", null);
            //추천, 즐겨찾기 정보를 같이 불러온다
            dto = new AsyncBoardDetail(ModifyActivity.this).execute(no, b_userid).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        modifyNick.setText(dto.getB_nick());
        modifyLocation.setText(dto.getB_local());
        modifyTitle.setText(dto.getB_title());
        modifyComent.setText(dto.getB_coment());

        Glide
                .with(modifyImageView)
                .load(dto.getB_imgpath())
                .override(350, 150)
                .into(modifyImageView);

        Glide
                .with(modifyProfile)
                .load(dto.getB_profileimg())
                .into(modifyProfile);


    }// onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOCATION && resultCode == RESULT_OK) {
            modifyLocation.setText(data.getStringExtra("location"));
            latitude = data.getDoubleExtra("latitude", 0);
            longitude = data.getDoubleExtra("longitude", 0);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.write) {
            if (modifyTitle.getText().toString().trim().equals("")) {
                alert("제목을 입력해주세요");
                return;
            } else if (modifyLocation.getText().toString().trim().equals("")) {
                alert("위치를 추가해주세요");
                return;
            } else if (modifyComent.getText().toString().trim().equals("")) {
                alert("코멘트를 입력해주세요");
                return;
            }
            final Intent intent = getIntent();
            String b_no = String.valueOf(intent.getIntExtra("b_no", 0));

            Board dto = new Board();
            dto.setB_no(Integer.parseInt(b_no));
            dto.setB_local(modifyLocation.getText().toString());
            dto.setB_title(modifyTitle.getText().toString());
            dto.setB_coment(modifyComent.getText().toString());
            dto.setB_latitude(latitude);
            dto.setB_longitude(longitude);

            AsyncBoardModify asyncBoardModify = new AsyncBoardModify(ModifyActivity.this, dto);
            try { // 수정된 정보를 가져옴
                result = new AsyncBoardModify(ModifyActivity.this, dto).execute().get();
                Log.i("=======result", result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //뒤로가기 눌렀을시 홈화면으로 전환
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
