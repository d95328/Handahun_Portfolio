package com.example.moment.activity.board;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.adapter.BoardMyListAdapter;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements BoardMyListAdapter.RecyclerViewClickListener{

    String url = Common.SERVER_URL + "/MyFavoritesListAction.mo";
    SharedPreferences sharedPreferences;
    TextView noListText;

    private RecyclerView mRecyclerView;
    private BoardMyListAdapter myBoardMyListAdapter;
    private ArrayList<Board> mBoardList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar_myAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("즐겨찾기");

        mRecyclerView = findViewById(R.id.favRv);
        mRecyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        noListText = findViewById(R.id.noListTextView_fav);

        try {
            mBoardList = new AsyncBoardList(FavoriteActivity.this).execute(url, sharedPreferences.getString("u_userid","")).get();
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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomAppBar_fav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = null;

                if(id == R.id.bottom_home) {
                    if(getApplicationContext() instanceof MainActivity) {
                        alert("메인 페이지 입니다.");
                        return false;
                    } else {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }else if(id == R.id.bottom_mylist){
                    if(getApplicationContext() instanceof MyListActivity) {
                        alert("현재 페이지입니다.");
                        return false;
                    } else {
                        intent = new Intent(getApplicationContext(), MyListActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }else if(id == R.id.bottom_favorite){
                    if(getApplicationContext() instanceof FavoriteActivity) {
                        alert("현재 페이지입니다.");
                        return false;
                    } else {
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
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

}//end of class

