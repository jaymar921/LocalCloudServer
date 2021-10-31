package com.JayMar.data;

import com.JayMar.handler.InputThread;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IPHolder extends Thread{

    private static List<String> ip_address;
    public IPHolder(){
        ip_address = new ArrayList<>();
        start();
    }

    public static List<String> getIPlist(){
        return ip_address;
    }

    public void run(){
        try{
            while(true){
                if(InputThread.isClosed) {
                    LoggerFactory.getLogger("[IPHolder]").info("Ip monitoring closed");
                    return;
                }
                sleep(8000);
                ip_address.clear();
            }
        }catch (Exception ignore){}
    }

}
