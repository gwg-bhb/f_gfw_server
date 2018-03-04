package com.bhb.wheat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "pick_info")
public class pick_info extends HttpServlet {

    final String Key = "c5760$%^1d6191202487a94d4()_2d1a";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("pick-info do post");
        //1 设置Content-Type
        response.setContentType("application/json");
        //2 先验证token
        String token = request.getParameter("token");
        //根据token从sitetoken里面取出username
        sitetokenDao tmp = new sitetokenDao();
        User usr = new User();
        usr.setToken(token);
        usr = tmp.getUser_name(usr);
        //3 根据username从ShadowSockService里面取出相应的服务器地址并且返回
        ServerDao s_dao = new ServerDao();
        usr = s_dao.getUser_serverIP(usr);
        String server_ip = usr.getServerip();
        //使用AES算法加密serverip
        byte [] encrypted = utiltool.AES_CBC_Encrypt(server_ip.getBytes(), Key.getBytes());
        String server_ip_cipher = utiltool.byteToHexString(encrypted);

        //返回加密的数据
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(server_ip_cipher);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("pick-info do get");
        doPost(request, response);
    }
}
