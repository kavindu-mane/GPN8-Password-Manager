package com.hexa2zero.main;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Preference{
    static Preferences preferences = Preferences.userRoot();

    public static void storePreference(String key , String value) throws Exception{
        preferences.put(key , value);
    }

    public static String getPreferences(String key){
        return preferences.get(key , null);
    }

    public static void removePreference(String key){
        preferences.remove(key);
    }

    public static void removeUser(String user) throws BackingStoreException {
        String[] keysArray = preferences.keys();

        for (String s:keysArray) {
            if (s.contains(user + "__")){
                preferences.remove(s);
            }
        }
        preferences.remove(user); /**remove user account details*/

    }
}
