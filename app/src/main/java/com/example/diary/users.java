package com.example.diary;

public class users {
    private String email, login, password;

    public users(){

    }

    //конструктор для всех переменных
    public users (String login, String email, String password){
        this.password = password;
        this.email = email;
        this.login = login;
    }
    // метод получения переменных
    public String getlogin(){
        return login;
    }
    //установка
    public void setlogin(String login){
        this.login = login;
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

