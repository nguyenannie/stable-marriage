package com.example.matchmakinglocal.services;

import com.example.matchmakinglocal.models.Partner;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

  final PartnerService partnerService;


  private String apiKey = "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";

  @Autowired
  public EmailService(PartnerService partnerService) {
    this.partnerService = partnerService;
  }

  public void sendEmail() {

    System.out.println("checking before");

    Email from = new Email("tramtramrhinomo@gmail.com");
    String subject = "Annie is trying SendGrid :)";
    SendGrid sg = new SendGrid(apiKey);
    Request request = new Request();

    for(Partner partner : partnerService.findAll()) {
      System.out.println("email: " + partner.getEmail());
      Email to = new Email(partner.getEmail());
      Content content = new Content("text/plain", "http://match-making-greenfox.com/" + partner.getId());
      Mail mail = new Mail(from, subject, to, content);

      try {
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("check catch");
      }
    }
  }

}
