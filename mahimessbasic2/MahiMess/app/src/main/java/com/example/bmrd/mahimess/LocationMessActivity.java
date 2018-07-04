package com.example.bmrd.mahimess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LocationMessActivity extends AppCompatActivity {

//    private RecyclerView mMessListL;

    ListView listViewMesses;

    private long totalChild;

    private int count=0;
    private ScrollView scroll;
    private DatabaseReference mMessesDatabase;
    private static DatabaseReference mUserDatabase;
    private Activity context;

    List<Messes> messes;

    private static double lat1;
    private static double lng1;
//    private static double[] indices=new double[10];

    private int i=0;
    private int s=0;
    private static ImageView mImage0;
    private static TextView mText0;
    private static TextView mTextd0;
    private static Button button0;
    private static TextView mText10;



    private static ImageView mImage1;
    private static TextView mText1;
    private static TextView mTextd1;
    private static Button button1;
    private static TextView mText11;


    private static ImageView mImage2;
    private static TextView mText2;
    private static TextView mTextd2;
    private static Button button2;
    private static TextView mText12;


    private static ImageView mImage3;
    private static TextView mText3;
    private static TextView mTextd3;
    private static Button button3;
    private static TextView mText13;


    private static ImageView mImage4;
    private static TextView mText4;
    private static TextView mTextd4;
    private static Button button4;
    private static TextView mText14;


    private static ImageView mImage5;
    private static TextView mText5;
    private static TextView mTextd5;
    private static Button button5;
    private static TextView mText15;


    private static ImageView mImage6;
    private static TextView mText6;
    private static TextView mTextd6;
    private static Button button6;
    private static TextView mText16;


    private static ImageView mImage7;
    private static TextView mText7;
    private static TextView mTextd7;
    private static Button button7;
    private static TextView mText17;


    private static ImageView mImage8;
    private static TextView mText8;
    private static TextView mTextd8;
    private static Button button8;
    private static TextView mText18;


    private static ImageView mImage9;
    private static TextView mText9;
    private static TextView mTextd9;
    private static Button button9;
    private static TextView mText19;
    private ProgressDialog mRegProgress;




    public static Context baseContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_mess);

        scroll=(ScrollView)findViewById(R.id.scrollView2);
        scroll.fullScroll(ScrollView.FOCUS_UP);


        mRegProgress=new ProgressDialog(this);

        mRegProgress.setTitle("Fetching messes near you");
        mRegProgress.setMessage("Breath in ...Breath out...in...out!!");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();


        baseContext = getBaseContext();



        mImage0=(ImageView) findViewById(R.id.imageMess);
        mText0=(TextView)findViewById(R.id.textName);
        mTextd0=(TextView)findViewById(R.id.textDistance);
        mText10=(TextView)findViewById(R.id.textOther);
        button0=(Button) findViewById(R.id.btn0);


        mImage1=(ImageView) findViewById(R.id.imageMess1);
        mText1=(TextView)findViewById(R.id.textName1);
        mTextd1=(TextView)findViewById(R.id.textDistance1);
        mText11=(TextView)findViewById(R.id.textOther1);
        button1=(Button) findViewById(R.id.btn1);

        mImage2=(ImageView) findViewById(R.id.imageMess2);
        mText2=(TextView)findViewById(R.id.textName2);
        mTextd2=(TextView)findViewById(R.id.textDistance2);
        mText12=(TextView)findViewById(R.id.textOther2);
        button2=(Button) findViewById(R.id.btn2);

        mImage3=(ImageView) findViewById(R.id.imageMess3);
        mText3=(TextView)findViewById(R.id.textName3);
        mTextd3=(TextView)findViewById(R.id.textDistance3);
        mText13=(TextView)findViewById(R.id.textOther3);
        button3=(Button) findViewById(R.id.btn3);

        mImage4=(ImageView) findViewById(R.id.imageMess4);
        mText4=(TextView)findViewById(R.id.textName4);
        mTextd4=(TextView)findViewById(R.id.textDistance4);
        button4=(Button) findViewById(R.id.btn4);
        mText14=(TextView)findViewById(R.id.textOther4);


        mImage5=(ImageView) findViewById(R.id.imageMess5);
        mText5=(TextView)findViewById(R.id.textName5);
        mTextd5=(TextView)findViewById(R.id.textDistance5);
        button5=(Button) findViewById(R.id.btn5);
        mText15=(TextView)findViewById(R.id.textOther5);


        mImage6=(ImageView) findViewById(R.id.imageMess6);
        mText6=(TextView)findViewById(R.id.textName6);
        mTextd6=(TextView)findViewById(R.id.textDistance6);
        button6=(Button) findViewById(R.id.btn6);
        mText16=(TextView)findViewById(R.id.textOther6);


        mImage7=(ImageView) findViewById(R.id.imageMess7);
        mText7=(TextView)findViewById(R.id.textName7);
        mTextd7=(TextView)findViewById(R.id.textDistance7);
        button7=(Button) findViewById(R.id.btn7);
        mText17=(TextView)findViewById(R.id.textOther7);


        mImage8=(ImageView) findViewById(R.id.imageMess8);
        mText8=(TextView)findViewById(R.id.textName8);
        mTextd8=(TextView)findViewById(R.id.textDistance8);
        button8=(Button) findViewById(R.id.btn8);
        mText18=(TextView)findViewById(R.id.textOther8);


        mImage9=(ImageView) findViewById(R.id.imageMess9);
        mText9=(TextView)findViewById(R.id.textName9);
        mTextd9=(TextView)findViewById(R.id.textDistance9);
        button9=(Button) findViewById(R.id.btn9);
        mText19=(TextView)findViewById(R.id.textOther9);


        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);


        lat1=MapsActivity.sendLat();
     //   Toast.makeText(LocationMessActivity.this,"Latitude"+lat1+"",Toast.LENGTH_LONG).show();

        lng1=MapsActivity.sendLng();


        listViewMesses = (ListView) findViewById(R.id.listViewMesses);

        messes = new ArrayList<>();

        mMessesDatabase= FirebaseDatabase.getInstance().getReference().child("Users");





        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="0";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="1";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="2";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="3";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="4";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="5";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="6";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="7";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="8";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });


        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value="9";
                Intent i = new Intent(LocationMessActivity.this, Mess0MapsActivity.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });





    }


    @Override
    protected void onStart() {
        super.onStart();
        int p=0;


        if(s==0) {
            scroll.fullScroll(ScrollView.FOCUS_UP);
        }


        //attaching value event listener
        mMessesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                messes.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Messes mess = postSnapshot.getValue(Messes.class);
                    //adding artist to the list
                    messes.add(mess);
                }

                //creating adapter
                MessList messAdapter = new MessList(LocationMessActivity.this, messes);
                //attaching adapter to the listview
                listViewMesses.setAdapter(messAdapter);


                if(s==0) {
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                    mRegProgress.dismiss();                                 //dismiss progress bar onn success
                    s++;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static void sent(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[1]+" *********************hi");

        mTextd0.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");

        button0.setEnabled(true);
        button0.setText("Locate on Map");

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[0]);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("thum_image").getValue().toString();
                    String time = dataSnapshot.child("timeStamp").getValue().toString();

                    mText0.setText(name);
                    mText10.setText(time);


                    //for image uploading

                    Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage0);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }


    public static void sent1(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[1]+" *********************hi");

        mTextd1.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");

        button1.setEnabled(true);
        button1.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[1]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText1.setText(name);
                mText11.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public static void sent2(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[2]+" *********************hi");

        mTextd2.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button2.setEnabled(true);
        button2.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[2]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText2.setText(name);
                mText12.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public static void sent3(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[3]+" *********************hi");

        mTextd3.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button3.setEnabled(true);
        button3.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[3]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText3.setText(name);
                mText13.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage3);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static void sent4(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[4]+" *********************hi");

        mTextd4.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button4.setEnabled(true);
        button4.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[4]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText4.setText(name);
                mText14.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static void sent5(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[5]+" *********************hi");

        mTextd5.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button5.setEnabled(true);
        button5.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[5]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText5.setText(name);
                mText15.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static void sent6(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[6]+" *********************hi");

        mTextd6.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button6.setEnabled(true);
        button6.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[6]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText6.setText(name);
                mText16.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage6);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static void sent7(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[7]+" *********************hi");

        mTextd7.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button7.setEnabled(true);
        button7.setText("Locate on Map");

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[7]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText7.setText(name);
                mText17.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage7);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static void sent8(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[8]+" *********************hi");

        mTextd8.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button8.setEnabled(true);
        button8.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[8]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText8.setText(name);
                mText18.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage8);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static void sent9(double distance) {
        String[] indices = MessList.send();

        System.out.println(indices[9]+" *********************hi");

        mTextd9.setText("Distance from your location : "+new DecimalFormat("##.##").format(distance)+" km");
        button9.setEnabled(true);
        button9.setText("Locate on Map");


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(indices[9]);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thum_image").getValue().toString();
                String time = dataSnapshot.child("timeStamp").getValue().toString();

                mText9.setText(name);
                mText19.setText(time);


                //for image uploading

                Picasso.with(baseContext).load(image).placeholder(R.drawable.pics).into(mImage9);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
