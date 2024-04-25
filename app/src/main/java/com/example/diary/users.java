package com.example.diary;

public class users {
    private String email, username, password;

    public users(){

    }

    //конструктор для всех переменных
    public users (String email, String username, String password){
        this.password = password;
        this.email = email;
        this.username = username;
    }
    // метод получения переменных
    public String getUsername(){
        return username;
    }
    //установка
    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}

