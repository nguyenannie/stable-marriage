package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Preference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PreferenceService {

  void save(Preference preference);
  List<Preference> findAllByUserId(String id);
  List<Preference> findAll();
  Preference findOne(long id);
  List<String> selectedUserPreferenceNames(String guid);

}

