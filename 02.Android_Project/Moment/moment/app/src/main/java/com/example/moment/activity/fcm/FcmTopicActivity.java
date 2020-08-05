package com.example.moment.activity.fcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.moment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FcmTopicActivity extends AppCompatActivity {

    Toolbar toolbar;
    private String TAG = "Fcmalarm_activity";
    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_topic);
        setTitle("알림 설정");

        pref = getSharedPreferences("fcmsetting", MODE_PRIVATE);
        editor = pref.edit();
        Switch gwangju = findViewById(R.id.gwangju);
        Switch seoul = findViewById(R.id.seoul);
        Switch busan = findViewById(R.id.busan);
        Switch kyungki = findViewById(R.id.kyungki);
        Switch kangwon = findViewById(R.id.kangwon);
        Switch chungchung = findViewById(R.id.chungchung);
        Switch junra = findViewById(R.id.junra);
        Switch kyungsang = findViewById(R.id.kyungsang);
        Switch jeju = findViewById(R.id.jeju);

        gwangju.setChecked(pref.getBoolean("gwangju",false));
        seoul.setChecked(pref.getBoolean("seoul",false));
        busan.setChecked(pref.getBoolean("busan",false));
        kyungki.setChecked(pref.getBoolean("kyungki",false));
        kangwon.setChecked(pref.getBoolean("kangwon",false));
        chungchung.setChecked(pref.getBoolean("chungchung",false));
        junra.setChecked(pref.getBoolean("junra",false));
        kyungsang.setChecked(pref.getBoolean("kyungsang",false));
        jeju.setChecked(pref.getBoolean("jeju",false));


        //툴바생성 & 뒤로가기버튼
        toolbar = findViewById(R.id.toolbar_mainAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //주제 스위치
        gwangju.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("gwangju",true);
                    editor.commit();
                    FirebaseMessaging.getInstance().subscribeToTopic("gwangju")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "광주 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "광주구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else {
                    Log.d(TAG,"여기 체크해제" );
                    editor.putBoolean("gwangju",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("gwangju");

                }
            }
        });

        seoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("seoul",true);
                    editor.commit();
                    FirebaseMessaging.getInstance().subscribeToTopic("seoul")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "서울 구독";

                                    if (!task.isSuccessful()) {
                                        msg ="서울구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("seoul",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("seoul");

                }
            }
        });

        busan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("busan",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("busan")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "부산 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "부산 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("busan",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("busan");

                }
            }
        });

        kyungki.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("kyungki",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("kyungki")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "경기도 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "경기도 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("kyungki",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("kyungki");

                }
            }
        });
         kangwon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("kangwon",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("kangwon")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "강원도 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "강원도 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("kangwon",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("kangwon");

                }
            }
        });
        chungchung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("chungchung",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("chungchung")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "충청도 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "충청도 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("chungchung",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("chungchung");

                }
            }
        });
        junra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("junra",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("junra")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "전라도 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "전라도 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("junra",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("junra");

                }
            }
        });
        kyungsang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("kyungsang",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("kyungsang")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "경상도 구독";

                                    if (!task.isSuccessful()) {
                                        msg = " 경상도 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("kyungsang",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("kyungsang");

                }
            }
        });
        jeju.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("jeju",true);

                    editor.commit();

                    FirebaseMessaging.getInstance().subscribeToTopic("jeju")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "제주도 구독";

                                    if (!task.isSuccessful()) {
                                        msg = "제주도 구독 실패";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(FcmTopicActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    editor.putBoolean("jeju",false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("jeju");

                }
            }
        });


    }









    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
