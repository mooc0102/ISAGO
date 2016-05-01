package com.example.yucheng.whattowear;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class gps extends AppCompatActivity {
    ArrayList<String> hislocationtime ;
    ArrayList<String> hislocationlo ;
    ArrayList<String> hislocationla ;
   // ArrayList<String> histimepluslocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        Bundle bundle =this.getIntent().getExtras();

        hislocationtime = bundle.getStringArrayList("histime");
        hislocationlo = bundle.getStringArrayList("hislo");
        hislocationla = bundle.getStringArrayList("hisla");

        final String ID_TITLE = "TITLE", ID_SUBTITLE = "SUBTITLE";
        ArrayList<HashMap<String,String>> myListData = new ArrayList<HashMap<String,String>>();
        String[] timelist = new String[hislocationtime.size()];
        String[] geo = new String[hislocationlo.size()];

        for(int i = 0; i < hislocationtime.size(); i++){
            timelist[i] = hislocationtime.get(i);
            geo[i] = "( "+hislocationlo.get(i) +" , "+hislocationla.get(i)+" )";
        }

        for( int i=0;i<timelist.length ; ++i) {
            HashMap<String,String> item = new HashMap<String,String>();
            item.put(ID_TITLE,timelist[i]);
            item.put(ID_SUBTITLE,geo[i]);
            myListData.add(item);
        }

        ListView mListView = (ListView)findViewById(R.id.listView);
        if( mListView != null ) {
            mListView.setAdapter(
                    new SimpleAdapter(
                            this,
                            myListData ,
                            android.R.layout.simple_list_item_2,
                            new String[] {ID_TITLE, ID_SUBTITLE},
                            new int[] { android.R.id.text1, android.R.id.text2 }
                    )
            );
        }
    }
}
