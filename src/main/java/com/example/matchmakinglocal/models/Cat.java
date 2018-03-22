package com.example.matchmakinglocal.models;

public class Cat {

  private String gender;
  private String age;

  public Cat() {

  }

  public Cat(String gender, String age) {
    this.gender = gender;
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }
}
