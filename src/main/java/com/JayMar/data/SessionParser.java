package com.JayMar.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SessionParser {

    public static Logger LOGGER = LoggerFactory.getLogger("[Parser]");


    public static DataPacket Parse(DataInputStream inputStream){
        DataPacket dataPacket = null;
        try{
            LOGGER.info("Packet Transfer <->");
            //get the size of header bytes
            int header_size = inputStream.readInt();
            if(header_size > 0){
                byte[] headerContent = new byte[header_size];
                inputStream.readFully(headerContent,0,header_size);
                String header = new String(headerContent);

                //get the size of bytes
                int filename_size = inputStream.readInt();

                //get the filename
                if(filename_size>0){
                    byte[] filename_contents = new byte[filename_size];
                    inputStream.readFully(filename_contents, 0, filename_size);

                    String filename = new String(filename_contents);
                    if(header.contains("UPLOAD"))
                        LOGGER.info("(Client)"+filename+" --> server");
                    //get the file directory
                    int file_directory_size = inputStream.readInt();
                    if(file_directory_size>0){
                        byte[] fileDirectory = new byte[file_directory_size];
                        inputStream.readFully(fileDirectory, 0, file_directory_size);
                        String file_directory = new String(fileDirectory);

                        //get the file content
                        int fileContent_size = inputStream.readInt();
                        if(fileContent_size>0){
                            byte[] fileContent = new byte[fileContent_size];
                            inputStream.readFully(fileContent,0,fileContent_size);

                            //parse the header, filename, fileContent and file_directory
                            dataPacket = new DataPacket(header, filename, fileContent, file_directory);
                        }
                    }
                }
            }


        }catch (Exception error){
            LOGGER.info("Failed to parse data..."+"\n"+error.getMessage());
        }
        return dataPacket;
    }
}
