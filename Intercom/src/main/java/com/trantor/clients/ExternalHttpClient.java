package com.trantor.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExternalHttpClient {

  public static final String API_RESPONSE_CODE = "API response code: {}";
  public static final String FINAL_URL = "Final url:  {}";

  private Logger logger = LoggerFactory.getLogger(ExternalHttpClient.class);

  @Autowired RestTemplate restTemplate;

  /**
   * Method to send the HTTP GET request.
   *
   * @param <T>
   * @param url
   * @param mediaType
   * @return
   */
  public <T> ResponseEntity<T> sendGetRequest(
      String url,
      HttpHeaders requestHeaders,
      MediaType mediaType,
      Class<T> responseDto) {

    // Add query params if any from original request
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
//    for (Map.Entry<String, String> entry : requestParams.entrySet()) {
//      builder.queryParam(entry.getKey(), entry.getValue());
//    }
    logger.info(FINAL_URL, builder.build(false).toUriString());
    HttpEntity entity = new HttpEntity(" ", requestHeaders);

    ResponseEntity<T> response =
        restTemplate.exchange(
            builder.build(false).toUriString(), HttpMethod.GET, entity, responseDto);
    logger.info(API_RESPONSE_CODE, response.getStatusCode().value());

    return response;
  }

  public <T> ResponseEntity<T> sendGetRequestId(
          String url,
          Map<String, String> requestParams,
         HttpHeaders requestHeaders,
          MediaType mediaType,
          Class<T> responseDto){

    // Add query params if any from original request
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    for (Map.Entry<String, String> entry : requestParams.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getValue());
    }
    logger.info(FINAL_URL, builder.build(false).toUriString());
//
//    HttpHeaders restHeaders = prepareHeaders1(requestHeaders, mediaType);
//    restHeaders.add("X-PrettyPrint", "1");
//
//    MultiValueMap<String, String> mv2Map = new LinkedMultiValueMap<>();
//
//    HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(mv2Map, restHeaders);
    HttpEntity entity = new HttpEntity(" ", requestHeaders);
    ResponseEntity<T> response =
            restTemplate.exchange(
                    builder.build(false).toUriString(), HttpMethod.GET, entity, responseDto);
    logger.info(API_RESPONSE_CODE, response.getStatusCode().value());

    return response;
  }

  public <T> ResponseEntity<T> sendPostRequest(
          String url,
          Map<String, String> requestBody,
          HashMap<String, String> requestHeaders,
          MediaType mediaType,
          Class<T> responseDto) {

    logger.info(FINAL_URL, url);

    HttpHeaders restHeaders = prepareHeaders2(requestHeaders, mediaType);

    MultiValueMap<String, String> requestBodyMap = new LinkedMultiValueMap<>();
    for (Map.Entry<String, String> entry : requestBody.entrySet()) {
      requestBodyMap.add(entry.getKey(), entry.getValue());
    }

    HttpEntity<MultiValueMap<String, String>> restRequest =
            new HttpEntity<>(requestBodyMap, restHeaders);

    HttpEntity<MultiValueMap<String, String>> mapHttpEntity = new HttpEntity<>(requestBodyMap, restHeaders);

    ResponseEntity<T> response =
            restTemplate.exchange(url, HttpMethod.POST, mapHttpEntity, responseDto);



    return response;
  }

  /**
   * Prepare the headers from input map.
   *
   * @param requestHeaders
   * @param mediaType
   * @return
   */

  private HttpHeaders prepareHeaders1(HashMap<String,String> requestHeaders, MediaType mediaType) {
    HttpHeaders restHeaders = new HttpHeaders();
    restHeaders.setContentType(mediaType);

    // Add headers if any from original request
    for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
      restHeaders.add(entry.getKey(), entry.getValue());
    }
    return restHeaders;
  }

  private HttpHeaders prepareHeaders2(HashMap<String,String> requestHeaders, MediaType mediaType) {
    HttpHeaders restHeaders = new HttpHeaders();
    restHeaders.setContentType(mediaType);

    // Add headers if any from original request
    for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
      restHeaders.add(entry.getKey(), entry.getValue());
    }
    return restHeaders;
  }
}
