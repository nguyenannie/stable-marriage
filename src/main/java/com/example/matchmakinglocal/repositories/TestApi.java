package com.example.matchmakinglocal.repositories;

import com.example.matchmakinglocal.models.Apprentice;
import com.example.matchmakinglocal.models.ApprenticeDTO;
import com.example.matchmakinglocal.models.TestResposneDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import java.util.List;

public interface TestApi {

  @GET("api/apprentice/all")
  Call<List<Apprentice>> testInterface();

}
