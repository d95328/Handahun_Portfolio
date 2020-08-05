
package com.example.moment.trancefer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;


import com.example.moment.common.Common;
import com.example.moment.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncUserIdDuplicateCheck extends AsyncTask<String, Boolean, String> {

    Activity activity;
    ProgressDialog progressDialog;
    Boolean isConnection = false;
    User dto;

    //Async 스레드를 시작


    public AsyncUserIdDuplicateCheck(Activity activity, User dto) {
        this.activity = activity;
        this.dto = dto;
    }

    @Override
    //동작을 취하기전에 선행적으로 해야하는 처리(ex 로딩바 시작)
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "아이디 중복 확인", "아이디 중복 확인");
        super.onPreExecute();
    }

    @Override
    //실제 작업하는곳
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        String url = "";

        try {
            url = Common.SERVER_URL + "/userIdDuplicateCheck.mo";
            String jsonPage = getStringFromUrl(url);
            JSONObject jsonObject = new JSONObject(jsonPage);
            sb.append(jsonObject.getString("result"));
            Log.i("로그인 중복확인", "읽기 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    //  데이터전송, 로딩바 끝내기)
    protected void onPostExecute(String result) {
        if (isConnection) {

            progressDialog.dismiss();
        }else {
            progressDialog.dismiss();
        }
    }


    public String getStringFromUrl (String mUrl){
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

            OutputStream outputStream = urlConnection.getOutputStream();
            String email = "u_userid="+(dto.getU_userid());
            outputStream.write(email.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            InputStream contentStream = urlConnection.getInputStream();
            buffreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            String line = null;

            while ((line = buffreader.readLine()) != null) {
                Log.d("line", line);
                page.append(line);
            }
            isConnection = true;
        } catch (Exception e) {
            isConnection = false;
            e.printStackTrace();
            return "";
        } finally {
            try {
                //자원회수
                buffreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return page.toString();
    }
}

