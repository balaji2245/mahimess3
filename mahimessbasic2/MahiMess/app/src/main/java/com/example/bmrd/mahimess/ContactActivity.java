package com.example.bmrd.mahimess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private DatabaseReference mUserDatabase;

    private TextView mName;
    private TextView mEmail;
    private TextView mEdu;
    private TextView mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Toolbar
        mToolbar=(Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Contact App Developer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mName=(TextView)findViewById(R.id.nameText);
        mEmail=(TextView)findViewById(R.id.emailText);
        mEdu=(TextView)findViewById(R.id.eduText);
        mContact=(TextView)findViewById(R.id.contactText);


        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child("admin");


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name=dataSnapshot.child("name").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String edu=dataSnapshot.child("edu").getValue().toString();
                String contact=dataSnapshot.child("contact").getValue().toString();


                //  String image=dataSnapshot.child("image").getValue().toString();

                mName.setText(name);
                mEmail.setText(email);
                mEdu.setText(edu);
                mContact.setText(contact);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
