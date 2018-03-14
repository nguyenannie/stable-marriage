package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Apprentice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApprenticeService {
  void save(Apprentice apprentice);
  Apprentice findOne(String id);
  List retrieveApprentices(Apprentice filter);
  List<Apprentice> findAll();
//    List<Apprentice> findByCohortClassAndEmail(String cohortClass, String email);
//    List<Apprentice> findOptional(String cohortClass, String email);

}
