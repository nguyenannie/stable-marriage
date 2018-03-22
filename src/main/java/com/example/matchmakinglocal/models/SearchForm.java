package com.example.matchmakinglocal.models;

public class SearchForm {

  private String radioButton;
  private String checkbox;
  private String textbox;

  public SearchForm() {

  }

  public SearchForm(String radioButton, String checkbox) {
    this.radioButton = radioButton;
    this.checkbox = checkbox;
  }

  public String getRadioButton() {
    return radioButton;
  }

  public void setRadioButton(String radioButton) {
    this.radioButton = radioButton;
  }

  public String getCheckbox() {
    return checkbox;
  }

  public void setCheckbox(String checkbox) {
    this.checkbox = checkbox;
  }

  public String getTextbox() {
    return textbox;
  }

  public void setTextbox(String textbox) {
    this.textbox = textbox;
  }

}
