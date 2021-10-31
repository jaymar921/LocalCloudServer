package com.JayMar.data;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PacketReader {

    public static void Read(DataPacket packet, DataOutputStream dataOutputStream){

        if(packet.getHeader().toLowerCase().contains("upload"))
            save(packet.getDirectory(), packet.getFilename(), packet.getData());
        if(packet.getHeader().toLowerCase().contains("download"))
            upload(packet.getDirectory(), packet.getFilename(), dataOutputStream);
    }

    private static void save(String dir, String filename, byte[] data){
        try{
            File file = new File(ServerConfiguration.directory+"/"+dir+"/"+filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(data,0,data.length);
            fileOutputStream.close();
        }catch (Exception error){
            SessionParser.LOGGER.info("There was an issue saving the file ["+filename+"]");
        }
    }

    private static void upload(String dir, String filename, DataOutputStream dataOutputStream){
        try {
            File file = new File(ServerConfiguration.directory+"/"+dir+"/"+filename);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] _data = new byte[(int)inputStream.getChannel().size()];
            inputStream.read(_data, 0, _data.length);
            inputStream.close();

            SessionParser.LOGGER.info("(Server:"+_data.length+")"+filename+" --> client");
            dataOutputStream.writeInt(_data.length);
            dataOutputStream.write(_data);


            byte[] additional_byte = "".getBytes();
            dataOutputStream.writeInt(additional_byte.length);
            dataOutputStream.write(additional_byte);

        }catch (Exception error){
            SessionParser.LOGGER.info("There was an issue uploading the file ["+filename+"]"+error.getMessage());
        }
    }

}
