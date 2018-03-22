package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.factories.ApprenticeFactory;
import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Status;
import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.repositories.PartnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class ApprenticeServiceDbImplTest {

  @Mock
  private ApprenticeRepository apprenticeRepositoryMock;

  @InjectMocks
  private ApprenticeServiceDbImpl apprenticeServiceDb;

  private List<Apprentice> apprentices = new ArrayList<>();
  private ApprenticeFactory apprenticeFactory = new ApprenticeFactory();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    apprentices.add(apprenticeFactory.create("ann@gmail.com", "1234-5678", "ann", "green", Status.ACTIVE));
    apprentices.add(apprenticeFactory.create("bob@gmail.com", "2234-5678", "bob", "pink", Status.ACTIVE));
    apprentices.add(apprenticeFactory.create("charlie@gmail.com", "1234-8320", "charlie", "blue", Status.ACTIVE));
    apprentices.add(apprenticeFactory.create("dick@gmail.com", "1234-5678", "dick", "purple", Status.ACTIVE));
    apprentices.add(apprenticeFactory.create("elle@gmail.com", "1234-5678", "elle", "black", Status.ACTIVE));

    for (Apprentice apprentice : apprentices) {
      doReturn(apprentice).when(apprenticeRepositoryMock).findOne(apprentice.getId());
    }
  }

  @Test
  public void findOne_Should_ReturnApprenticeByTheProvidedId_Test() throws Exception {
    for (Apprentice apprentice : apprentices) {
      assertEquals(apprentice, apprenticeServiceDb.findOne(apprentice.getId()));
    }
  }

  @Test
  public void findOne_Should_ReturnNull_When_IdDoesNotExist_Test() throws Exception {
    String doesNotExistId = "92834kjhsakjhfkjsdf";
    doReturn(null).when(apprenticeRepositoryMock).findOne(doesNotExistId);
    assertEquals(null, apprenticeServiceDb.findOne(doesNotExistId));
  }

  @Test
  public void findAll_ShouldReturnAllTheApprenticesInTheDb_Test() throws Exception {
    doReturn(apprentices).when(apprenticeRepositoryMock).findAll();
    assertEquals(apprentices, apprenticeServiceDb.findAll());
  }

  @Test
  public void findAll_Should_ReturnNull_When_ThereIsNoApprenticesInDB_Test() throws Exception {
    doReturn(null).when(apprenticeRepositoryMock).findAll();
    assertEquals(null, apprenticeServiceDb.findAll());
  }

  @Test
  public void findApprenticeNameById_Should_ReturnApprenticeName_Test() throws Exception {
    for (Apprentice apprentice : apprentices) {
      assertEquals(apprentice.getFirstName() + " " + apprentice.getLastName(),
              apprenticeServiceDb.findApprenticeNameById(apprentice.getId()));
    }
  }

  @Test
  public void findApprenticeNameById_Should_ReturnNull_WhenApprenticeIdDoesNotExist_Test() throws Exception {
    String doesNotExistId = "9829482jhfskjhfadsf";
    doReturn(null).when(apprenticeRepositoryMock).findOne(doesNotExistId);
    assertEquals(null, apprenticeServiceDb.findApprenticeNameById(doesNotExistId));
  }

}