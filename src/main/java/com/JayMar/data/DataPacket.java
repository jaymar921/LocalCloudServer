package com.JayMar.data;

public class DataPacket {

    private final String header;
    private final String filename;
    private final byte[] data;
    private final String directory;
    public DataPacket(String header, String filename, byte[] data, String directory){
        this.header = header;
        this.filename = filename;
        this.data = data;
        this.directory = directory;
    }

    public String getHeader(){
        return header;
    }
    public String getFilename(){
        return filename;
    }

    public String getDirectory(){
        return directory;
    }

    public byte[] getData(){
        return data;
    }
}
