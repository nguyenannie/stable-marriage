package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.entities.Partner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends CrudRepository<Partner, String> {

  List<Partner> findAll();
  Partner findById(String id);

}

