package com.hexa2zero.main;

import java.util.Scanner;

public class LoginRegister {

    private static String userName;
    Scanner scanner = new Scanner(System.in);
    protected void register() throws Exception{
        Main.clearConsole();
        System.out.println("\n\n  " + ConsoleColors.WHITE_BACKGROUND_BLACK  +"*************************************************** Register **************************************************" + ConsoleColors.RESET);
        do {
            System.out.println("  **Username must be greater than 5 characters.   \n  **Password must be greater than 8 characters.");
            System.out.println("  ***************************************************************************************************************");
            System.out.print("  Enter username : ");
            String tmp1 = scanner.nextLine();

            System.out.print("  Enter password : ");
            String tmp2 = scanner.nextLine();
            if (tmp2.length() < 9 || tmp1.length() < 6) {
                System.out.println();
            } else if (Preference.getPreferences(tmp1) != null) {
                System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 This username already reserved." + ConsoleColors.RESET);
            }else {
                Preference.storePreference(tmp1 , tmp2);
                userName = tmp1;
                break;
            }

        }while (true);

    }

    protected boolean login() throws Exception {
        Main.clearConsole();
        System.out.println("\n\n  " + ConsoleColors.WHITE_BACKGROUND_BLACK + "**************************************************** Login ****************************************************" + ConsoleColors.RESET);
        boolean stop ;
        do {
            System.out.print("  Enter username : ");
            String tmp1 = scanner.nextLine();

            System.out.print("  Enter password : ");
            String tmp2 = scanner.nextLine();

            if (String.valueOf(Preference.getPreferences(tmp1)).equals(tmp2)) {
                userName = tmp1;
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
