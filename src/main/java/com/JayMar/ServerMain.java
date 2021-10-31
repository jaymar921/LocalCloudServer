package com.JayMar;

import com.JayMar.data.FileConfiguration;
import com.JayMar.data.IPHolder;
import com.JayMar.data.ServerConfiguration;
import com.JayMar.database.DatabaseHandler;
import com.JayMar.handler.DataThread;
import com.JayMar.handler.InputThread;
import com.JayMar.handler.ServerThread;
import com.JayMar.utils.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    Local Cloud Storage
    By: JayMar921
    Home Project - 10/27/2021

    This app is a server for my local cloud storage.
    It manipulates the files of the users, and it is
    dependent in the local_cloud database where user
    information are stored including their folder
    directories.
*/
public class ServerMain {

    public static final Logger LOGGER = LoggerFactory.getLogger("[ServerMain]");
    public static void main(String... args){
        //loading the configuration file
        FileConfiguration configuration = new FileConfiguration();
        //testing Database Connectivity
        if(!DatabaseHandler.DatabaseConnectionTest()){
            LOGGER.info("There was an issue connecting to database");
            LOGGER.info("Would you like to continue? [Y/n]");
            if(!Verification.Confirmation()) {
                LOGGER.info("Program terminated...");
                return;
            }
        }
        LOGGER.info("Starting Data Thread");
        new DataThread();
        new IPHolder();
        new InputThread();
        LOGGER.info("Starting Server Thread");
        new ServerThread(ServerConfiguration.port);
    }
}
