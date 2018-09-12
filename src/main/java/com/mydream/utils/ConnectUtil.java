package com.mydream.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by  lpw'ASUS on 2018/7/26.
 */
public class ConnectUtil {

    /**
     * @param dataBaseDriver 数据库驱动
     * @param dataBaseUrl 数据库连接地址
     * @param dataBaseUsername 数据库用户名
     * @param dataBasePassword 数据库密码
     * @return Connection java.sql.Connection
     * @throws ClassNotFoundException
     * @throws SQLException
     * @Description 创建数据库连接
     */
    public static Connection getConnection(String dataBaseDriver, String dataBaseUrl, String dataBaseUsername, String dataBasePassword){

        try {
            Class.forName(dataBaseDriver);
            return DriverManager.getConnection(dataBaseUrl,dataBaseUsername,dataBasePassword);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param connection java.sql.Connection
     * @Description 关闭数据库连接
     */
    public static void closeConnection(Connection connection){
        try{
            if(connection != null){
                connection.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
