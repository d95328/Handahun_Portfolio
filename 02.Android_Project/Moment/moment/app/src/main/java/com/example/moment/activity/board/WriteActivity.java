package com.example.moment.activity.board;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.example.moment.activity.gmap.GoogleMapsActivity;
import com.example.moment.activity.user.ModifyActivity;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.FcmPushMessege;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WriteActivity extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 111;
    private static final int REQUEST_TAKE_ALBUM = 222;
    private static final int REQUEST_LOCATION = 333;
    ImageView writeImageView;
    ImageView writeProfile;
    EditText writeTitle;
    LinearLayout writeLocationBtn;
    TextView printLocation;
    EditText writeContents;
    TextView writeLocation;
    TextView writeNick;
    Button sendBtn;

    File filePath;
    private String mCurrentPhotoPath;
    NoticeWriteAsync noticeWriteAsync;
    FcmPushMessege fcmPushMessege;
    int reqWidth;
    int reqHeight;

    Uri imageUri;
    URL url;
    File imgFile;
    String sendUrl = Common.SERVER_URL + "/BoardWriteAction.mo";
    SharedPreferences sharedPreferences;

    String write;

    Toolbar toolbar;

    Activity activity;


    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        setTitle("");
        //툴바생성 & 뒤로가기버튼
        toolbar = findViewById(R.id.toolbar_mainAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        write = intent.getStringExtra("write");

        writeTitle = findViewById(R.id.writeTitle);
        writeLocation = findViewById(R.id.writeLocation);
        writeLocationBtn = findViewById(R.id.writeLocationBtn);
        printLocation = findViewById(R.id.printLocation);
        writeContents = findViewById(R.id.writeContents);
        writeImageView = findViewById(R.id.writeImageView);
        writeProfile = findViewById(R.id.writeProfile);
        writeNick = findViewById(R.id.writeNick);

        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);

        writeNick.setText(sharedPreferences.getString("u_nick",""));

        //프로필사진 glide
        Glide
                .with(this)
                .load(sharedPreferences.getString("u_profileimg",""))
                .into(writeProfile);

        // 구글맵 인텐트
        writeLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intentMaps = new Intent(WriteActivity.this, GoogleMapsActivity.class);
                startActivityForResult(intentMaps,REQUEST_LOCATION);
            }
        });
        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);

        if(write.equals("camera")){
            captureCamera();
        }else if(write.equals("storage")){
            getAlbum();
        }

    }//end of onCreate


    public void onClick(View v) {
        if (v.getId() == R.id.writeImageView) {
            PopupMenu menu = new PopupMenu(getApplication(), v);
            getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.camera:
                            // alert("카메라 촬영 선택");
                            captureCamera();
                            break;
                        case R.id.gallery:
//                            alert("갤러리 선택");
                            getAlbum();
                            break;
                    }
                    return true;
                }
            });
            menu.show();
        } else if (v.getId() == R.id.write) {
            if(writeTitle.getText().toString().trim().equals("")){
                alert("제목을 입력해주세요");
                return ;
            }else if (writeLocation.getText().toString().trim().equals("")){
                alert("위치를 추가해주세요");
                return ;
            }else if(writeContents.getText().toString().trim().equals("")){
                alert("코멘트를 입력해주세요");
                return ;
            }

            Board dto = getBoardData();
            fcmPushMessege = new FcmPushMessege(this, dto);
            fcmPushMessege.execute();

            noticeWriteAsync = new NoticeWriteAsync();
            noticeWriteAsync.execute(mCurrentPhotoPath.toString());

        }
    }

    private void captureCamera() {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (photoFile != null) {
                        Log.i("getPackageName() ===> ", getPackageName());
                        Uri providerURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                        imageUri = providerURI;
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
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

    public File createImageFile() throws IOException {
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/myApp");

        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림","storageDir 존재함 " + storageDir.toString());

        filePath = new File(storageDir,imgFileName);
        mCurrentPhotoPath = filePath.getAbsolutePath();

        return filePath;
    }
    private void getAlbum() {
        Intent getAlbumIntent = new Intent(Intent.ACTION_PICK);
        getAlbumIntent.setType("image/*");

        //포토, 갤러리 선택
        getAlbumIntent.putExtra(getAlbumIntent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(getAlbumIntent.createChooser(getAlbumIntent, "Select Picture"), REQUEST_TAKE_ALBUM);
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

            writeImageView.setImageBitmap(rotate(bitmap, exifDegree));

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
                        .into(writeImageView);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(requestCode == REQUEST_LOCATION && resultCode == RESULT_OK){
            printLocation.setText(data.getStringExtra("location"));
            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);
        }
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);

        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
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

    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
    public Board getBoardData() {
        Board b = new Board();
        b.setB_title(writeTitle.getText().toString());
        b.setB_local(printLocation.getText().toString());
        b.setB_coment(writeContents.getText().toString());
        return b;
    }



    private class NoticeWriteAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... URI) {
            String imgPath = URI[0];
            String receiveMsg = "";
            String createResult = "";
            sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

            String twoHyphens = "--";
            String boundary = "*****";
            DataOutputStream dos = null;
            String lindEnd = "\r\n";
            byte[] buffer;
            int maxBufferSize = 5 * 1024 * 1024, ret = 0;
            imgFile = new File(imgPath);
            imgFile.renameTo(new File(String.valueOf(System.currentTimeMillis())));

            Log.i("p_name", "============" + writeTitle.getText().toString());
            Log.i("imgFile", "============" + imgFile);
            if (imgFile.isFile()) {
                try {
                    FileInputStream fis = new FileInputStream(imgFile);
                    url = new URL(sendUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setUseCaches(false);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    int jpg = imgPath.indexOf(".jpg");

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_title\"\r\n\r\n" + URLEncoder.encode(writeTitle.getText().toString(), "UTF-8") + lindEnd);
                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_local\"\r\n\r\n" +  URLEncoder.encode(printLocation.getText().toString(), "UTF-8") + lindEnd);
                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_coment\"\r\n\r\n" +  URLEncoder.encode(writeContents.getText().toString(),"UTF-8") + lindEnd);
                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_latitude\"\r\n\r\n" +  URLEncoder.encode(String.valueOf(latitude),"UTF-8") + lindEnd);
                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_longitude\"\r\n\r\n" +  URLEncoder.encode(String.valueOf(longitude),"UTF-8") + lindEnd);

                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_userid\"\r\n\r\n" +  URLEncoder.encode(sharedPreferences.getString("u_userid",""),"UTF-8") + lindEnd);
                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_tag\"\r\n\r\n" +  URLEncoder.encode("tag","UTF-8") + lindEnd);


                    dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                    dos.writeBytes("Content-Disposition: form-data; name=\"b_imgpath\";filename=\"" + imgPath.substring(0 , jpg) + System.currentTimeMillis() + ".jpg" +"\"" + lindEnd);
                    dos.writeBytes(lindEnd);

                    buffer = new byte[maxBufferSize];
                    int length = -1;

                    while ((length = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, length);
                    }

                    dos.writeBytes(lindEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lindEnd);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        String str = "";
                        InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(isr);
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            stringBuffer.append(str.toString());
                        }

                        receiveMsg = stringBuffer.toString();

                        JSONObject jsonObject = new JSONObject(receiveMsg);
                        createResult = jsonObject.getString("createResult");
                    } else {
                        Log.i("글 등록", "실패");
                        return createResult;
                    }

                    fis.close();
                    dos.flush();
                    dos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return createResult;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Integer.parseInt(result) > 0) {
                Toast.makeText(WriteActivity.this, "등록 성공", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(WriteActivity.this, "등록 실패", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }
}
