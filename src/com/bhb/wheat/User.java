package com.bhb.wheat;

public class User {
    private String username;

    private String password;

    private  String salt;

    private String token;

    private String authcode;

    private String mobile;

    private String serverip;

    private String register_date;
    private String expiring_date;

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public  String getSalt(){
        return this.salt;
    }
    public  void  setSalt(String salt){
        this.salt = salt;
    }

    public String getToken(){
        return this.token;
    }
    public void setToken(String token){
        this.token = token;
    }

    public String getAuthcode() {
        return this.authcode;
    }
    public  void setAuthcode(String authcode){
        this.authcode = authcode;
    }

    public String getMobile() {
        return this.mobile;
    }
    public  void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getServerip() {
        return this.serverip;
    }
    public void setServerip(String serverip){
        this.serverip = serverip;
    }

    public String getExpiring_date(){
        return this.expiring_date;
    }
    public void setExpiring_date(String expiring_date){
        this.expiring_date = expiring_date;
    }

    public String getRegister_date(){
        return this.register_date;
    }
    public void setRegister_date(String register_date){
        this.register_date = register_date;
    }
}
