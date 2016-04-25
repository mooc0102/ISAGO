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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.*;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) this.findViewById(R.id.buttonObj);
        Button b1 = (Button) this.findViewById(R.id.buttonObj1);





        b.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Reason.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }

        });

        b1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                MainActivity.this.finish();
            }

        });
    };

}

