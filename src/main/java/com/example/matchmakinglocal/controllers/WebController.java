package com.example.matchmakinglocal.controllers;

import com.example.matchmakinglocal.models.SearchForm;
import com.example.matchmakinglocal.models.entities.Apprentice;
import com.example.matchmakinglocal.models.entities.Cohort;
import com.example.matchmakinglocal.models.entities.Partner;
import com.example.matchmakinglocal.services.*;
import com.github.seratch.jslack.api.methods.SlackApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class WebController {

  private final SlackService slackService;
  private final EmailService emailService;
  private final MatchmakingService matchmakingService;
  private final PartnerService partnerService;
  List<Apprentice> apprentices = null;
  List<Partner> partners = null;
  List<Cohort> cohorts = null;
  List result = null;
  String filter = "";
  private final SearchFormService searchFormService;

  @Autowired
  public WebController(SlackService slackService, EmailService emailService, MatchmakingService matchmakingService, PartnerService partnerService, SearchFormService searchFormService) {
    this.slackService = slackService;
    this.emailService = emailService;
    this.matchmakingService = matchmakingService;
    this.partnerService = partnerService;
    this.searchFormService = searchFormService;
  }

  @GetMapping("/")
  public String getHome(Principal principal, Model model) {
    model.addAttribute("principal", principal);
    return "index";
  }

  public static String getEmail(Principal principal) {
    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
    System.out.println("    >>>>     class name " + principal.getClass().getName());
    System.out.println("details class name " + oAuth2Authentication.getUserAuthentication().getDetails().getClass().getName());
    Map<String, String> details;
    try {
        details = (LinkedHashMap<String, String>)(oAuth2Authentication.getUserAuthentication().getDetails());
    } catch (Exception e) {
        details = new LinkedHashMap<>();
    }

    System.out.println("details email " + details.get("email"));
    return details.get("email");
  }

  @PostMapping("/slack/send")
  public String sendSlack() {
    try {
        slackService.postMessage();
    } catch (IOException | SlackApiException e) {
        e.printStackTrace();
    }
    return "redirect:/";
  }

  @PostMapping("/slack/lookUpUser")
  public String slackUser() throws IOException, SlackApiException {
    String userId = slackService.lookUpUserByEmail("pin.terminator@gmail.com");
    System.out.println(userId);
    return "redirect:/";
  }

  @GetMapping("/admin")
  public String getAdmin(Principal principal, Model model) {
    model.addAttribute("searchForm", new SearchForm("", ""));
    model.addAttribute("principal", principal);
    model.addAttribute("results", result);
    model.addAttribute("filter", filter);
    return "admin";
  }

  @PostMapping("/mail/sendAll")
  public String sendEmail() {
    emailService.sendEmail();
    return "redirect:/admin";
  }

  @GetMapping("/result")
  public String getResult(Model model) {
    model.addAttribute("apprentices", matchmakingService.returnMatchedApprenticesInOrder());
    model.addAttribute("partners", partnerService.findAll());
    model.addAttribute("stringmaps", matchmakingService.returnMatchesInStringMap());
    model.addAttribute("resultmaps", matchmakingService.returnMatchesInObjectMap());
    return "result";
  }

  @PostMapping("/radiobuttoncheck")
  public String radioButtonCheck(@ModelAttribute(value = "searchForm") SearchForm searchForm, Model model) {
    String radioButton = searchForm.getRadioButton();
    String checkbox = searchForm.getCheckbox();

    switch (radioButton) {
      case "apprentice":
        result = searchFormService.checkApprentice(checkbox);
        filter = "apprentice";
        break;
      case "partner":
        result = searchFormService.checkPartner(checkbox);
        filter = "partner";
        break;
      case "cohort":
        result = searchFormService.checkCohort(checkbox);
        filter = "cohort";
        break;
    }

    if (apprentices != null) {
      for (Object apprentice : apprentices) {
        System.out.println(apprentice.toString());
      }
    } else {
      System.out.println("apprentice list is null");
    }

    if (cohorts != null) {
      for (Object cohort : cohorts) {
        System.out.println(cohort.toString());
      }
    } else {
      System.out.println("cohort list is null");
    }

    if (partners != null) {
      for (Object partner : partners) {
        System.out.println(partner.toString());
      }
    } else {
      System.out.println("partner list is null");
    }

    model.addAttribute("results", result);
    model.addAttribute("filter", filter);
    return "redirect:/admin";
  }

}
