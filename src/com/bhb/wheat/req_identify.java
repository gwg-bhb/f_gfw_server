package com.bhb.wheat;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "req_identify", urlPatterns = "/req_identify")
public class req_identify extends HttpServlet {

    private  static  final String url = "http://gw.api.taobao.com/router/rest";
    private  static  final String appkey = "23568229";
    private  static  final String secret = "6804e983312fe51329cc265ab9cbd889";

    private  static boolean sendmsgByAlidayu(String telephone, String authcode) {
        try {
            System.out.println("sendmsgByAlidayu");
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("");
            req.setSmsType("normal");
            req.setSmsFreeSignName("小麦生活");
            req.setSmsParamString("{\"name\":\"~.~ 瞄！\",\"number\":\"" + authcode + "\"}");
            req.setRecNum(telephone);
            req.setSmsTemplateCode("SMS_34325154");
            DefaultTaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());
        }catch (Exception e){
            System.out.println("sendmsgByAlidayu failed");
            e.printStackTrace();
        }
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("req_identify dopost");
        //1 取出手机号码telNum
        String telephone = request.getParameter("telNum");
        //2 生成6位验证码
        String  authcode = Integer.toString((int)((Math.random()*9+1)*100000));
        //3 将验证码和手机号存在数据库中，数据库名：IdentifyNum
        //重复发送验证码+第一次发送验证码
        AuthcodeDao acd = new AuthcodeDao();
        if (!acd.saveAuthCode(telephone, authcode))
        {   //失败，返回400
            response.setStatus(400);
            return;
        }

        //4  调用阿里大鱼  发送短信
        if(!sendmsgByAlidayu(telephone, authcode))
        {
            //失败返回400
            response.setStatus(400);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("req_identify doget");
        doPost(request, response);
    }
}
