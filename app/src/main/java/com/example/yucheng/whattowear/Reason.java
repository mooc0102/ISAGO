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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Reason extends AppCompatActivity implements LocationListener {
    double latitude = 0, longitude = 0, orla = 0, orlo = 0;
    String address; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String road;
    String city;
    String state;
    String country;

    String postalCode;
    String knownName;  // Only if available else return NULL

    private String[] mood = {"nothing", "happy", "angry", "sad"};
    private String[] health = {"nothing", "health", "normal", "bad"};
    int brightness = 0, thickness = 0;
    private String[] tainanact = {"nothing", "吉米音樂劇<<向前走向右走>>", "觀光工廠創意王國體驗"};
    private String[] no = {"nothing"};
    private LocationManager locationManager;
    private LocationListener locationListener;
    ArrayList<String> hislocationtime = new ArrayList<String>();
    ArrayList<String> hislocationlo = new ArrayList<String>();
    ArrayList<String> hislocationla = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        Spinner spinner = (Spinner) findViewById(R.id.moodadvans);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mood);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                TextView msco = (TextView) findViewById(R.id.moodsco);
                switch (mood[position]) {
                    case "nothing":
                        msco.setText("(0, 0)");
                        break;
                    case "angry":
                        msco.setText("(-2, -2)");
                        brightness += -2;
                        thickness += -2;
                        break;
                    case "sad":
                        msco.setText("(3, 3)");
                        brightness += 3;
                        thickness += 3;
                        break;
                    case "happy":
                        msco.setText("(2, 2)");
                        brightness += 2;
                        thickness += 2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        Spinner spinner1 = (Spinner) findViewById(R.id.healthans);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, health);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                TextView hsco = (TextView) findViewById(R.id.healthsco);
                switch (health[position]) {
                    case "nothing":
                        hsco.setText("(0, 0)");
                        break;
                    case "health":
                        hsco.setText("(0, -2)");
                        brightness += 0;
                        thickness += -2;
                        break;
                    case "normal":
                        hsco.setText("(0, 1)");
                        brightness += 0;
                        thickness += 1;
                        break;
                    case "bad":
                        hsco.setText("(4, 4)");
                        brightness += 4;
                        thickness += 4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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

        TextView t = (TextView) this.findViewById(R.id.textView2);
        t.setClickable(true);
        t.setFocusable(true);
        t.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                System.out.println("test....");
                Intent intent = new Intent();
                intent.setClass(Reason.this, gps.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("histime",hislocationtime);
                bundle.putStringArrayList("hisla",hislocationla);
                bundle.putStringArrayList("hislo",hislocationlo);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });


        Button r = (Button) this.findViewById(R.id.buttonr);

        r.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(Reason.this, result.class);
                intent.putExtra("new_variable_name", brightness + ", " + thickness);
                startActivity(intent);

            }

        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);





        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        TextView localtv = (TextView) findViewById(R.id.countryans);
                        localtv.setText(country + "/" + state + "/" + city + "/" + road);
                        break;

                }

            }
        };
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {
                        if (Math.abs(latitude - orla) + Math.abs(longitude - orlo) > 0.00001) {
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
                            orla = latitude;
                            orlo = longitude;

                            int year  = (Calendar.getInstance()).get(Calendar.YEAR);
                            int month = (Calendar.getInstance()).get(Calendar.MONTH);
                            int date = (Calendar.getInstance()).get(Calendar.DATE);
                            int hours = (Calendar.getInstance()).get(Calendar.HOUR);
                            int minutes = (Calendar.getInstance()).get(Calendar.MINUTE);
                            int seconds = (Calendar.getInstance()).get(Calendar.SECOND);

                            //year/month/date/hours:minutes:seconds
                            hislocationtime.add(year+"/"+month+"/"+date+"  "+hours+":"+minutes+":"+seconds);
                            hislocationla.add(String.valueOf(latitude));
                            hislocationlo.add(String.valueOf(longitude));

                        }
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        System.out.println(latitude);
        System.out.println(longitude);
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
}