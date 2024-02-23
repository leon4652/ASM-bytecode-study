package org.sch.init;

import java.time.LocalDateTime;

public class Banner {

    public static void send(String agentArgs) {
        System.out.println("  /$$$$$$   /$$$$$$  /$$      /$$        /$$$$$$                                  /$$ ");
        System.out.println(" /$$__  $$ /$$__  $$| $$$    /$$$       /$$__  $$                                | $$    ");
        System.out.println("| $$  \\ $$| $$  \\__/| $$$$  /$$$$      | $$  \\ $$  /$$$$$$   /$$$$$$  /$$$$$$$  /$$$$$$  ");
        System.out.println("| $$$$$$$$|  $$$$$$ | $$ $$/$$ $$      | $$$$$$$$ /$$__  $$ /$$__  $$| $$__  $$|_  $$_/  ");
        System.out.println("| $$__  $$ \\____  $$| $$  $$$| $$      | $$__  $$| $$  \\ $$| $$$$$$$$| $$  \\ $$  | $$    ");
        System.out.println("| $$  | $$ /$$  \\ $$| $$\\  $ | $$      | $$  | $$| $$  | $$| $$_____/| $$  | $$  | $$ /$$");
        System.out.println("| $$  | $$|  $$$$$$/| $$ \\/  | $$      | $$  | $$|  $$$$$$$|  $$$$$$$| $$  | $$  |  $$$$/");
        System.out.println("|__/  |__/ \\______/ |__/     |__/      |__/  |__/ \\____  $$ \\_______/|__/  |__/   \\___/  ");
        System.out.println("                                                  /$$  \\ $$                              ");
        System.out.println("                                                 |  $$$$$$/                              ");
        System.out.println("                                                  \\______/                               ");

        System.out.println("nowTime : " + LocalDateTime.now() + " my Agent has been invoked with args: " + agentArgs);
    }
}
