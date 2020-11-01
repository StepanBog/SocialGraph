package ru.bogdanov.cursach.models;

public class Response {
    public String access_token;
    public int expires_in;
    public int user_id;

    public Response(String access_token, int expires_in, int user_id) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.user_id = user_id;
    }
    public Response(){

    }
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
