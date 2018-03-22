package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApprenticeServiceDbImpl implements ApprenticeService {

  private final ApprenticeRepository apprenticeRepository;

  @Autowired
  public ApprenticeServiceDbImpl(ApprenticeRepository apprenticeRepository) {
    this.apprenticeRepository = apprenticeRepository;
  }

  @Override
  public void save(Apprentice apprentice) {
      apprenticeRepository.save(apprentice);
  }

  @Override
  public Apprentice findOne(String id) {
      return apprenticeRepository.findOne(id);
  }

  @Override
  public List<Apprentice> findByCohortClass(String cohortClass) {
    return apprenticeRepository.findByCohortClassContainingIgnoreCase(cohortClass);
  }

  @Override
  public Apprentice findByEmail(String email) {
    return apprenticeRepository.findByEmailContaining(email);
  }

  @Override
  public Apprentice findByPhoneNumber(String phoneNumber) {
    return apprenticeRepository.findByPhoneNumber(phoneNumber);
  }

  @Override
  public List<Apprentice> findByFirstName(String firstName) {
    return apprenticeRepository.findByFirstNameContainingIgnoreCase(firstName);
  }

  @Override
  public List<Apprentice> findByLastName(String lastName) {
    return apprenticeRepository.findByLastNameContainingIgnoreCase(lastName);
  }

  @Override
  public List<Apprentice> findByCohort(String cohort) {
    return apprenticeRepository.findByCohortContainingIgnoreCase(cohort);
  }

  @Override
  public List<Apprentice> findByHungarianSpeaker(boolean isHungarianSpeaker) {
    return apprenticeRepository.findByHungarianSpeaker(isHungarianSpeaker);
  }

  public List retrieveApprentices(Apprentice filter) {
      return apprenticeRepository.findAll((root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();
          if(filter.getCohort() != null) predicates.add(cb.equal(root.get("cohort"), filter.getCohort()));
          return cb.and(predicates.toArray(new Predicate[0]));
      });
  }

  @Override
  public List<Apprentice> findAll() {
      return apprenticeRepository.findAll();
  }

  @Override
  public String findApprenticeNameById(String id) {
    if(apprenticeRepository.findOne(id) != null) {
      return apprenticeRepository.findOne(id).getFirstName() + " " + apprenticeRepository.findOne(id).getLastName();
    } else {
      return null;
    }
  }

}
