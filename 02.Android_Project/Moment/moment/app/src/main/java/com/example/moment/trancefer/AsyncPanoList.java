package com.example.moment.trancefer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.moment.activity.board.FavoriteActivity;
import com.example.moment.activity.board.MyListActivity;
import com.example.moment.activity.board.SearchActivity;
import com.example.moment.model.Board;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncPanoList extends AsyncTask<String, Void, ArrayList<Board>> {

    Activity activity;
    ProgressDialog progressDialog;
    Boolean isConnection = false;
    ArrayList<Board> list = new ArrayList<>();

    public AsyncPanoList(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "자료 불러오기", "자료 불러오는중");
        super.onPreExecute();
    }

    @Override
    //실제 작업하는곳
    protected ArrayList<Board> doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        try {
            String jsonPage = getStringFromUrl(params[0]);
            JSONArray jsonArray = new JSONArray(jsonPage);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Board dto = new Board();
                dto.setB_no(jsonObject.getInt("b_no"));
                dto.setB_imgpath(jsonObject.getString("b_imgpath"));
                dto.setB_coment(jsonObject.getString("b_coment"));
                dto.setB_ddabong(jsonObject.getInt("b_ddabong"));
                dto.setB_tag(jsonObject.getString("b_tag"));
                dto.setB_local(jsonObject.getString("b_local"));
                dto.setB_writedate(jsonObject.getString("b_writedate"));
                dto.setB_readcnt(jsonObject.getInt("b_readcnt"));
                dto.setB_latitude(jsonObject.getDouble("b_latitude"));
                dto.setB_longitude(jsonObject.getDouble("b_longitude"));
                dto.setB_title(jsonObject.getString("b_title"));
                dto.setB_userid(jsonObject.getString("b_userid"));
                dto.setB_profileimg(jsonObject.getString("b_profileimg"));
                dto.setB_nick(jsonObject.getString("b_nick"));
                Log.i("dto",dto.toString());
                list.add(dto);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    //  데이터전송, 로딩바 끝내기)
    protected void onPostExecute(ArrayList<Board> result) {
            progressDialog.dismiss();
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
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            //JSONObject 읽기 작업
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

