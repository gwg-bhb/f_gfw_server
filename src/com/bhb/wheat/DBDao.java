package com.bhb.wheat;
import java.sql.*;
import static java.sql.DriverManager.*;

public class DBDao {
    private static String USER = "root";
    private static String PASSWORD = "gwg134679";
    private static String DB_URL = "jdbc:mysql://localhost:3306/mytest";
    private static String DB_DRIVER = "com.mysql.jdbc.Driver";
    //  private static String SQL = "";
    private static Connection connection = null;

    //连接数据库
    public static Connection getConnection(){

    try{
        Class.forName(DB_DRIVER);
    }catch(ClassNotFoundException e){
        System.out.println("找不到驱动程序类 ，加载驱动失败！");
        e.printStackTrace() ;
    }

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("数据库连接异常");
            e.printStackTrace();
        }
        return connection;
    }
    public  static void closeConnection(Connection connection){

        if(connection != null){
            try {
                connection.close(); // 关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
