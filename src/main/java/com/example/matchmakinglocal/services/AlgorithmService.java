package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Apprentice;
import com.example.matchmakinglocal.models.Partner;
import com.example.matchmakinglocal.models.Preference;
import com.example.matchmakinglocal.models.User;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import com.example.matchmakinglocal.repositories.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class AlgorithmService {

  @Autowired
  ApprenticeRepository apprenticeRepository;

  @Autowired
  PartnerRepository partnerRepository;

  @Autowired
  PreferenceRepository preferenceRepository;

  public List matchmaker() {

    int changeCounter = 1;
    int followerCounter = 0;

    while (!(changeCounter == followerCounter)) {
      followerCounter = changeCounter;

      List<User> allApprentices = (List)apprenticeRepository.findAll();

      for (int i = 0; i < allApprentices.size(); i++) {


        List<Preference> currentUserPreferenceList = preferenceRepository.findByUser(allApprentices.get(i));
        currentUserPreferenceList.sort(Comparator.comparing(Preference::getRanking));

        for (int j = 0; j < currentUserPreferenceList.size(); j++) {

          if (partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()).getMatchedUserId().equals("")) {

            createMatch(currentUserPreferenceList, allApprentices, i, j);

            changeCounter++;

            break;

          } else if(!(preferenceRepository
                  .findByUserAndSelectionId(
                          partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()),
                          allApprentices.get(i).getId()) == null)){

            int partnerSRankingOfAlreadyRecordedApprenticeMatch = preferenceRepository
                    .findByUserAndSelectionId(
                            partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()),
                            partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()).getMatchedUserId())
                    .getRanking();

            int partnerSRankingOfCurrentApprenticeMatch = preferenceRepository
                    .findByUserAndSelectionId(
                            partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()),
                            allApprentices.get(i).getId())
                    .getRanking();

            int apprenticeSRankingOfAlreadyRecordedPartnerMatch = preferenceRepository
                    .findByUserAndSelectionId(
                            apprenticeRepository.findById(
                                    partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId())
                                            .getMatchedUserId()),
                            currentUserPreferenceList.get(j).getSelectionId())
                    .getRanking();

            int apprenticeSRankingOfCurrentPartnerMatch = currentUserPreferenceList.get(j).getRanking();

            if (partnerSRankingOfCurrentApprenticeMatch + apprenticeSRankingOfCurrentPartnerMatch <
                    partnerSRankingOfAlreadyRecordedApprenticeMatch + apprenticeSRankingOfAlreadyRecordedPartnerMatch) {

              deleteAlreadyRecordedApprenticeMatch(currentUserPreferenceList, j);

              createMatch(currentUserPreferenceList, allApprentices, i, j);

              changeCounter++;

              break;
            } else if ((partnerSRankingOfCurrentApprenticeMatch + apprenticeSRankingOfCurrentPartnerMatch) ==
                    (partnerSRankingOfAlreadyRecordedApprenticeMatch + apprenticeSRankingOfAlreadyRecordedPartnerMatch) &&
                    !(allApprentices.get(i).getId()).equals(
                            apprenticeRepository.findById(
                                    partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId())
                                            .getMatchedUserId()).getId())) {

              if (apprenticeSRankingOfCurrentPartnerMatch < apprenticeSRankingOfAlreadyRecordedPartnerMatch) {

                deleteAlreadyRecordedApprenticeMatch(currentUserPreferenceList, j);

                createMatch(currentUserPreferenceList, allApprentices, i, j);

              }
            }
          }
        }
      }
    }
    //TODO: edit according to implementation
    List<Partner> partners = (List) partnerRepository.findAll();
    HashMap<String, String> partnersMatchedUserIds = new HashMap<>();
    for (Partner partner : partners) {
      partnersMatchedUserIds.put(partner.getId(), partner.getMatchedUserId());
    }
    //TODO: edit according to implementation
    List<Apprentice> apprentices = (List) apprenticeRepository.findAll();
    HashMap<String, String> apprenticeMatchedUserIds = new HashMap<>();
    for (Apprentice apprentice : apprentices) {
      apprenticeMatchedUserIds.put(apprentice.getId(), apprentice.getMatchedUserId());
    }
    //TODO: edit according to implementation
    List<HashMap> results = new ArrayList<>();
    results.add(partnersMatchedUserIds);
    results.add(apprenticeMatchedUserIds);
    return results;
  }

  public void createMatch(List<Preference> currentUserSPreferenceList, List<User> allApprentices, int i, int j) {

    partnerRepository.findById(currentUserSPreferenceList.get(j).getSelectionId())
            .setMatchedUserId(allApprentices.get(i).getId());
    partnerRepository.save(partnerRepository.findById(currentUserSPreferenceList.get(j).getSelectionId()));

    allApprentices.get(i)
            .setMatchedUserId(partnerRepository.findById(currentUserSPreferenceList.get(j).getSelectionId()).getId());
    apprenticeRepository.save((Apprentice)allApprentices.get(i));
  }

  public void deleteAlreadyRecordedApprenticeMatch(List<Preference> currentUserPreferenceList, int j) {
    apprenticeRepository.findById(
            partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()).getMatchedUserId())
            .setMatchedUserId(null);
    apprenticeRepository.save(apprenticeRepository.findById(
            partnerRepository.findById(currentUserPreferenceList.get(j).getSelectionId()).getMatchedUserId()));
  }

}
