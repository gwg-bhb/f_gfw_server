package com.bhb.wheat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class sitetokenDao {

    private  String SQLselect = "";

    public User getUser_name(User usr){
        SQLselect = "select * from sitetoken where token = ?";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBDao.getConnection();
            pstmt = (PreparedStatement) connection.prepareStatement(SQLselect);
            //这里的意思将用户名和密码填到SQL语句的问号处
            pstmt.setString(1, usr.getToken());
            ResultSet rSet = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
            //判断结果集是否有效
            if(rSet.next()){
                usr.setServerip(rSet.getString("username"));
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
        return usr;
    }

}
