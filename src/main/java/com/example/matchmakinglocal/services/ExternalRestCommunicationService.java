package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.entities.Program;
import com.example.matchmakinglocal.models.entities.Status;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ExternalRestCommunicationService {

  //  @Headers({
//          //access token shall be entered here
//  })
  @GET("api/apprentice")
  Call<List<Object>> ListAllApprentices(@Query("firstName") String firstName,
                                        @Query("lastName") String lastName,
                                        @Query("cohort") String cohort,
                                        @Query("cohortClass") String cohortClass,
                                        @Query("email") String email,
                                        @Query("status") Status status,
                                        @Query("program") Program program);

  //  @Headers({
//          //access token shall be entered here
//  })
  @GET("api/partner")
  Call<List<Object>> ListAllPartners(@Query("companyName") String companyName,
                                     @Query("email") String email,
                                     @Query("status") Status status,
                                     @Query("partnerType") Program program);

  //  @Headers({
//          //access token shall be entered here
//  })
  @GET("api/admin")
  Call<List<Object>> ListAllAdmin(@Query("firstName") String firstName,
                                  @Query("lastName") String lastName,
                                  @Query("email") String email,
                                  @Query("status") Status status);

  //  @Headers({
//          //access token shall be entered here
//  })
  @GET("api/cohort")
  Call<List<Object>> ListAllCohort(@Query("cohortName") String cohortName,
                                   @Query("status") Status status);

}
