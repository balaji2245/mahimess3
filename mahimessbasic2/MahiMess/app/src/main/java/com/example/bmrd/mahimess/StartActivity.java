package com.example.bmrd.mahimess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button mRegBtn;
    private Button mLoginBtn;
    private Button mLoginMBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegBtn=(Button) findViewById(R.id.start_reg_button);

        mLoginBtn=(Button) findViewById(R.id.start_login_button);

        mLoginMBtn=(Button) findViewById(R.id.start_loginm_button);

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reg_intent=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(reg_intent);

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_intent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(login_intent);
            }
        });

        mLoginMBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_m_intent=new Intent(StartActivity.this,LoginMessActivity.class);
                startActivity(login_m_intent);
            }
        });


    }
}

