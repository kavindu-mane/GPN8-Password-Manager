package com.hexa2zero.main;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);  /**Create Scanner object*/

    public static void main(String[] args) {

        LoginRegister loginRegister = new LoginRegister();  /**Create LoginRegister object*/
        String[] tempArray;
        int returnValue;

        System.out.print("Enter 'R or r' for Register | Enter 'L or l' for Login : ");

        tempArray = new String[]{"R", "L"};
        returnValue = getInput(tempArray);
        if (returnValue == 0){
            loginRegister.register();
        }else {
            if (!loginRegister.login()) exit();
        }
        Dashboard dashboard = new Dashboard();
        dashboard.menu();
    }

    private static int getInput(String[] arg){
        do {
            try {
                String letter = scanner.nextLine().toUpperCase();
                if (letter.equals(arg[0])){
                   return 0;
                }else if (letter.equals(arg[1])){
                    return 1;
                }else {
                    throw new IOException();
                }
            }catch (Exception e){
                System.out.println("Please enter valid values");
                System.out.print("Enter again : ");
            }
        }while (true);
    }

    private static void exit(){
        System.out.println("\n\n*************** Bye. ***************\n");
        System.exit(0);
    }
}