package com.example.matchmakinglocal.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "apprentice")
public class Apprentice extends User {

  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @Column(nullable = false)
  private String cohort;
  @Column(name = "cohort_class", nullable = false)
  private String cohortClass;
  @Column(name = "is_Hungarian_Speaker")
  private boolean isHungarianSpeaker;
  @Column(name = "slack_channel_id")
  private String slackChannelId;

  public Apprentice() {
      super();
  }

  public Apprentice(String email) {
      super(email);
  }

  public Apprentice(String email, String phoneNumber) {
      super(email, phoneNumber);
  }

  public Apprentice(String firstName, String lastName, String cohort, String cohortClass, boolean isHungarianSpeaker) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.cohort = cohort;
    this.cohortClass = cohortClass;
    this.isHungarianSpeaker = isHungarianSpeaker;
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

  public boolean isHungarianSpeaker() {
      return isHungarianSpeaker;
  }

  public void setHungarianSpeaker(boolean hungarianSpeaker) {
      isHungarianSpeaker = hungarianSpeaker;
  }

  public String getCohortClass() {
      return cohortClass;
  }

  public void setCohortClass(String cohortClass) {
      this.cohortClass = cohortClass;
  }

  @Override
  public String toString() {
    return  "Apprentice{ id : " + this.id
            + ", First name : " + this.firstName
            + ", Last name : " + this.lastName
            + ", Email : " + this.email
            + ", Phone Number : " + this.phoneNumber
            + ", Cohort : " + this.cohort
            + ", Cohort class : " + this.cohortClass
            + ", Is Hungarian Speaker : " + this.isHungarianSpeaker
            + " }";
  }

  public String getSlackChannelId() {
      return slackChannelId;
  }

  public void setSlackChannelId(String slackChannelId) {
      this.slackChannelId = slackChannelId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }

    if (!(obj instanceof Apprentice)) {
        return false;
    }

    Apprentice compareApprentice = (Apprentice) obj;

    return compareApprentice.id.equals(this.id);
  }

}
