package com.hexa2zero.main;

import com.hexa2zero.decryption.Decryption;
import com.hexa2zero.encryption.Encryption;

import java.util.Random;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;

public class Dashboard extends Encryption implements Decryption {
    static boolean looping; /**loop state*/
    static Scanner scanner = new Scanner(System.in);
    private static String USERNAME;

    Dashboard(){
        USERNAME = LoginRegister.userNameGetter();
    }

    protected void menu() throws Exception{
        Main.clearConsole(); /**for clear current console*/

        do {
            System.out.println("\n  " +ConsoleColors.WHITE_BACKGROUND_BLACK + "************************************************** Dashboard **************************************************" + ConsoleColors.RESET);
            System.out.println("  Create new Password \t\t\t- 1");
            System.out.println("  Create & save new Password \t\t- 2");
            System.out.println("  Get saved Password \t\t\t- 3");
            System.out.println("  Change saved password \t\t- 4");
            System.out.println("  Remove saved password \t\t- 5");
            System.out.println("  Change user account Password \t\t- 6");
            System.out.println("  Remove user Account \t\t\t- 7");
            System.out.println("  Log out \t\t\t\t- 8");
            System.out.println("  ***************************************************************************************************************");

            System.out.print("  Enter choice : ");
            int choice = getIntValue();
            looping = false;

            switch (choice) {
                case 1 -> createNewPassword();
                case 2 -> {
                    String pass = createNewPassword();
                    savePassword(pass);
                }
                case 3 -> getSavedPassword();
                case 4 -> changeSavedPassword();
                case 5 -> removePassword();
                case 6 -> changeUserPassword();
                case 7 -> removeUser();
                case 8 -> Main.exit();
                default -> {
                    System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 Wrong selection" + ConsoleColors.RESET);
                    looping = true;
                }
            }
        }while (looping);

        redirectToDashBoard();
        menu();
    }

    private static String createNewPassword(){
        System.out.print("  Enter password length : ");
        int length = getIntValue();
        Random random = new Random(); /**create random object*/
        StringBuilder password = new StringBuilder(); /**create string builder object*/

        for (int i = 0; i < length; i++) {
            password.append((char) random.nextInt(33, 127));
        }
        System.out.print("\n  Generated password: " + ConsoleColors.GREEN + password + "\n"  + ConsoleColors.RESET);
        return String.valueOf(password);
    }

    private static void savePassword(String password) throws Exception{

        String key;
        do {
            System.out.print("  Enter password saving key (EX: Facebook , Gmail) : ");
            key = scanner.nextLine();
            looping = false;
            if (Preference.getPreferences(USERNAME + "__" + key) != null) {
                System.out.print(ConsoleColors.RED + "\n  \u0021\u0021 " + key + " is already saved key. Replace it ? (Y/Any) : " + ConsoleColors.RESET);
                String letter = scanner.nextLine().toUpperCase();
                if (!letter.equals("Y")) {
                    looping = true;
                }
            }
        }while (looping);
        makeEncryption(String.valueOf(password) , USERNAME + "__"+key); /**password saving key combination ---> username__key */
    }

    private static void getSavedPassword() throws Exception {
        if(!Preference.getAllKeys(USERNAME)){
            return;
        }
        System.out.print("  Enter password key : ");
        String key = scanner.nextLine();
        String fake = Preference.getPreferences(USERNAME + "__" +key);

        if (fake == null){
            System.out.println(ConsoleColors.RED + "  No password found.Recheck entered key and try again" + ConsoleColors.RESET +"\n");
            getSavedPassword();
        }else {
            Decryption decryption = new Dashboard(); /**create interface object*/
            System.out.println("  Your " + key + " password : " + ConsoleColors.GREEN + decryption.makeDecryption(fake) + ConsoleColors.RESET);
        }
    }

    private static void changeSavedPassword(){

    }

    private static void removePassword(){
        System.out.print("  Enter password key : ");
        String key = scanner.nextLine();
        System.out.print("  Are you sure delete " + key + " ? (Y/Any) : ");
        String letter = scanner.nextLine().toUpperCase();
        if (letter.equals("Y")) {
            if (Preference.getPreferences(USERNAME + "__" + key) != null ){
                Preference.removePreference(USERNAME + "__" + key);
                System.out.println(ConsoleColors.GREEN + "  " + key+ " removed" + ConsoleColors.RESET);
            }else {
                System.out.println(ConsoleColors.RED + "  " + key+ " not available" + ConsoleColors.RESET);
            }
        }
    }

    private static void changeUserPassword(){

    }

    private static void removeUser(){

    }

    private static int getIntValue(){
        int value = 0;
        do {
            try {
                value = scanner.nextInt();
                looping = false;
            }catch (Exception e) {
                looping = true;
                removePendingScanner(); /**for remove inputted value*/
                System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 Please enter numerical values only" + ConsoleColors.RESET);
                System.out.print("  Enter again : ");
            }
        }while (looping);
        removePendingScanner(); /**for remove pending inputs*/
        return value;
    }

    public String makeDecryption(String encPassword){
        char[] chars = encPassword.toCharArray();
        int len = encPassword.length();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < len; i+=2) {
            char letter =  (char) (((int) chars[i]) * 2 - (int) chars[len - (i + 1)]);
            password.append(letter);
        }
        return String.valueOf(password);
    }

    protected static void removePendingScanner(){
        if (scanner.hasNextLine()){
            scanner.nextLine();
        }
    }

    private static void redirectToDashBoard(){
        System.out.print("\n  Press enter to continue... ");
        removePendingScanner();
    }
}
