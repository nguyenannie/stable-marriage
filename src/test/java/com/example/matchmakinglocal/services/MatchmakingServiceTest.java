package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.models.entities.Status;
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
  private ApprenticeRepository apprenticeRepositoryMock;

  @Mock
  private PartnerRepository partnerRepositoryMock;

  @InjectMocks
  private MatchmakingService matchmakingService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void returnMatchesWhenBothPartnersAndApprenticesAllRateEachOther() throws Exception {
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

    apprentices.get(0).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
                                                    new Preference(2, partners.get(0).getId()),
                                                    new Preference(3, partners.get(2).getId())));
    apprentices.get(1).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
                                                    new Preference(2, partners.get(1).getId()),
                                                    new Preference(3, partners.get(2).getId())));
    apprentices.get(2).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
                                                    new Preference(2, partners.get(1).getId()),
                                                    new Preference(3, partners.get(2).getId())));
    apprentices.get(3).setPreferences(Arrays.asList(new Preference(1, partners.get(2).getId()),
                                                    new Preference(2, partners.get(1).getId()),
                                                    new Preference(3, partners.get(0).getId())));
    apprentices.get(4).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
                                                    new Preference(2, partners.get(2).getId()),
                                                    new Preference(3, partners.get(0).getId())));

    partners.get(0).setPreferences(Arrays.asList(new Preference(1, apprentices.get(0).getId()),
                                                 new Preference(2, apprentices.get(1).getId()),
                                                 new Preference(3, apprentices.get(4).getId()),
                                                 new Preference(4, apprentices.get(2).getId()),
                                                 new Preference(5, apprentices.get(3).getId()) ));
    partners.get(1).setPreferences(Arrays.asList(new Preference(1, apprentices.get(2).getId()),
                                                 new Preference(2, apprentices.get(0).getId()),
                                                 new Preference(3, apprentices.get(3).getId())));
    partners.get(2).setPreferences(Arrays.asList(new Preference(1, apprentices.get(2).getId()),
                                                 new Preference(2, apprentices.get(3).getId()),
                                                 new Preference(3, apprentices.get(0).getId())));

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

    assertEquals(results, matchmakingService.returnMatchesInStringMap());
  }

  @Test
  public void returnMatchesWhenApprenticesRateAllPartnerButParnersDontRateAllApprentices() throws Exception {
    List<Apprentice> apprentices = new ArrayList<>();
    apprentices.add(new Apprentice("ann@gmail.com", "1234-5678", Status.ACTIVE,"",
            "ann","green","corsac","Csharp", true));
    apprentices.add(new Apprentice("bob@gmail.com", "2234-5678", Status.ACTIVE,"",
            "bob","pink","corsac","Java", false));
    apprentices.add(new Apprentice("charlie@gmail.com", "3234-5678", Status.ACTIVE,"",
            "charlie","blue","corsac","Java", true));
    apprentices.add(new Apprentice("dick@gmail.com", "4234-5678", Status.ACTIVE,"",
            "dick","purple","corsac","JavaScript", true));
    apprentices.add(new Apprentice("elle@gmail.com", "5234-5678", Status.ACTIVE,"",
            "elle","black","corsac","Csharp", true));
    apprentices.add(new Apprentice("fanny@gmail.com", "4820-3720", Status.ACTIVE,"",
            "fanny","white","corsac","JavaScript", true));
    apprentices.add(new Apprentice("ginnie@gmail.com", "3280-2342", Status.ACTIVE,"",
            "ginnie","yellow","corsac","JavaScript", true));
    apprentices.add(new Apprentice("hulk@gmail.com", "4837-4928", Status.ACTIVE,"",
            "hulk","red","corsac","Java", false));
    apprentices.add(new Apprentice("ian@gmail.com", "4820-5730", Status.ACTIVE,"",
            "ian","brown","corsac","Csharp", true));
    apprentices.add(new Apprentice("jay@gmail.com", "5039-5932", Status.ACTIVE,"",
            "jay","grey","corsac","Csharp", true));

    List<Partner> partners = new ArrayList<>();
    partners.add(new Partner("google@gmail.com", "9248-4354",Status.ACTIVE,"", "Google"));
    partners.add(new Partner("microsoft@gmail.com", "2343-0954",Status.ACTIVE,"",  "Microsoft"));
    partners.add(new Partner("intel@gmail.com", "4830-1234",Status.ACTIVE,"",  "Intel"));
    partners.add(new Partner("apple@gmail.com", "3984-2343",Status.ACTIVE,"",  "Apple"));

    apprentices.get(0).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(2).getId()),
            new Preference(4, partners.get(3).getId())));
    apprentices.get(1).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(3).getId()),
            new Preference(3, partners.get(2).getId()),
            new Preference(4, partners.get(0).getId())));
    apprentices.get(2).setPreferences(Arrays.asList(new Preference(1, partners.get(2).getId()),
            new Preference(2, partners.get(0).getId()),
            new Preference(3, partners.get(1).getId()),
            new Preference(4, partners.get(3).getId())));
    apprentices.get(3).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(2).getId()),
            new Preference(3, partners.get(1).getId()),
            new Preference(4, partners.get(3).getId())));
    apprentices.get(4).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(2).getId()),
            new Preference(3, partners.get(3).getId()),
            new Preference(4, partners.get(0).getId())));
    apprentices.get(5).setPreferences(Arrays.asList(new Preference(1, partners.get(3).getId()),
            new Preference(2, partners.get(0).getId()),
            new Preference(3, partners.get(1).getId()),
            new Preference(4, partners.get(2).getId())));
    apprentices.get(6).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(3).getId()),
            new Preference(3, partners.get(2).getId()),
            new Preference(4, partners.get(1).getId())));
    apprentices.get(7).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(3).getId()),
            new Preference(4, partners.get(2).getId())));
    apprentices.get(8).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(3).getId()),
            new Preference(3, partners.get(0).getId()),
            new Preference(4, partners.get(2).getId())));
    apprentices.get(9).setPreferences(Arrays.asList(new Preference(1, partners.get(2).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(3).getId()),
            new Preference(4, partners.get(0).getId())));

    partners.get(0).setPreferences(Arrays.asList(new Preference(1, apprentices.get(4).getId()),
            new Preference(2, apprentices.get(1).getId()),
            new Preference(3, apprentices.get(3).getId()),
            new Preference(4, apprentices.get(7).getId()),
            new Preference(5, apprentices.get(9).getId()) ));
    partners.get(1).setPreferences(Arrays.asList(new Preference(1, apprentices.get(1).getId()),
            new Preference(2, apprentices.get(7).getId()),
            new Preference(3, apprentices.get(5).getId()),
            new Preference(4, apprentices.get(0).getId()),
            new Preference(5, apprentices.get(2).getId()),
            new Preference(6, apprentices.get(8).getId())));
    partners.get(2).setPreferences(Arrays.asList(new Preference(1, apprentices.get(3).getId()),
            new Preference(2, apprentices.get(2).getId()),
            new Preference(3, apprentices.get(1).getId()),
            new Preference(4, apprentices.get(6).getId()),
            new Preference(5, apprentices.get(4).getId()) ));
    partners.get(3).setPreferences(Arrays.asList(new Preference(1, apprentices.get(8).getId()),
            new Preference(2, apprentices.get(7).getId()),
            new Preference(3, apprentices.get(6).getId()),
            new Preference(4, apprentices.get(9).getId()),
            new Preference(5, apprentices.get(3).getId()) ));

    doReturn(apprentices).when(apprenticeRepositoryMock).findAll();
    doReturn(partners).when(partnerRepositoryMock).findAll();

    doReturn(apprentices.get(0)).when(apprenticeRepositoryMock).findOne(apprentices.get(0).getId());
    doReturn(apprentices.get(1)).when(apprenticeRepositoryMock).findOne(apprentices.get(1).getId());
    doReturn(apprentices.get(2)).when(apprenticeRepositoryMock).findOne(apprentices.get(2).getId());
    doReturn(apprentices.get(3)).when(apprenticeRepositoryMock).findOne(apprentices.get(3).getId());
    doReturn(apprentices.get(4)).when(apprenticeRepositoryMock).findOne(apprentices.get(4).getId());
    doReturn(apprentices.get(5)).when(apprenticeRepositoryMock).findOne(apprentices.get(5).getId());
    doReturn(apprentices.get(6)).when(apprenticeRepositoryMock).findOne(apprentices.get(6).getId());
    doReturn(apprentices.get(7)).when(apprenticeRepositoryMock).findOne(apprentices.get(7).getId());
    doReturn(apprentices.get(8)).when(apprenticeRepositoryMock).findOne(apprentices.get(8).getId());
    doReturn(apprentices.get(9)).when(apprenticeRepositoryMock).findOne(apprentices.get(9).getId());

    doReturn(partners.get(0)).when(partnerRepositoryMock).findOne(partners.get(0).getId());
    doReturn(partners.get(1)).when(partnerRepositoryMock).findOne(partners.get(1).getId());
    doReturn(partners.get(2)).when(partnerRepositoryMock).findOne(partners.get(2).getId());
    doReturn(partners.get(3)).when(partnerRepositoryMock).findOne(partners.get(3).getId());

    HashMap<String, String> results = new HashMap<>();
    results.put(partners.get(0).getId(), apprentices.get(3).getId());
    results.put(partners.get(1).getId(), apprentices.get(1).getId());
    results.put(partners.get(2).getId(), apprentices.get(2).getId());
    results.put(partners.get(3).getId(), apprentices.get(8).getId());

    assertEquals(results, matchmakingService.returnMatchesInStringMap());
  }

  @Test
  public void returnNullWhenPartnerOrApprenticeDontGiveTheirPreference() throws Exception {
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

    apprentices.get(0).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(0).getId()),
            new Preference(3, partners.get(2).getId())));
    apprentices.get(1).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(2).getId())));
    apprentices.get(2).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(2).getId())));
    apprentices.get(4).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(2).getId()),
            new Preference(3, partners.get(0).getId())));

    partners.get(0).setPreferences(Arrays.asList(new Preference(1, apprentices.get(0).getId()),
            new Preference(2, apprentices.get(1).getId()),
            new Preference(3, apprentices.get(4).getId()),
            new Preference(4, apprentices.get(2).getId()),
            new Preference(5, apprentices.get(3).getId()) ));

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

    assertEquals(null, matchmakingService.returnMatchesInStringMap());
  }

  @Test
  public void returnMatchesWhenPartnersAndApprenticesDontFullyRateEachOther() throws Exception {
    List<Apprentice> apprentices = new ArrayList<>();
    apprentices.add(new Apprentice("ann@gmail.com", "1234-5678", Status.ACTIVE,"",
            "ann","green","corsac","Csharp", true));
    apprentices.add(new Apprentice("bob@gmail.com", "2234-5678", Status.ACTIVE,"",
            "bob","pink","corsac","Java", false));
    apprentices.add(new Apprentice("charlie@gmail.com", "3234-5678", Status.ACTIVE,"",
            "charlie","blue","corsac","Java", true));
    apprentices.add(new Apprentice("dick@gmail.com", "4234-5678", Status.ACTIVE,"",
            "dick","purple","corsac","JavaScript", true));
    apprentices.add(new Apprentice("elle@gmail.com", "5234-5678", Status.ACTIVE,"",
            "elle","black","corsac","Csharp", true));
    apprentices.add(new Apprentice("fanny@gmail.com", "4820-3720", Status.ACTIVE,"",
            "fanny","white","corsac","JavaScript", true));
    apprentices.add(new Apprentice("ginnie@gmail.com", "3280-2342", Status.ACTIVE,"",
            "ginnie","yellow","corsac","JavaScript", true));
    apprentices.add(new Apprentice("hulk@gmail.com", "4837-4928", Status.ACTIVE,"",
            "hulk","red","corsac","Java", false));
    apprentices.add(new Apprentice("ian@gmail.com", "4820-5730", Status.ACTIVE,"",
            "ian","brown","corsac","Csharp", true));
    apprentices.add(new Apprentice("jay@gmail.com", "5039-5932", Status.ACTIVE,"",
            "jay","grey","corsac","Csharp", true));

    List<Partner> partners = new ArrayList<>();
    partners.add(new Partner("google@gmail.com", "9248-4354",Status.ACTIVE,"", "Google"));
    partners.add(new Partner("microsoft@gmail.com", "2343-0954",Status.ACTIVE,"",  "Microsoft"));
    partners.add(new Partner("intel@gmail.com", "4830-1234",Status.ACTIVE,"",  "Intel"));
    partners.add(new Partner("oracle@gmail.com", "3984-2343",Status.ACTIVE,"",  "Apple"));
    partners.add(new Partner("facebook@gmail.com", "3984-2343",Status.ACTIVE,"",  "Apple"));
    partners.add(new Partner("qualcomm@gmail.com", "3984-2343",Status.ACTIVE,"",  "Apple"));

    apprentices.get(0).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(3).getId()),
            new Preference(3, partners.get(4).getId()),
            new Preference(4, partners.get(2).getId())));
    apprentices.get(1).setPreferences(Arrays.asList(new Preference(1, partners.get(2).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(4).getId()),
            new Preference(4, partners.get(3).getId()),
            new Preference(4, partners.get(5).getId()),
            new Preference(4, partners.get(0).getId())));
    apprentices.get(2).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(0).getId()),
            new Preference(3, partners.get(2).getId()),
            new Preference(4, partners.get(4).getId())));
    apprentices.get(3).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(4).getId()),
            new Preference(3, partners.get(1).getId()),
            new Preference(4, partners.get(3).getId()),
            new Preference(4, partners.get(2).getId()),
            new Preference(4, partners.get(5).getId())));
    apprentices.get(4).setPreferences(Arrays.asList(new Preference(1, partners.get(2).getId()),
            new Preference(2, partners.get(3).getId()),
            new Preference(3, partners.get(0).getId())));
    apprentices.get(5).setPreferences(Arrays.asList(new Preference(1, partners.get(1).getId()),
            new Preference(2, partners.get(5).getId()),
            new Preference(3, partners.get(2).getId()),
            new Preference(4, partners.get(0).getId()),
            new Preference(4, partners.get(3).getId()),
            new Preference(4, partners.get(4).getId())));
    apprentices.get(6).setPreferences(Arrays.asList(new Preference(1, partners.get(4).getId()),
            new Preference(2, partners.get(3).getId()),
            new Preference(3, partners.get(5).getId())));
    apprentices.get(7).setPreferences(Arrays.asList(new Preference(1, partners.get(0).getId()),
            new Preference(2, partners.get(4).getId()),
            new Preference(3, partners.get(3).getId()),
            new Preference(4, partners.get(1).getId()),
            new Preference(4, partners.get(5).getId()),
            new Preference(4, partners.get(2).getId())));
    apprentices.get(8).setPreferences(Arrays.asList(new Preference(1, partners.get(4).getId()),
            new Preference(2, partners.get(1).getId()),
            new Preference(3, partners.get(3).getId())));
    apprentices.get(9).setPreferences(Arrays.asList(new Preference(1, partners.get(4).getId()),
            new Preference(2, partners.get(0).getId()),
            new Preference(3, partners.get(2).getId()),
            new Preference(4, partners.get(3).getId()),
            new Preference(4, partners.get(1).getId()),
            new Preference(4, partners.get(5).getId())));

    partners.get(0).setPreferences(Arrays.asList(new Preference(1, apprentices.get(5).getId()),
            new Preference(2, apprentices.get(4).getId()),
            new Preference(3, apprentices.get(3).getId()),
            new Preference(4, apprentices.get(8).getId())));
    partners.get(1).setPreferences(Arrays.asList(new Preference(1, apprentices.get(8).getId()),
            new Preference(2, apprentices.get(3).getId()),
            new Preference(3, apprentices.get(4).getId()),
            new Preference(4, apprentices.get(2).getId())));
    partners.get(2).setPreferences(Arrays.asList(new Preference(1, apprentices.get(2).getId()),
            new Preference(2, apprentices.get(1).getId()),
            new Preference(3, apprentices.get(4).getId()),
            new Preference(4, apprentices.get(5).getId()),
            new Preference(5, apprentices.get(3).getId()) ));
    partners.get(3).setPreferences(Arrays.asList(new Preference(1, apprentices.get(0).getId()),
            new Preference(2, apprentices.get(3).getId()),
            new Preference(3, apprentices.get(2).getId()),
            new Preference(4, apprentices.get(1).getId()),
            new Preference(5, apprentices.get(4).getId()),
            new Preference(5, apprentices.get(6).getId())));
    partners.get(4).setPreferences(Arrays.asList(new Preference(1, apprentices.get(9).getId()),
            new Preference(2, apprentices.get(8).getId()),
            new Preference(3, apprentices.get(7).getId()),
            new Preference(4, apprentices.get(4).getId()),
            new Preference(5, apprentices.get(1).getId())));
    partners.get(5).setPreferences(Arrays.asList(new Preference(1, apprentices.get(2).getId()),
            new Preference(2, apprentices.get(3).getId()),
            new Preference(3, apprentices.get(6).getId()),
            new Preference(4, apprentices.get(5).getId())));

    doReturn(apprentices).when(apprenticeRepositoryMock).findAll();
    doReturn(partners).when(partnerRepositoryMock).findAll();

    doReturn(apprentices.get(0)).when(apprenticeRepositoryMock).findOne(apprentices.get(0).getId());
    doReturn(apprentices.get(1)).when(apprenticeRepositoryMock).findOne(apprentices.get(1).getId());
    doReturn(apprentices.get(2)).when(apprenticeRepositoryMock).findOne(apprentices.get(2).getId());
    doReturn(apprentices.get(3)).when(apprenticeRepositoryMock).findOne(apprentices.get(3).getId());
    doReturn(apprentices.get(4)).when(apprenticeRepositoryMock).findOne(apprentices.get(4).getId());
    doReturn(apprentices.get(5)).when(apprenticeRepositoryMock).findOne(apprentices.get(5).getId());
    doReturn(apprentices.get(6)).when(apprenticeRepositoryMock).findOne(apprentices.get(6).getId());
    doReturn(apprentices.get(7)).when(apprenticeRepositoryMock).findOne(apprentices.get(7).getId());
    doReturn(apprentices.get(8)).when(apprenticeRepositoryMock).findOne(apprentices.get(8).getId());
    doReturn(apprentices.get(9)).when(apprenticeRepositoryMock).findOne(apprentices.get(9).getId());

    doReturn(partners.get(0)).when(partnerRepositoryMock).findOne(partners.get(0).getId());
    doReturn(partners.get(1)).when(partnerRepositoryMock).findOne(partners.get(1).getId());
    doReturn(partners.get(2)).when(partnerRepositoryMock).findOne(partners.get(2).getId());
    doReturn(partners.get(3)).when(partnerRepositoryMock).findOne(partners.get(3).getId());
    doReturn(partners.get(4)).when(partnerRepositoryMock).findOne(partners.get(4).getId());
    doReturn(partners.get(5)).when(partnerRepositoryMock).findOne(partners.get(5).getId());

    HashMap<String, String> results = new HashMap<>();
    results.put(partners.get(0).getId(), apprentices.get(3).getId());
    results.put(partners.get(1).getId(), apprentices.get(8).getId());
    results.put(partners.get(2).getId(), apprentices.get(1).getId());
    results.put(partners.get(3).getId(), apprentices.get(0).getId());
    results.put(partners.get(4).getId(), apprentices.get(9).getId());
    results.put(partners.get(5).getId(), apprentices.get(5).getId());

    assertEquals(results, matchmakingService.returnMatchesInStringMap());
  }

}