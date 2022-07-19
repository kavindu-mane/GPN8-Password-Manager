package com.hexa2zero.main;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);  /**Create Scanner object*/

    public static void main(String[] args) throws Exception{

        LoginRegister loginRegister = new LoginRegister();  /**Create LoginRegister object*/
        int returnValue;

        System.out.println(ConsoleColors.GREEN +  "\n\n"+"""
                \t\t\t\t ##########  ##########  ###      ##  ##########
                \t\t\t\t ##      ##  ##      ##  ## #     ##  ##      ##
                \t\t\t\t ##          ##      ##  ##  #    ##  ##      ##
                \t\t\t\t ##  ######  ##########  ##   #   ##  ##########
                \t\t\t\t ##      ##  ##          ##    #  ##  ##      ##
                \t\t\t\t ##      ##  ##          ##     # ##  ##      ##
                \t\t\t\t ##########  ##          ##      ###  ##########
        
                  ##########        ##        ##########  ##########  ##                   ##  ##########  ##########  ##########
                  ##      ##       ####       ##          ##           ##                 ##   ##      ##  ##      ##   #      ##
                  ##      ##      ##  ##      ##          ##            ##               ##    ##      ##  ##      ##   #      ##
                  ##########     ########     ##########  ##########     ##     ###     ##     ##      ##  ##########   #      ##
                  ##            ##      ##            ##          ##      ##   ## ##   ##      ##      ##  ##  ##       #      ##
                  ##           ##        ##           ##          ##       ## ##   ## ##       ##      ##  ##    ##     #      ##
                  ##          ##          ##  ##########  ##########        ###     ###        ##########  ##      ##  ##########

                \t    ###     ###        ##        ###      ##        ##        ##########  ##########  ##########
                \t    ## #   # ##       ####       ## #     ##       ####       ##      ##  ##          ##      ##
                \t    ##  # #  ##      ##  ##      ##  #    ##      ##  ##      ##          ##          ##      ##
                \t    ##   #   ##     ########     ##   #   ##     ########     ##  ######  ##########  ##########
                \t    ##       ##    ##      ##    ##    #  ##    ##      ##    ##      ##  ##          ##  ##
                \t    ##       ##   ##        ##   ##     # ##   ##        ##   ##      ##  ##          ##    ##
                \t    ##       ##  ##          ##  ##      ###  ##          ##  ##########  ##########  ##      ##""" + "\n\n" + ConsoleColors.RESET);


        System.out.print("  Press enter to start... ");
        Dashboard.removePendingScanner();
        Main.clearConsole(); /**for clear current console*/

        System.out.println("\n  ***************************************************************************************************************");
        System.out.print("  Register \t-----> R or r \n  Login \t-----> L or l\n");
        System.out.println("  ***************************************************************************************************************");
        System.out.print("  Enter selection : ");

        returnValue = getInput();
        if (returnValue == 0){
            loginRegister.register();
        }else {
            if (!loginRegister.login()) exit();
        }
        Dashboard dashboard = new Dashboard();
        dashboard.menu();
    }

    private static int getInput(){
        do {
            try {
                String letter = scanner.nextLine().toUpperCase();
                if (letter.equals("R")){
                   return 0;
                }else if (letter.equals("L")){
                    return 1;
                }else {
                    throw new IOException();
                }
            }catch (Exception e){
                System.out.println( ConsoleColors.RED +"\n  \u0021\u0021 Please enter valid values" + ConsoleColors.RESET);
                System.out.print("  Enter again : ");
            }
        }while (true);
    }

    protected static void clearConsole() throws  Exception {
        final String os = System.getProperty("os.name").toUpperCase();
        if (os.contains("WINDOWS")){
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }else {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    }

    protected static void exit(){
        System.out.println("\n\n  ***************************************************** Bye *****************************************************\n");
        System.exit(0);
    }

}