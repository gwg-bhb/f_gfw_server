package com.bhb.wheat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServerDao {

    User user = null;
    private String SQLinsert ="";
    private String SQLupdate = "";

    public boolean insert_updateInfo(User usr){
        SQLinsert = "insert into ShadowSockService(user, server, expiring_date) values(?, ?, ?)";
        SQLupdate = "update ShadowSockService set expiring_date = ? where user = ?;";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBDao.getConnection();
            pstmt = (PreparedStatement) connection.prepareStatement(SQLupdate);
            //这里的意思将用户名和密码填到SQL语句的问号处

            pstmt.setString(1, usr.getUsername());
            pstmt.setString(2, usr.getServerip());
            pstmt.setString(3, usr.getExpiring_date());

            if(0 == pstmt.executeUpdate())
            {
                //这个地方好像不起作用
                pstmt.close();
                pstmt = (PreparedStatement) connection.prepareStatement(SQLinsert);
                pstmt.setString(1, usr.getExpiring_date());
                pstmt.setString(2, usr.getUsername());
                if(0 == pstmt.executeUpdate()){
                    pstmt.close();
                    connection.close();
                    return false;
                }
            }
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            DBDao.closeConnection(connection);
        }
        return true;
    }
}
