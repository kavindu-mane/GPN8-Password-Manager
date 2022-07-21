package com.hexa2zero.main;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    /**
     * Create Scanner object
     */

    private static final LoginRegister loginRegister = new LoginRegister();

    public static void main(String[] args) throws Exception {

        System.out.println(ConsoleColors.GREEN + "\n\n" + """
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
        getStartMode();

    }

    protected static void getStartMode() throws Exception {
        Main.clearConsole(); /**for clear current console*/

        System.out.println("\n  ***************************************************************************************************************");
        System.out.println("  Register \t-----> R or r \n  Login \t-----> L or l\n  Exit \t\t-----> E or e");
        System.out.println("  ***************************************************************************************************************");
        System.out.print("  Enter selection : ");

        /**Create LoginRegister object*/
        int returnValue = getInput();
        if (returnValue == 0)  loginRegister.register();
        else if (returnValue == 1) {
            if (!loginRegister.login()) exit();
        }else exit();

        Dashboard dashboard = new Dashboard();
        dashboard.menu();
    }

    private static int getInput() {
        do {
            try {
                String letter = scanner.nextLine().toUpperCase();
                return switch (letter) {
                    case "R" -> 0;
                    case "L" -> 1;
                    case "E" -> 2;
                    default -> throw new IOException();
                };
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 Please enter valid values" + ConsoleColors.RESET);
                System.out.print("  Enter again : ");
            }
        } while (true);
    }

    protected static void clearConsole() throws Exception {
        final String os = System.getProperty("os.name").toUpperCase();
        if (os.contains("WINDOWS")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    }

    public static void errorText(String error , String selection) {
        System.out.println(ConsoleColors.RED + "\n  \u0021\u0021 "+ error + ConsoleColors.RESET);
        System.out.println("  ***************************************************************************************************************");
        System.out.println(selection);
        System.out.println("  ***************************************************************************************************************");
        System.out.print("  Enter selection : ");
    }

    protected static void exit() {
        System.out.println("\n\n  ***************************************************** Bye *****************************************************\n");
        System.exit(0);
    }

}