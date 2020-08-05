package com.example.moment.activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.moment.R;
import com.example.moment.activity.board.DetailActivity;
import com.example.moment.activity.board.FavoriteActivity;
import com.example.moment.activity.board.MyListActivity;
import com.example.moment.activity.board.SearchActivity;
import com.example.moment.activity.board.WriteActivity;
import com.example.moment.activity.fcm.FcmTopicActivity;
import com.example.moment.activity.iot.External_device;
import com.example.moment.activity.user.LoginActivity;
import com.example.moment.activity.user.ModifyActivity;
import com.example.moment.activity.user.SignUpActivity;
import com.example.moment.adapter.BoardSectionPagerAdapter;
import com.example.moment.fragment.LatelyListFragment;
import com.example.moment.model.Board;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LatelyListFragment.OnSearchResultListener {
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final String TAG = "MainActivity";

    private Animation floating_open;
    private Animation floating_close;
    private FloatingActionButton fab;
    private FloatingActionButton fabNoticeCamera;
    private FloatingActionButton fabNoticeStorage;
    private boolean isFabOpen = false;

    String[] permissions;
    BoardSectionPagerAdapter adapter;

    TabLayout tabLayout;
    ViewPager mViewPager;

    //SharedPreferences 테스트
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    //fragment 정보 담기위한 리스트객체
    ArrayList<Board> board = new ArrayList<>();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        //툴바생성 (main xml과 appbar xml 두개에 툴바가 선언되어있는데 메인액티비티xml에 선언된걸 가져와서 써야함!
        toolbar = findViewById(R.id.toolbar_mainAct);
        //Manifest <application> 안에 android:theme="@style/AppTheme.NoActionBar" 를 먼저 추가해줘야 이게 호출될때 이미 툴바가 호출되었다는 익셉션이 발생하지 않음! (30분이나잡아먹었네..)
        setSupportActionBar(toolbar);

        //DrawerLayout생성 (Drawer 뜻 = 서랍 -> 네비게이션바를 서랍이라고 생각하면됨 숨켜져있다가 누르면나오는)
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //네비게이션 헤더 텍스트 셋팅
        View nav_headerView = navigationView.getHeaderView(0);
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        ImageView nav_userImg = nav_headerView.findViewById(R.id.nav_userImg);
        TextView nav_userNick = nav_headerView.findViewById(R.id.nav_userNick);
        TextView nav_userId = nav_headerView.findViewById(R.id.nav_userId);

        if( sharedPreferences.getString("u_userid", "").equals("") ) {
            //로그인정보 없을 시
            nav_userImg.setImageResource(R.drawable.logo);
            nav_userNick.setText("로그인이 필요합니다.");
            nav_userId.setText("");

            navigationView.getMenu().setGroupVisible(R.id.noneLogin_menu,true);
            navigationView.getMenu().setGroupVisible(R.id.login_menu,false);
        } else if( !sharedPreferences.getString("u_userid", "").equals("") ){
            //로그인정보 있을 시
            Glide
                    .with(this)
                    .load(sharedPreferences.getString("u_profileimg",""))
                    .into(nav_userImg);
            nav_userNick.setText(sharedPreferences.getString("u_nick","") + " 님 환영합니다.");
            nav_userId.setText(sharedPreferences.getString("u_userid",""));

            navigationView.getMenu().setGroupVisible(R.id.noneLogin_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.login_menu,true);

            //nav_header_main header layout 클릭시 회원 수정으로 이동
            LinearLayout nav_userModify = nav_headerView.findViewById(R.id.nav_userModify);
            nav_userModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
                    startActivity(intent);
                }
            });

        }



        //BottomNavigationView 처리
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomAppBar_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = null;
                if(id == R.id.bottom_home) {
                    alert("메인 페이지 입니다.");
                    return false;
                } else if(id == R.id.bottom_mylist){
                    if(sharedPreferences.getString("u_userid", "").equals("")) {
                        dialog();
                    } else {
                        intent = new Intent(getApplicationContext(), MyListActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }else if(id == R.id.bottom_favorite){
                    if(sharedPreferences.getString("u_userid", "").equals("")){
                        dialog();
                    } else {
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });


        floating_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.floating_btn_open);
        floating_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.floating_btn_close);
        //플로팅 버튼 아이디값
        fab = findViewById(R.id.fab);
        fabNoticeCamera = findViewById(R.id.fabNoticeCamera);
        fabNoticeStorage = findViewById(R.id.fabNoticeStorage);

        //플로팅 버튼 --> 이너 클래스
        fab.setOnClickListener(new setOnclickListener());
        fabNoticeCamera.setOnClickListener(new setOnclickListener());
        fabNoticeStorage.setOnClickListener(new setOnclickListener());

        //어댑터와 연결
        mViewPager = findViewById(R.id.viewPager);


        //메인 페이지에 어댑터를 이용하여 프래그먼트를 끼워 넣어줌
        //액티비티에서 프래그먼트 매니저를 사용할 수 있게 해줌.
        adapter = new BoardSectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        permissions = new String[]{
                //외부저장소
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //카메라
                Manifest.permission.CAMERA,
                //장소
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        //Build.VERSION_CODES.M 마쉬멜로우 버젼
        //마쉬멜로우 버젼보다 크면..
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!hasPermissions(permissions)){
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = "Moment";
            String channelName = "Moment";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

    }// end of create

    //검색 인터페이스 메소드 구현(프래그먼트에 있는 리스트 정보 model을 담아주기 위해)
    @Override
    public void onSearchResultSet(ArrayList<Board> board) {
        this.board = board;
    }

    //검색창 클릭시 검색화면으로 이동
    public void boardSearchClick(View view) {
        Intent intent;
        intent = new Intent(this, SearchActivity.class);
        intent.putParcelableArrayListExtra("list", board);
        Log.i("ListSend >>> ", "list[0] : " + board.get(0).getB_title() + " / " + board.get(0).getB_coment());
        startActivity(intent);
    }

    //네비게이션 아이템 선택 액션
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        builder.setMessage("로그아웃 하시겠습니까?");

        if(id == R.id.item_myBoard) {
            Intent intent = new Intent(this, MyListActivity.class);
            startActivity(intent);
        } else if(id == R.id.item_signUp) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        } else if(id == R.id.item_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if(id == R.id.item_favorite) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        } else if(id == R.id.item_logout) {
            builder
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            alert("로그아웃 완료");
                            sharedEditor.remove("u_userid");
                            sharedEditor.commit();
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .create();
            AlertDialog dialog = builder.show();

            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setTextSize(20.0f);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else if(id == R.id.fcm_alarm) {
            Intent intent = new Intent(this, FcmTopicActivity.class);
            startActivity(intent);
        } else if(id == R.id.item_external){
            Intent intent = new Intent(this, External_device.class);
            startActivity(intent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //플로팅 버튼 액션
    private class setOnclickListener implements View.OnClickListener {
        Intent intent;
        @Override
        public void onClick(View v) {
            //로그인 정보가 있을 경우
            if(!sharedPreferences.getString("u_userid", "").equals("") ){
                if (v.getId() == R.id.fab) {
                    floatingAnim();
                    return;
                } else if (v.getId() == R.id.fabNoticeStorage) {
                    //외부 저장소 사용 글작성. write 키 이름으로 저장소인지 카메라인지 확인하여 인텐트
                    floatingAnim();
                    if(hasPermissions(permissions)){
                        intent = new Intent(MainActivity.this, WriteActivity.class);
                        intent.putExtra("write","storage");
                        startActivity(intent);
                    }

                    return;
                } else if (v.getId() == R.id.fabNoticeCamera) {
                    //카메라 사용하여 글 작성
                    floatingAnim();
                    if(hasPermissions(permissions)){
                        intent = new Intent(MainActivity.this, WriteActivity.class);
                        intent.putExtra("write","camera");
                        startActivity(intent);
                    }
                    return;
                }
            }else {
                //로그인 정보가 없을 경우
                dialog();
            }
        }
    }

    //플로팅 버튼 효과
    private void floatingAnim() {
        if (isFabOpen) {
            fabNoticeCamera.startAnimation(floating_close);
            fabNoticeStorage.startAnimation(floating_close);
            fabNoticeCamera.setClickable(false);
            fabNoticeStorage.setClickable(false);
            isFabOpen = false;
        } else {
            fabNoticeCamera.startAnimation(floating_open);
            fabNoticeStorage.startAnimation(floating_open);
            fabNoticeCamera.setClickable(true);
            fabNoticeStorage.setClickable(true);
            isFabOpen = true;
        }
    }

    //퍼미션 확인
    private boolean hasPermissions(String[] permissions){
        int result;
        for(String perms : permissions){
            //자기 스스로 퍼미션이 되어있는지 확인한다.
            result = ContextCompat.checkSelfPermission(this, perms);
            if(result == PackageManager.PERMISSION_DENIED){
                //허용이 안될 시 false
                return false;
            }
        }
        //허용이 될 시 true
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_CODE:
                //grantResults.length 사용자가 거부한 갯수
                if(grantResults.length > 0){
                    //어떤 것들을 허용했는지 체크
                    boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraExternalStorage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean findLocation = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseLocation = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    if(!readExternalStorage || !writeExternalStorage || !cameraExternalStorage||
                            !findLocation || !coarseLocation){
                        alert("글을 작성 하시려면 퍼미션을 허가 하셔야합니다.");
                    }
                }
                break;
        }
    }

    //토스트 메세지
    public void alert(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //다이얼로그 메소드
    public void dialog() {
        AlertDialog.Builder ddabongBuilder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        ddabongBuilder.setMessage("로그인이 필요한 서비스입니다.\n 로그인 화면으로 가시겠습니까?");
        ddabongBuilder
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })//확인버튼 이벤트 마무리
                .setNegativeButton("취소", null)
                .create();
        AlertDialog ddabongDialog = ddabongBuilder.show();
        TextView textView = ddabongDialog.findViewById(android.R.id.message);
        textView.setTextSize(20.0f);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }
}

