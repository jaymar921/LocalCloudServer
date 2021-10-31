package com.JayMar.database;

import com.JayMar.ServerMain;
import com.JayMar.enums.AccountStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    public static boolean DatabaseConnectionTest(){
        ServerMain.LOGGER.info("Database Connection test");
        Connection connection = connect(false);
        if(connection!=null){
            ServerMain.LOGGER.info("Connection status passed");
            try {
                connection.close();
            }catch (Exception ignore){}
            return true;
        }
        ServerMain.LOGGER.info("Failed to connect to database...");
        return false;
    }

    private static Connection connect(boolean silent){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            if(!silent)
                ServerMain.LOGGER.info("Connecting to Database...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.122:3306/local_cloud");
            if(!silent)
                ServerMain.LOGGER.info("Connected to Database [192.168.1.122:3306]");
            return connection;
        }catch (Exception e){
            return null;
        }
    }

    public static List<AccountData> getAccounts(){
        try {
            Connection connection = connect(true);
            if(connection==null)
                return new ArrayList<>();
            String query = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccountData> accountDataList = new ArrayList<>();
            while (resultSet.next()){
                AccountData data = new AccountData();
                data.username = resultSet.getString("username");
                data.password = resultSet.getString("password");
                data.storage_directory = resultSet.getString("folder_directory");
                data.status = AccountStatus.valueOf(resultSet.getString("status"));
                accountDataList.add(data);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
            return accountDataList;

        }catch (Exception ignore){
            return new ArrayList<>();
        }
    }

    public static void updateAccount(AccountData accountData){
        try {
            Connection connection = connect(true);
            if(connection == null)
                return;
            String query = "UPDATE accounts SET status=? WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountData.status.toString());
            preparedStatement.setString(2, accountData.username);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }catch (Exception ignore){}
    }

    public static List<FileData> accountData(String directory){
        List<FileData> data = new ArrayList<>();
        try{
            FileData fileData;

            Connection connection = connect(true);
            if(connection == null)
                throw new Exception("Database is null");

            String query = "SELECT * FROM storage WHERE directory=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,directory);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                fileData = new FileData();
                fileData.directory = directory;
                fileData.filename = resultSet.getString("filename");
                fileData.status = resultSet.getString("status");
                data.add(fileData);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }catch (Exception error){
            ServerMain.LOGGER.info("Error in database... "+error.getMessage());
        }
        return data;
    }

    public static void setExist(FileData data){
        try{
            Connection connection = connect(true);
            if(connection == null)
                throw new Exception("Database is null");

            String query = "UPDATE storage SET status=? WHERE filename=? AND directory=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,data.status);
            preparedStatement.setString(2,data.filename);
            preparedStatement.setString(3,data.directory);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }catch (Exception error){
            ServerMain.LOGGER.info("Error in database... "+error.getMessage());
        }
    }

}
