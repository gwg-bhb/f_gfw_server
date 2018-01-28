package com.bhb.fgfw;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "login", urlPatterns="/login")
public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do post");

        String ret = "";
        String username = "";
        String passwd = "";

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = (String)params.nextElement();
            String value = request.getParameter(key);
            if (key.equals("username")){
                username = value;
            }
            if (key.equals("passwd")){
                passwd = value;
            }
        }

        System.out.println(username + "=" + passwd);

        //=========================================
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        ret = "<title>"+username + "</title>";
        out.println(ret);
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello World!this is a f_gfw post test</h1>");
        out.println("</body>");
        out.println("</html>");
        //=========================================
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
