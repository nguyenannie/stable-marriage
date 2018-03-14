package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Apprentice;
import com.example.matchmakinglocal.models.Partner;
import com.example.matchmakinglocal.models.Preference;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchmakingService {

  private final ApprenticeRepository apprenticeRepo;
  private final PartnerRepository partnerRepo;

  @Autowired
  public MatchmakingService(ApprenticeRepository apprenticeRepo, PartnerRepository partnerRepo) {
    this.apprenticeRepo = apprenticeRepo;
    this.partnerRepo = partnerRepo;
  }

  public Map<String, String> returnMatches() {
    Algorithm algorithm = new Algorithm(convertApprenticePreference(), convertPartnerPreference());
    int[] partnerChoices = algorithm.calculateMatches();
    return convertMatches(partnerChoices);
  }

  private int[][] convertPartnerPreference() {
    List<Apprentice> apprentices = apprenticeRepo.findAll();
    List<Partner> partners = partnerRepo.findAll();

    int[][] partnerPreferences = new int[partners.size()][apprentices.size()];
    for(int[] a : partnerPreferences) {
      Arrays.fill(a, -1);
    }
    for(int i = 0; i < partners.size(); i++) {
      List<Preference> preferences = partners.get(i).getPreferences();
      preferences.sort(Comparator.comparing(Preference::getRanking));
      for (int j = 0; j < preferences.size(); j++) {
        String chosenApprenticeId = preferences.get(j).getSelectionId();
        partnerPreferences[i][j] = getApprenticeIndex(apprentices, apprenticeRepo.findOne(chosenApprenticeId));
      }
    }
    return partnerPreferences;
  }

  private int[][] convertApprenticePreference() {
    List<Apprentice> apprentices = apprenticeRepo.findAll();
    List<Partner> partners = partnerRepo.findAll();

    int[][] apprenticePreferences = new int[apprentices.size()][partners.size()];
    for(int[] a : apprenticePreferences) Arrays.fill(a, -1);
    for(int i = 0; i < apprentices.size(); i++) {
      List<Preference> preferences = apprentices.get(i).getPreferences();
      preferences.sort(Comparator.comparing(Preference::getRanking));
      for (int j = 0; j < preferences.size(); j++) {
        String chosenPartnerId = preferences.get(j).getSelectionId();
        apprenticePreferences[i][j] = getPartnerIndex(partners, partnerRepo.findOne(chosenPartnerId));
      }
    }
    return apprenticePreferences;
  }

  private Map<String, String> convertMatches(int[] partnerChoices) {
    List<Apprentice> apprentices = apprenticeRepo.findAll();
    List<Partner> partners = partnerRepo.findAll();

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
      if(apprentices.get(i).getId().equals(apprentice.getId())) {
        return i;
      }
    }
    return -1;
  }

}




