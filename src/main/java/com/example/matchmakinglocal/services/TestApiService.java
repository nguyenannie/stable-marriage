package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Apprentice;
import com.example.matchmakinglocal.models.ApprenticeDTO;
import com.example.matchmakinglocal.models.ListApprenticeDTO;
import com.example.matchmakinglocal.models.TestResposneDTO;
import com.example.matchmakinglocal.repositories.TestApi;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
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

//    .enqueue(new Callback<List<ApprenticeDTO>>() {
//      @Override
//      public void onResponse(Call<List<ApprenticeDTO>> call, Response<List<ApprenticeDTO>> response) {
//        apprenticeDTOS.add(new ApprenticeDTO("annie but success"));
//        t = 2;
//        System.out.println("annie but success");
////        for(ApprenticeDTO apprentice: response.body()) {
////          apprenticeDTOS.add(apprentice);
////          System.out.println(apprentice.toString());
////        }
//      }
//
//      @Override
//      public void onFailure(Call<List<ApprenticeDTO>> call, Throwable t) {
//        apprenticeDTOS.add(new ApprenticeDTO("kris but failed"));
//        System.out.println("kris but failed");
////        System.out.println(t.getMessage());
//      }
//    });
//    System.out.println("T " + t);
//
//    return apprenticeDTOS;

