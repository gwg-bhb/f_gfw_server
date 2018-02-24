package com.bhb.wheat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebServlet(name = "register2", urlPatterns = "/register2")
public class register2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("register 2 post");

        //1 获取username+pwd+phonenum
        String userName = request.getParameter("username");
        String pwd = request.getParameter("password");
        String phoneNum = request.getParameter("phoneNum");
        String num = request.getParameter("identifyingNum");
        //2 判断username是否存在，存在返回错误码1
        //在user数据库中查询  是否存在username
        UserDao userDao = new UserDao();
        if(userDao.isExist_userName(userName)){
            System.out.println("register2 failed, 该用户名已经存在");
            response.setStatus(1);
            return;
        }
        //3 判断手机号是否存在，存在返回错误码2---表示该手机号已经注册过了
        if (userDao.isExist_phonenum(phoneNum)){
            System.out.println("register2 failed, 该手机号已经注册");
            response.setStatus(2);
            return;
        }
        //4 获取验证码，并和数据库中验证码进行比较，失败返回错误码3---注册码错误
        //在数据库IdentifyNum  查询验证码是否一致
        AuthcodeDao authcodeDao = new AuthcodeDao();
        String authcodeString = authcodeDao.getUserAuthcode(userName).getAuthcode();
        System.out.println("authcodeString:"+authcodeString);
        if (! authcodeString.equals(num)){
            System.out.println("register2 failed, 注册码错误");
            response.setStatus(3);
            return;
        }

        //5 将用户数据插入数据库中,将user, pwd, salt, phoneNum 插入到 user数据库中
        //5.1 先生成salt，再计算pwd_sha256
        String salt = "62448d4197938e56845bc01f101d0fdb69b435750943447fab5a964a88b28aa9";
        String pwd_sha256 = utiltool.SHA256(salt+pwd);

        Date date = new Date(System.currentTimeMillis());
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);

        User user = new User();
        user.setUsername(userName);
        user.setPassword(pwd_sha256);
        user.setMobile(phoneNum);
        user.setSalt(salt);
        user.setRegister_date(time);
        //5.2 插入到user表中
        userDao.insertuserinfo(user);

        //6 分配一台可用服务器，不可用为no
        //???????
        String ser_ip = "192.168.0.1";
        user.setServerip(ser_ip);

        //7 将分配信息以及用户对应关系插入到ShadowSockService  中，并保存可用时间，默认7天试用期
        //????????
        String exp_time = time+7;
        user.setExpiring_date(exp_time);

        ServerDao serDao = new ServerDao();
        serDao.insert_updateInfo(user);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("register 2 doget");
        doPost(request, response);
    }
}
