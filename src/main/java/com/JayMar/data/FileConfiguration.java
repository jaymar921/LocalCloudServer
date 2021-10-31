package com.JayMar.data;

import com.JayMar.ServerMain;

import java.io.File;

public class FileConfiguration {

    File server_files = null;
    public FileConfiguration(){
        try {
            ServerMain.LOGGER.info("Loading FileConfiguration");
            //The default Directory of files will be at /LocalCloud/
            File server_directory = new File(ServerConfiguration.directory);
            if(!server_directory.exists()) {
                server_directory.mkdir();
                ServerMain.LOGGER.info("Setting server directory ["+server_directory.getPath()+"]");
            }
            ServerMain.LOGGER.info("Server cloud files directory ["+server_directory.getPath()+"]");
            server_files =server_directory;
        }catch (Exception ignore){}
    }

    public File getServerDirectory(){
        return server_files;
    }
}
