package com.bhb.wheat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import com.bhb.wheat.User;
import com.bhb.wheat.UserDao;

@WebServlet(name = "login", urlPatterns = "/login")
public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do post");
        response.setCharacterEncoding("GBK");
        //获取用户名和密码
        String username = request.getParameter("username");

        String password = request.getParameter("password");
        //获取UserDao实例
        UserDao userDao = new UserDao();

        User user = userDao.login(username);
        // 判断user是否为空
        if(user != null){
            String pwd = user.getSalt()+password;
            String pwd_sha256 = utiltool.SHA256(pwd);
            if(user.getPassword().equals(pwd_sha256)) {
                System.out.println("login success");
                //1 生成uuid
                String token = UUID.randomUUID().toString();
                System.out.println(token);
                //2 将token存到数据库中SiteToken
                user.setToken(token);
                //??????

                //3 将uuid存到cookie中，并且返回
                Cookie cookie = new Cookie("mytoken", token);
                response.addCookie(cookie);
            }
            else{
                System.out.println("login failed");
                // 密码错误，返回403
                response.setStatus(403);
            }
        }else{
            // 登录失败，返回403
            response.setStatus(403);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do get");
        doPost(request, response);
    }
}
