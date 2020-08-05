package com.example.moment.trancefer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;


import com.example.moment.activity.user.LoginActivity;
import com.example.moment.common.Common;
import com.example.moment.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AsyncUserModify extends AsyncTask<String, String, String> {

    SharedPreferences.Editor sharedEditor;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    Boolean isConnection = false;
    File imgFile;
    Activity activity;
    User dto;
    URL url;
    String sendUrl = Common.SERVER_URL + "/userModifyAction.mo";
    String imgPath;


    public AsyncUserModify(Activity activity, User dto) {
        this.activity = activity;
        this.dto = dto;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity, "Reading", "데이터 수신중");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... URI) {
        if (URI[0] == null) {
            imgPath = dto.getU_profileimg(); //수정안헀을때
        } else {
            imgPath = URI[0]; //수정했을때
        }
        Log.i("imgPath", "============" + imgPath);


        String receiveMsg = "";
        String result = "";

        String twoHyphens = "--";
        String boundary = "*****";
        DataOutputStream dos = null;
        String lindEnd = "\r\n";
        byte[] buffer;
        int maxBufferSize = 5 * 1024 * 1024, ret = 0;

        imgFile = new File(imgPath);
        Log.i("imgFile", "============" + imgFile);


        if (imgFile.isFile()) { //쓰기
            try {
                FileInputStream fis = new FileInputStream(imgFile);
                Log.i("fis", "============" + fis);
                url = new URL(sendUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lindEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"u_userid\"\r\n\r\n" + URLEncoder.encode(dto.getU_userid(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_nick\"\r\n\r\n" + URLEncoder.encode(dto.getU_nick(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_userpw\"\r\n\r\n" + URLEncoder.encode(dto.getU_userpw(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_local\"\r\n\r\n" + URLEncoder.encode(dto.getU_local(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"includeImg\"\r\n\r\n" + URLEncoder.encode("yes", "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_profileimg\";filename=\"" + imgPath + "\"" + lindEnd);
                dos.writeBytes(lindEnd);

                buffer = new byte[maxBufferSize];
                int length = -1;

                Log.i("==========dos", dto.toString() + imgPath);

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
                        stringBuffer.append(str);
                    }
                    receiveMsg = stringBuffer.toString();

                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    result = jsonObject.getString("result");
                } else {
                    return "0";
                }

                fis.close();
                dos.flush();
                dos.close();
                isConnection = true;
            } catch (Exception e) {
                isConnection = false;
                e.printStackTrace();
            }
        } else {
            try {
                //  FileInputStream fis = new FileInputStream(imgFile);
                url = new URL(sendUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                dos = new DataOutputStream(conn.getOutputStream());
                int index =dto.getU_profileimg().indexOf("profileimg");
                dto.setU_profileimg( dto.getU_profileimg().substring(index+11));

                Log.i("image",dto.getU_profileimg());

                dos.writeBytes(twoHyphens + boundary + lindEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"u_userid\"\r\n\r\n" + URLEncoder.encode(dto.getU_userid(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_nick\"\r\n\r\n" + URLEncoder.encode(dto.getU_nick(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_userpw\"\r\n\r\n" + URLEncoder.encode(dto.getU_userpw(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_local\"\r\n\r\n" + URLEncoder.encode(dto.getU_local(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_profileimg\"\r\n\r\n" + URLEncoder.encode(dto.getU_profileimg(), "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"includeImg\"\r\n\r\n" + URLEncoder.encode("no", "UTF-8") + lindEnd);
                dos.writeBytes(twoHyphens + boundary + lindEnd); // header역할
                dos.writeBytes("Content-Disposition: form-data; name=\"u_profileimg\";filename=\"" + dto.getU_profileimg() + "\"" + lindEnd);
                dos.writeBytes(lindEnd);



                buffer = new byte[maxBufferSize];
                int length = -1;

                Log.i("==========dos", dto.toString() + imgPath);


                dos.writeBytes(lindEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lindEnd);


                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String str = "";
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        stringBuffer.append(str);
                    }
                    receiveMsg = stringBuffer.toString();

                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    result = jsonObject.getString("result");
                } else {
                    return "0";
                }

                // fis.close();
                dos.flush();
                dos.close();
                isConnection = true;
            } catch (Exception e) {
                isConnection = false;
                e.printStackTrace();
            }

        } // else
        return result;
    } //doingonback


    @Override
    protected void onPostExecute(String result) {
        if (isConnection) {
            sharedPreferences = activity.getSharedPreferences("userInfo", activity.MODE_PRIVATE);
            sharedEditor = sharedPreferences.edit();
            sharedEditor.clear();
            sharedEditor.commit();
            progressDialog.dismiss();
        } else {
            progressDialog.dismiss();
        }
    }

}

