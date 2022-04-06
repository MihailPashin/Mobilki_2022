package com.example.blocknot_paskhin_kislov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private final static String FILENAME = "sample.txt"; // имя файла
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.editText);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open:
                openFile(FILENAME);
                return true;
            case R.id.action_save:
                saveFile(FILENAME);
                return true;
            case R.id.action_close:
                this.finish();
                return true;
            case R.id.action_clear:
                mEditText = (EditText) findViewById(R.id.editText);
                mEditText.getText().clear();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(this, Settings_LoadActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        // читаем установленное значение из CheckBoxPreference
        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
            openFile(FILENAME);
        }

        try{
            float fSize = Float.parseFloat(prefs.getString(getString(R.string.pref_size), "28"));
            if ((fSize < 28) || (fSize > 36)) {
                fSize=28;
//Как изменить место, куда вводили число????
            }
// применяем настройки в текстовом поле
            mEditText.setTextSize(fSize);
            // читаем стили текста из ListPreference
            String regular = prefs.getString(getString(R.string.pref_style), "");

            int typeface = Typeface.NORMAL;
            if (regular.contains("Черный"))
                mEditText.setTextColor(getResources().getColor(R.color.black));
            if (regular.contains("Красный"))
                mEditText.setTextColor(getResources().getColor(R.color.red));
            if (regular.contains("Зеленый"))
                mEditText.setTextColor(getResources().getColor(R.color.green));
            if (regular.contains("Синий"))
                mEditText.setTextColor(getResources().getColor(R.color.blue));

            String current_font = prefs.getString(getString(R.string.user_font), "");
            Typeface font = Typeface.DEFAULT;
            if (current_font.contains("cyrillic_goth"))
            {
                font = ResourcesCompat.getFont(this, R.font.cyrillicgoth);
            }
            if (current_font.contains("calligraph"))
            {
                font = ResourcesCompat.getFont(this, R.font.calligraph);
            }
            if (current_font.contains("cyrillic_old"))
            {
                font = ResourcesCompat.getFont(this, R.font.cyrillicold);
            }


            LinearLayout layout = (LinearLayout)findViewById(R.id.osnova);
            if (prefs.getBoolean(getString(R.string.background), false)) {

                layout.setBackgroundResource(R.drawable.background_);
            }
            else
            {
                layout.setBackgroundResource(R.color.white);
            }

            mEditText.setTypeface(font);

        }
        catch(Exception e)
        {
            Toast toast = Toast.makeText(this, "Ошибка ",Toast.LENGTH_LONG);
            toast.show();

        }

    }



    // Метод для открытия файла
    private void openFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                mEditText.setText(builder.toString());
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    // Метод для сохранения файла
    private void saveFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(mEditText.getText().toString());
            osw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

}