package com.example.yucheng.whattowear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Linkapp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkapp);
        Button b = (Button) this.findViewById(R.id.buttonObj3);

        b.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent intent = new Intent();
                intent.setClass(Linkapp.this, Reason.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }

        });
    }
}
