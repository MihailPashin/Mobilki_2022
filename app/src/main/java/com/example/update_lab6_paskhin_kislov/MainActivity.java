package com.example.update_lab6_paskhin_kislov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<State_of_Task> states = new ArrayList<State_of_Task>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitialData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cleanAll:
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("zadachnik.db", MODE_PRIVATE, null);
                for(int i = 0; i < states.size(); i++)
                {
                    db.execSQL("DELETE FROM zadachi WHERE name='" + states.get(i).getName() + "'");
                }
                states.clear();
                setInitialData();
                return true;
            case R.id.action_exit:
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            default:
                return true;
        }

    }

    private void setInitialData(){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("zadachnik.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS zadachi (name TEXT, description TEXT, data TEXT, time TEXT, priority TEXT, UNIQUE(name))");

        Cursor query = db.rawQuery("SELECT * FROM zadachi;", null);

        while (query.moveToNext()) {
            String name = query.getString(0);
            String description = query.getString(1);
            String date = query.getString(2);
            String time = query.getString(3);
            String priority = query.getString(4);
            states.add(new State_of_Task(name, description, date, time, priority));
        }
        query.close();
        db.close();

        RecyclerView recyclerView = findViewById(R.id.list);
        // создаем адаптер
        Adapter_State adapter = new Adapter_State(this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }
    public void onClick(View view){
        startSecond("", "", "--.--.----", "--:--", "Высокий");
    }

    public void startSecond(String name, String description, String date, String time, String priority)
    {
        Intent intent = new Intent(MainActivity.this, _SecondActivity.class);

        intent.putExtra("name", name);
        intent.putExtra("description", description);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("priority", priority);

        startActivity(intent);
    }
}