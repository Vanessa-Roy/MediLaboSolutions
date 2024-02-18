package com.medilabosolutions.assessmentService.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The type Api request builder.
 */
@Component
public class ApiRequestBuilder {

    /**
     * The Gateway url.
     */
    @Value("${gateway.url}")
    private String gatewayUrl;

    /**
     * Gets request.
     *
     * @param endpoint the endpoint
     * @return the request
     * @throws URISyntaxException the uri syntax exception
     */
    public HttpRequest getRequest(String endpoint) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + endpoint))
                .build();
    }

    /**
     * Gets string http response.
     *
     * @param request the request
     * @return the string http response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public HttpResponse<String> getStringHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
