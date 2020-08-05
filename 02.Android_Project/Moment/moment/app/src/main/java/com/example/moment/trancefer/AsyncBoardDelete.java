package com.example.moment.trancefer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.moment.activity.MainActivity;
import com.example.moment.common.Common;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncBoardDelete extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    Activity activity;
    String no;
    String result;
    String jsonString = "";
    boolean isConnection = false;

    public AsyncBoardDelete (Activity activity, String no) {
        this.activity = activity;
        this.no = no;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "Reading", "데이터 수신중....");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String NewUrl = Common.SERVER_URL + "/BoardDeleteAction.mo";
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

            jsonObject.put("b_no", no);

            String jsonData = jsonObject.toString();
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(jsonData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            if(HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {
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

        } catch (Exception e) {
            e.printStackTrace();
            isConnection = false;
        }
        return result;
    }//end of doInBackground;

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if (result.equals("success")) {
            alert("삭제가 완료되었습니다!");
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            alert("삭제에 실패했습니다.");
            return;
        }
    }//end of onPost

    //토스트 메소드
    public void alert(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

}//end of class
