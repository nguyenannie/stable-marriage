package com.example.matchmakinglocal.controllers;

import com.example.matchmakinglocal.models.*;
import com.example.matchmakinglocal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

  @Autowired
  PreferenceService preferenceService;

  @Autowired
  ApprenticeService apprenticeService;

  @Autowired
  PartnerService partnerService;

  @Autowired
  TestApiService testApiService;

  @Autowired
  MatchmakingService matchmakingService;

  @Autowired
  AlgorithmService algorithmService;

  @GetMapping("/user")
  public Principal user(Principal principal) {
      return principal;
  }

  @GetMapping("/test")
  public List<Integer> getTest() {
      List<Integer> rankings = new ArrayList<>();
      List<Preference> preferences = preferenceService.findAllByUserId("f86167cb-15c7-4dd2-a87b-e629a87c60ed");
      for(Preference preference : preferences ) {
          rankings.add(preference.getRanking());
      }
      return rankings;
  }

  @GetMapping("/testApiCall")
  public List<Apprentice> testApiCall() {
    List<Apprentice> l = testApiService.test();
    System.out.println("test call " + l);
    return l;
  }

  @GetMapping("/search")
  public List search(@RequestParam(required = false) String cohort) {
    Apprentice fetchCohortCriteria = new Apprentice();
    fetchCohortCriteria.setCohort(cohort);
    return apprenticeService.retrieveApprentices(fetchCohortCriteria);
  }

  @GetMapping("/getMatches")
  public HashMap<String, String> getMatches() {
    return matchmakingService.secondApproach(apprenticeService.findAll(), partnerService.findAll());
    //return matchmakingService.losingApproach();
  }

}

