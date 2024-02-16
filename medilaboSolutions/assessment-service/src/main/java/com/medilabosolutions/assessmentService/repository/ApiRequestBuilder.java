package com.medilabosolutions.assessmentService.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Component
public class ApiRequestBuilder {

    @Value("${gateway.url}")
    public String gatewayUrl;
    @Value("${user.username}")
    public String username;
    @Value("${user.password}")
    public String userPassword;


    public HttpRequest getRequest(String endpoint) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + endpoint))
                .header("Authorization", getAuthorizationValue())
                .build();
    }

    public HttpResponse<String> getStringHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getAuthorizationValue() {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + userPassword).getBytes());
    }
}
