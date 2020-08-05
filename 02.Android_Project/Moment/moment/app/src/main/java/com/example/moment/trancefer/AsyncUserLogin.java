package com.example.moment.trancefer;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.example.moment.activity.MainActivity;
import com.example.moment.common.Common;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncUserLogin extends AsyncTask<String, Void, String> {
    Activity activity;
    ProgressDialog progressDialog;
    Boolean isConnection = false;
    EditText userid;
    EditText userpw;
    EditText username;
    EditText useremail;
    String actionUrl;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    String jsonString;
    JSONObject signUpData;

    //Async 스레드를 시작

    public AsyncUserLogin(Activity activity) {
        this.activity = activity;
    }

    @Override
    //동작을 취하기전에 선행적으로 해야하는 처리(ex 로딩바 시작)
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "Reading", "데이터 수신중....");
        super.onPreExecute();
    }
    @Override
    //실제 작업하는곳
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        String url = "";
        sharedPreferences = null;

        try {
            //쓰기 및 읽기 진행할 URL  -> Servlet 주소 + Controller url
            url = Common.SERVER_URL + actionUrl;

            Log.i("url 주소", url);
            //쓰기 읽기 메소드  리턴값: String 타입 ->  getStringFromUrl(url);
                 sharedPreferences = activity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                sharedEditor = sharedPreferences.edit();
                String jsonPage = getStringFromUrl(url);
                JSONObject result = new JSONObject(jsonPage);
                Log.i("result------", result.toString());

                sharedEditor.putString("u_name", result.getString("u_name"));
                sharedEditor.putString("u_userid", result.getString("u_userid"));
                sharedEditor.putString("u_userpw", result.getString("u_userpw"));
                sharedEditor.putString("u_nick", result.getString("u_nick"));
                sharedEditor.putString("u_local", result.getString("u_local"));
                sharedEditor.putString("u_profileimg", result.getString("u_profileimg"));
                jsonString = result.getString("result");

       /*     SharedPreferences info = activity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            sharedEditor = info.edit();
            sharedEditor.putString("u_userid", result.getString("u_userid"));
            sharedEditor.putString("u_local", result.getString("u_local"));
            sharedEditor.putString("u_nick", result.getString("u_nick"));
            sharedEditor.putString("u_profileimg", result.getString("u_profileimg"));


*/


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    //  데이터전송, 로딩바 끝내기)
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (isConnection) {
                progressDialog.dismiss();
                Log.i("접속처리 : ", "처리됨");
                if (result.trim().equals("success")) {
                    sharedEditor.commit();
                    Log.i("로그인 처리값: ", result);
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    Toast.makeText(activity, "접속성공", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            Log.i("로그인 처리값: ", result);
    }


    public String getStringFromUrl(String mUrl) {
        //2byte 처리. 문자열 처리.
        BufferedReader buffreader = null;
        HttpURLConnection urlConnection = null;

        StringBuffer page = new StringBuffer();

        try {
            URL url = new URL(mUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            //get 방식 post방식
            urlConnection.setRequestMethod("POST");
            //쓰기모드
            urlConnection.setDoOutput(true);
            //읽기모드
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setDefaultUseCaches(false);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            //로그인 쓰기직업
                //쓰기 작업 -> jsonObject -> String -> byte로 변환 하여 자바 컨트롤러 URL 쓰기작업
                //outputStream으로 쓰기작업시 byte 타입만 가능.
                //반대로 자바에서 불러올때 byte -> String -> jsonObject 변환
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("u_userid", userid.getText().toString());
                jsonObject.put("u_userpw", userpw.getText().toString());
                Log.i("id, pwd", userid.getText().toString() + userpw.getText().toString());
                String jsonData = jsonObject.toString();
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(jsonData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

            //읽기작업 자바에서 보낸 JSON타입 String으로 변환
            InputStream contentStream = urlConnection.getInputStream();
            buffreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            String line = null;

            while ((line = buffreader.readLine()) != null) {
                //java syso -> android log.d
                //식별자, 데이터
                Log.d("line", line);
                page.append(line);
            }
         //   Log.i("jsonString----------", jsonString);
            jsonString = page.toString();
            contentStream.close();

            isConnection = true;
        } catch (Exception e) {
            isConnection = false;
            e.printStackTrace();
        } finally {
            try {
                //자원회수
                buffreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

    public void setUserid(EditText editText) {
        this.userid = editText;
    }

    public void setUserpw(EditText editText) {
        this.userpw = editText;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public void setSignUpData(JSONObject jsonObject)
    {
        this.signUpData = jsonObject;
    }

    public void setUseremail(EditText useremail) {
        this.useremail = useremail;
    }
}

