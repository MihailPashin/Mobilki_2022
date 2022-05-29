package com.example.lr7_paskhin_kislov;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> paired;

    ListView listView;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        bt = findViewById(R.id.search_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { searching(); }
        });

        listView.setEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                HashMap<String, String> map = (HashMap<String, String>)listView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("Address", map.get("Address"));
                startActivity(intent);
            }
        });

        BA = BluetoothAdapter.getDefaultAdapter();

        if(BA == null)
        {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
        }

        searching();
    }
    public void searching()
    {
        paired = BA.getBondedDevices();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<String, String>();

        for(BluetoothDevice bt : paired)
        {
            map = new HashMap<>();
            map.put("Name", bt.getName());
            map.put("Address", bt.getAddress());
            arrayList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Address"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }
}