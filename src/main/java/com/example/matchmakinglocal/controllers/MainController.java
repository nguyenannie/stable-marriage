package com.example.matchmakinglocal.controllers;

import com.example.matchmakinglocal.models.entities.Program;
import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.models.entities.Status;
import com.example.matchmakinglocal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

  private final PreferenceService preferenceService;
  private final ApprenticeService apprenticeService;
  private final PartnerService partnerService;
  private final TestApiService testApiService;
  private final MatchmakingService matchmakingService;
  private final AlgorithmService algorithmService;
  private final MatchmakingServiceDb matchmakingServiceDb;
  private final RetrofitService retrofitService;

  @Autowired
  public MainController(PreferenceService preferenceService, ApprenticeService apprenticeService,
                        PartnerService partnerService, TestApiService testApiService,
                        MatchmakingService matchmakingService, AlgorithmService algorithmService,
                        MatchmakingServiceDb matchmakingServiceDb, RetrofitService retrofitService) {
    this.preferenceService = preferenceService;
    this.apprenticeService = apprenticeService;
    this.partnerService = partnerService;
    this.testApiService = testApiService;
    this.matchmakingService = matchmakingService;
    this.algorithmService = algorithmService;
    this.matchmakingServiceDb = matchmakingServiceDb;
    this.retrofitService = retrofitService;
  }

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

//  @GetMapping("/search")
//  public List search(@RequestParam(required = false) String cohort) {
//    Apprentice fetchCohortCriteria = new Apprentice();
//    fetchCohortCriteria.setCohort(cohort);
//    return apprenticeService.retrieveApprentices(fetchCohortCriteria);
//  }

  @GetMapping("/getMatches")
  public Map<String, String> getMatches() {
    return matchmakingService.returnMatchesInStringMap();
//    return matchmakingServiceDb.secondApproach(apprenticeService.findAll(), partnerService.findAll());
  }

  @RequestMapping("/apprenticecall")
  public List requestApprenticeList(@RequestParam(name = "firstName", required = false) String firstName,
                                    @RequestParam(name = "lastName", required = false) String lastName,
                                    @RequestParam(name = "cohort", required = false) String cohort,
                                    @RequestParam(name = "cohortClass", required = false) String cohortClass,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "status", required = false) Status status,
                                    @RequestParam(name = "program", required = false) Program program) {
    return retrofitService.callApprenticeList(firstName, lastName, cohort, cohortClass, email, status, program);
  }

  @RequestMapping("/partnercall")
  public List requestApprenticeList(@RequestParam(name = "companyName", required = false) String companyName,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "status", required = false) Status status,
                                    @RequestParam(name = "program", required = false) Program program) {
    return retrofitService.callPartnerList(companyName, email, status, program);
  }

  @RequestMapping("/admincall")
  public List requestAdminList(@RequestParam(name = "firstName", required = false) String firstName,
                               @RequestParam(name = "lastName", required = false) String lastName,
                               @RequestParam(name = "email", required = false) String email,
                               @RequestParam(name = "status", required = false) Status status) {
    return retrofitService.callAdminList(firstName, lastName, email, status);
  }

  @GetMapping("/tryEndpoint")
  public List tryEndpoint() {
    return retrofitService.callAdminList(null, null, null, Status.ACTIVE);
  }

}
