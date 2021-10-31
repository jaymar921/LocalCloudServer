package com.JayMar.handler;

import com.JayMar.utils.ServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputThread extends Thread{
    private final Logger LOGGER = LoggerFactory.getLogger("[InputThread]");
    public static List<Socket> sockets = new ArrayList<>();
    public static ServerSocket serverSocket;
    public static boolean isClosed = false;
    public InputThread(){
        LOGGER.info("Listening inputs");
        start();
    }

    public void run(){
        while (true){
            try{
                String input = new Scanner(System.in).next();
                switch (ServerHelper.getCommand(input).getValue()) {
                    case 1 -> {
                        close();
                        LOGGER.info("Input Listener is closed");
                        return;
                    }
                    case 2 -> LOGGER.info("Invalid Command [" + input + "]");
                }
            }catch (Exception ignore){}
        }
    }

    private void close(){
        for(Socket socket : sockets){
            try{
                LOGGER.info("Terminating - "+socket.getInetAddress());
                socket.getInputStream().close();
            }catch (Exception ignore){}
            try{
                socket.getOutputStream().close();
            }catch (Exception ignore){}
            try{
                socket.close();
            }catch (Exception ignore){}
        }
        try {
            LOGGER.info("Closing Server");
            if(serverSocket!=null)
                serverSocket.close();
        }catch (Exception ignore){}
        isClosed = true;
    }

}
