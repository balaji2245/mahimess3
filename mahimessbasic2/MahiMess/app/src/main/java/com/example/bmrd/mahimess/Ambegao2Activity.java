package com.example.bmrd.mahimess;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Ambegao2Activity extends AppCompatActivity {

    private RecyclerView mMessList;

    private DatabaseReference mMessesDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambegao2);


        mMessList=(RecyclerView) findViewById(R.id.mess_list);
        mMessList.setHasFixedSize(true);

        mMessList.setLayoutManager(new LinearLayoutManager(this));

        mMessesDatabase= FirebaseDatabase.getInstance().getReference().child("Users");




    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Messes,MessesViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Messes, MessesViewHolder>(
                Messes.class,
                R.layout.mess_single_layout,
                MessesViewHolder.class,
                mMessesDatabase
                        .orderByChild("address").equalTo("Ambegao(BK)")
        ) {
            @Override
            protected void populateViewHolder(MessesViewHolder viewHolder, Messes model, int position) {

                int result=decideToShowOrNot(model.getName(),model.getStatus(),model.getAddress(),model.getThum_image(),getApplicationContext());
             if(result==1)
                viewHolder.setName(model.getName(),model.getStatus(),model.getAddress(),model.getThum_image(),getApplicationContext());
//                viewHolder.setAddress(model.getAddress());
//                viewHolder.setMessImage(model.getThum_image(),getApplicationContext());

            }

            private int decideToShowOrNot(String name, String status, String address, String thum_image, Context applicationContext) {
                if(!status.equals("n")) {

                    return 1;
                }
                return 0;


            }
        };

        mMessList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class MessesViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MessesViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

        }



        public void setName(String name,String status,String address,String thum_image,Context ctx){

    TextView messNameView = (TextView) mView.findViewById(R.id.textView1);
    messNameView.setText(name);

    TextView messAddressView = (TextView) mView.findViewById(R.id.textView11);
    messAddressView.setText(address);

    ImageView messImageView = (ImageView) mView.findViewById(R.id.imageView1);
    Picasso.with(ctx).load(thum_image).placeholder(R.drawable.pics).into(messImageView);


        }

    }

}
