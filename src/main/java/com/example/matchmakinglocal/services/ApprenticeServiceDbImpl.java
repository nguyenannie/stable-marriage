package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Apprentice;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApprenticeServiceDbImpl implements ApprenticeService {
    @Autowired
    ApprenticeRepository apprenticeRepository;

    @Override
    public void save(Apprentice apprentice) {
        apprenticeRepository.save(apprentice);
    }

    @Override
    public Apprentice findOne(String id) {
        return apprenticeRepository.findOne(id);
    }

//    @Override
//    public List<Apprentice> findByCohortClassAndEmail(String cohortClass, String email) {
//        return apprenticeRepository.findByCohortClassAndEmail(cohortClass, email);
//    }

//    public List<Apprentice> findOptional(String cohortClass, String email) {
//        List<Apprentice> result;
//
//        if(email == null || email.equals("")) {
//            result = apprenticeRepository.findByCohortClass(cohortClass);
//        } else if (cohortClass == null || cohortClass.equals("")){
//            result = apprenticeRepository.findByCohortClass(email);
//        } else {
//            result = this.findByCohortClassAndEmail(cohortClass, email);
//        }
//
//        return result;
//
//    }

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
}
