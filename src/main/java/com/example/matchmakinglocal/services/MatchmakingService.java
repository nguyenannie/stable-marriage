package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Apprentice;
import com.example.matchmakinglocal.models.Partner;
import com.example.matchmakinglocal.models.Preference;
import com.example.matchmakinglocal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MatchmakingService {

  private final ApprenticeService apprenticeService;
  private final PartnerService partnerService;
  private List<Apprentice> apprentices;
  private List<Partner> partners;
  private Algorithm algorithm;

  @Autowired
  public MatchmakingService(ApprenticeService apprenticeService, PartnerService partnerService) {
    this.apprenticeService = apprenticeService;
    this.partnerService = partnerService;
    apprentices = apprenticeService.findAll();
    partners = partnerService.findAll();
    algorithm = new Algorithm(convertApprenticePreference(), convertPartnerPreference(), apprentices.size(),
            partners.size());
  }

  public Map<String, String> returnMatches() {
    int[] partnerChoices = algorithm.calculateMatches();
    return convertMatches(partnerChoices);
  }

  private int[][] convertPartnerPreference() {
    int[][] partnerPreferences = new int[partners.size()][apprentices.size()];
    for(int[] a : partnerPreferences) {
      Arrays.fill(a, -1);
    }
    for(int i = 0; i < partners.size(); i++) {
      List<Preference> preferences = partners.get(i).getPreferences();
      preferences.sort(Comparator.comparing(Preference::getRanking));
      for (int j = 0; j < preferences.size(); j++) {
        String chosenApprenticeId = preferences.get(j).getSelectionId();
        partnerPreferences[i][j] = getApprenticeIndex(apprentices, apprenticeService.findOne(chosenApprenticeId));
      }
    }
    return partnerPreferences;
  }

  private int[][] convertApprenticePreference() {
    int[][] apprenticePreferences = new int[apprentices.size()][partners.size()];
    for(int[] a : apprenticePreferences) Arrays.fill(a, -1);
    for(int i = 0; i < apprentices.size(); i++) {
      List<Preference> preferences = apprentices.get(i).getPreferences();
      preferences.sort(Comparator.comparing(Preference::getRanking));
      for (int j = 0; j < preferences.size(); j++) {
        String chosenPartnerId = preferences.get(j).getSelectionId();
        apprenticePreferences[i][j] = getPartnerIndex(partners, partnerService.findOne(chosenPartnerId));
      }
    }
    return apprenticePreferences;
  }

  private Map<String, String> convertMatches(int[] partnerChoices) {
    Map<String, String> matches = new HashMap<>();
    for (int i = 0; i < partnerChoices.length; i++) {
      matches.put(partners.get(i).getId(), apprentices.get(partnerChoices[i]).getId());
    }
    return matches;
  }

  private int getPartnerIndex(List<Partner> partners, Partner partner) {
    for (int i = 0; i < partners.size(); i++) {
      if(partners.get(i).getId().equals(partner.getId())) {
        return i;
      }
    }
    return -1;
  }

  private int getApprenticeIndex(List<Apprentice> apprentices, Apprentice apprentice) {
    for (int i = 0; i < apprentices.size(); i++) {
      if(apprentices.get(i) == apprentice) {
        return i;
      }
    }
    return -1;
  }

}




