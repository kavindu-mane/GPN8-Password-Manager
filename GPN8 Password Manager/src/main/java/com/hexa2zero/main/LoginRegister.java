package com.hexa2zero.main;

import com.hexa2zero.decryption.Decryption;
import com.hexa2zero.encryption.Encryption;

import java.util.Scanner;
import java.util.regex.Pattern;

public class LoginRegister {

    private static String userName;
    Scanner scanner = new Scanner(System.in);
    protected void register() throws Exception{
        Main.clearConsole();
        System.out.println("\n\n  " + ConsoleColors.WHITE_BACKGROUND_BLACK  +"*************************************************** Register **************************************************" + ConsoleColors.RESET);
        do {
            System.out.println("  **Username must be greater than 5 characters  \n  **Username can only contain include 0-9, a-z, A-Z and - \n  **Password must be greater than 8 characters ");
            System.out.println("  ***************************************************************************************************************");
            System.out.print("  Enter username : ");
            String user = scanner.nextLine();

            System.out.print("  Enter password : ");
            String password = scanner.nextLine();
            if (password.length() < 9 || user.length() < 6 || !characterCheck(user)) {
                System.out.println();
            } else if (Preference.getPreferences(user) != null) {
                System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 This username already reserved." + ConsoleColors.RESET);
            }else {
//                Preference.storePreference(user , password);
                Encryption.makeEncryption(password , user);
                userName = user;
                break;
            }

        }while (true);
    }

    private static boolean characterCheck(String user){
        Pattern pattern = Pattern.compile("[a-zA-Z\\d-]+");
        return pattern.matcher(user).matches();
    }

    protected boolean login() throws Exception {
        Main.clearConsole();
        System.out.println("\n\n  " + ConsoleColors.WHITE_BACKGROUND_BLACK + "**************************************************** Login ****************************************************" + ConsoleColors.RESET);
        boolean stop ;
        do {
            System.out.print("  Enter username : ");
            String user = scanner.nextLine();

            System.out.print("  Enter password : ");
            String password = scanner.nextLine();

            Decryption decryption = new Dashboard();  /**create dashboard object*/
            if (decryption.makeDecryption(Preference.getPreferences(user)).equals(password)) {
                userName = user;
                return true;
            } else {
                System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 Incorrect Username Password combination." + ConsoleColors.RESET);
                System.out.println("  ***************************************************************************************************************");
                System.out.print("  Retry \t-----> R or r \n  Exit \t\t-----> Any\n");
                System.out.println("  ***************************************************************************************************************");
                System.out.print("  Enter selection : ");
                if (scanner.nextLine().equalsIgnoreCase("R")) {
                    stop = true;
                    System.out.println();
                }else {
                    stop = false;
                }
            }
        }while (stop);
        return false;
    }

    protected static String userNameGetter(){  /**for return private username filed*/
        return userName;
    }

}
