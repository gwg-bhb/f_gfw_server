package com.bhb.wheat;

import com.bhb.wheat.User;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.bhb.wheat.utiltool;

public class UserDao {
    User user = null;
    private String SQL ="";
    public User login(String username){
//        SQL = "select * from users where name = ? and password = ?";
        SQL = "select * from users where name = ?";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBDao.getConnection();
            pstmt = (PreparedStatement) connection.prepareStatement(SQL);
            //这里的意思将用户名和密码填到SQL语句的问号处
            pstmt.setString(1, username);
//            pstmt.setString(2, pwd_sha256);
            ResultSet rSet = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
            //判断结果集是否有效
            if(rSet.next()){
                user = new User();
                user.setUsername(rSet.getString("name"));
                user.setPassword(rSet.getString("password"));
                user.setSalt(rSet.getString("salt"));
            }
            rSet.close();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            DBDao.closeConnection(connection);
        }
        return user;
    }

    public void  inserttoken(User usr){
        SQL = "insert into sitetoken(token, username, created) values(?, ?, ?)";
        Connection connection = null;
        PreparedStatement pstmt = null;

        Date date = new Date(System.currentTimeMillis());
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);

        try {
            connection = DBDao.getConnection();
            pstmt = (PreparedStatement) connection.prepareStatement(SQL);
            //这里的意思将用户名和密码填到SQL语句的问号处
            pstmt.setString(1, usr.getToken());
            pstmt.setString(2, usr.getUsername());
            pstmt.setString(3, time);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            DBDao.closeConnection(connection);
        }
        return;
    }
}

