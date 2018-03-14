package com.example.matchmakinglocal.services;

public class Algorithm {

  private int[][] apprenticePreferences;
  private int[][] partnerPreferences;
  private int apprenticeSize;
  private int partnerSize;

  public Algorithm() {

  }

  public Algorithm(int[][] apprenticePreferences, int[][] partnerPreferences) {
    this.apprenticePreferences = apprenticePreferences;
    this.partnerPreferences = partnerPreferences;
    this.apprenticeSize = apprenticePreferences.length;
    this.partnerSize = partnerPreferences.length;
  }

  public int[] calculateMatches() {
    boolean changed;
    boolean[] pairedPartners = new boolean[partnerSize];

    int[] partnerChoices = new int[partnerSize];

    do {
      changed = false;
      for (int i = 0; i < apprenticeSize; i++) {
        int[] preferences = apprenticePreferences[i];
        for (int preference : preferences) {
          if(currentChoiceIsBetterThanNewChoice(i, preference, partnerChoices)) {
            break;
          } else {
            boolean pairedPartner = pairedPartners[preference];
            if (!pairedPartner) {
              partnerChoices[preference] = i;
              pairedPartners[preference] = true;
              changed = true;
              break;
            } else {
              int currentPairOfThePartner = partnerChoices[preference];
              if (wannaSwap(preference, currentPairOfThePartner, i)) {
                partnerChoices[preference] = i;
                changed = true;
                break;
              }
            }
          }
        }
      }
    } while (changed);

    return partnerChoices;
  }

  private boolean wannaSwap(int partner, int currentChoice, int newChoice) {
    int currentPairRank = findRankOfApprenticeInPartnerPreference(partner, currentChoice)
            + findRankOfPartnerInApprenticePreference(currentChoice, partner);
    int newPairRank = findRankOfApprenticeInPartnerPreference(partner, newChoice)
            + findRankOfPartnerInApprenticePreference(newChoice, partner);
    return currentPairRank > newPairRank;
  }

  private boolean currentChoiceIsBetterThanNewChoice(int apprentice, int preference, int[] partnerChoices) {
    if(findApprenticeCurrentPartnerId(apprentice, partnerChoices) != null) {
      int apprenticeCurrentPartner = findApprenticeCurrentPartnerId(apprentice, partnerChoices);
      return findRankOfPartnerInApprenticePreference(apprentice, apprenticeCurrentPartner) <
              findRankOfPartnerInApprenticePreference(apprentice, preference);
    } else {
      return false;
    }
  }

  private Integer findApprenticeCurrentPartnerId(int apprentice, int[] partnerChoice) {
    for (int i = 0; i < partnerChoice.length; i++) {
      if (partnerChoice[i] == apprentice) {
        return i;
      }
    }
    return null;
  }

  private int findRankOfApprenticeInPartnerPreference(int partner, int apprentice) {
    int[] preferences = partnerPreferences[partner];
    for (int i = 0; i < preferences.length; i++) {
      if (preferences[i] == apprentice) {
        return i + 1;
      }
    }
    return apprenticeSize + 100;
  }

  private int findRankOfPartnerInApprenticePreference(int apprentice, int partner) {
    int[] preferences = apprenticePreferences[apprentice];
    for (int i = 0; i < preferences.length; i++) {
      if (preferences[i] == partner) {
        return i + 1;
      }
    }
    return partnerSize + 100;
  }

}
