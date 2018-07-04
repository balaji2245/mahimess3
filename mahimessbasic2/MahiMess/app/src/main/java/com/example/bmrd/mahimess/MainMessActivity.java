package com.example.bmrd.mahimess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class MainMessActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //android layout

    private ImageView mImage;
    private TextView mName;
    private Button mChangeButton;

    private static final int GALLARY_PICK=1;

    private StorageReference mImageStorage;

    private String current_uid;

    private ProgressDialog mProgressDialog;

    private  Bitmap thumb_bitmap;

    private String mealType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mess);

        //Toolbar
        mToolbar=(Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Upload Menu here");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //android toolbar
        mImage=(ImageView) findViewById(R.id.imageView);
        mName=(TextView)findViewById(R.id.mess_name);
        mChangeButton=(Button) findViewById(R.id.change_button);


        mImageStorage= FirebaseStorage.getInstance().getReference();



        ////
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        current_uid=mCurrentUser.getUid();


        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                String name=dataSnapshot.child("name").getValue().toString();
                String image=dataSnapshot.child("thum_image").getValue().toString();

                mName.setText(name);

                //for image uploading
                Picasso.with(MainMessActivity.this).load(image).into(mImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent gallaryIntenet=new Intent();
                gallaryIntenet.setType("image/*");
                gallaryIntenet.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallaryIntenet,"Select Image"),GALLARY_PICK);
            */

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(MainMessActivity.this);

            }
        });


    }catch(Exception e){
        Toast.makeText(MainMessActivity.this,"You are not authorized mess owner please contact the app owner to get authorized or login as student",Toast.LENGTH_LONG).show();

        //sendToStart
        Intent startIntent=new Intent(MainMessActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();                           //to make sure the user dont come back to main page by pressing back button



        // sendToStart();
    }


}
/*
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==){

        }

    }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                //ProgressDialog
                mProgressDialog=new ProgressDialog(MainMessActivity.this);
                mProgressDialog.setTitle("Changing Menu...");
                mProgressDialog.setMessage("Please wait while we upload the image");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                Uri resultUri = result.getUri();

                File thumb_filePath=new File(resultUri.getPath());
                try {
                   thumb_bitmap=new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(35)
                            .compressToBitmap(thumb_filePath);


                } catch (IOException e) {
                    e.printStackTrace();
                }


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();


                StorageReference filepath=mImageStorage.child("menu_images").child(current_uid+random()+".jpg");
                final StorageReference thumb_filepath=mImageStorage.child("menu_images").child("thumbs").child(current_uid+random()+"1.jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){


                            final String download_url=task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);

                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                    String thumb_downloadUrl=thumb_task.getResult().getDownloadUrl().toString();

                           if(thumb_task.isSuccessful()){



                               SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                               String format = s.format(new Date());

                               String totalFormat=getDetailTime(format);


                               Map update_hashMap=new HashMap();
                               update_hashMap.put("image",download_url);
                               update_hashMap.put("thum_image",thumb_downloadUrl);
                               update_hashMap.put("timeStamp",totalFormat);


                               mUserDatabase.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           mProgressDialog.dismiss();
                                           Toast.makeText(MainMessActivity.this,"Menu Uploaded successfully...",Toast.LENGTH_LONG).show();

                                       }
                                   }
                               });

                           }else
                           {
                               Toast.makeText(MainMessActivity.this,"Error in uploading the menu...",Toast.LENGTH_LONG).show();
                               mProgressDialog.dismiss();

                           }


                                }
                            });



                        }
                        else{
                            Toast.makeText(MainMessActivity.this,"Error in uploading the menu...please try again",Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }

                    }
                });


                //Toast.makeText(MainMessActivity.this,""+resultUri,Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(100);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        try {

            mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        current_uid=mCurrentUser.getUid();


        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Toast.makeText(MainMessActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                            String name = dataSnapshot.child("name").getValue().toString();
                    // String image=dataSnapshot.child("image").getValue().toString();
                               //mName.setText(name);

                //for image uploading
                //Picasso.with(MainMessActivity.this).load(image).into(mImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }catch(Exception e){
            Toast.makeText(MainMessActivity.this,"You are not authorized mess owner please contact app owner to get authorized or login as student",Toast.LENGTH_LONG).show();

            //sendToStart
            Intent startIntent=new Intent(MainMessActivity.this,StartActivity.class);
            startActivity(startIntent);
            finish();                           //to make sure the user dont come back to main page by pressing back button



            // sendToStart();
        }


    }
/*
    private void sendToStart() {
        Intent startIntent=new Intent(MainMessActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();                           //to make sure the user dont come back to main page by pressing back button

    }
*/

private String getDetailTime(String format){
    String detailFormat="";

    char d1=format.charAt(0);
    char d2=format.charAt(1);

    //////////getting date here
    String date= new StringBuilder().append(d1).append(d2).toString();

    char m1=format.charAt(2);
    char m2=format.charAt(3);

    String monthString= new StringBuilder().append(m1).append(m2).toString();
    //String monthString=m1+m2+"";

    int monthInt = Integer.parseInt(monthString);

    String[] getMonth = new String[]{"Jan","Feb","March","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};

    ////////getting month here
    String month="";
    for( int i=1;i<=12;i++){
        if (monthInt == i){
            month=getMonth[i-1];

        }
    }

    char hr1=format.charAt(8);
    char hr2=format.charAt(9);

    /////////getting hour here

    String hour= new StringBuilder().append(hr1).append(hr2).toString();



    int hrInt = Integer.parseInt(hour);

    if(hrInt<11 && hrInt>6){
        mealType="DINNER";
    }
    else
        mealType="LUNCH";

    char min1=format.charAt(10);
    char min2=format.charAt(11);

    /////////getting minutes here

    String minute= new StringBuilder().append(min1).append(min2).toString();

    detailFormat="  "+mealType+"("+hour+":"+minute+"  "+month+" "+date+")";
    return detailFormat;
}

}
