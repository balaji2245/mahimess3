package com.example.bmrd.mahimess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginMessActivity extends AppCompatActivity {

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Button mLoginButton;

    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    private ProgressDialog mLoginProgress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mess);


        //Toolbar
        mToolbar=(Toolbar) findViewById(R.id.main_app_bar7);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Mess Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Progress dialog
        mLoginProgress=new ProgressDialog(this);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Android fields

        mLoginEmail=(TextInputLayout) findViewById(R.id.login_email);
        mLoginPassword=(TextInputLayout) findViewById(R.id.login_password);
        mLoginButton=(Button)findViewById(R.id.login_btn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mLoginEmail.getEditText().getText().toString();
                String password=mLoginPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Breath in ...Breath out...in...out!!");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();


                    loginUser(email,password);
                }

            }
        });

    }

    private void loginUser(String email, String password) {                 //Signin code

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
/*
                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=current_user.getUid();

                    mDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
*/




                    mLoginProgress.dismiss();

                    Intent mainIntent=new Intent(LoginMessActivity.this,MainMessActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                }else
                {
                    mLoginProgress.hide();
                    Toast.makeText(LoginMessActivity.this,"Oops...error...please try again",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
