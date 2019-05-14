package com.example.mislugares.presentacion;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.example.mislugares.R;

public class PreferenciasFragment extends PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
