package com.hexa2zero.main;

import java.util.prefs.Preferences;

public class Preference {
    static Preferences preferences = Preferences.userRoot();

    public static void storePreference(String key , String value){
        preferences.put(key , value);
    }

    public static String getPreferences(String key) {
        return preferences.get(key , null);
    }
}
