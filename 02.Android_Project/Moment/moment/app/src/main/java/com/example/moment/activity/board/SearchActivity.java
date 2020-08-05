package com.example.moment.activity.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.activity.user.LoginActivity;
import com.example.moment.adapter.BoardSearchListAdapter;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchActivity extends AppCompatActivity implements BoardSearchListAdapter.RecyclerViewClickListener {

    String url = Common.SERVER_URL + "/SearchBoardListAction.mo";

    Spinner searchSpinner;
    TextView searchNoResult;
    String choice;

    SharedPreferences sharedPreferences;

    //리사이클러 어댑터
    private RecyclerView sRecyclerView;
    private BoardSearchListAdapter searchListAdapter;
    private ArrayList<Board> sBoardList = new ArrayList<>();
    LinearLayout searchRvLayout;

    //검색시 사용하는 어댑터
    private ArrayAdapter<String> adapter;
    ListView searchResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_search);
        setTitle("");

        //툴바생성 & 뒤로가기버튼
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //메인에서 넘겨온 게시글리스트 배열 받기
        Intent intent = getIntent();
        final ArrayList<Board> board = intent.getParcelableArrayListExtra("list");
        Log.i("list>>>", board.get(0).toString() + board.get(1).toString() );

        //리사이클러뷰
        searchRvLayout = findViewById(R.id.searchRvLayout);
        sRecyclerView = findViewById(R.id.searchListRv);
        sRecyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sRecyclerView.setLayoutManager(layoutManager);

        //SearchView 생성 및 리스트뷰 & 어댑터 생성
        searchSpinner = findViewById(R.id.searchSpinner);
        searchNoResult = findViewById(R.id.searchNoResult);
        searchResultList = findViewById(R.id.searchResultList);

        final SearchView searchView = findViewById(R.id.searchBox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //검색onkey될때마다 쿼리처리되려면 이 콜백 메소드를 사용해야함. 위에 Submit 메소드는 키보드에서 검색버튼 터치했을때만 반응!
                String boxText = searchView.getQuery().toString();
                if(boxText.equals("")) {
                    //searchView에 사용자가 아무것도 입력하지 않았을 경우
                    searchNoResult.setVisibility(View.VISIBLE);
                    searchResultList.setVisibility(View.GONE);
//                    sRecyclerView.setVisibility(View.GONE);
                    searchRvLayout.setVisibility(View.GONE);

                } else if(!boxText.equals("")){
                    //searchView에 사용자가 입력이 시작되면
                    searchNoResult.setVisibility(View.GONE);
                    searchResultList.setVisibility(View.VISIBLE);
//                    sRecyclerView.setVisibility(View.GONE);
                    searchRvLayout.setVisibility(View.GONE);

                    choice = searchSpinner.getSelectedItem().toString();
                    adapter = new ArrayAdapter(SearchActivity.this, R.layout.items_search_resultlist, search(newText, choice, board));
                    searchResultList.setAdapter(adapter);
                }
                return true;
            }
        });

        //검색결과로 출력된 리스트뷰에서 사용자가 select 했을 시에 처리 -> 해당
        searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchText = (String) searchResultList.getItemAtPosition(position);
                choice = searchSpinner.getSelectedItem().toString();

                try {
                    sBoardList = new AsyncBoardList(SearchActivity.this).execute(url, searchText, choice).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //검색어 결과가 있으면
                if(sBoardList.size() > 0) {
                    searchResultList.setVisibility(View.GONE);
                    searchNoResult.setVisibility(View.GONE);
//                    sRecyclerView.setVisibility(View.VISIBLE);
                    searchRvLayout.setVisibility(View.VISIBLE);

                    searchListAdapter = new BoardSearchListAdapter(sBoardList);
                    searchListAdapter.setOnClickListener(SearchActivity.this);
                    sRecyclerView.setAdapter(searchListAdapter);

                    DefaultItemAnimator animator = new DefaultItemAnimator();
                    animator.setAddDuration(1000);
                    animator.setRemoveDuration(1000);
                    animator.setMoveDuration(1000);
                    animator.setChangeDuration(1000);
                    sRecyclerView.setItemAnimator(animator);
                } else {
                    searchNoResult.setVisibility(View.VISIBLE);
                    searchResultList.setVisibility(View.GONE);
                    sRecyclerView.setVisibility(View.GONE);
                    searchRvLayout.setVisibility(View.GONE);
                }
            }
        });

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        //BottomNavigationView 처리
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomAppBar_search);
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
                    if(sharedPreferences.getString("u_userid", "").equals("")) {
                        dialog();
                    } else {
                        intent = new Intent(getApplicationContext(), MyListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
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


    //사용자가 검색한 검색어와 불러온 arraylist 안의 데이터와 일치 여부 판단하는 메소드
    private ArrayList<String> search(String query, String choice, ArrayList<Board> list) {
        //목록에서 spinner 에서 선택된 제목, 닉네임, 위치에 맞는 데이터만 담아주는 배열선언
        ArrayList<String> items = new ArrayList<>();
        //대조결과 검색어를 포함하는 데이터를 담아주는 배열 선언
        ArrayList<String> result = new ArrayList<>();
        //spinnner 가 제목일 경우
        if(choice.equals("제목")) {
            //for문을 돌려서 fragment 통신결과로 가져온 list 배열에서 title만 싹가져와서 items 배열에 담아준다. + 검색한 글자도! 포함된거 모두 검색할 수 있도록
            items.add(query);
            for (int i = 0; i < list.size(); i++) {
                items.add(list.get(i).getB_title());
                //검색어와 items배열에 담긴 데이터들과 사용자가 검색어로 쓴 text와 toLowerCase로 contains함수를 이용해서 비교후 위에 선언해준 result 배열에 담아주고
                //이 배열을 리턴해주면 완료!
                if(items.get(i).toLowerCase().contains(query.toLowerCase())) {
                    //중복제거! result 배열에 items에서 for문돌려 온 데이터가 이미 있으면 추가하지않음!
                    if( !result.contains(items.get(i)) ) {
                        result.add(items.get(i));
                    }
                }
            }
        } else if(choice.equals("닉네임")) {
            items.add(query);
            for (int i = 0; i < list.size(); i++) {
                items.add(list.get(i).getB_nick());
                if(items.get(i).toLowerCase().contains(query.toLowerCase())) {
                    if ( !result.contains(items.get(i)) ) {
                        result.add(items.get(i));
                    }
                }
            }
        } else if(choice.equals("위치")) {
            items.add(query);
            for (int i = 0; i < list.size(); i++) {
                items.add(list.get(i).getB_local());
                if(items.get(i).toLowerCase().contains(query.toLowerCase())) {
                    if( !result.contains(items.get(i)) ) {
                        result.add(items.get(i));
                    }
                }
            }
        }
        return result;
    }

    //뒤로가기 눌렀을시 홈화면으로 전환
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //토스트 메세지
    public void alert(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("b_no", sBoardList.get(position).getB_no());
        intent.putExtra("Activity", MainActivity.class);
        startActivity(intent);
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

}//end of class
