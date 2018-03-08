package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Preference;
import com.example.matchmakinglocal.repositories.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceServiceDbImpl implements PreferenceService {
    @Autowired
    PreferenceRepository preferenceRepository;
    @Override
    public void save(Preference preference) {
        preferenceRepository.save(preference);
    }

    @Override
    public List<Preference> findAllByUserId(String id) {
        return preferenceRepository.findAllByUserId(id);
    }
}

