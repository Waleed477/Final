package com.codingelab.tutorial;

public class User {

  private String id;
  private String name;
  private String phone;
  private String email;

  public User(String user_id, String user_name, String user_phone,String user_email) {

    id= user_id;
    name= user_name;
    phone= user_phone;
    email= user_email;
  }

  public User() {

  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
