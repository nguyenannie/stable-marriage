package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class MatchmakingServiceTest {

  @Mock
  ApprenticeRepository apprenticeRepositoryMock;

  @Mock
  PartnerRepository partnerRepositoryMock;

  @InjectMocks
  MatchmakingService matchmakingService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void returnMatches() throws Exception {
    List<Apprentice> apprentices = new ArrayList<>();
    apprentices.add(new Apprentice("a1@gmail.com", "1234-5678", "karakk", "Csharp", true));
    apprentices.add(new Apprentice("a2@gmail.com", "2234-5678", "karakk", "Csharp", true));
    apprentices.add(new Apprentice("a3@gmail.com", "3234-5678", "vukk", "java", true));
    apprentices.add(new Apprentice("a4@gmail.com", "4234-5678", "vukk", "java", true));
    apprentices.add(new Apprentice("a5@gmail.com", "5234-5678", "karakk", "Csharp", true));

    List<Partner> partners = new ArrayList<>();
    partners.add(new Partner("p1@gmail.com", "0123-4567",  "CoolCode"));
    partners.add(new Partner("p2@gmail.com", "0223-4567", "AdvancedAPI"));
    partners.add(new Partner("p3@gmail.com", "0133-4567", "DreamLoop"));

    int apprenticeSize = apprentices.size();
    int partnerSize = partners.size();

    apprentices.get(0).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()), new Preference(2, partners.get(0).getId()), new Preference(3, partners.get(2).getId())));
    apprentices.get(1).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()), new Preference(2, partners.get(1).getId()), new Preference(3, partners.get(2).getId())));
    apprentices.get(2).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()), new Preference(2, partners.get(1).getId()), new Preference(3, partners.get(2).getId())));
    apprentices.get(3).setPreferences(Arrays.asList(new Preference(1, partners.get(2).getId()), new Preference(2, partners.get(1).getId()), new Preference(3, partners.get(0).getId())));
    apprentices.get(4).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()), new Preference(2, partners.get(2).getId()), new Preference(3, partners.get(0).getId())));

    partners.get(0).setPreferences(Arrays.asList(new Preference(1, apprentices.get(0).getId()),new Preference(2, apprentices.get(1).getId()), new Preference(3, apprentices.get(4).getId()), new Preference(4, apprentices.get(2).getId()), new Preference(5, apprentices.get(3).getId()) ));
    partners.get(1).setPreferences(Arrays.asList(new Preference(1, apprentices.get(2).getId()),new Preference(2, apprentices.get(0).getId()), new Preference(3, apprentices.get(3).getId())));
    partners.get(2).setPreferences(Arrays.asList(new Preference(1, apprentices.get(2).getId()),new Preference(2, apprentices.get(3).getId()), new Preference(3, apprentices.get(0).getId())));

    doReturn(apprentices).when(apprenticeRepositoryMock).findAll();
    doReturn(partners).when(partnerRepositoryMock).findAll();

    doReturn(apprentices.get(0)).when(apprenticeRepositoryMock).findOne(apprentices.get(0).getId());
    doReturn(apprentices.get(1)).when(apprenticeRepositoryMock).findOne(apprentices.get(1).getId());
    doReturn(apprentices.get(2)).when(apprenticeRepositoryMock).findOne(apprentices.get(2).getId());
    doReturn(apprentices.get(3)).when(apprenticeRepositoryMock).findOne(apprentices.get(3).getId());
    doReturn(apprentices.get(4)).when(apprenticeRepositoryMock).findOne(apprentices.get(4).getId());

    doReturn(partners.get(0)).when(partnerRepositoryMock).findOne(partners.get(0).getId());
    doReturn(partners.get(1)).when(partnerRepositoryMock).findOne(partners.get(1).getId());
    doReturn(partners.get(2)).when(partnerRepositoryMock).findOne(partners.get(2).getId());

    HashMap<String, String> results = new HashMap<>();
    results.put(partners.get(0).getId(), apprentices.get(1).getId());
    results.put(partners.get(1).getId(), apprentices.get(2).getId());
    results.put(partners.get(2).getId(), apprentices.get(3).getId());

    assertEquals(results, matchmakingService.returnMatches());
  }

}