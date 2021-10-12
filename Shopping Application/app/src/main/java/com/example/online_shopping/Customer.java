package com.example.online_shopping;

public class Customer {
    public int getId() {
        return id;
    }

    private int id ;
    private String name ;
    private String Email ;
    private String Phone ;
    private String Gender;
    private String Job ;
    private String password ;
    private String Birthday ;
    private String SQuestion ;
    private String SAnswer ;

    public Customer(String username, String email, String phone, String password, String birthday, String gender ,String job ,String question , String answer) {
        name = username;
        Email = email;
        Phone = phone;
        this.password = password;
        Birthday = birthday;
        Gender = gender;
        Job=job;
        SQuestion=question;
        SAnswer=answer;
    }
    public String getName() {
        return name;
    }

    public String getSQuestion() {
        return SQuestion;
    }

    public String getSAnswer() {
        return SAnswer;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getJob() {
        return Job;
    }


}
