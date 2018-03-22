package com.example.matchmakinglocal.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchFormService {

  List checkApprentice(String checkbox);
  List checkPartner(String checkbox);
  List checkCohort(String checkbox);
  List textboxApprentice(String textbox);
  List textboxPartner(String partner);
  List textboxCohort(String cohort);

}
