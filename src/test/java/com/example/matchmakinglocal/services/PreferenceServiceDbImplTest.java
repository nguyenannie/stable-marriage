package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.factories.PreferenceFactory;
import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.models.entities.Preference;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import com.example.matchmakinglocal.repositories.PreferenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class PreferenceServiceDbImplTest {

  @Mock
  PartnerRepository partnerRepositoryMock;

  @Mock
  ApprenticeRepository apprenticeRepositoryMock;

  @Mock
  PreferenceRepository preferenceRepositoryMock;

  @InjectMocks
  PreferenceServiceDbImpl preferenceServiceDb;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findAllWithData() throws Exception {
    Partner testPartner = new Partner();
    List<Preference> preferences = new ArrayList<>();
    preferences.add(preferenceFactory.create(1, "selectionId", testPartner));
    preferences.add(preferenceFactory.create(2, "selectionId2", testPartner));

    doReturn(preferences.get(0)).when(preferenceRepositoryMock).findOne(preferences.get(0).getId());
    doReturn(preferences.get(1)).when(preferenceRepositoryMock).findOne(preferences.get(1).getId());
    doReturn(preferences).when(preferenceRepositoryMock).findAll();

    assertEquals(preferences, preferenceServiceDb.findAll());
  }

  @Test
  public void findAllWithoutData() throws Exception {
    Partner testPartner = new Partner();
    List<Preference> preferences = new ArrayList<>();
    preferences.add(preferenceFactory.create(1, "selectionId", testPartner));
    preferences.add(preferenceFactory.create(2, "selectionId2", testPartner));
    preferences = null;

    when(preferenceRepositoryMock.findAll()).thenReturn(preferences);
    assertEquals(null, preferenceServiceDb.findAll());
  }

  @Test
  public void findOneWithData() throws Exception {
    Partner testPartner = new Partner();
    Apprentice apprentice1 = new Apprentice();
    Apprentice apprentice2 = new Apprentice();
    List<Preference> preferences = new ArrayList<>();
    preferences.add(preferenceFactory.create(0,1, apprentice1.getId(), testPartner));
    preferences.add(preferenceFactory.create(1,2, apprentice2.getId(), testPartner));

    doReturn(preferences.get(0)).when(preferenceRepositoryMock).findOne(preferences.get(0).getId());
    doReturn(preferences.get(1)).when(preferenceRepositoryMock).findOne(preferences.get(1).getId());
    doReturn(preferences).when(preferenceRepositoryMock).findAll();

    assertEquals(preferences.get(0), preferenceServiceDb.findOne(preferences.get(0).getId()));
    assertEquals(preferences.get(1), preferenceServiceDb.findOne(preferences.get(1).getId()));
  }

  @Test
  public void selectedUserPreferenceNamesTest() throws Exception {
    Partner testPartner = new Partner();
    List<Partner> partners = new ArrayList<>();
    partners.add(testPartner);
    Apprentice apprentice1 = new Apprentice();
    apprentice1.setFirstName("Annie");
    apprentice1.setLastName("Nguyen");
    Apprentice apprentice2 = new Apprentice();
    apprentice2.setFirstName("Krisz");
    apprentice2.setLastName("Pinter");
    List<Apprentice> apprentices = new ArrayList<>();
    apprentices.add(apprentice1);
    apprentices.add(apprentice2);
    List<Preference> preferences = new ArrayList<>();
    Preference preference1 = preferenceFactory.create(0,1, apprentice1.getId(), testPartner);
    Preference preference2 = preferenceFactory.create(1,2, apprentice2.getId(), testPartner);
    Preference preference3 = preferenceFactory.create(2,1, testPartner.getId(), apprentice1);
    Preference preference4 = preferenceFactory.create(3,2, testPartner.getId(), apprentice2);
    preferences.add(preference1);
    preferences.add(preference2);
    testPartner.setPreferences(new ArrayList<>(Arrays.asList(preference1, preference2)));
    apprentice1.setPreferences(new ArrayList<>(Arrays.asList(preference3)));
    apprentice2.setPreferences(new ArrayList<>(Arrays.asList(preference4)));

    doReturn(preferences.get(0)).when(preferenceRepositoryMock).findOne(preferences.get(0).getId());
    doReturn(preferences.get(1)).when(preferenceRepositoryMock).findOne(preferences.get(1).getId());
    doReturn(preferences).when(preferenceRepositoryMock).findAll();
    doReturn(partners).when(partnerRepositoryMock).findAll();
    doReturn(partners.get(0)).when(partnerRepositoryMock).findOne(partners.get(0).getId());
    doReturn(apprentices.get(0)).when(apprenticeRepositoryMock).findOne(apprentices.get(0).getId());
    doReturn(apprentices.get(1)).when(apprenticeRepositoryMock).findOne(apprentices.get(1).getId());

    List<String> result1 = new ArrayList<>(Arrays.asList(apprentice1.getFirstName() + " " + apprentice1.getLastName(), apprentice2.getFirstName() + " " + apprentice2.getLastName()));
    List<String> result2 = new ArrayList<>(Arrays.asList(testPartner.getCompanyName()));
    assertEquals(result1, preferenceServiceDb.selectedUserPreferenceNames(testPartner.getId()));
    assertEquals(result2, preferenceServiceDb.selectedUserPreferenceNames(apprentice1.getId()));
  }

  private final PreferenceFactory preferenceFactory = new PreferenceFactory();
  private List<Preference> preferences = new ArrayList<>();
  private List<Apprentice> apprentices = new ArrayList<>();
  private List<Partner> partners = new ArrayList<>();

  @Before
  public void createData() {
    Partner testPartner = new Partner("testCompany@gmail.com", "1234-5678", "testCompany");
    Partner testPartner2 = new Partner("testCompany2@gmail.com", "1234-5678", "testCompany2");
    Partner testPartner3 = new Partner("testCompany3@gmail.com", "1234-5678", "testCompany3");
    partners.add(testPartner);
    partners.add(testPartner2);
    partners.add(testPartner3);
    Apprentice testApprentice = new Apprentice("testApprentice@gmail.com", "1234-5678", "Peter", "Mock", true);
    Apprentice testApprentice2 = new Apprentice("testApprentice2@gmail.com", "1234-5678", "Jane", "Mock", true);
    Apprentice testApprentice3 = new Apprentice("testApprentice3@gmail.com", "1234-5678", "Sam", "Mock",  true);
    apprentices.add(testApprentice);
    apprentices.add(testApprentice2);
    apprentices.add(testApprentice3);
    preferences.add(preferenceFactory.create(0, 1, testApprentice.getId(), testPartner));
    preferences.add(preferenceFactory.create(1, 2, testApprentice2.getId(), testPartner));
    preferences.add(preferenceFactory.create(2, 3, testApprentice3.getId(), testPartner));
    preferences.add(preferenceFactory.create(3, 1, testPartner.getId(), testApprentice));
    preferences.add(preferenceFactory.create(4, 2, testPartner2.getId(), testApprentice));
    preferences.add(preferenceFactory.create(5, 3, testPartner3.getId(), testApprentice));
    testPartner.setPreferences(new ArrayList<>(Arrays.asList(preferences.get(0), preferences.get(1), preferences.get(2))));
    testApprentice.setPreferences(new ArrayList<>(Arrays.asList(preferences.get(3), preferences.get(4), preferences.get(5))));
  }

  @Test
  public void findOneWithInputWithData() throws Exception {
    when(preferenceRepositoryMock.findOne(preferences.get(0).getId())).thenReturn(preferences.get(0));
    when(preferenceRepositoryMock.findOne(preferences.get(1).getId())).thenReturn(preferences.get(1));
    for (int i = 0; i < 2; i++) {
      assertEquals(preferences.get(i), preferenceServiceDb.findOne(preferences.get(i).getId()));
    }
  }

  @Test
  public void findOneWithWrongInputWithData() throws Exception {
    when(preferenceRepositoryMock.findOne((long)3)).thenReturn(null);
    assertEquals(null, preferenceServiceDb.findOne((long)3));
  }

  @Test
  public void findOneWithInputWithout() throws Exception {
    when(preferenceRepositoryMock.findOne(preferences.get(1).getId())).thenReturn(null);
    assertEquals(null, preferenceServiceDb.findOne(preferences.get(1).getId()));
  }

  @Test
  public void selectedUserPreferenceNames_Should_ReturnPreferenceListAsStringInOrder_Test() throws Exception {
    List<String> apprenticeStringResults = new ArrayList<>();
    List<String> partnerStringResults = new ArrayList<>();
    apprentices.stream().map(apprentice -> apprentice.getFirstName() + " " + apprentice.getLastName()).forEach(apprenticeStringResults::add);
    partners.stream().map(Partner::getCompanyName).forEach(partnerStringResults::add);
    System.out.println(partnerStringResults);
    System.out.println(apprenticeStringResults);

    doReturn(preferences.get(0)).when(preferenceRepositoryMock).findOne(preferences.get(0).getId());
    doReturn(preferences.get(1)).when(preferenceRepositoryMock).findOne(preferences.get(1).getId());
    doReturn(preferences.get(2)).when(preferenceRepositoryMock).findOne(preferences.get(2).getId());
    doReturn(preferences).when(preferenceRepositoryMock).findAll();
    doReturn(partners).when(partnerRepositoryMock).findAll();
    doReturn(partners.get(0)).when(partnerRepositoryMock).findOne(partners.get(0).getId());
    doReturn(apprentices.get(0)).when(apprenticeRepositoryMock).findOne(apprentices.get(0).getId());
    doReturn(apprentices.get(1)).when(apprenticeRepositoryMock).findOne(apprentices.get(1).getId());
    doReturn(apprentices.get(2)).when(apprenticeRepositoryMock).findOne(apprentices.get(2).getId());
    assertEquals(apprenticeStringResults, preferenceServiceDb.selectedUserPreferenceNames(partners.get(0).getId()));
  }


}