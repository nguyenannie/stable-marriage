package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Program;
import com.example.matchmakinglocal.models.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchFormServiceImpl implements SearchFormService {

  private final RetrofitService retrofitService;

  @Autowired
  public SearchFormServiceImpl(RetrofitService retrofitService) {
    this.retrofitService = retrofitService;
  }

  @Override
  public List checkApprentice(String checkbox) {
    switch (checkbox) {
      case "":
      case "active,inactive,":
        return retrofitService.callApprenticeList(null, null, null, null, null, null, null);
      case "headhunting":
        return retrofitService.callApprenticeList(null, null, null, null, null, null, Program.HEADHUNTING);
      case "active,":
        return retrofitService.callApprenticeList(null, null, null, null, null, Status.ACTIVE, null);
      case "inactive,":
        return retrofitService.callApprenticeList(null, null, null, null, null, Status.INACTIVE, null);
      case "active,headhunting,":
        return retrofitService.callApprenticeList(null, null, null, null, null, Status.ACTIVE, Program.HEADHUNTING);
      case "inactive,headhunting,":
        return retrofitService.callApprenticeList(null, null, null, null, null, Status.INACTIVE, Program.HEADHUNTING);
      default:
        return null;
    }
  }

  @Override
  public List checkPartner(String checkbox) {
    switch (checkbox) {
      case "":
      case "active,inactive,":
        return retrofitService.callPartnerList(null, null, null, null);
      case "active,":
        return retrofitService.callPartnerList(null, null, Status.ACTIVE, null);
      case "inactive,":
        return retrofitService.callPartnerList(null, null, Status.INACTIVE, null);
      case "goldpartner,":
      case "active,inactive,goldpartner,":
        return retrofitService.callPartnerList(null, null, null, Program.GOLDPARTNER);
      case "active,goldpartner,":
        return retrofitService.callPartnerList(null, null, Status.ACTIVE, Program.GOLDPARTNER);
      case "inactive,goldpartner,":
        return retrofitService.callPartnerList(null, null, Status.INACTIVE, Program.GOLDPARTNER);
      default:
        return null;
    }
  }

  @Override
  public List checkCohort(String checkbox) {
    switch (checkbox) {
      case "":
      case "active,inactive,":
        return retrofitService.callAllCohort(null, null);
      case "active,":
        return retrofitService.callAllCohort(null, Status.ACTIVE);
      case "inactive,":
        return retrofitService.callAllCohort(null, Status.INACTIVE);
      default:
        return null;
    }
  }

  @Override
  public List textboxApprentice(String textbox) {
    List apprentices = null;
    apprentices.add(retrofitService.callApprenticeList(textbox, null, null, null, null, null, null));
    apprentices.add(retrofitService.callApprenticeList(null, textbox, null, null, null, null, null));
    apprentices.add(retrofitService.callApprenticeList(null, null, textbox, null, null, null, null));
    apprentices.add(retrofitService.callApprenticeList(null, null, null, textbox, null, null, null));
    apprentices.add(retrofitService.callApprenticeList(null, null, null, null, textbox, null, null));
    return apprentices;
  }

  @Override
  public List textboxPartner(String textbox) {
    List partners = null;
    partners.add(retrofitService.callPartnerList(textbox, null, null, null));
    partners.add(retrofitService.callPartnerList(null, textbox, null, null));
    return partners;
  }

  @Override
  public List textboxCohort(String cohort) {
    List cohorts = null;
    cohorts.add(retrofitService.callAllCohort(cohort, null));
    return cohorts;
  }

  private List addIfNotExist(List list1, List list2) {
    if (list1 != null && list2 != null) {
      for (Object aList2 : list2) {
        if (!list1.contains(aList2)) {
          list1.add(aList2);
        }
      }
      return list1;
    }

    return null;
  }

  private String[] splitTextBoxInput(String textBoxInput) {
    return textBoxInput.trim().split("\\s+");
  }

  private List callApprentice(String firstName, String lastName, String cohort, String cohortClass, String email, Status status, Program program) {
    return retrofitService.callApprenticeList(firstName, lastName, cohort, cohortClass, email, status, program);
  }

}

