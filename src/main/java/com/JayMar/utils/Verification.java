package com.JayMar.utils;

import com.JayMar.ServerMain;

import java.util.Scanner;

public class Verification {

    public static boolean Confirmation(){
        while (true){
            String input = new Scanner(System.in).next().toLowerCase();
            if(input.contains("y") || input.contains("yes"))
                return true;
            else if(input.contains("n") || input.contains("no"))
                return false;
            ServerMain.LOGGER.info("Invalid input! ["+input+"]");
        }
    }
}
