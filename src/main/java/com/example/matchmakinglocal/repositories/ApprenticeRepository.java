package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.entities.Apprentice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprenticeRepository extends CrudRepository<Apprentice, String>, JpaSpecificationExecutor {

  List<Apprentice> findByCohortClass(String cohortClass);
  List<Apprentice> findByEmail(String email);
  List<Apprentice> findAll();
  Apprentice findById(String id);

}

