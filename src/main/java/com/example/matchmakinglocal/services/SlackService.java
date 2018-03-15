package com.example.matchmakinglocal.services;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsListResponse;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackService {

  public void sendLink(String userName) throws IOException {
    String url = "";

    Payload payload = Payload.builder()
            .channel("@" + userName)
            .iconEmoji(":smile_cat:")
            .text("Hello World!")
            .build();

    Slack slack = Slack.getInstance();
    slack.send(url, payload);
  }

  public WebhookResponse sendSlack(String username) throws IOException {
    String url = "";

    Payload payload = Payload.builder()
            .channel("@" + username)
            .iconEmoji(":smile_cat:")
            .text("Hello World!")
            .build();

    Slack slack = Slack.getInstance();
    return slack.send(url, payload);
  }

  public void postMessage() throws IOException, SlackApiException {
    String token = "xoxp-319729411664-321435972727-321233213894-3331ee70d2a8b2a1a789e7c653879c61";
    Slack slack = Slack.getInstance();

    ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
            ChatPostMessageRequest.builder()
                    .token(token)
                    .channel("U9DMGCFTJ")
                    .text("Hello Krisz")
                    .build());

    System.out.println(postResponse);

  }

  public String lookUpUserByEmail(String email) throws IOException, SlackApiException {
    String token = "xoxp-319729411664-321435972727-321233213894-3331ee70d2a8b2a1a789e7c653879c61";
    Slack slack = Slack.getInstance();

    UsersLookupByEmailResponse emailResponse = slack.methods().usersLookupByEmail(
            UsersLookupByEmailRequest.builder()
                    .token(token)
                    .email(email)
                    .build()
    );

    System.out.println(emailResponse);

    return emailResponse.getUser().getId();
  }

}
