package com.example.update_lab6_paskhin_kislov;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Calendar;

public class _SecondActivity extends AppCompatActivity {
    private EditText mT1;
    private EditText mT2;
    private TextView mT3;
    private TextView mT4;
    private Spinner mT5;
    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mT1 = findViewById(R.id.editName);
        mT2 = findViewById(R.id.editDescription);
        mT3 = findViewById(R.id.textView4);
        mT4 = findViewById(R.id.textView6);
        mT5 = findViewById(R.id.spinner1);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String priority = getIntent().getStringExtra("priority");

        mT1.setText(name);
        mT2.setText(description);
        mT3.setText(date);
        mT4.setText(time);
        ArrayAdapter adapter = (ArrayAdapter) mT5.getAdapter();
        mT5.setSelection(adapter.getPosition(priority));
    }

    public void setDate(View v) {
        new DatePickerDialog(_SecondActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(_SecondActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDate() {
        int day = dateAndTime.get(Calendar.DAY_OF_MONTH);
        int month = dateAndTime.get(Calendar.MONTH);
        int year = dateAndTime.get(Calendar.YEAR);
        mT3.setText((day < 10 ? ("0" + day) : day) + "." + (month < 10 ? ("0" + month) : month) + "." + (year < 10 ? ("0" + year) : year));
    }
    private void setInitialTime() {
        int hours = dateAndTime.get(Calendar.HOUR_OF_DAY);
        int minutes = dateAndTime.get(Calendar.MINUTE);
        mT4.setText((hours < 10 ? ("0" + hours) : hours) + ":" + (minutes < 10 ? ("0" + minutes) : minutes));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    public void onClick(View view){
        String name = mT1.getText().toString();
        String description = mT2.getText().toString();
        String date = mT3.getText().toString();
        String time = mT4.getText().toString();
        String priority = mT5.getSelectedItem().toString();

        if(date.equals("--.--.----")) {
            date = "";
            Toast.makeText(_SecondActivity.this,
                    "Сначала введите дату!", Toast.LENGTH_LONG).show();
            return;
        }
        if(description.equals("") || name.equals("") ) {
            date = "";
            Toast.makeText(_SecondActivity.this,
                    "Не забудьте про описание/имя", Toast.LENGTH_LONG).show();
            return;
        }


        if(time.equals("--:--"))
            time = "";


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("zadachnik.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS zadachi (name TEXT, description TEXT," +
                " data TEXT, time TEXT, priority TEXT,  UNIQUE(name))");
        if(getIntent().getStringExtra("name").equals(name)){
            String bd = "UPDATE zadachi SET description='" + description
                    + "', data='" + date +"', time='" + time + "', priority='"
                    + priority + "' WHERE name='" + name + "'";
            db.execSQL(bd);
        }
        else {
            String bd ="INSERT OR IGNORE INTO zadachi VALUES ('" + name + "', '"
                    + description + "', '" + date + "', '" + time + "', '" + priority + "');";
            db.execSQL(bd);
        }
        Intent intent = new Intent(_SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

