package com.example.matchmakinglocal;

import com.example.matchmakinglocal.repositories.ApprenticeRepository;
import com.example.matchmakinglocal.services.ApprenticeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBTest {

  @Mock
  ApprenticeRepository apprenticeRepository;

  @InjectMocks
  ApprenticeService apprenticeService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

//  @Test
//  public void apprenticeServiceTest


}
