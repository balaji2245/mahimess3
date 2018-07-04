package com.example.bmrd.mahimess;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private Toolbar mToolbar;


    private Button mLocation;
    private Button mContactButton;
    private Button mMessLoginButton;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar=(Toolbar) findViewById(R.id.main_app_bar2);                //top toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Mahi Mess");


        mLocation=(Button) findViewById(R.id.location_btn);

        mContactButton=(Button) findViewById(R.id.contactButton);
        mMessLoginButton=(Button) findViewById(R.id.mess_login_btn);

        spinner=(Spinner)findViewById(R.id.spinner);

        adapter=ArrayAdapter.createFromResource(this,R.array.locations,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




                /*
                if(!adapterView.getItemAtPosition(i).equals("Select location")) {
                    Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected", Toast.LENGTH_LONG).show();
                }
                */



                if(adapterView.getItemAtPosition(i).equals("Ambegao(BK)")){

                    Intent reg_intent=new Intent(MainActivity.this,Ambegao2Activity.class);
                    startActivity(reg_intent);


                }



                if(adapterView.getItemAtPosition(i).equals("Vadgao")){

                    Intent reg_intent=new Intent(MainActivity.this,VadgaoActivity.class);
                    startActivity(reg_intent);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        mMessLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent=new Intent(MainActivity.this,MainMessActivity.class);
              //  mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
               // finish();
            }
        });



        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent=new Intent(MainActivity.this,MapsActivity.class);
                            //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                            //finish();
//
//                String latLang=MapsActivity.send();
//                Toast.makeText(MainActivity.this,latLang+"",Toast.LENGTH_LONG).show();

            }
        });





        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reg_intent=new Intent(MainActivity.this,ContactActivity.class);
                startActivity(reg_intent);

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if ( currentUser == null ) {            //to send the user to start page if they are not already logged in
            sendToStart();
        }

    }

    private void sendToStart() {
        Intent startIntent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();                           //to make sure the user dont come back to main page by pressing back button

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                 //for menu of three dots
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn){               //for signing out
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }
        return true;
    }



}
