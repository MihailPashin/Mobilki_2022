package com.example.laba6_paskhin_kislov;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class _SecondActivity extends AppCompatActivity {
    private EditText mT1;
    private EditText mT2;
    private TextView mT3;
    private TextView mT4;
    private String mT5;
    Calendar dateAndTime=Calendar.getInstance();
    String[] pr = { "Высокий", "Низкий"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Spinner spinner = findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pr);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                mT5 = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);


        mT1 = findViewById(R.id.editName);
        mT2 = findViewById(R.id.editDescription);
        mT3 = findViewById(R.id.textView4);
        mT4 = findViewById(R.id.textView6);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        int priority = getIntent().getIntExtra("priority", 1);

        if(priority == 1)
            spinner.setSelection(1);
        else if(priority == 2)
            spinner.setSelection(0);
        mT1.setText(name);
        mT2.setText(description);
        mT3.setText(date);
        mT4.setText(time);
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
        int priority = 1;

        if(mT5.equals("Высокий"))
            priority = 2;

        if(date.equals("--.--.----")) {
            date = "";
            Toast.makeText(_SecondActivity.this, "Сначла введите дату!", Toast.LENGTH_LONG).show();
            return;
        }

        if(time.equals("--:--")) {
            time = "";
        }

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("zadachnik1.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS zadachi (name TEXT, description TEXT, data TEXT, time TEXT, priority INTEGER, UNIQUE(name))");
        if(!name.equals(""))
        {
            if(getIntent().getStringExtra("name").equals(name)){
                db.execSQL("UPDATE zadachi SET description='" + description + "', data='" + date +"', time='" + time + "', priority='" + priority +"' WHERE name='" + name + "'");
            }
            else {
                db.execSQL("INSERT OR IGNORE INTO zadachi VALUES ('" + name + "', '" + description + "', '" + date + "', '" + time + "', " + priority + ");");
            }
            Intent intent = new Intent(_SecondActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(_SecondActivity.this, "Сначла введите название!", Toast.LENGTH_LONG).show();
            return;
        }
    }
}


