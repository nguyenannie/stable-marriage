package com.example.matchmakinglocal.factories;

import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.models.entities.Status;

public class PartnerFactory {

  public Partner create(String email, String phoneNumber, Status status, String matchedUserId, String companyName) {
    return new Partner(email, phoneNumber, status, matchedUserId, companyName);
  }

}
