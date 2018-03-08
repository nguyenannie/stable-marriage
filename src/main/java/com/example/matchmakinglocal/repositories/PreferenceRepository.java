package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.Preference;
import com.example.matchmakinglocal.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends CrudRepository<Preference, String> {
    List<Preference> findAllByUserId(String id);
    List<Preference> findAll();
    List<Preference> findByUser(User user);
    List<Preference> findByUserEmail(String email);
    List<Preference> findByUserPhoneNumber(String phoneNumber);
    List<Preference> findBySelectionId(String selectionId);
    Preference findByUserAndRanking(User user, int ranking);
    List<Preference> findByRanking(int ranking);
    Preference findByUserAndSelectionId(User user, String selectionId);
}

