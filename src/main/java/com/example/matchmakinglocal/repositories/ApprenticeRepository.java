package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.entities.Apprentice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprenticeRepository extends CrudRepository<Apprentice, String>, JpaSpecificationExecutor {

  List<Apprentice> findByCohortClassContainingIgnoreCase(String cohortClass);
  Apprentice findByEmailContaining(String email);
  Apprentice findByPhoneNumber(String phoneNumber);
  List<Apprentice> findByFirstNameContainingIgnoreCase(String firstName);
  List<Apprentice> findByLastNameContainingIgnoreCase(String lastName);
  List<Apprentice> findByCohortContainingIgnoreCase(String cohort);
  List<Apprentice> findByHungarianSpeaker(boolean isHungarianSpeaker);
  List<Apprentice> findAll();

}

