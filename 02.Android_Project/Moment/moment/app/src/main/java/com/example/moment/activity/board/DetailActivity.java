package com.example.moment.activity.board;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.activity.gmap.DetailGoogleMapsActivity;
import com.example.moment.activity.user.LoginActivity;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardDelete;
import com.example.moment.trancefer.AsyncBoardDetail;
import com.example.moment.trancefer.AsyncFavorite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    Board dto;

    ImageView detailImageView;
    ImageView detailProfile;
    ImageView detailHeartEmpty;
    ImageView detailHeartFill;
    ImageView detailStarEmpty;
    ImageView detailStarFill;

    TextView detailNick;
    TextView detailLocation;
    TextView detailTitle;
    TextView detailComent;
    TextView detailDdabongCnt;

    FrameLayout detailDdabong;
    FrameLayout detailFavorite;
    LinearLayout detailMenu;

    //파노라마뷰 버튼 활성화
    LinearLayout panorama_button_view;
    Button detail_panorama_button;
    View panorama_button_bar;

    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        //툴바생성 & 뒤로가기버튼
        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("게시물");

        //SharedPreferences
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        final Intent intent = getIntent();
        String no = String.valueOf(intent.getIntExtra("b_no",0));
        Log.i("no =====>", no);
        detailProfile = findViewById(R.id.detailProfile);
        detailNick = findViewById(R.id.detailNick);
        detailLocation = findViewById(R.id.detailLocal);
        detailTitle = findViewById(R.id.detailTitle);
        detailComent = findViewById(R.id.detailComent);
        detailImageView = findViewById(R.id.detailImageView);
        //추천&즐겨찾기 컨트롤
        detailDdabong = findViewById(R.id.detailDdabong);
        detailDdabongCnt = findViewById(R.id.detailDdabongCnt);
        detailFavorite = findViewById(R.id.detailFavorite);
        detailHeartEmpty = findViewById(R.id.detailHeartEmpty);
        detailHeartFill = findViewById(R.id.detailHeartFill);
        detailStarEmpty = findViewById(R.id.detailStarEmpty);
        detailStarFill = findViewById(R.id.detailStarFill);

        //파노라마뷰 버튼
        panorama_button_view = findViewById(R.id.panorama_button_view);
        panorama_button_bar = findViewById(R.id.panorama_button_bar);
        detail_panorama_button = findViewById(R.id.detail_panorama_button);

        //Detail 정보 불러오기
        try {
            //유저 아이디를 받아옴.
            String b_userid = sharedPreferences.getString("u_userid",null);

            //유저 정보가 있을때
            if(b_userid != null){
                //추천, 즐겨찾기 정보를 같이 불러온다
                dto = new AsyncBoardDetail(DetailActivity.this).execute(no, b_userid).get();
            }else{
                //유저 정보가 없을때.. 게시판 정보만 불러온다
                dto = new AsyncBoardDetail(DetailActivity.this).execute(no, "null").get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        detailNick.setText(dto.getB_nick());
        detailLocation.setText(dto.getB_local());
        detailTitle.setText(dto.getB_title());
        detailComent.setText(dto.getB_coment());
        detailDdabongCnt.setText(dto.getB_ddabong() + "명");


        //이미지 넣어주기
        Glide
            .with(this)
            .load(dto.getB_imgpath())
            .into(detailImageView);
        Log.i("detailimg path",dto.getB_imgpath());
        //프로필사진 glide
        Glide
                .with(this)
                .load(dto.getB_profileimg())
                .into(detailProfile);

        //구글 맵 버튼
        detailLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, DetailGoogleMapsActivity.class);
                intent.putExtra("dto",dto);
                startActivity(intent);
            }
        });

        //BottomNavigationView 처리
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomAppBar_detail);
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

        //추천 즐찾 N이면 안함 Y이면 했었음 (테스트
        final Board testBoard = new Board();

        //글 상세 딱 들어왔을때 추천버튼 표시처리
        if(dto.getF_ddabong().equals("N")) {
            //추천 이력 없으면!
            setEmptyHeart(detailHeartEmpty, detailHeartFill);
        } else if (dto.getF_ddabong().equals("Y")) {
            //추천을 누른 이력이 있으면!
            setFillHeart(detailHeartEmpty, detailHeartFill);
        }

        //추천 & 즐찾 두개는 로그인이 안되어있으면 아예 버튼 안보이게!
        String b_userid = sharedPreferences.getString("u_userid",null);

        //추천버튼(framelayout) 클릭 이벤트
        if(b_userid != null) {
            detailDdabong.setVisibility(View.VISIBLE);
            detailDdabong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dto.getF_ddabong().equals("N")) {
                        setFillHeart(detailHeartEmpty, detailHeartFill);
                        dto.setF_ddabong("Y");
                        new AsyncFavorite(DetailActivity.this).execute(setFavoriteData());
                        alert("추천하셨습니다.");
                    } else if (dto.getF_ddabong().equals("Y")) {
                        setEmptyHeart(detailHeartEmpty, detailHeartFill);
                        dto.setF_ddabong("N");
                        new AsyncFavorite(DetailActivity.this).execute(setFavoriteData());
                        alert("추천을 취소하셨습니다.");
                    }
                }
            });
        } else if (b_userid == null) {
            //비로그인 상황이면 (로그인이 필요한 서비스입니다. 띄우고 로그인화면으로 갈지안갈지)
            detailDdabong.setVisibility(View.VISIBLE);
            setEmptyHeart(detailHeartEmpty, detailHeartFill);
            detailDdabong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog();
                }
            });
        }

        //글 상세 들어왔을때 즐겨찾기 버튼 표시처리
        if(dto.getF_favorites().equals("N")) {
            //로그인된 사용자의 즐겨찾기 목록에 없으면!
            setEmptyStar(detailStarEmpty, detailStarFill);
        } else if(dto.getF_favorites().equals("Y")) {
            //즐겨찾기 목록에 있으면!
            setFillStar(detailStarEmpty, detailStarFill);
        }

        if( b_userid != null) {
            detailFavorite.setVisibility(View.VISIBLE);
            //즐겨찾기버튼(framelayout) 클릭 이벤트
            detailFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dto.getF_favorites().equals("N")) {
                        setFillStar(detailStarEmpty, detailStarFill);
                        dto.setF_favorites("Y");
                        new AsyncFavorite(DetailActivity.this).execute(setFavoriteData());
                        alert("즐겨찾기에 추가되었습니다.");
                    } else if (dto.getF_favorites().equals("Y")) {
                        setEmptyStar(detailStarEmpty, detailStarFill);
                        dto.setF_favorites("N");
                        new AsyncFavorite(DetailActivity.this).execute(setFavoriteData());
                        alert("즐겨찾기를 취소하셨습니다.");
                    }
                }
            });
        } else if( b_userid == null) {
            detailFavorite.setVisibility(View.INVISIBLE);
        }
        //점 메뉴 생성
        detailMenu = findViewById(R.id.detailMenu);

        //로그인 정보가 있으면 보이게, 없으면 안보이게
        if( b_userid != null) {
            //로그인상황이면
            detailMenu.setVisibility(View.VISIBLE);
            //작성자가 클릭시 & 작성자가 아닌사람이 클릭시 (id비교 후 리스트변경
            if( b_userid.equals(dto.getB_userid()) ) {
                //작성자가 버튼을 클릭했을 시에 (수정, 삭제 리스트가 나와야함)
                detailMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        printDialog("수정","삭제");
                    }
                }); //end of Menu btn Listener
            } else if ( !b_userid.equals(dto.getB_userid()) ) {
                //작성자가 아닌 사람이 클릭했을 시에 (신고? 버튼 나와야함)
                detailMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       printDialog("신고");
                    }
                }); //end of Menu btn Listener
            }

        } else if ( b_userid == null) {
            //비로그인 상황시 메뉴 아예 안보이게!
            detailMenu.setVisibility(View.GONE);
        }

        //파노라마 사진이라면 파노라마 뷰 활성화
        if(intent.getStringExtra("b_pano").equals("Y")){
            panorama_button_view.setVisibility(View.VISIBLE);
            panorama_button_bar.setVisibility(View.VISIBLE);
        }
        //파노라마 뷰로 보기!
        detail_panorama_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pano360 = new Intent(DetailActivity.this, DetailPanoViewActivity.class);
                pano360.putExtra("imgpath",dto.getB_imgpath());
                startActivity(pano360);
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

    //추천버튼 & 즐겨찾기버튼 Visibility 메소드
    public void setEmptyHeart (ImageView img1, ImageView img2) {
        Glide
                .with(this)
                .load(R.drawable.heart_empty)
                .into(detailHeartEmpty);

        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.INVISIBLE);
    }

    public void setFillHeart (ImageView img1, ImageView img2) {
        Glide
                .with(this)
                .load(R.drawable.heart_fill)
                .into(detailHeartFill);

        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.VISIBLE);
    }

    public void setEmptyStar (ImageView img1, ImageView img2) {
        Glide
                .with(this)
                .load(R.drawable.star_empty)
                .into(detailStarEmpty);
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.INVISIBLE);
    }

    public void setFillStar (ImageView img1, ImageView img2) {
        Glide
                .with(this)
                .load(R.drawable.star_fill)
                .into(detailStarFill);
        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.VISIBLE);
    }

    //추천 즐겨찾기 정보 통신
    public String setFavoriteData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("u_userid", sharedPreferences.getString("u_userid",null));
            jsonObject.put("b_no", dto.getB_no());
            jsonObject.put("f_ddabong", dto.getF_ddabong());
            jsonObject.put("f_favorites", dto.getF_favorites());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    //다이얼로그 메소드
    public void printDialog(String... parms) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this, R.style.AlertDialogStyle);
        final String param[] = parms;

        builder.setItems(param, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(param.length > 1) {
                    if (item == 0) {
                        //첫번째 메뉴 수정 클릭시 이벤트
                        Intent intent = new Intent(DetailActivity.this, ModifyActivity.class);
                        intent.putExtra("b_no", dto.getB_no());
                        startActivity(intent);
                    } else if (item == 1) {
                        //두번째 메뉴 삭제 클릭시 이벤트 (삭제하시겠습니까? 다이얼로그 띄우기)
                        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(DetailActivity.this, R.style.AlertDialogStyle);
                        deleteBuilder.setMessage("게시물을 삭제하시겠습니까?");
                        deleteBuilder
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    final Intent intent = getIntent();
                                    String no = String.valueOf(intent.getIntExtra("b_no",0));
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //AsyncTask 처리
                                        try {
                                            String result = new AsyncBoardDelete(DetailActivity.this, no).execute().get();
                                            Log.i("삭제결과>>>>>>>>>>", result);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })//확인버튼 이벤트 마무리
                                .setNegativeButton("취소", null)
                                .create();
                        AlertDialog deleteDialog = deleteBuilder.show();
                        TextView textView = deleteDialog.findViewById(android.R.id.message);
                        textView.setTextSize(20.0f);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                } else if (param.length < 2) {
                    if (item == 0) {
                        //신고버튼 클릭시 이벤트
                    }
                }
                dialog.dismiss();
            }
        }); //end of item Listener
        builder.show();
    }//end of printDialog

    //토스트 메소드
    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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


