package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import com.example.matchmakinglocal.repositories.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
public class PreferenceServiceDbImpl implements PreferenceService {

  private final PreferenceRepository preferenceRepository;
  private final PartnerRepository partnerRepository;
  private final ApprenticeRepository apprenticeRepository;

  @Autowired
  public PreferenceServiceDbImpl(PreferenceRepository preferenceRepository, PartnerService partnerService, ApprenticeService apprenticeService, PartnerRepository partnerRepository, ApprenticeRepository apprenticeRepository) {
    this.preferenceRepository = preferenceRepository;
    this.partnerRepository = partnerRepository;
    this.apprenticeRepository = apprenticeRepository;
  }

  @Override
  public void save(Preference preference) {
      preferenceRepository.save(preference);
  }
  @Override
  public List<Preference> findAllByUserId(String id) {
      return preferenceRepository.findAllByUserId(id);
  }

  @Override
  public List<Preference> findAll() {
    return preferenceRepository.findAll();
  }

  @Override
  public Preference findOne(long id) {
    return preferenceRepository.findOne(id);
  }

  @Override
  public List<String> selectedUserPreferenceNames(String guid) {
    List<String> selectedUserPreferenceNames = new ArrayList<>();
    List<Preference> selectedPreferenceList = new ArrayList<>();
    if(partnerRepository.findOne(guid) != null){
      selectedPreferenceList.addAll(partnerRepository.findOne(guid).getPreferences());
      selectedPreferenceList.sort(Comparator.comparing(Preference::getRanking));
      selectedPreferenceList.stream()
              .map(preference -> apprenticeRepository.findOne(preference.getSelectionId()).getFirstName() + " " + apprenticeRepository.findOne(preference.getSelectionId()).getLastName())
              .forEach(selectedUserPreferenceNames::add);
    } else if (apprenticeRepository.findOne(guid) != null) {
      selectedPreferenceList.addAll(apprenticeRepository.findOne(guid).getPreferences());
      selectedPreferenceList.sort(Comparator.comparing(Preference::getRanking));
      selectedPreferenceList.stream()
              .map(preference -> partnerRepository.findOne(preference.getSelectionId()).getCompanyName())
              .forEach(selectedUserPreferenceNames::add);
    }
    return selectedUserPreferenceNames;
  }

  private List<Preference> getDistinctPreferenceList(List<Apprentice> apprentices) {
    List<Preference> preferences = new ArrayList<>();
    for(Apprentice apprentice : apprentices) {
      List<Preference> apprenticePreferences = apprentice.getPreferences();
      preferences.addAll(apprenticePreferences);
    }
    return new ArrayList<>(new HashSet<>(preferences));
  }

}

