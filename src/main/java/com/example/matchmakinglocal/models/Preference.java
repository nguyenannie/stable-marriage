package com.example.matchmakinglocal.models;

import javax.persistence.*;

@Entity
@Table(name = "preference")
public class Preference {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String selectionId;
  private int ranking;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Preference() {
  }

  public Preference(int ranking, String selectionId, User user) {
    this.ranking = ranking;
    this.selectionId = selectionId;
    this.user = user;
  }

  public Preference(int ranking, String selectionId) {
    this.ranking = ranking;
    this.selectionId = selectionId;
  }

  public int getRanking() {
      return ranking;
  }

  public void setRanking(int ranking) {
      this.ranking = ranking;
  }

  public User getUser() {
      return user;
  }

  public void setUser(User user) {
      this.user = user;
  }

  public long getId() {
      return id;
  }

  public void setId(long id) {
      this.id = id;
  }

  public String getSelectionId() {
      return selectionId;
  }

  public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
    }
}

