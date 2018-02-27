package com.example.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by peterpomlett on 17/02/2018.
 * to display settings
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        // currently only have one preference so it will be at index 0
        Preference p = prefScreen.getPreference(0);
        String value = sharedPreferences.getString(p.getKey(), "");
        setTheSummary(p, value);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (null != preference) {
            // Updates the summary for the preference when it is changed
            String value = sharedPreferences.getString(preference.getKey(), "");
            setTheSummary(preference, value);
        }
    }

    private void setTheSummary(Preference preference, String value) {
        // For list preferences, figure out the label of the selected value
        //In this case we only have one preference and it is a ListPreference so we can safely cast it
        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(value);
        if (prefIndex >= 0) {
            // Set the summary to that label
            listPreference.setSummary(listPreference.getEntries()[prefIndex]);
        }
    }

    //Register change listener
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    //UnRegister change listener(clean up)
    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
