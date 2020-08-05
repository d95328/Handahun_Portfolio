package com.example.moment.activity.board;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.activity.user.LoginActivity;
import com.example.moment.adapter.BoardMyListAdapter;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity implements BoardMyListAdapter.RecyclerViewClickListener {

    String url = Common.SERVER_URL + "/MyBoardListAction.mo";
    SharedPreferences sharedPreferences;
    TextView noListText;

    private RecyclerView mRecyclerView;
    private BoardMyListAdapter myBoardMyListAdapter;
    private ArrayList<Board> mBoardList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_board_list);

        Toolbar toolbar = findViewById(R.id.toolbar_myAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("내 게시물");

        mRecyclerView = findViewById(R.id.myListRv);
        mRecyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        noListText = findViewById(R.id.noListTextView);

        try {
            mBoardList = new AsyncBoardList(MyListActivity.this).execute(url, sharedPreferences.getString("u_userid","")).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mBoardList.size() > 0) {
            noListText.setVisibility(View.INVISIBLE);
            myBoardMyListAdapter = new BoardMyListAdapter(mBoardList);
            myBoardMyListAdapter.setOnClickListener(this);
            mRecyclerView.setAdapter(myBoardMyListAdapter);

            DefaultItemAnimator animator = new DefaultItemAnimator();
            animator.setAddDuration(1000);
            animator.setRemoveDuration(1000);
            animator.setMoveDuration(1000);
            animator.setChangeDuration(1000);
            mRecyclerView.setItemAnimator(animator);
        } else {
            noListText.setVisibility(View.VISIBLE);
        }

        //BottomNavigationView 처리
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomAppBar_mylist);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = null;
                if(id == R.id.bottom_home) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if(id == R.id.bottom_mylist){
                    alert("내 게시물 페이지입니다.");
                }else if(id == R.id.bottom_favorite){
                    if(sharedPreferences.getString("u_userid", "").equals("")){
                        dialog();
                    } else {
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });


    }//end of onCreate

    //뒤로가기 눌렀을시 홈화면으로 전환
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("b_no", mBoardList.get(position).getB_no());
        intent.putExtra("Activity", MainActivity.class);
        startActivity(intent);
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
