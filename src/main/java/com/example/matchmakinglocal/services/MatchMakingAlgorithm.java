package com.example.matchmakinglocal.services;

public class MatchMakingAlgorithm {

  int apprenticeNum;
  int partnerNum;
  public int[] match;
  int[] apprenticeCounter;
  int count;
  int[][] fc;
  int[][] apprenticeChoice;
  int[][] partnerChoice;

  public MatchMakingAlgorithm(int apprenticeNum, int partnerNum, int[][] prenticeChoice, int[][] partnerChoice) {
    this.apprenticeChoice = apprenticeChoice;
    this.partnerChoice = partnerChoice;
    this.apprenticeNum = apprenticeNum;
    this.partnerNum = partnerNum;
    match = new int[partnerNum + 1];
    fc = new int[partnerNum + 1][apprenticeNum + 1];
    apprenticeCounter = new int[apprenticeNum + 1];
  }

  void propose(int i) {
    if(i != 0 && apprenticeCounter[i] < partnerNum + 1) {
      count++;
      int j = apprenticeCounter[i];
      apprenticeCounter[i] = j + 1;
      refusal(i, apprenticeChoice[i][j]);
    }
  }

  void refusal(int i, int j) {
    if (fc[j][match[j]] > fc[j][i]) {
      int l = match[j];
      match[j] = i;
      propose(l);
    } else {
      propose(i);
    }
  }

  void init() {
    for (int i = 1; i < partnerNum + 1; i++) {
      for(int j = 1; j < apprenticeNum + 1; j++) {
        int pc = partnerChoice[i][j];
        if(pc == 0) {
          fc[i][pc] = Integer.MAX_VALUE;
        } else {
          fc[i][pc] = j;
        }
        match[i] = 0;
        fc[i][0] = apprenticeNum + 1;
      }
    }

    for (int i = 1; i < apprenticeNum + 1; i++) {
      apprenticeCounter[i] = 1;
    }

    count = 0;
  }

  public void execute() {
    init();
    for (int i = 1; i < apprenticeNum + 1; i++) {
      propose(i);
    }
  }

}
