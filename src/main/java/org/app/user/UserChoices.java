package org.app.user;

import java.util.Scanner;

public class UserChoices {
    public static String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of a resource you would like to see \n");
        String answer = scanner.nextLine();
        scanner.close();
        return  answer;
    }
}
