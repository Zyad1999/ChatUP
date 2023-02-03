package com.chatup.utils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DBConnection{

    private static Connection connection;

    private DBConnection(){

    }

    public static Connection getConnection(){
        if(connection == null) {
            DataSource ds = getMySQLDataSource();
            try{
                connection = ds.getConnection();
                System.out.println("Connected to DB");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void stopConnection(){
        if(connection == null){
            System.out.println("No DB connection");
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DataSource getMySQLDataSource(){
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream(DBConnection.class.getClassLoader().getResource("db.properties").toString().substring(6));
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
}