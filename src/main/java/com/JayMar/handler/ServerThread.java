package com.JayMar.handler;

import com.JayMar.data.IPHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{

    public static Logger LOGGER = LoggerFactory.getLogger("[ServerThread]");

    ServerSocket serverSocket;
    Socket socket;

    public ServerThread(int port){
        try {
            serverSocket = new ServerSocket(port);
            InputThread.serverSocket = serverSocket;
            LOGGER.info("Server started ["+ InetAddress.getLocalHost()+":"+port+"]");
            start();
        }catch (Exception error){
            LOGGER.info("Failed to start server");
        }
    }

    @Override
    public void run() {
        LOGGER.info("Server is running");
        while (true){
            try {
                socket = serverSocket.accept();
                if(IPHolder.getIPlist().contains(socket.getInetAddress().toString())) {
                    socket.close();
                    continue;
                }
                LOGGER.info("Connection accepted "+socket.getInetAddress());
                new InternalSession(socket);
            }catch (Exception error){
                if(!serverSocket.isClosed())
                    LOGGER.info("Server Thread Error");
                LOGGER.info("Shutting Down");
                break;
            }
        }
    }
}
