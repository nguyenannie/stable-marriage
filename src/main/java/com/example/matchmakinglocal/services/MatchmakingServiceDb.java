package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MatchmakingServiceDb {

  private final ApprenticeService apprenticeService;
  private final PartnerService partnerService;
  private List<Apprentice> apprentices;
  private List<Partner> partners;

  @Autowired
  public MatchmakingServiceDb(ApprenticeService apprenticeService, PartnerService partnerService) {
    this.apprenticeService = apprenticeService;
    this.partnerService = partnerService;
    apprentices = apprenticeService.findAll();
    partners = partnerService.findAll();
  }

  //second approach

  public Map secondApproach(List<Apprentice> apprentices, List<Partner> partners) {
    HashMap<String, String> matches = new HashMap<>();
    int changed = 1;
    int unChanged = 0;
    int numOfPartners = partners.size();
    boolean[] notFreePartners = new boolean[numOfPartners];
    Apprentice[] partnerChoices = new Apprentice[numOfPartners];
    while(changed != unChanged) {
      changed = unChanged;
      for (Apprentice thisApprentice : apprentices) {

        List<Preference> thisPreferences = thisApprentice.getPreferences();
        thisPreferences.sort(Comparator.comparing(Preference::getRanking));

        for (Preference thePreference : thisPreferences) {
          Partner thePartner = partnerService.findOne(thePreference.getSelectionId());
          if(findApprenticeCurrentPartnerId(thisApprentice, matches) != null) {
            String apprenticeCurrentPartnerId = findApprenticeCurrentPartnerId(thisApprentice, matches);
            Partner apprenticeCurrentPartner = partnerService.findOne(apprenticeCurrentPartnerId);
            if (findRankOfPartnerInApprenticePreference(thisApprentice, apprenticeCurrentPartner) < findRankOfPartnerInApprenticePreference(thisApprentice, thePartner)) {
              break;
            }
          } else {
            int thePartnerIndex = getPartnerIndex(partners, thePartner);
            boolean isNotFree = notFreePartners[thePartnerIndex];
            if (!isNotFree) {
              matches.put(thePartner.getId(), thisApprentice.getId());
              notFreePartners[thePartnerIndex] = true;
              partnerChoices[thePartnerIndex] = thisApprentice;
              changed++;
              break;
            } else {
              Apprentice currentPairOfThePartner = partnerChoices[thePartnerIndex];
              if (wannaSwap(thePartner, currentPairOfThePartner, thisApprentice, matches)) {
                partnerChoices[thePartnerIndex] = thisApprentice;
                matches.put(thePartner.getId(), thisApprentice.getId());
                changed++;
                break;
              }
            }
          }
        }
      }
    }
    return matches;
  }

  private int getPartnerIndex(List<Partner> partners, Partner partner) {
    for (int i = 0; i < partners.size(); i++) {
      if(Objects.equals(partners.get(i).getId(), partner.getId())) {
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

  private boolean wannaSwap(Partner partner, Apprentice currentChoice, Apprentice newChoice, HashMap<String, String> matches) {
    int currentPairRank = findRankOfApprenticeInPartnerPreference(partner, currentChoice) + findRankOfPartnerInApprenticePreference(currentChoice, partner);
    int newPairRank = findRankOfApprenticeInPartnerPreference(partner, newChoice) + findRankOfPartnerInApprenticePreference(newChoice, partner);
    return currentPairRank > newPairRank;
  }

  private int findRankOfApprenticeInPartnerPreference(Partner partner, Apprentice apprentice) {
    List<Preference> preferences = partner.getPreferences();
    for (Preference preference : preferences) {
      if (preference.getSelectionId().equals(apprentice.getId())) {
        return preference.getRanking();
      }
    }
    //just return something very big that it's rank always loses
    return apprenticeService.findAll().size() + 10;
  }

  private int findRankOfPartnerInApprenticePreference(Apprentice apprentice, Partner partner) {
    List<Preference> preferences = apprentice.getPreferences();
    for (Preference preference : preferences) {
      if (preference.getSelectionId().equals(partner.getId())) {
        return preference.getRanking();
      }
    }
    //just return something very big that it's rank always loses
    return partnerService.findAll().size() + 10;
  }

  private String findApprenticeCurrentPartnerId(Apprentice apprentice, HashMap<String, String> matches) {
    String apprenticeId = apprentice.getId();
    if(matches.containsValue(apprenticeId)) {
      for (Map.Entry<String, String> entry : matches.entrySet()) {
        if (apprenticeId.equals(entry.getValue())) {
          return entry.getKey();
        }
      }
      return null;
    } else {
      return null;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////
  //don't read this, all bad attempt but can be improved and debugged later maybe?
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
          Apprentice preferredApprentice = apprenticeService.findOne(partnerPreferences.get(i));
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
