package com.jan.firebaseandroidexample.data.db.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static User jsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
}
