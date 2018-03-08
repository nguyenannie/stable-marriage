package com.example.matchmakinglocal.models;

import com.google.gson.annotations.SerializedName;

public class ApprenticeDTO {

  @SerializedName("firstName")
  private String firstName;
  @SerializedName("lastName")
  private String lastName;
  @SerializedName("cohort")
  private String cohort;
  @SerializedName("cohortClass")
  private String cohortClass;
  @SerializedName("isHungarianSpeaker")
  private boolean isHungarianSpeaker;
  @SerializedName("slackChannelId")
  private String slackChannelId;
  @SerializedName("id")
  protected String id;
  @SerializedName("email")
  protected String email;
  @SerializedName("phoneNumber")
  protected String phoneNumber;
//  @SerializedName("isActiveUser")
//  protected boolean isActiveUser;

  public ApprenticeDTO() {

  }

  public ApprenticeDTO(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getCohort() {
    return cohort;
  }

  public void setCohort(String cohort) {
    this.cohort = cohort;
  }

  public String getCohortClass() {
    return cohortClass;
  }

  public void setCohortClass(String cohortClass) {
    this.cohortClass = cohortClass;
  }

  public boolean isHungarianSpeaker() {
    return isHungarianSpeaker;
  }

  public void setHungarianSpeaker(boolean hungarianSpeaker) {
    isHungarianSpeaker = hungarianSpeaker;
  }

  public String getSlackChannelId() {
    return slackChannelId;
  }

  public void setSlackChannelId(String slackChannelId) {
    this.slackChannelId = slackChannelId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

//  public boolean isActiveUser() {
//    return isActiveUser;
//  }
//
//  public void setActiveUser(boolean activeUser) {
//    isActiveUser = activeUser;
//  }

  @Override
  public String toString() {
    return "Apprentice{ id : " + this.id
            + ", First name : " + this.firstName
            + ", Last name : " + this.lastName
            + ", Email : " + this.email
            + ", Phone Number : " + this.phoneNumber
            + ", Cohort : " + this.cohort
            + ", Cohort class : " + this.cohortClass
            + ", Is Hungarian Speaker : " + this.isHungarianSpeaker
           // + ", is Active user : " + this.isActiveUser
            + ", slack channel id : " + this.slackChannelId
            + " }";
  }
}
