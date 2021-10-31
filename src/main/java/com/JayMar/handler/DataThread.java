package com.JayMar.handler;

import com.JayMar.data.ServerConfiguration;
import com.JayMar.database.AccountData;
import com.JayMar.database.DatabaseHandler;
import com.JayMar.database.FileData;
import com.JayMar.enums.AccountStatus;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class DataThread extends Thread{

    public DataThread(){
        start();
    }

    @Override
    public void run() {
        while(true){
            try {
                if(InputThread.isClosed)
                    break;

                List<AccountData> accountDataList = DatabaseHandler.getAccounts();
                for (AccountData accountData : accountDataList){
                    updateAccount(accountData);
                    updateDirectory(accountData);
                }
                sleep(5000);
            }catch (Exception ignore){}
        }
        LoggerFactory.getLogger("[DataThread]").info("Account Monitoring is closed");
    }

    private void updateDirectory(AccountData accountData){
        List<FileData> fileData = DatabaseHandler.accountData(accountData.storage_directory);
        try{
            for(FileData data : fileData){
                File file = new File(ServerConfiguration.directory+"/"+accountData.storage_directory+"/"+data.filename);
                if(file.exists() && data.status.contains("not_exist")){
                    data.status = "exist";
                    DatabaseHandler.setExist(data);
                }else if(!file.exists() && data.status.contains("exist")){
                    data.status = "not_exist";
                    DatabaseHandler.setExist(data);
                }
            }
        }catch (Exception ignore){}
    }

    private void updateAccount(AccountData accountData){
        if(!accountData.status.equals(AccountStatus.PENDING))
            return;

        //create a directory for the user
        String directory = ServerConfiguration.directory+"/"+accountData.storage_directory;
        try {
            File file = new File(directory);
            if(!file.exists()) {
                file.mkdir();
                LoggerFactory.getLogger("[DataThread]").info("Directory added for ["+accountData.username+"]");
            }
        }catch (Exception ignore){}

        accountData.status = AccountStatus.CREATED;
        DatabaseHandler.updateAccount(accountData);
    }
}
