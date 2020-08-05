package com.example.moment.activity.user;

import android.Manifest;
import android.content.Intent;
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
import com.example.moment.trancefer.AsyncUserIdDuplicateCheck;
import com.example.moment.trancefer.AsyncUserNickDuplicateCheck;
import com.example.moment.trancefer.AsyncUserSignUp;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    TextView uSignup_inline_userid;
    TextView uSignup_inline_usernick;
    TextView uSignup_inline_userpw;
    TextView uSignup_inline_userpwc;
    TextView uSignup_inline_userlocal;
    TextView uSignup_inline_username;

    EditText uSignup_userid;
    EditText uSignup_usernick;
    EditText uSignup_username;
    EditText uSignup_userpw;
    EditText uSignup_userpwc;
    Spinner uSignup_userlocal;

    TextView uSignup_signtext;
    Button uSignup_cancelbtn;
    User dto = null;
    String result;
    Toolbar toolbar;


    String validid, validnick, validname, validpw, validpwc;
    String uSignup_user_local;
    String profildefaultimg = "profiledefault.png";
    /*======================카메라 부분==============================================*/
    private static final int PERMISSION_REQUEST_CODE = 1000;
    CircleImageView uSignup_userProfileImg;
    Button sendBtn;
    Uri imageUri;
    private static final int REQUEST_TAKE_PHOTO = 111;
    private static final int REQUEST_TAKE_ALBUM = 222;
    private String mCurrentPhotoPath;
    int reqWidth;
    int reqHeight;

    File filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);


        setTitle("");
        //툴바생성 & 뒤로가기버튼
        toolbar = findViewById(R.id.toolbar_mainAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uSignup_userid = findViewById(R.id.uSignup_userid);
        uSignup_usernick = findViewById(R.id.uSignup_usernick);
        uSignup_username = findViewById(R.id.uSignup_username);
        uSignup_userpw = findViewById(R.id.uSignup_userpw);
        uSignup_userpwc = findViewById(R.id.uSignup_userpwc);
        uSignup_userlocal = findViewById(R.id.uSignup_userlocal);

        uSignup_inline_userid = findViewById(R.id.uSignup_inline_userid);
        uSignup_inline_usernick = findViewById(R.id.uSignup_inline_usernick);
        uSignup_inline_userpw = findViewById(R.id.uSignup_inline_userpw);
        uSignup_inline_userpwc = findViewById(R.id.uSignup_inline_userpwc);
        uSignup_inline_username = findViewById(R.id.uSignup_inline_username);

        uSignup_signtext = findViewById(R.id.uSignup_signtext);
//        uSignup_cancelbtn = findViewById(R.id.uSignup_cancelbtn);

        uSignup_userProfileImg = findViewById(R.id.uSignup_userProfileImg);


        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);



        /*==================================================================================================*/


        /* 아이디 */
        uSignup_userid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (validid()) { // 공백, 패턴 검사 후  /* 아이디 중복 체크 */

                    dto = new User();
                    dto.setU_userid(uSignup_userid.getText().toString());

                    try {
                        result = new AsyncUserIdDuplicateCheck(SignUpActivity.this, dto).execute().get();
                        Log.i("result---", result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.equals("fail")) {
                        uSignup_inline_userid.setText("중복된 이메일입니다");
                        uSignup_inline_userid.setTextColor(Color.parseColor("#FF0000"));


                    } else {
                        uSignup_inline_userid.setText("사용하실 수 있는 아이디입니다");
                        uSignup_inline_userid.setTextColor(Color.parseColor("#206108"));
                    }

                }

            }
        });

        /* 닉네임 */
        uSignup_usernick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (validnick()) { //공백 검사 후
                    dto = new User();
                    dto.setU_nick(uSignup_usernick.getText().toString());

                    try {
                        result = new AsyncUserNickDuplicateCheck(SignUpActivity.this, dto).execute().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.equals("fail")) {
                        uSignup_inline_usernick.setText("중복된 닉네임입니다");
                        uSignup_inline_usernick.setTextColor(Color.parseColor("#FF0000"));

                    } else {
                        uSignup_inline_usernick.setText("사용하실 수 있는 닉네임입니다");
                        uSignup_inline_usernick.setTextColor(Color.parseColor("#206108"));
                    }
                }
            }
        });


        /* 이름 */
        uSignup_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (validname()) {
                    uSignup_inline_username.setText("사용하실 수 있는 이름입니다");
                    uSignup_inline_username.setTextColor(Color.parseColor("#206108"));
                }
            }
        });






        /*비밀번호 확인*/
        uSignup_userpw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(validpw()){
                    uSignup_inline_userpw.setText("사용하실 수 있는 비밀번호입니다");
                    uSignup_inline_userpw.setTextColor(Color.parseColor("#206108"));
                }

            }


        });


        /*비밀번호 확인*/
        uSignup_userpwc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(validpwc()){
                    uSignup_inline_userpwc.setText("비밀번호가 일치합니다");
                    uSignup_inline_userpwc.setTextColor(Color.parseColor("#206108"));
                }
                return false;
            }


        });

        /* ----------- 지역 스피너 ---------------------------------------------------------------------*/

        String locallist[] = {"지역선택", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "제주특별자치도",
                "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도"};


        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, locallist);
        uSignup_userlocal.setAdapter(spinnerAdapter);

        uSignup_userlocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uSignup_user_local = (String) uSignup_userlocal.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* ----------- 지역 스피너 끝---------------------------------------------------------------------*/



        /* 회원가입 버튼 클릭시 유효성 검사 -> asyncTask */
        uSignup_signtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(final_valid()){


                    dto = new User();
                    dto.setU_userid(uSignup_userid.getText().toString());
                    dto.setU_nick(uSignup_usernick.getText().toString());
                    dto.setU_name(uSignup_username.getText().toString());
                    dto.setU_userpw(uSignup_userpw.getText().toString());
                    dto.setU_local(uSignup_user_local);
                    dto.setU_profileimg(mCurrentPhotoPath);

                    if(mCurrentPhotoPath == null){ //프로필 안 넣었을때
                        dto.setU_profileimg(profildefaultimg);
                        Log.i("defaultprofileimg", profildefaultimg);
                    } else { //프로필 넣었을때
                        dto.setU_profileimg(mCurrentPhotoPath);

                    }


                    try {
                        result = new AsyncUserSignUp(SignUpActivity.this, dto).execute(mCurrentPhotoPath).get();
                        if (result.equals("success")) { //-------회원가입성공시 로그인화면
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {  // 유효성 체크가 false일 경우
                    Toast.makeText(SignUpActivity.this, "회원가입 작성을 다시한번 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });





    } //oncreate



    /*---------------------------------------------------------------------------------------------------------*/




    public boolean validid() { /*이메일 유효성 체크 */

        if (uSignup_userid.getText().toString().trim().length() == 0) {
            uSignup_inline_userid.setText("이메일을 입력해주세요");
            uSignup_inline_userid.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$", uSignup_userid.getText().toString())) {
            uSignup_inline_userid.setText("이메일 형식에 맞지 않습니다");
            uSignup_inline_userid.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        return true;
    }



    public boolean validnick() { /*닉네임 유효성 체크*/

        if (uSignup_usernick.getText().toString().trim().length() == 0) {
            uSignup_inline_usernick.setText("닉네임을 입력해주세요");
            uSignup_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[가-힣A-Za-z0-9]{2,12}$", uSignup_usernick.getText().toString())) {
            uSignup_inline_usernick.setText("닉네임은 영어,숫자,한글만 가능합니다");
            uSignup_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;
    }



    public boolean validname() { /*이름 유효성 체크 */

        if (uSignup_username.getText().toString().trim().length() == 0) {
            uSignup_inline_username.setText("이름을 입력해주세요");
            uSignup_inline_username.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[가-힣]{2,5}$", uSignup_username.getText().toString())) {
            uSignup_inline_username.setText("이름은 한글만 사용가능합니다");
            uSignup_inline_username.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;
    }

    public boolean validpw() { /*비밀번호 체크*/

        if (uSignup_userpw.getText().toString().trim().length() == 0) {
            uSignup_inline_userpw.setText("비밀번호를 입력해주세요");
            uSignup_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", uSignup_userpw.getText().toString())) {
            uSignup_inline_userpw.setText("비밀번호 형식은 최소 8 자, 최소 하나의 영문 및 하나의 숫자");
            uSignup_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;
    }


    public boolean validpwc(){

        if (uSignup_userpwc.getText().toString().trim().length() == 0) {
            uSignup_inline_userpwc.setText("비밀번호 확인을 다시 입력해주세요");
            uSignup_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (!uSignup_userpwc.getText().toString().equals(uSignup_userpw.getText().toString())) {
            uSignup_inline_userpwc.setText("비밀번호가 서로 일치하지 않습니다");
            uSignup_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;
    }




    public boolean final_valid(){

        if (uSignup_userid.getText().toString().trim().length() == 0) {
            uSignup_inline_userid.setText("이메일을 입력해주세요");
            uSignup_inline_userid.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$", uSignup_userid.getText().toString())) {
            uSignup_inline_userid.setText("이메일 형식에 맞지 않습니다");
            uSignup_inline_userid.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }


        if (Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$", uSignup_userid.getText().toString())) {

                dto = new User();
                dto.setU_userid(uSignup_userid.getText().toString());

                try {
                    result = new AsyncUserIdDuplicateCheck(SignUpActivity.this, dto).execute().get();
                    Log.i("result---", result);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result.equals("fail")) {
                    uSignup_inline_userid.setText("중복된 이메일입니다");
                    uSignup_inline_userid.setTextColor(Color.parseColor("#FF0000"));
                    return false;
                } else {
                    uSignup_inline_userid.setText("사용가능한 이메일입니다");
                    uSignup_inline_userid.setTextColor(Color.parseColor("#206108"));
                }

        }



        if (uSignup_usernick.getText().toString().trim().length() == 0) {
            uSignup_inline_usernick.setText("닉네임을 입력해주세요");
            uSignup_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[가-힣A-Za-z0-9]{2,12}$", uSignup_usernick.getText().toString())) {
            uSignup_inline_usernick.setText("닉네임은 한글,숫자,영어만 가능합니다");
            uSignup_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }


        if(Pattern.matches("^[가-힣A-Za-z0-9]{2,12}$", uSignup_usernick.getText().toString())){
                dto = new User();
                dto.setU_nick(uSignup_usernick.getText().toString());

                try {
                    result = new AsyncUserNickDuplicateCheck(SignUpActivity.this, dto).execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (result.equals("fail")) {
                    uSignup_inline_usernick.setText("중복된 닉네임입니다");
                    uSignup_inline_usernick.setTextColor(Color.parseColor("#FF0000"));
                    return false;
                }else {
                    uSignup_inline_usernick.setText("사용가능한 닉네임입니다");
                    uSignup_inline_usernick.setTextColor(Color.parseColor("#206108"));
                }
        }

        if (uSignup_username.getText().toString().trim().length() == 0) {
            uSignup_inline_username.setText("이름을 입력해주세요");
            uSignup_inline_username.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^[가-힣]{2,5}$", uSignup_username.getText().toString())) {
            uSignup_inline_username.setText("이름은 한글만 사용가능합니다");
            uSignup_inline_username.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (uSignup_userpw.getText().toString().trim().length() == 0) {
            uSignup_inline_userpw.setText("비밀번호를 입력해주세요");
            uSignup_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", uSignup_userpw.getText().toString())) {
            uSignup_inline_userpw.setText("비밀번호 형식은 최소 8 자, 최소 하나의 영문 및 하나의 숫자");
            uSignup_inline_userpw.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (uSignup_userpwc.getText().toString().trim().length() == 0) {
            uSignup_inline_userpwc.setText("비밀번호 확인을 다시 입력해주세요");
            uSignup_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!uSignup_userpwc.getText().toString().equals(uSignup_userpw.getText().toString())) {
            uSignup_inline_userpwc.setText("비밀번호가 서로 일치하지 않습니다");
            uSignup_inline_userpwc.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return true;
    }



    /*=========================================================================================================*/



    public void onClick(View v) {
        if (v.getId() == R.id.uSignup_userProfileImg) {
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

    //뒤로가기 눌렀을시 홈화면으로 전환
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
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

        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림","storageDir 존재함 " + storageDir.toString());

        filePath = new File(storageDir,imgFileName);  // File 파일 변수 = new File(파일경로, 파일이름);
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

            uSignup_userProfileImg.setImageBitmap(rotate(bitmap, exifDegree));
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
                        .into(uSignup_userProfileImg);
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
        Toast.makeText(this,"사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
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






} // class
