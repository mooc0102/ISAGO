package com.example.yucheng.whattowear;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Reason extends AppCompatActivity {
    double latitude = 0, longitude = 0;
    String address; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String road;
    String city;
    String state;
    String country;

    String postalCode;
    String knownName;  // Only if available else return NULL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                longitude = location.getLongitude();
                latitude = location.getLatitude();
                System.out.println(longitude);
                System.out.println(latitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null){
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        }



        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);




        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:  //Upgrade Progress"
                        TextView localtv = (TextView) findViewById(R.id.countryans);
                        localtv.setText(country+"/"+ state+"/"+ city+"/"+ road );
                        break;

                }

        }};
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        new Thread(new Runnable(){
            @Override
            public void run() {

                    try{
                        while(true){
                        if (latitude != 0 || longitude != 0) {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            road = address.substring(address.indexOf("區") + 1, address.indexOf("路") + 1);
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postalCode = addresses.get(0).getPostalCode();
                            knownName = addresses.get(0).getFeatureName();

                            Message msg = new Message();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                            break;
                        }
                            Thread.sleep(500);
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

            }
        }).start();



            Button b = (Button) this.findViewById(R.id.button);

            b.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(Reason.this, Linkapp.class);
                    startActivity(intent);
                    //MainActivity.this.finish();
                }

            });
            Spinner spinner = (Spinner) findViewById(R.id.moodadvans);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"nothing", "happy", "angry", "sad"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Spinner spinner1 = (Spinner) findViewById(R.id.healthans);
            ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"nothing", "health", "normal", "bad"});
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);


        }


    }
