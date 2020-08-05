package com.example.moment.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.moment.BuildConfig;
import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.model.User;
import com.example.moment.trancefer.AsyncUserModify;
import com.example.moment.trancefer.AsyncUserNickDuplicateCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyActivity extends AppCompatActivity {

    TextView uModify_inline_userid;
    TextView uModify_inline_usernick;
    TextView uModify_inline_userpw;
    TextView uModify_inline_userpwc;
    TextView uModify_inline_userlocal;

    EditText uModify_usernick;
    EditText uModify_userpw;
    EditText uModify_userpwc;
    Spinner uModify_userlocal;
    CircleImageView uModify_userProfileImg;
    TextView uModify_userid;
    Button uModify_cancelbtn;
    TextView uModify_modifytext;
    String uModify_user_local = "";


    String validnick = "yes", validpw, validpwc;
    String shared_id, shared_local, shared_nick, shared_profileimg;
    User dto;
    String result;

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_TAKE_PHOTO = 111;
    private static final int REQUEST_TAKE_ALBUM = 222;
    private String mCurrentPhotoPath;
    Uri imageUri;
    int reqWidth;
    int reqHeight;

    Toolbar toolbar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    File filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);

        setTitle("");
        //툴바생성 & 뒤로가기버튼
        toolbar = findViewById(R.id.toolbar_mainAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uModify_inline_userid = findViewById(R.id.uModify_inline_userid);
        uModify_inline_userpw = findViewById(R.id.uModify_inline_userpw);
        uModify_inline_userpwc = findViewById(R.id.uModify_inline_userpwc);
        uModify_inline_usernick = findViewById(R.id.uModify_inline_usernick);

        uModify_usernick = findViewById(R.id.uModify_usernick);
        uModify_userpw = findViewById(R.id.uModify_userpw);
        uModify_userpwc = findViewById(R.id.uModify_userpwc);
        uModify_userlocal = findViewById(R.id.uModify_userlocal);
        uModify_userid = findViewById(R.id.uModify_userid);

        uModify_userProfileImg = findViewById(R.id.uModify_userProfileImg);

        uModify_modifytext = findViewById(R.id.uModify_modifytext);



        /*SharedPreferences*/
        SharedPreferences info = getSharedPreferences("userInfo", MODE_PRIVATE);
        shared_id = info.getString("u_userid", "");
        shared_local = info.getString("u_local", "");
        shared_nick = info.getString("u_nick", "");
        shared_profileimg = info.getString("u_profileimg", "");

        Log.i("u_profileimg", shared_profileimg);


        uModify_userid.setText(shared_id);
        uModify_usernick.setText(shared_nick);


        Glide
                .with(this)
                .asBitmap()
                .load(shared_profileimg)
                .error(R.drawable.camera_icon)
                .override(100, 100)
                .into(uModify_userProfileImg);


        //닉네임 체크
        uModify_usernick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (validnick()) { //공백 검사 후
                    if(uModify_usernick.getText().toString().equals(shared_nick)) {
                        uModify_inline_usernick.setText("사용하실 수 있는 닉네임 입니다");
                        uModify_inline_usernick.setTextColor(Color.parseColor("#206108"));
                    }else{
                        if (!Pattern.matches("^[가-힣A-Za-z0-9]{2,12}$", uModify_usernick.getText().toString())) {
                            uModify_inline_usernick.setText("닉네임은 한글,영어,숫자만 가능합니다");
                            uModify_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
                            return;
                        }

                        dto = new User();
                        dto.setU_nick(uModify_usernick.getText().toString());

                        try {
                            result = new AsyncUserNickDuplicateCheck(ModifyActivity.this, dto).execute().get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (result.equals("fail")) {
                            uModify_inline_usernick.setText("중복된 닉네임입니다");
                            uModify_inline_usernick.setTextColor(Color.parseColor("#FF0000"));

                        } else {
                            uModify_inline_usernick.setText("사용하실 수 있는 닉네임 입니다");
                            uModify_inline_usernick.setTextColor(Color.parseColor("#206108"));
                        }

                    }

                }


            }

        });

        //비밀번호 체크
        uModify_userpw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (validpw()) {
                    uModify_inline_userpw.setText("사용하실 수 있는 비밀번호입니다");
                    uModify_inline_userpw.setTextColor(Color.parseColor("#206108"));
                }
            }
        });



        /*비밀번호 확인*/
        uModify_userpwc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(validpwc()){
                    uModify_inline_userpwc.setText("비밀번호가 일치합니다");
                    uModify_inline_userpwc.setTextColor(Color.parseColor("#206108"));
                }
                return false;
            }


        });




        /* 지역 스피너 -------------------------------------------------------------------*/

        String locallist[] = {"지역선택", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "제주특별자치도",
                "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도"};


        // ArrayAapter
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, locallist);
        uModify_userlocal.setAdapter(spinnerAdapter);

        //event listener
        uModify_userlocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                uModify_user_local = uModify_userlocal.getSelectedItem().toString();
                Log.i("local----", uModify_user_local);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < locallist.length; i++ ){
            uModify_userlocal.setSelection(i);
            if(locallist[i].equals(shared_local)){
                break;
            }
        }




        /* 스피너 끝-------------------------------------------------------------------*/


        uModify_modifytext.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                if (final_valid()) {
                    Log.i("v------------", validpw + validpwc);


                    User dto = new User();
                    dto.setU_userid(shared_id);
                    dto.setU_nick(uModify_usernick.getText().toString());
                    dto.setU_userpw(uModify_userpw.getText().toString());

                    if (mCurrentPhotoPath == null) { //프로필수정안했을때
                        dto.setU_profileimg(shared_profileimg);
                    } else { //프로필수정했을때
                        dto.setU_profileimg(mCurrentPhotoPath);

                    }

                    // 지역 수정 안했을때
                    if (uModify_user_local.equals("지역선택")) {
                        dto.setU_local(shared_local);
                    } else { // 수정 했을때
                        dto.setU_local(uModify_user_local);
                    }

                    // AsyncUserModify asyncUserModify = new AsyncUserModify(ModifyActivity.this, dto);

                    try { // 수정된 정보의 결과값 가져옴
                        result = new AsyncUserModify(ModifyActivity.this, dto).execute(mCurrentPhotoPath).get();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (result.equals("success")) { // 회원정보수정 성공시 ------> 로그아웃되서 로그인으로 이동
                        Intent intent = new Intent(ModifyActivity.this, LoginActivity.class);
                        Log.i("modify수정 결과", result);

                        startActivity(intent);
                        finish();
                        Toast.makeText(ModifyActivity.this, "회원정보 수정", Toast.LENGTH_SHORT).show();

                    }

                } else { //유효성 검사가 틀리면
                    Toast.makeText(ModifyActivity.this, "수정 내용을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                    if (validpw != "yes") {
                        uModify_userpw.clearFocus();
                    } else {
                        uModify_userpwc.clearFocus();
                    }

                }
            }
        }); //정보수정 버튼




    }


    public boolean validnick() { /*닉네임 유효성 체크*/

        if (uModify_usernick.getText().toString().trim().length() == 0) {
            uModify_inline_usernick.setText("닉네임을 입력해주세요");
            uModify_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        return true;
    }

    public boolean validpw() { /*비밀번호 체크*/

        if (uModify_userpw.getText().toString().trim().length() == 0) {
            uModify_inline_userpw.setText("비밀번호를 입력해주세요");
            uModify_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", uModify_userpw.getText().toString())) {
            uModify_inline_userpw.setText("비밀번호 형식은 최소 8 자, 최소 하나의 영문 및 하나의 숫자");
            uModify_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        return true;
    }



    public boolean validpwc(){

        if (uModify_userpwc.getText().toString().trim().length() == 0) {
            uModify_inline_userpwc.setText("비밀번호 확인을 다시 입력해주세요");
            uModify_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (!uModify_userpwc.getText().toString().equals(uModify_userpw.getText().toString())) {
            uModify_inline_userpwc.setText("비밀번호가 서로 일치하지 않습니다");
            uModify_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;
    }






    public boolean final_valid() {


        if (uModify_usernick.getText().toString().trim().length() == 0) {
            uModify_inline_usernick.setText("닉네임을 입력해주세요");
            uModify_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[가-힣A-Za-z0-9]{2,12}$", uModify_usernick.getText().toString())) {
            uModify_inline_usernick.setText("닉네임은 한글,영어,숫자만 가능합니다");
            uModify_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(uModify_usernick.getText().toString().equals(shared_nick)){
            uModify_inline_usernick.setText("사용하실 수 있는 닉네임 입니다");
            uModify_inline_usernick.setTextColor(Color.parseColor("#206108"));

        }else{
            if (Pattern.matches("^[가-힣A-Za-z0-9]{2,12}$", uModify_usernick.getText().toString())) {
                dto = new User();
                dto.setU_nick(uModify_usernick.getText().toString());

                try {
                    result = new AsyncUserNickDuplicateCheck(ModifyActivity.this, dto).execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result.equals("fail")) {
                    uModify_inline_usernick.setText("중복된 닉네임입니다");
                    uModify_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
                    return false;
                } else {
                    uModify_inline_usernick.setText("사용하실 수 있는 닉네임 입니다");
                    uModify_inline_usernick.setTextColor(Color.parseColor("#206108"));
                }
            }

        }





        if (uModify_userpw.getText().toString().trim().length() == 0) {
            uModify_inline_userpw.setText("비밀번호를 입력해주세요");
            uModify_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", uModify_userpw.getText().toString())) {
            uModify_inline_userpw.setText("비밀번호 형식은 최소 8 자, 최소 하나의 영문 및 하나의 숫자");
            uModify_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (uModify_userpwc.getText().toString().trim().length() == 0) {
            uModify_inline_userpwc.setText("비밀번호 확인을 다시 입력해주세요");
            uModify_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (!uModify_userpwc.getText().toString().equals(uModify_userpw.getText().toString())) {
            uModify_inline_userpwc.setText("비밀번호가 서로 일치하지 않습니다");
            uModify_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;

    }

















    // 프로필 사진 클릭시
    public void onClick(View v) {
        if (v.getId() == R.id.uModify_userProfileImg) {
            PopupMenu menu = new PopupMenu(getApplication(), v);
            getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.camera:
                            captureCamera();
                            break;
                        case R.id.gallery:
                            getAlbum();
                            break;
                    }
                    return true;
                }
            });
            menu.show();

        }
    }

    private void captureCamera() {
        String state = Environment.getExternalStorageState(); //외부 저장소(SD카드)의 최상위 경로를 반환

        if (Environment.MEDIA_MOUNTED.equals(state)) { // 읽고 쓰기가 가능한 상태
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();  //파일이름,경로 리턴 받음
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (photoFile != null) {
                        Log.i("getPackageName() ===> ", getPackageName());
                        Uri providerURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                        // 앱과 앱사이의 안전한 파일 공유를 가능케 하는 ContentProvider의 하위 클래스 , file:// -> content://
                        imageUri = providerURI;
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);  // 인텐트 provider한 uri
                    }
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        } else {
            alert("SD카드 접근 불가능 합니다.");
            return;
        }
    }


    private void getAlbum() {
        Intent getAlbumIntent = new Intent(Intent.ACTION_PICK);

        getAlbumIntent.setType("image/*");
        getAlbumIntent.putExtra(getAlbumIntent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(getAlbumIntent.createChooser(getAlbumIntent, "Select Picture"), REQUEST_TAKE_ALBUM);
    }


    public File createImageFile() throws IOException {
        String imgFileName = System.currentTimeMillis() + ".jpg"; //이미지 파일 이름
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/myApp"); //디렉터리

        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림", "storageDir 존재함 " + storageDir.toString());

        filePath = new File(storageDir, imgFileName);  // File 파일 변수 = new File(파일경로, 파일이름);
        mCurrentPhotoPath = filePath.getAbsolutePath(); //내부 경로로 지정

        return filePath;  // 파일경로, 파일이름 리턴
    }


    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            galleryAddPic();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try {
                InputStream in = new FileInputStream(filePath);
                BitmapFactory.decodeStream(in, null, options);
                in.close();
                in = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

            int inSampleSize = calulateInSampleSize();

            BitmapFactory.Options imgOptions = new BitmapFactory.Options();

            imgOptions.inSampleSize = inSampleSize;

            Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);

            ExifInterface exif = null;

            try {
                exif = new ExifInterface(filePath.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            uModify_userProfileImg.setImageBitmap(rotate(bitmap, exifDegree));
        }

        if (requestCode == REQUEST_TAKE_ALBUM && resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(imageUri);

                Glide
                        .with(this)
                        .asBitmap()
                        .load(mCurrentPhotoPath)
                        .override(100, 100)
                        .into(uModify_userProfileImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
    }


    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            column_index = cursor.getColumnIndex(proj[0]);
        }
        return cursor.getString(column_index);
    }


    private int calulateInSampleSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthtRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthtRatio ? heightRatio : widthtRatio;
        }

        return inSampleSize;
    }


    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    //뒤로가기 눌렀을시 홈화면으로 전환
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

}