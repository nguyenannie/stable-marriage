package com.example.matchmakinglocal.models;

public class TestResposneDTO {
  private String message;
  private int luckyNumber;
  private boolean rightOrWrong;

  public TestResposneDTO() {

  }

  public TestResposneDTO(String message, int luckyNumber, boolean rightOrWrong) {
    this.luckyNumber = luckyNumber;
    this.message = message;
    this.rightOrWrong = rightOrWrong;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getLuckyNumber() {
    return luckyNumber;
  }

  public void setLuckyNumber(int luckyNumber) {
    this.luckyNumber = luckyNumber;
  }

  public boolean isRightOrWrong() {
    return rightOrWrong;
  }

  public void setRightOrWrong(boolean rightOrWrong) {
    this.rightOrWrong = rightOrWrong;
  }
}
