package com.JayMar.handler;

import com.JayMar.data.DataPacket;
import com.JayMar.data.PacketReader;
import com.JayMar.data.SessionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class InternalSession extends Thread{

    private final Logger LOGGER = LoggerFactory.getLogger("[Internal]");

    Socket socket;
    public InternalSession(Socket socket){
        this.socket = socket;
        start();
    }

    @Override
    public void run(){
        try {
            InputThread.sockets.add(socket);


            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            DataPacket dataPacket = SessionParser.Parse(new DataInputStream(inputStream));

            PacketReader.Read(dataPacket, new DataOutputStream(outputStream));
            //LOGGER.info(data.code.toString() + " " + data.filename + " to " + data.directory);

            inputStream.close();
            outputStream.close();


            LOGGER.info("Session ended ["+socket.getInetAddress()+"]");
            try {
                InputThread.sockets.remove(socket);
                socket.close();
            }catch (Exception ignore){}
        }catch (Exception error){
            LOGGER.info("Internal Server Error"+error.getMessage());
        }

    }
}
