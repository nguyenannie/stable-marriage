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
    boolean[] isPartnerPaired = new boolean[partnerSize];
    int[] partnerChoices = new int[partnerSize];

    do {
      changed = false;
      for (int apprenticeIx = 0; apprenticeIx < apprenticeSize; apprenticeIx++) {
        int[] thisApprenticePreferences = findApprenticeRealPreference(apprenticePreferences[apprenticeIx]);
        for (int consideredPartnerIx : thisApprenticePreferences) {
          if (isApprenticeSatisfied(apprenticeIx, consideredPartnerIx, partnerChoices)) {
            break;
          } else {
            if (!isPartnerPaired[consideredPartnerIx]) {
              // the apprentice is dissatisfied and the partner being considered is free, so a match is made
              partnerChoices[consideredPartnerIx] = apprenticeIx;
              isPartnerPaired[consideredPartnerIx] = true;
              changed = true;
              break;
            } else {
              int currentPairOfThePartner = partnerChoices[consideredPartnerIx];
              if (isPartnerDissatisfied(consideredPartnerIx, currentPairOfThePartner, apprenticeIx)) {
                partnerChoices[consideredPartnerIx] = apprenticeIx;
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

  private void createMatch(int partnerChoice, int apprenticeIx, boolean paired, boolean changed) {
    partnerChoice = apprenticeIx;
    paired = true;
    changed = true;
  }

  /**
   * Finds apprentice's real preferences in the preference array that was populated by 'dummy' partners in data conversion
   * @param preferences apprentice's preference in data conversion
   * @return apprentice's real preference
   */
  private int[] findApprenticeRealPreference(int[] preferences) {
    int numberOfPreferences = 0;
    for (int preference : preferences) {
      if (preference == -1) {
        break;
      }
      numberOfPreferences++;
    }
    int[] result = new int[numberOfPreferences];
    System.arraycopy(preferences, 0, result, 0, numberOfPreferences);
    return result;
  }

  /**
   * Check if the apprentice prefers consideredPartnerIx more than her current one
   * @param apprenticeIx the index of the apprentice
   * @param consideredPartnerIx the index of the partner being considered by the apprentice
   * @param partnerChoices the current pairings
   * @return whether the apprentice wants to keep her pair
   */
  private boolean isApprenticeSatisfied(int apprenticeIx, int consideredPartnerIx, int[] partnerChoices) {
    if (findCurrentPartnerId(apprenticeIx, partnerChoices) != -1) {
      int currentPartner = findCurrentPartnerId(apprenticeIx, partnerChoices);
      return findRank(currentPartner, apprenticePreferences[apprenticeIx]) <
              findRank(consideredPartnerIx, apprenticePreferences[apprenticeIx]);
    } else {
      return false;
    }
  }

  /**
   * Check if partner would be more satisfied with her new choice than the current choice
   * @param partnerIx the index of the partner
   * @param currentChoiceIx the index of the apprentice the partner is currently paired with
   * @param newChoiceIx the index of the proposing apprentice
   * @return whether the partner would be more satisfied
   */
  private boolean isPartnerDissatisfied(int partnerIx, int currentChoiceIx, int newChoiceIx) {
    int currentPairRank = findRank(currentChoiceIx, partnerPreferences[partnerIx])
            + findRank(partnerIx, apprenticePreferences[currentChoiceIx]);
    int newPairRank = findRank(newChoiceIx, partnerPreferences[partnerIx])
            + findRank(partnerIx, apprenticePreferences[newChoiceIx]);
    return currentPairRank > newPairRank;
  }

  /**
   * Finds the current partner that the apprentice is matched with
   * @param apprenticeIx apprentice index
   * @param partnerChoices partner's choices
   * @return null if the apprentice doesn't have a partner yet else return index of the current partner
   */
  private int findCurrentPartnerId(int apprenticeIx, int[] partnerChoices) {
    for (int i = 0; i < partnerChoices.length; i++) {
      if (partnerChoices[i] == apprenticeIx) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Finds the rank for a user (apprentice or partner) in a preference array. If the user does not
   * appear in the preferences, we return a large value to indicate apathy.
   * @param userIx the index of the user
   * @param preferences the array containing user indices ordered by rank
   * @return the rank of the user
   */
  private int findRank(int userIx, int[] preferences) {
    for (int i = 0; i < preferences.length; i++) {
      if (preferences[i] == userIx) {
        return i + 1;
      }
    }
    final int LARGE_VALUE = 1000;
    return Math.max(partnerSize, apprenticeSize) + LARGE_VALUE;
  }

}
