package com.example.matchmakinglocal.factories;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Status;
import org.springframework.stereotype.Component;

@Component
public class ApprenticeFactory {

  public Apprentice create(String email, String phoneNumber, String firstName, String lastName, Status status) {
    Apprentice apprentice= new Apprentice(email, phoneNumber);
    apprentice.setFirstName(firstName);
    apprentice.setLastName(lastName);
    apprentice.setStatus(status);
    return apprentice;
  }

}
