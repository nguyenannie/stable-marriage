package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Preference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PreferenceService {

  void save(Preference preference);
  List<Preference> findAllByUserId(String id);

}

