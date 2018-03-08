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

  final ApprenticeService apprenticeService;
  final PartnerService partnerService;

  @Autowired
  public MatchmakingService(ApprenticeService apprenticeService, PartnerService partnerService) {
    this.apprenticeService = apprenticeService;
    this.partnerService = partnerService;
  }

  //first approach

  // find all the apprentices for a @partner that ranked that partner at @rank
  private List<Apprentice> findApprenticesWhoRanked(List<Apprentice> apprentices, Partner partner, int rank) {
    List<Apprentice> result = new ArrayList<>();
    for (Apprentice apprentice : apprentices) {
      List<String> apprenticePreferences = getUserPreferences(apprentice);
      if(partnerService.findOne(apprenticePreferences.get(rank - 1)).getId().equals(partner.getId())) {
        result.add(apprentice);
      }
    }
    return result;
  }

  private List<String> getUserPreferences(User user) {
    List<Preference> userPreferences = user.getPreferences();
    userPreferences.sort(Comparator.comparing(Preference::getRanking));
    return userPreferences.stream().map(Preference::getSelectionId).collect(Collectors.toList());
  }

  public Map<String, String> firstApproach() {
    List<Apprentice> apprentices = apprenticeService.findAll();
    List<Partner> partners = partnerService.findAll();

    Map<String, String> matches = new HashMap<>();
    int counter = 0;
    int rounds = partners.size();
    int rank = 1;
    while(counter < rounds) {
      List<Partner> pairedPartners = new ArrayList<>();
      List<Apprentice> pairedApprentices = new ArrayList<>();

      for (Partner partner : partners) {
        List<Apprentice> interestedApprentices = findApprenticesWhoRanked(apprentices, partner, rank);
        List<String> partnerPreferences = getUserPreferences(partner);
        boolean continueInnerLoop = true;
        for (int i = 0; i < partnerPreferences.size() && continueInnerLoop; i++) {
          Apprentice preferredApprentice = apprenticeService.findById(partnerPreferences.get(i));
          for (Apprentice interestedApprentice : interestedApprentices) {
            if (preferredApprentice.getId().equals(interestedApprentice.getId())) {
              matches.put(preferredApprentice.getId(), partner.getId());
              pairedPartners.add(partner);
              pairedApprentices.add(preferredApprentice);
              counter++;
              continueInnerLoop = false;
              break;
            }
          }
        }
      }
      rank++;
      partners.removeAll(pairedPartners);
      apprentices.removeAll(pairedApprentices);
    }
    return matches;
  }



  private List<Partner> notWantedPartners(List<Apprentice> apprentices, List<Partner> partners) {
    List<Partner> notWantedPartners = partners;
    for (int i = 0; i < partners.size() && notWantedPartners.size() > 0; i++) {
      Partner p = partners.get(i);
      for (int j = 0; j < apprentices.size() && notWantedPartners.size() > 0; j++) {
        Apprentice a = apprentices.get(j);
        for (int k = 0; k < a.getPreferences().size() && notWantedPartners.size() > 0; k++) {
          Preference pf = a.getPreferences().get(k);
          if(partnerService.findOne(pf.getSelectionId()) == p) {
            notWantedPartners.remove(i);
            break;
          }
        }
      }
    }
    return notWantedPartners;
  }

  //second approach

//  public HashMap<Apprentice, Partner> secondApproach() {
//    HashMap<Apprentice, Partner> matches = new HashMap<>();
//    int changed = 0;
//    int unChanged = 1;
//    int numOfApprentices = apprentices.size();
//    int numOfPartners = partners.size();
//    boolean[] notFreePartners = new boolean[numOfPartners];
//    boolean notBreak = true;
//    Apprentice[] partnerChoices = new Apprentice[numOfPartners];
//    while(!(changed == unChanged)) {
//      changed = unChanged;
//      for (int i = 0; i < numOfApprentices; i++) {
//        Apprentice thisApprentice = apprentices.get(i);
//        List<Preference> thisPreferences = thisApprentice.getPreferences();
//        int thisPreferenceSize = thisPreferences.size();
//        for (int j = 0; j < thisPreferenceSize && notBreak; j++) {
//          Preference thePreference = thisPreferences.get(j);
//          Partner thePartner = partnerService.findOne(thePreference.getSelectionId());
//          int thePartnerIndex = getPartnerIndex(thePartner);
//          boolean isNotFree = notFreePartners[thePartnerIndex];
//          if (!isNotFree) {
//            matches.put(thisApprentice, thePartner);
//            notFreePartners[thePartnerIndex] = true;
//            partnerChoices[thePartnerIndex] = thisApprentice;
//            changed++;
//            notBreak = false;
//            break;
//          } else {
//            Apprentice currentPairOfThePartner = partnerChoices[thePartnerIndex];
//            if (wannaSwap(thePartner, currentPairOfThePartner, thisApprentice)) {
//              partnerChoices[thePartnerIndex] = thisApprentice;
//              matches.put(thisApprentice, thePartner);
//              changed++;
//              notBreak = false;
//              break;
//            }
//          }
//        }
//      }
//    }
//    return matches;
//  }
//
//  private int getPartnerIndex(Partner partner) {
//    for (int i = 0; i < partners.size(); i++) {
//      if(Objects.equals(partners.get(i).getId(), partner.getId())) {
//        return i;
//      }
//    }
//    return -1;
//  }
//
//  private int getApprenticeIndex(Apprentice apprentice) {
//    for (int i = 0; i < apprentices.size(); i++) {
//      if(apprentices.get(i) == apprentice) {
//        return i;
//      }
//    }
//    return -1;
//  }
//
//  private boolean wannaSwap(Partner partner, Apprentice currentChoice, Apprentice newChoice) {
//    int numOfPreference = partner.getPreferences().size();
//    List<Preference> thisPreferences = partner.getPreferences();
//    for (int i = 0; i < numOfPreference; i++) {
//      String thisApprenticeId = thisPreferences.get(i).getSelectionId();
//      Apprentice thisApprentice = apprenticeService.findById(thisApprenticeId);
//      if(thisApprentice == currentChoice) {
//        return false;
//      }
//      if(thisApprentice == newChoice) {
//        return true;
//      }
//    }
//    return false;
//  }


  // losing approach

  private <A> int indexIf(List<A> l, Function<A, Boolean> p) {
    for(int i = 0; i < l.size(); ++i) {
      if(p.apply(l.get(i))) return i;
    }
    return -1;
  }

  public Map<String, String> losingApproach() {
    // get data from db
    List<Apprentice> apprentices = apprenticeService.findAll();
    List<Partner> partners = partnerService.findAll();
    // convert data for algorithm
    int[][] apprenticeChoice = new int[apprentices.size() + 1][partners.size() + 1];
    int[][] partnerChoice = new int[partners.size() + 1][apprentices.size() + 1];

    for(int i = 0; i < apprentices.size(); ++i) {
      List<Preference> preferences = apprentices.get(i).getPreferences();
      preferences.sort(Comparator.comparing(Preference::getRanking));
      for(int j = 0; j < preferences.size(); ++j) {
        //find index of partner
        String partnerId = preferences.get(j).getSelectionId();
        int partnerIndex = indexIf(partners, (partner) -> partner.getId().equals(partnerId));
        apprenticeChoice[i+1][j+1] = partnerIndex + 1;
      }
    }

    for(int i = 0; i < partners.size(); ++i) {
      List<Preference> preferences = partners.get(i).getPreferences();
      preferences.sort(Comparator.comparing(Preference::getRanking));
      for(int j = 0; j < preferences.size(); ++j) {
        //find index of apprentice
        String apprenticeId = preferences.get(j).getSelectionId();
        int apprenticeIndex = indexIf(apprentices, (apprentice) -> apprentice.getId().equals(apprenticeId));
        partnerChoice[i+1][j+1] = apprenticeIndex + 1;
      }
    }
    // run algorithm
    MatchMakingAlgorithm mma = new MatchMakingAlgorithm(apprentices.size(), partners.size(), apprenticeChoice, partnerChoice);
    mma.execute();
    // convert result
    Map<Apprentice, Partner> matchMap = new HashMap<>();
    Map<String, String> stringMap = new HashMap<>();
    for(int i = 1; i < mma.match.length; ++i) {
      matchMap.put(apprentices.get(mma.match[i] - 1), partners.get(i - 1));
      stringMap.put(apprentices.get(mma.match[i] - 1).getId(), partners.get(i - 1).getId());
    }

    System.out.println(stringMap);
    return stringMap;
  }
}




