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
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FcmPushMessege extends AsyncTask<String, String, String> {

    Activity activity;
    Board dto;
    ProgressDialog progressDialog;
    String jsonString = "";
    boolean isConnection = false;
    String to = "/topics/gwangju";
    String title = "알림";

    public FcmPushMessege(Activity activity, Board dto) {
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
        String NewUrl = "https://fcm.googleapis.com/fcm/send";
        HttpURLConnection urlConnection = null;
        String line = "";
        String result = "";

        setFcm_location();
        try {

            URL url = new URL(NewUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization","key=AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D");

            JSONObject jsonObject = new JSONObject();
            JSONObject info  = new JSONObject();

            jsonObject.put("to", to);

            info.put("title", title);
            info.put("body", dto.getB_title());

            jsonObject.put("notification", info);
            Log.i("Board FCM------- ", dto.toString());
            String jsonData = jsonObject.toString();
            Log.i("Board FCM------- ", jsonData);


            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(jsonObject.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                Log.i("fcm_result",output);
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
    }
    private void setFcm_location(){
        String location = dto.getB_local();
        location = location.trim().substring(0,7);

        if(location.equals("대한민국 서울")){
            to = "/topics/seoul";
            title = "서울 알람\n";
        }else if(location.equals("대한민국 부산")){
            to = "/topics/busan";
            title = "부산 알람\n";
        }else if(location.equals("대한민국 경기")){
            to = "/topics/kyungki";
            title = "경기 알람\n";
        }else if(location.equals("대한민국 광주")){
            to = "/topics/gwanju";
            title = "광주 알람\n";
        }else if(location.equals("대한민국 강원")){
            to = "/topics/kangwon";
            title = "강원도 알람\n";
        }else if(location.equals("대한민국 충청")){
            to = "/topics/chungchung";
            title = "충청도 알람\n";
        }else if(location.equals("대한민국 전라")){
            to = "/topics/junra";
            title = "전라도 알람\n";
        }else if(location.equals("대한민국 경상")){
            to = "/topics/kyungsang";
            title = "경상도 알람\n";
        }else if(location.equals("대한민국 제주")){
            to = "/topics/jeju";
            title = "제주도 알람\n";
        }
    }
}
