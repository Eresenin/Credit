package com.lpnu.vasyliev.credit.entity;


public class User {
    private String login;
    private String password;
    private String name;
    private String status;
    private int money;

    public User() {
    }

    public User(String login, String password, String name, String status, int money) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.money = money;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User[" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", money=" + money +
                ']';
    }
}
