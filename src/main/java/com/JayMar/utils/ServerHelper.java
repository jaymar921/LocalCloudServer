package com.JayMar.utils;

import com.JayMar.enums.Commands;
import com.JayMar.enums.Requests;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ServerHelper {

    public static boolean containRequest(String request){
        AtomicBoolean bool = new AtomicBoolean(false);
        EnumSet.allOf(Requests.class).forEach(REQUEST->{
            if(REQUEST.toString().equalsIgnoreCase(request))
                bool.set(true);
        });
        return bool.get();
    }

    public static Commands getCommand(String input){
        AtomicReference<Commands> command = new AtomicReference<>(Commands.INVALID);
        EnumSet.allOf(Commands.class).forEach(COMMAND->{
            if(COMMAND.toString().equalsIgnoreCase(input))
                command.set(COMMAND);
        });
        return command.get();
    }
}
