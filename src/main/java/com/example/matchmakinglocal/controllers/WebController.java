package com.example.matchmakinglocal.controllers;

import com.example.matchmakinglocal.services.EmailService;
import com.example.matchmakinglocal.services.SlackService;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    SlackService slackService;

    @Autowired
    EmailService emailService;

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
        model.addAttribute("principal", principal);
        return "admin";
    }

    @PostMapping("/mail/sendAll")
    public String sendEmail() {
        emailService.sendEmail();
        return "redirect:/admin";
    }

}
