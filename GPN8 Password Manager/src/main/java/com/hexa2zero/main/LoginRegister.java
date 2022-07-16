package com.hexa2zero.main;

import java.util.Scanner;

public class LoginRegister {
    Scanner scanner = new Scanner(System.in);
    protected void register(){

        do {
            System.out.println("**Username must be greater than 5 characters. \n**Password must be greater than 8 characters.");
            System.out.print("Enter username : ");
            String tmp1 = scanner.nextLine();

            System.out.print("Enter password : ");
            String tmp2 = scanner.nextLine();
            if (tmp2.length() < 9 || tmp1.length() < 6) continue;

            Preference.storePreference(tmp1 , tmp2);
            break;
        }while (true);

    }

    protected boolean login(){
        boolean stop = false;
        do {
            System.out.print("Enter username : ");
            String tmp1 = scanner.nextLine();

            System.out.print("Enter password : ");
            String tmp2 = scanner.nextLine();

            if (String.valueOf(Preference.getPreferences(tmp1)).equals(tmp2)) {
                return true;
            } else {
                System.out.println("Incorrect Username Password combination.");
                System.out.println("Enter R for Retry and any other for Exit.");
                if (scanner.nextLine().equalsIgnoreCase("R")) {
                    stop = true;
                }
            }
        }while (stop);
        return false;
    }
}
