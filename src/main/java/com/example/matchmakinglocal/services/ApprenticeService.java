package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApprenticeService {

  void save(Apprentice apprentice);
  Apprentice findOne(String id);
  List<Apprentice> findByCohortClass(String cohortClass);
  Apprentice findByEmail(String email);
  Apprentice findByPhoneNumber(String phoneNumber);
  List<Apprentice> findByFirstName(String firstName);
  List<Apprentice> findByLastName(String lastName);
  List<Apprentice> findByCohort(String cohort);
  List<Apprentice> findByHungarianSpeaker(boolean isHungarianSpeaker);
  String findApprenticeNameById(String id);
  List<Apprentice> findAll();

}
