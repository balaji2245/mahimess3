package com.example.bmrd.mahimess;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class MessList extends ArrayAdapter<Messes> {
    private Activity context;
    List<Messes> messes;

    private static double lat1;

    private static double lng1;

    private static String[] indices=new String[10];
    private static String[] latt22=new String[10];
    private static String[] lngg22=new String[10];



    private int i=0;


    public MessList(Activity context, List<Messes> messes) {
        super(context, R.layout.layout_messes_view, messes);
        this.context = context;
        this.messes = messes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_messes_view, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Messes mess = messes.get(position);
        /////////////////////

        String index=mess.getIndex()+"";
        String latt2=mess.getLatt2();
        String lngg2=mess.getLngg2();

        lat1=MapsActivity.sendLat();
        //   Toast.makeText(LocationMessActivity.this,"Latitude"+lat1+"",Toast.LENGTH_LONG).show();

        lng1=MapsActivity.sendLng();


        double distance=decideToShowOrNot(latt2,lngg2);

        System.out.println(distance);

        if(i<10 && distance>0 && distance<3) {

///////////
            indices[i]= index;

            latt22[i]=latt2;
            lngg22[i]=lngg2;

            System.out.println(indices[i]+"      decideToShowOrNot");

            if(i==0)
                                        LocationMessActivity.sent(distance);

            if(i==1)
                LocationMessActivity.sent1(distance);


            if(i==2)
                LocationMessActivity.sent2(distance);


            if(i==3)
                LocationMessActivity.sent3(distance);


            if(i==4)
                LocationMessActivity.sent4(distance);


            if(i==5)
                LocationMessActivity.sent5(distance);


            if(i==6)
                LocationMessActivity.sent6(distance);


            if(i==7)
                LocationMessActivity.sent7(distance);


            if(i==8)
                LocationMessActivity.sent8(distance);


            if(i==9)
                LocationMessActivity.sent9(distance);



            textViewName.setText(mess.getIndex());
            i++;
//////////////

        }

        return listViewItem;
    }


    public static String[] send(){

        return indices;
    }

    public static String[] sendLatt22(){

        return latt22;
    }

    public static String[] sendLngg22(){

        return lngg22;
    }




    private double decideToShowOrNot(String latt2, String lngg2) {

        if(!latt2.equals("")) {

            double lat2 = Double.parseDouble(latt2);
            double lng2 = Double.parseDouble(lngg2);
     //       Toast.makeText(LocationMessActivity.this, "I am in decideToShowOrNot" + "", Toast.LENGTH_LONG).show();

            double distance = getDistanceFromLatLonInKm(lat1, lng1, lat2, lng2);

//            Toast.makeText(LocationMessActivity.this, "Distance=" + distance + "", Toast.LENGTH_LONG).show();

            return distance;
        }
        else

            return -1;


    }




    public double getDistanceFromLatLonInKm (double lat1,double lon1,double lat2,double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);

       // Toast.makeText(MessList.this, "calculating distance" + "", Toast.LENGTH_LONG).show();

        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }


}