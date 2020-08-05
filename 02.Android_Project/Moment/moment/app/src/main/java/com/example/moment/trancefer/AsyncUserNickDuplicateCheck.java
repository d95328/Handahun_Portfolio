package com.example.moment.trancefer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.moment.common.Common;
import com.example.moment.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncUserNickDuplicateCheck extends AsyncTask<String, String, String> {

    Activity activity;
    User dto;
    ProgressDialog progressDialog;
    String jsonString = "";
    boolean isConnection = false;
    String result = null;


    public AsyncUserNickDuplicateCheck(Activity activity, User dto) {
        this.activity = activity;
        this.dto = dto;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "Reading", "데이터수신중");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String NewUrl = Common.SERVER_URL + "/userNickDuplicateCheck.mo";
        HttpURLConnection urlConnection = null;
        String line = "";
        String joinResult = "";
        BufferedReader reader = null;

        try {

            URL url = new URL(NewUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);

            OutputStream outputStream = urlConnection.getOutputStream();
            String nick = "u_nick="+(dto.getU_nick());
            outputStream.write(nick.getBytes("UTF-8"));

            // ------------------ 서버에서 받아옴
            if (HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
                reader = new BufferedReader(inputStream);
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                jsonString = sb.toString();
                JSONObject jsonObject = new JSONObject(jsonString);
                result = jsonObject.getString("result"); //닉네임이 있는지 없는 지 체크 결과
                Log.i("result",result);
            }

            isConnection = true;

        } catch (Exception e) {
            e.printStackTrace();
            isConnection = false;
        } finally {
            try {
                //자원회수
                reader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

        }
    }

    @Override
    protected void onPostExecute (String result) {
        if(isConnection){
            progressDialog.dismiss();
        } else {
            progressDialog.dismiss();
        }
    }
}
