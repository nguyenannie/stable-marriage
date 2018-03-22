package com.example.matchmakinglocal.factories;

import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.models.entities.User;

public class PreferenceFactory {

  public Preference create(int ranking, String selectionId, User user) {
    return new Preference(ranking, selectionId, user);
  }

  public Preference create(long id, int ranking, String selectionId, User user) {
    return new Preference(id, ranking, selectionId, user);
  }

}
