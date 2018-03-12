package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.Apprentice;
import com.sun.istack.internal.Nullable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprenticeRepository extends CrudRepository<Apprentice, String>, JpaSpecificationExecutor {
//  @Query("select a from apprentice a where (:cohortClass is null and a.email = :email)")
//  List<Apprentice> findOptional(@Param("cohortClass") String cohortClass, @Param("email") String email);
//  List<Apprentice> findByCohortClassAndEmail(@Param("cohortClass") String cohortClass, @Param("email") String email);
  List<Apprentice> findByCohortClass(String cohortClass);
  List<Apprentice> findByEmail(String email);
  List<Apprentice> findAll();
  Apprentice findById(String id);
}

