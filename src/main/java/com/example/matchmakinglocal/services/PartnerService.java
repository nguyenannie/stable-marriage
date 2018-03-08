package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Partner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartnerService {
    void save(Partner partner);
    List<Partner> findAll();
    List<String> getAllEmails();
    Partner findOne(String id);
}
