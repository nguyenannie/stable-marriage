package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Program;
import com.example.matchmakinglocal.models.entities.Status;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetrofitService {

  private List<Object> userList = new ArrayList<>();

  Retrofit retrofit = new Retrofit.Builder()
          //TODO when secured create an environmental variable
          .baseUrl("https://matchmaking-fake-backend.herokuapp.com/")
          .addConverterFactory(JacksonConverterFactory.create())
          .build();

  ExternalRestCommunicationService comms = retrofit.create(ExternalRestCommunicationService.class);

  public List callApprenticeList(String firstName, String lastName, String cohort, String cohortClass,
                                 String email, Status status, Program program) {

    Call<List<Object>> call = comms.ListAllApprentices(
            firstName, lastName, cohort, cohortClass, email, status, program);

    try {
      userList = call.execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userList;
  }

  public List callPartnerList(String companyName, String email, Status status, Program program) {

    Call<List<Object>> call = comms.ListAllPartners(
            companyName, email, status, program);

    try {
      userList = call.execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userList;
  }

  public List callAdminList(String firstName, String lastName, String email, Status status) {

    Call<List<Object>> call = comms.ListAllAdmin(firstName, lastName, email, status);

    try {
      userList = call.execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userList;
  }

  public List callAllCohort(String cohortName, Status status) {

    Call<List<Object>> call = comms.ListAllCohort(cohortName, status);

    try {
      userList = call.execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userList;
  }

}
