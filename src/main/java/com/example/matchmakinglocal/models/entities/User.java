package com.example.matchmakinglocal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

  @Id
  protected String id;
  @Column(nullable = false)
  protected String email;
  protected String phoneNumber;
  @JsonIgnore
  protected String matchedUserId;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  protected List<Preference> preferences;

  @Enumerated(EnumType.STRING)
  protected Status status;

  public User() {
      this.id = UUID.randomUUID().toString();
  }

  public User(String email) {
      this.id = UUID.randomUUID().toString();
      this.email = email;
  }

  public User(String email, String phoneNumber) {
      this.id = UUID.randomUUID().toString();
      this.email = email;
      this.phoneNumber = phoneNumber;
  }

  public User(String email, String phoneNumber, Status status, String matchedUserId) {
    this.id = UUID.randomUUID().toString();
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.status = status;
    this.matchedUserId = matchedUserId;
  }

  public String getId() {
      return this.id;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getEmail() {
      return email;
  }

  public String getPhoneNumber() {
      return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
  }

  public List<Preference> getPreferences() {
      return preferences;
  }

  public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getMatchedUserId() {
    return matchedUserId;
  }

  public void setMatchedUserId(String matchedUserId) {
    this.matchedUserId = matchedUserId;
  }

}
