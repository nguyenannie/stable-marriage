package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.entities.Apprentice;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface TestApi {

  @GET("api/apprentice/all")
  Call<List<Apprentice>> testInterface();

}
