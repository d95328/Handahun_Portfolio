package com.example.moment.trancefer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.moment.activity.MainActivity;
import com.example.moment.activity.board.DetailActivity;
import com.example.moment.common.Common;
import com.example.moment.model.Board;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncBoardModify extends AsyncTask<String, String, String> {

    Activity activity;
    Board dto;
    ProgressDialog progressDialog;
    String jsonString = "";
    boolean isConnection = false;



    public AsyncBoardModify(Activity activity, Board dto) {
        this.activity = activity;
        this.dto = dto;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "Reading", "데이터 수신중");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        //서버접속 ---------------------------------------------------------
        String NewUrl = Common.SERVER_URL + "/BoardModifyAction.mo";
        HttpURLConnection urlConnection = null;
        String line = "";
        String result = "";


        try {

            URL url = new URL(NewUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");


            JSONObject jsonObject = new JSONObject();

            jsonObject.put("b_no", dto.getB_no());
            jsonObject.put("b_local", dto.getB_local());
            jsonObject.put("b_title", dto.getB_title());
            jsonObject.put("b_coment", dto.getB_coment());
            jsonObject.put("b_latitude", dto.getB_latitude());
            jsonObject.put("b_longitude", dto.getB_longitude());

            Log.i("Board Modify------- ", dto.toString());
            String jsonData = jsonObject.toString();
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(jsonData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            // ------------------ 서버에서 받아옴
            if (HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(inputStream);
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                jsonString = sb.toString();
                jsonObject = new JSONObject(jsonString);
                result = jsonObject.getString("result");
            }

            isConnection = true;
        } catch (Exception e) {//서버 연결 실패했을 때
            e.printStackTrace();
            isConnection = false;

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (result.equals("success")) {
            Toast.makeText(activity, "수정이 완료되었습니다!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            Toast.makeText(activity, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
            return;

        }
    }



}
