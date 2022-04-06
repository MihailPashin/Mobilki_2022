package com.example.blocknot_paskhin_kislov;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings_LoadActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // загружаем предпочтения из ресурсов
        addPreferencesFromResource(R.xml.preference);
    }
}