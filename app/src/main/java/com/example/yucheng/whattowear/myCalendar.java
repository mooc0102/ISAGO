package com.example.yucheng.whattowear;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class myCalendar extends AppCompatActivity {
    private Button dateButton;
    private Button ndateButton;
    private Calendar calendar;
    private int sYear, sMonth, sDay;
    private int eYear, eMonth, eDay;
    private TextView dateText;
    private DatePickerDialog datePickerDialog;
    private Account[] accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        calendar = Calendar.getInstance();
        sYear = calendar.get(Calendar.YEAR);
        sMonth = calendar.get(Calendar.MONTH);
        sDay = calendar.get(Calendar.DAY_OF_MONTH);


        dateText = (TextView) findViewById(R.id.textView);
        DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker DPicker, int year, int month, int day) {
                int id = DPicker.getId();
                if(id == R.id.startDatePicker) {
                    sYear = year;
                    sMonth = month;
                    sDay = day;
                } else {
                    eYear = year;
                    eMonth = month;
                    eDay = day;
                }
            }
        };

        DatePicker startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        DatePicker endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);

        startDatePicker.init(sYear, sMonth, sDay, dateChangedListener);
        endDatePicker.init(sYear, sMonth, sDay, dateChangedListener);
        //startDatePicker.ge

    }

   // CalendarContentResolver calendarContent = new CalendarContentResolver(MainActivity.this);
/*
    @Override
    protected Dialog onCreateDialog(int id) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                dateText.append("你設定的日期event為" + setDateFormat(year, month, day) + "\n");
                test(myCalendar.this);

            }

        }, mYear, mMonth, mDay);

        return datePickerDialog;
    }
*/
    private String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    private void getEvent() {
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        beginTime.set(sYear, 4, 27, 7, 30);
        beginTime.set(2016, 4, 27, 8, 30);
        startMillis = beginTime.getTimeInMillis();
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();

    }

    private void getAcc() {
        accounts = AccountManager.get(this.getApplicationContext()).getAccounts();
        TextView tmp = (TextView)findViewById(R.id.textView2);
        for(Account acc : accounts) {
            tmp.append(acc.name + "\n");
        }
    }

    private void test(Context ctx){
        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION};

// 0 = January, 1 = February, ...

        Calendar startTime = Calendar.getInstance();
        startTime.set(sYear, sMonth, sDay, 00, 01);

        Calendar endTime = Calendar.getInstance();
        endTime.set(eYear, eMonth, eDay, 23, 59);

        //Toast.makeText(this.getApplicationContext(), "Title: " + cursor.getString(1) + " Start-Time: " + (new Date(cursor.getLong(3))).toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this.getApplicationContext(), setDateFormat(sYear, mMonth, mDay), Toast.LENGTH_LONG).show();
// the range is all data from 2014

        String selection = "(( " + CalendarContract.Events.ACCOUNT_NAME + " = ?) AND ("
                                + CalendarContract.Events.ACCOUNT_TYPE + " = ?) AND ("
                                + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( "
                                + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";
        String[] selectionArgs = new String[] {"a0976820406@gmail.com", "com.google"};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = ctx.getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, selectionArgs, null);
        //Cursor cursor = ctx.getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

// output the events
        TextView eventText = (TextView)findViewById(R.id.textView2);
        eventText.setText("");
        if (cursor.moveToFirst()) {
            do {
                eventText.append( "Title: " + cursor.getString(1) + " Start-Time: " + (new Date(cursor.getLong(3))).toString() + "\n");
                //Toast.makeText(this.getApplicationContext(), "Title: " + cursor.getString(1) + " Start-Time: " + (new Date(cursor.getLong(3))).toString(), Toast.LENGTH_LONG).show();
            } while ( cursor.moveToNext());
        }
    }
}
