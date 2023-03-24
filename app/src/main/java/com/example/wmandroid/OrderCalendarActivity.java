package com.example.wmandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

public class OrderCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_calendar);
        calendarView = findViewById(R.id.calendarView);
        date = findViewById(R.id.date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String todayDate = month + "/" + dayOfMonth + "/" + year;
                Log.d("date",todayDate);
                date.setText(todayDate);
            }
        });
    }
}