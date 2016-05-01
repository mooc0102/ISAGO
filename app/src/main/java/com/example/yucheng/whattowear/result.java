package com.example.yucheng.whattowear;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("new_variable_name");
        }
        TextView answer = (TextView)findViewById(R.id.answer);
        answer.setText("{休閒, "+value+" }");
    }
}
