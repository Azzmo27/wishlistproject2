package com.example.wishlist.model;

public class User {
    private String username;
    private String userPassword;
    private String first_name;
    private String lastName;
    private int userId;
    private String email;
    private String birthday;

    public User(){

    }
    public User(String username, String userPassword,String firstName,String lastName, int userId, String email, String birthday) {
        this.username = username;
        this.userPassword = userPassword;
        this.first_name = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.email = email;
        this.birthday = birthday;

}
    public String getUsername() {
         return username;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return lastName;
    }

    public int getUserId(){
        return userId;
    }

    public String getEmail(){
        return email;
    }

    public String getBirthday(){
        return birthday;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setUser_password(String user_password){
        this.userPassword = user_password;
    }
public void setFirst_name (String first_name){
        this.first_name = first_name;
}
public void setLast_name(String last_name){
        this.lastName = last_name;
}
public void setUser_id (int user_id){
        this.userId = user_id;
}
public void setEmail(String email){
        this.email = email;
}
public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
