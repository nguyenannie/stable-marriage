package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerServiceDbImpl implements PartnerService {

  private final PartnerRepository partnerRepository;

  @Autowired
  public PartnerServiceDbImpl(PartnerRepository partnerRepository) {
      this.partnerRepository = partnerRepository;
  }

  @Override
  public void save(Partner partner) {
      partnerRepository.save(partner);
  }

  @Override
  public List<Partner> findAll() {
      return partnerRepository.findAll();
  }

  public List<String> getAllEmails() {

      List<String> emails = new ArrayList<>();
      for(Partner partner : findAll()) {
          emails.add(partner.getEmail());
      }

      return emails;
  }

  @Override
  public Partner findOne(String id) {
      return partnerRepository.findOne(id);
  }

  @Override
  public Partner findByEmail(String email) {
      return partnerRepository.findByEmailContaining(email);
  }

  @Override
  public Partner findByPhoneNumber(String phoneNumber) {
      return partnerRepository.findByPhoneNumber(phoneNumber);
  }

  @Override
  public Partner findByCompanyName(String companyName) {
    return partnerRepository.findByCompanyNameContainingIgnoreCase(companyName);
  }

  @Override
  public String findCompanyNameById(String id) {
    return partnerRepository.findOne(id).getCompanyName();
  }

}
