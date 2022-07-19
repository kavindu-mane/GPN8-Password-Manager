package com.hexa2zero.encryption;

import com.hexa2zero.main.Preference;
import java.util.ArrayList;
import java.util.Random;

public class Encryption {

    protected static void makeEncryption(String realPassword , String key) throws Exception{
        Random random = new Random(); /**create random object*/
        StringBuilder password = new StringBuilder(); /**create string builder object*/
        int length = realPassword.length();
        char[] realPasswordArray = realPassword.toCharArray();
        ArrayList<Character> charArray = new ArrayList<>();

        for (int i = 0; i < length; i++) {
             int second = random.nextInt(33, 127);
             int fakeValue = second + (int) realPasswordArray[i];

             if (fakeValue % 2 == 1){
                 fakeValue++;
                 second++;
             }
             fakeValue /=2;
             charArray.add((char) fakeValue);
             charArray.add((char) second);
        }
        for (int i = 0; i < length * 2; i +=2) {
            password.append(charArray.get(i));
            password.append(charArray.get((length * 2) - (1 + i)));
        }
        savePassword(String.valueOf(password) , key);
    }

    private static void savePassword(String password , String key) throws Exception{
        Preference.storePreference(key , password);
    }
}
