package com.example.moment.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.moment.R;
import com.example.moment.trancefer.AsyncUserLogin;

import org.w3c.dom.Text;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    AsyncUserLogin asyncUserLogin;
    EditText uLogin_userid;
    EditText uLogin_userpw;
    TextView uLogin_userid_inline;
    TextView uLogin_userpw_inline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        Button uLoginbtn = findViewById(R.id.uLoginbtn);
        TextView uSignuptext = findViewById(R.id.uSignuptext);

        uLogin_userid = findViewById(R.id.uLogin_userid);
        uLogin_userpw = findViewById(R.id.uLogin_userpw);

        uLogin_userid_inline = findViewById(R.id.uLogin_userid_inline);
        uLogin_userpw_inline = findViewById(R.id.uLogin_userpw_inline);


        uLoginbtn.setOnClickListener(new View.OnClickListener() {
            //로그인 유효성검사
            @Override
            public void onClick(View v) {
                if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$", uLogin_userid.getText().toString())) {
                    uLogin_userid_inline.setText("아이디를 이메일 형식에 맞게 입력해주세요 ");
                } else if (!uLogin_userid.getText().toString().equals("") && uLogin_userpw.getText().toString().equals("")) {
                    uLogin_userid_inline.setText("");
                    uLogin_userpw_inline.setText("비밀번호를 입력해주세요");
                } else if (!uLogin_userpw.getText().toString().equals("") && !uLogin_userid.getText().toString().equals("")) {
                    uLogin_userpw_inline.setText("");
                    asyncUserLogin = new AsyncUserLogin(LoginActivity.this);
                    asyncUserLogin.setActionUrl("/userLoginAction.mo");
                    asyncUserLogin.setUserid(uLogin_userid);
                    asyncUserLogin.setUserpw(uLogin_userpw);
                    asyncUserLogin.execute();
                }
            }
        });

        uSignuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


}