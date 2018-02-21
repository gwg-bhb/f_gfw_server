package com.bhb.wheat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.bhb.wheat.User;

public class AuthcodeDao {
    User user = null;
    private String SQLinsert ="";
    private String SQLupdate = "";
    private String SQLselect = "";
    public User getUserAuthcode(String username) {
        SQLselect = "select * from IdentifyNum where name = ?";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBDao.getConnection();
            pstmt = (PreparedStatement) connection.prepareStatement(SQLselect);
            //这里的意思将用户名和密码填到SQL语句的问号处
            pstmt.setString(1, username);
//            pstmt.setString(2, pwd_sha256);
            ResultSet rSet = (ResultSet) pstmt.executeQuery();//得到数据库的查询结果,一个数据集
            //判断结果集是否有效
            if (rSet.next()) {
                user = new User();
                user.setAuthcode(rSet.getString("identifyingnum"));
            }
            rSet.close();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            DBDao.closeConnection(connection);
        }
        return user;
    }

    public boolean saveAuthCode(String telephone, String authcode){
        SQLinsert = "insert into IdentifyNum(telephone, identifyingnum) values(?, ?)";
        SQLupdate = "update IdentifyNum set identifyingnum = ? where telephone = ?;";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBDao.getConnection();
            pstmt = (PreparedStatement) connection.prepareStatement(SQLupdate);
            //这里的意思将用户名和密码填到SQL语句的问号处

            pstmt.setString(1, authcode);
            pstmt.setString(2, telephone);

            if(0 == pstmt.executeUpdate())
            {
                //这个地方好像不起作用
                pstmt.close();
                pstmt = (PreparedStatement) connection.prepareStatement(SQLinsert);
                pstmt.setString(1, telephone);
                pstmt.setString(2, authcode);
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
