package com.hexa2zero.main;

import com.hexa2zero.decryption.Decryption;
import com.hexa2zero.encryption.Encryption;

import java.util.Random;
import java.util.Scanner;

public class Dashboard extends Encryption implements Decryption {
    public static boolean looping; /**loop state*/
    static boolean waitingOrNot = false; /**redirectToDashBoard() method working */
    static Scanner scanner;
    private static final Decryption decryption = new Dashboard(); /**create interface object using upcasting*/
    private static String USERNAME;

    Dashboard(){
        USERNAME = LoginRegister.userNameGetter();
        scanner  = new Scanner(System.in);
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
                case 3 -> getAvailablePasswords(0);
                case 4 -> getAvailablePasswords(1);
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

    private static void savePassword(String password){
        String key;
        do {
            System.out.print("  Enter password saving key (EX: Facebook , Gmail) : ");
            key = scanner.nextLine();
            looping = false;
            if (key.equals("")){
                Main.errorText( "Cannot add empty key." , "  Retry \t-----> R or r \n  Back \t\t-----> Any");
                loopingContinueOrNot();
            }else if (Preference.getPreferences(USERNAME + "__" + key) != null ) {
                Main.errorText( key + " is already saved key." , "  Retry \t-----> R or r \n  Replace \t-----> Any");
                loopingContinueOrNot();
                waitingOrNot= false; /**for change looping & waiting states*/
            }
        }while (looping);

        if (!key.equals("")){
            makeEncryption(String.valueOf(password) , USERNAME + "__"+key); /**password saving key combination ---> username__key */
            System.out.println(ConsoleColors.GREEN + "  Password saved" + ConsoleColors.RESET);
        }
    }

    private static void getAvailablePasswords(int mode) throws Exception{
        if(!Preference.getAllKeys(USERNAME)){
            return;
        }
        System.out.print("  Enter password key : ");
        String key = scanner.nextLine();
        String fake = Preference.getPreferences(USERNAME + "__" +key);

        if (fake == null){
            System.out.println(ConsoleColors.RED + "  No password found.Recheck entered key and try again" + ConsoleColors.RESET +"\n");
            getAvailablePasswords(mode);
        }else {
            if (mode == 0){
                getRequestPassword(fake , key);
            }else {
                changeSavedPassword(key);
            }
        }
    }

    private static void getRequestPassword(String fake , String key){
        System.out.println("  Your " + key + " password : " + ConsoleColors.GREEN + decryption.makeDecryption(fake) + ConsoleColors.RESET);
    }

    private static void changeSavedPassword(String key) {
        System.out.println("  ***************************************************************************************************************");
        System.out.println("  Create password ------> C or c\n  Enter Password  ------> Any");
        System.out.println("  ***************************************************************************************************************");
        System.out.print("  Enter choice : ");
        String password;
        if (scanner.nextLine().equalsIgnoreCase("C")){
            password = createNewPassword();
        }else {
            System.out.print("  Enter password : ");
            password = scanner.nextLine();
        }
        makeEncryption(password , USERNAME + "__"+key); /**password saving key combination ---> username__key */
        System.out.println(ConsoleColors.GREEN + "  Password changed" + ConsoleColors.RESET);
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
        do {
            System.out.print("  Enter current password : ");
            String password = scanner.nextLine();
            if (checkPassword(password)) {
                do {
                    System.out.print("  Enter new password : ");
                    password = scanner.nextLine();
                    if (password.length() < 9) {
                        System.out.println(ConsoleColors.RED + "  Password must be greater than 8 characters\n" + ConsoleColors.RESET);
                    }else {
                        break;
                    }
                }while (true);

                makeEncryption(password , USERNAME);
                System.out.println(ConsoleColors.GREEN + "  Account password changed" + ConsoleColors.RESET);
            } else {
                Main.errorText("Incorrect Username Password combination.","  Retry \t-----> R or r \n  Back \t\t-----> Any");
                loopingContinueOrNot();
            }
        }while (looping);
    }

    private static void removeUser() throws Exception{
        do {
            System.out.print("  Enter current password : ");
            String password = scanner.nextLine();
            if (checkPassword(password)) {
                System.out.print(ConsoleColors.RED + "\n  \u0021\u0021 Are you sure delete " + USERNAME + " ? (Y/Any) : " + ConsoleColors.RESET);
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    Preference.removeUser(USERNAME);
                    Main.getStartMode();
                }else {
                    looping = false;
                    waitingOrNot = true;
                }

            }else {
                Main.errorText("Incorrect Username Password combination." , "  Retry \t-----> R or r \n  Back \t\t-----> Any");
                loopingContinueOrNot();
            }
        }while (looping);
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

    private static boolean checkPassword(String password){
        return decryption.makeDecryption(Preference.getPreferences(USERNAME)).equals(password);
    }

    private static void redirectToDashBoard(){
        if (!waitingOrNot){
            System.out.print("\n  Press enter to continue... ");
            removePendingScanner();
        }else {
            waitingOrNot = false;
        }
    }

    protected static void loopingContinueOrNot(){
        if (scanner.nextLine().equalsIgnoreCase("R")) {
            looping = true;
            System.out.println();
        } else {
            looping = false;
            waitingOrNot = true;
        }
    }
}
