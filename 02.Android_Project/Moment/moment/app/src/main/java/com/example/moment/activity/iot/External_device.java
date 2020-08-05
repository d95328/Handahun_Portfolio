package com.example.moment.activity.iot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.example.moment.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class External_device extends AppCompatActivity {
    final String TAG = "External_device";

    //XML 객체
    TextView connection_status_textview;
    TextView receiveData;
    TextView device_ready;

    //TCP 소켓 연결할 주소 및 소켓 객체 생성
    Socket socket = new Socket();
    int port = 9990;
    String host = "192.168.0.9";

    //데이터 송수신 Stream
    ObjectOutputStream outStream;
    DataInputStream inStream;

    //사용자 송수신 쓰레드
    ClientOutputThread outputThread;
    ClientInputThread inputThread;

    boolean isConnected = false;
    ProgressDialog progressDialog;

    //사용자 로그인 정보
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    Handler uiHandler;
    boolean stream_connect = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_device);

        //XML 아이디값 저장
        connection_status_textview = findViewById(R.id.connection_status_textview);
        receiveData = findViewById(R.id.receiveData);
        Button pair_button = findViewById(R.id.pair_button);
        device_ready = findViewById(R.id.device_ready);

        //로딩바 생성
        progressDialog = new ProgressDialog(this);

        //ui를 처리해줄 핸들러
        uiHandler = new Handler();
        //사용자 정보를 담아줌
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        //기기와 소켓연결
        pair_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectTread connectTread = new ConnectTread();
                connectTread.setDaemon(true);
                connectTread.start();
            }
        });

        //기기에게 사용자 정보 전송후 실행준비..
        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectChk();
                if(isConnected){
                    //주고 받을 쓰레드 생성
                    outputThread = new ClientOutputThread();
                    inputThread = new ClientInputThread();
                    //백그라운드가 종료될때 쓰레드를 같이 동작 중지를 함.
                    outputThread.setDaemon(true);
                    inputThread.setDaemon(true);
                    outputThread.start();
                    inputThread.start();
                }else{
                    Toast.makeText(External_device.this, "기기와 연결을 확인 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class ConnectTread extends Thread{
        @Override
        public void run() {
            try {
                socket = new Socket(host,port);
                outStream = new ObjectOutputStream(socket.getOutputStream());
                inStream = new DataInputStream(socket.getInputStream());
                //소켓을 확인하기위해 두가지를 체크한다. isConnected true & isClosed false
                    isConnected = socket.isConnected() && ! socket.isClosed();
                    if(isConnected){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(External_device.this, "외부장치 연결 성공", Toast.LENGTH_SHORT).show();
                                        connection_status_textview.setText("연결됨");
                                    }
                                });
                            }
                        }).start();

                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ConnectChk();
                                    }
                                });
                            }
                        }).start();
                    }
                while (true){
                    isConnected = socket.isConnected() && ! socket.isClosed();
                    if(!isConnected){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ConnectChk();
                                    }
                                });
                            }
                        }).start();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConnectChk();
                    }
                }).start();
            }
        }
    }
    class ClientOutputThread extends Thread {
        public void run() {
            try {
                if( !sharedPreferences.getString("u_userid", "").equals("") ){
                    String msg = "1"+sharedPreferences.getString("u_userid", "");
                    outStream.write(msg.getBytes("euc-kr"));
                    outStream.flush();
                    Log.d("ClientThread","서버로 보냄.");
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(External_device.this, "로그인 후 사용 바랍니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConnectChk();
                    }
                }).start();
            }
        }
    }

    class ClientInputThread extends Thread {
        @Override
        public void run() {
            try {
                while (stream_connect){
                    byte[] data;
                    data = new byte[1024];
                    inStream.read(data);

                    String m = "";
                    //받은 데이터를 문자화 시킴
                    for (byte b : data) {
                        char c;
                        if(b != 0){
                            c = (char) b;
                            m = m + c;
                        }
                    }

                    //사진이름 저장
                    if(m.length() >10){
                        m = m.substring(0,1).matches("8") ? m.substring(1) : m;
                        final String image_name = m;
                        stream_connect = false;
                        try {
                            device_ready.setVisibility(View.INVISIBLE);
                            //자원 회수
                            inStream.close();
                            outStream.close();
                            socket.close();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    uiHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(5000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(External_device.this, Pano360ViewActivity.class);
                                            intent.putExtra("filepath",image_name);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }).start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    final String msg = m;
                    Log.d("ClientThread", "받은 데이터 : " + msg);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(msg.matches("1")){
                                        device_ready.setVisibility(View.VISIBLE);
                                        Toast.makeText(External_device.this, "실행준비가 되었습니다.", Toast.LENGTH_SHORT).show();
                                    }else if(msg.matches("2")){
                                        Toast.makeText(External_device.this, "사진촬영을 시작합니다", Toast.LENGTH_SHORT).show();
                                        showLoadingBar();
                                    }else if(msg.matches("3")){

                                    }else if(msg.matches("4")){
                                        Toast.makeText(External_device.this, "에러 발생 !! 이전 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else if(msg.matches("5")){
                                        progressDialog.setMessage("사진 펼치는중..");
                                    }else if(msg.matches("6")){
                                        progressDialog.setMessage("사진 붙이는중..");
                                    }else if(msg.matches("7")){
                                        progressDialog.setMessage("사진 외관 처리중..");
                                    }else if(msg.matches("8")){
                                        progressDialog.setMessage("사진 저장중.....");

                                    }
                                }
                            });
                        }
                    }).start();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConnectChk();
                    }
                }).start();
            }
        }
    }

    //장치 연결 상태 확인
    private void ConnectChk(){
        isConnected = socket.isConnected() && ! socket.isClosed();
        if(isConnected){
            connection_status_textview.setText("연결됨");
        }else {
            connection_status_textview.setText("연결안됨");
            device_ready.setVisibility(View.INVISIBLE);
            Toast.makeText(External_device.this, "외부장치 연결 끊김", Toast.LENGTH_SHORT).show();
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
            progressDialog.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showLoadingBar(){
        progressDialog.setMessage("사진촬영 중..");
        progressDialog.setTitle("사진을 만드는 중입니다.");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}