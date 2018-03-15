package com.example.matchmakinglocal;

import com.example.matchmakinglocal.services.ApprenticeService;
import com.example.matchmakinglocal.services.PartnerService;
import com.example.matchmakinglocal.services.PreferenceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MatchmakinglocalApplication {

	public static void main(String[] args) {
    SpringApplication.run(MatchmakinglocalApplication.class, args);
  }
//	  int[][] apprenticeChoice = {{},
//	                              {0,5,7,1,2,6,8,4,3},
//                                {0,2,3,7,5,4,1,8,6},
//                                {0,8,5,1,4,6,2,3,7},
//                                {0,3,2,7,4,1,6,8,5},
//                                {0,7,2,5,1,3,6,8,4},
//                                {0,1,6,7,5,8,4,2,3},
//                                {0,2,5,7,6,3,4,8,1},
//                                {0,3,8,4,5,7,2,6,1},
//                                {0,1,6,7,4,2,5,8,3},
//                                {0,7,4,5,8,2,1,3,6}};
//
//	  int[][] partnerChoices = {{},
//	                            {0,5,3,7,6,9,10,1,2,8,4},
//                              {0,8,6,3,5,7,2,1,10,9,4},
//                              {0,1,5,6,2,10,4,9,8,7,3},
//                              {0,8,7,3,9,2,4,1,5,6,10},
//                              {0,6,4,7,3,8,1,10,9,2,5},
//                              {0,2,8,5,4,7,3,9,7,1,10},
//                              {0,7,5,10,9,2,1,8,6,4,3},
//                              {0,7,4,1,5,2,3,9,10,6,8}};
    //result 6 7 2 8 1 4 5 3

//    int[][] apprenticeChoice = {{},
//                                {0,2,3,0},
//                                {0,3,1,0},
//                                {0,1,1,0},
//                                {0,2,1,3},
//                                {0,1,2,3}};
//
//    int[][] partnerChoices = {{},
//                              {0,4,2,3,5,1},
//                              {0,1,4,2,0,0},
//                              {0,2,5,3,1,0}};


    //result 4, 1, 2
//
//    int[][] apprenticeChoice = {{},
//            {0,5,4,1,4,2},
//            {0,1,3,4,2,5},
//            {0,5,2,1,2,3},
//            {0,3,4,2,1,5},
//            {0,2,5,4,1,3},
//            {0,1,3,4,5,2},
//            {0,3,5,2,4,1},
//            {0,5,1,3,5,2}};
//
//    int[][] partnerChoices = {{},
//            {0,7,5,8,1,2,3,4,6},
//            {0,3,1,8,6,2,4,5,7},
//            {0,2,4,3,7,1,5,6,8},
//            {0,5,6,3,8,7,4,2,1},
//            {0,3,5,1,7,8,4,2,6}};

//      int[][] apprenticeChoice = {{},
//            {0,2,1,3},
//            {0,1,3,2},
//            {0,1,2,3},
//            {0,3,1,2},
//            {0,1,2,3}};
//
//    int[][] partnerChoices = {{},
//            {0,2,5,1,3,4},
//            {0,3,1,5,2,4},
//            {0,3,2,1,5,4}};

//    MatchMakingAlgorithm m = new MatchMakingAlgorithm(5,3, apprenticeChoice, partnerChoices);
//
//    m.execute();
//
//    for (int i = 0; i < m.match.length; i++) {
//      System.out.println(m.match[i]);
//    }
//
//    System.out.println("----------------------");
//
//		SpringApplication.run(MatchmakinglocalApplication.class, args);

  @Bean
  public CommandLineRunner demo(PartnerService partnerService, ApprenticeService apprenticeService, PreferenceService preferenceService) {
      return (String... args) -> {

      };
  }
}

//            Apprentice apprentice = new Apprentice();
//            apprentice.setFirstName("Rubble");
//            apprentice.setLastName("Cat");
//            apprentice.setCohort("Corsac");
//            apprentice.setCohortClass("Please");
//            apprentice.setEmail("rub@gmail.com");
//
//            Apprentice apprentice2 = new Apprentice();
//            apprentice2.setFirstName("Anh");
//            apprentice2.setLastName("Nguyen");
//            apprentice2.setCohort("Corsac");
//            apprentice2.setCohortClass("Please");
//            apprentice2.setEmail("anhnguyen@gmail.com");
//
//            Partner partner = new Partner();
//            partner.setCompanyName("ABC Corp.");
//            partner.setEmail("abc@gmail.com");
//
//            Partner partner1 = new Partner();
//            partner1.setCompanyName("XYZ Corp.");
//            partner1.setEmail("xyz@gmail.com");
//
//            Partner partner2 = new Partner();
//            partner2.setCompanyName("balll Corp.");
//            partner2.setEmail("blah@gmail.com");
//
//            Partner partner3 = new Partner();
//            partner3.setCompanyName("nah Corp.");
//            partner3.setEmail("nah@gmail.com");
//
//            Preference preference1 = new Preference();
//            preference1.setRanking(5);
//            preference1.setUser(apprentice);
//            partnerService.save(partner);
//            preference1.setSelectionId(partner.getId());
//            apprenticeService.save(apprentice);
//            preferenceService.save(preference1);
//
//
//            Preference preference2 = new Preference();
//            preference2.setRanking(10);
//            preference2.setUser(apprentice);
//            partnerService.save(partner2);
//            preference2.setSelectionId(partner2.getId());
//            apprenticeService.save(apprentice);
//            preferenceService.save(preference2);


//            Apprentice apprentice = new Apprentice();
//            apprentice.setFirstName("Rubble");
//            apprentice.setLastName("Cat");
//            apprentice.setCohort("Corsac");
//            apprentice.setCohortClass("Please");
//            apprentice.setEmail("rub@gmail.com");
//
//            Apprentice apprentice2 = new Apprentice();
//            apprentice2.setFirstName("Anh");
//            apprentice2.setLastName("Nguyen");
//            apprentice2.setCohort("Corsac");
//            apprentice2.setCohortClass("Please");
//            apprentice2.setEmail("anhnguyen@gmail.com");
//
//            Partner partner = new Partner();
//            partner.setCompanyName("ABC Corp.");
//            partner.setEmail("abc@gmail.com");
//
//            Partner partner1 = new Partner();
//            partner1.setCompanyName("XYZ Corp.");
//            partner1.setEmail("xyz@gmail.com");
//
//            Partner partner2 = new Partner();
//            partner2.setCompanyName("balll Corp.");
//            partner2.setEmail("blah@gmail.com");
//
//            Partner partner3 = new Partner();
//            partner3.setCompanyName("nah Corp.");
//            partner3.setEmail("nah@gmail.com");
//
//            Preference preference1 = new Preference();
//            preference1.setRanking(5);
//            preference1.setUser(apprentice);
//            partnerService.save(partner);
//            preference1.setSelectionId(partner.getId());
//            apprenticeService.save(apprentice);
//            preferenceService.save(preference1);
//
//
//            Preference preference2 = new Preference();
//            preference2.setRanking(10);
//            preference2.setUser(apprentice);
//            partnerService.save(partner2);
//            preference2.setSelectionId(partner2.getId());
//            apprenticeService.save(apprentice);
//            preferenceService.save(preference2);

