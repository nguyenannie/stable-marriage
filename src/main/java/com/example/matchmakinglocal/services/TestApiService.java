package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.repositories.TestApi;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestApiService {

  TestApi testApi;

  public TestApiService() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://matchmaking-fake-backend.herokuapp.com/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    testApi = retrofit.create(TestApi.class);
  }

  public List<Apprentice> test() {

    List<Apprentice> apprentices = new ArrayList<>();

    try {
      apprentices =  testApi.testInterface().execute().body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return apprentices;

  }

}

