package com.bhb.wheat;

public class User {
    private String username;

    private String password;

    private  String salt;

    private String token;

    private String authcode;

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
}
