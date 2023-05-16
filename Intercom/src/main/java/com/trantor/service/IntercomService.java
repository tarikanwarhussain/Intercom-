package com.trantor.service;

import com.trantor.clients.ExternalHttpClient;
import com.trantor.constant.ConversationPayloads;
import com.trantor.constant.TagPayloads;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Service
public class IntercomService {

    @Autowired private RestTemplate restTemplate;
    @Autowired
    ExternalHttpClient httpClient;
    private Logger logger = LoggerFactory.getLogger(ExternalHttpClient.class);

    @Value("${intercom.auth.baseurl}")
    private String authBaseUrl;

    @Value("${intercom.auth.baseurl1}")
    private String authBaseUrl1;

    @Value("${intercom.auth.baseurl2}")
    private String authBaseUrl2;

    public String getStreamObjectPaylod1(
            HttpHeaders requestHeaders,Map<String,String> requestParams) {
        String responseDto = "";
        logger.info("inside service :  requestHeaders {} , requestParams {},", requestHeaders);
        String url = buildObjectUrl1(requestHeaders,requestParams);
        responseDto = getStreamData1(url, requestHeaders,requestParams);
        return responseDto;
    }

    public String getStreamObjectPaylodId(
            HttpHeaders requestHeaders,Map<String, String> requestParams, String streamId) {
        String response = "";
        logger.info("inside service :  requestHeaders {} , requestParams {} , streamId {},", requestHeaders,requestParams,  streamId);
        String url = buildObjectUrlId(streamId, requestHeaders, requestParams);
        response = getStreamDataId(url, requestHeaders, requestParams, streamId);
        return response;
    }

    /**
     * Build the object url from base url.
     *
//     * @param baseUrl1
     * @return
     */
    public String buildObjectUrl1(HttpHeaders requestHeaders,Map<String, String> requestParams) {

        String finalUrl1 = "";
        try {
            finalUrl1 = authBaseUrl2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl1;
    }

    /**
     * Build the object url from base url.
     *
//     * @param baseUrl1
//     * @param objectName1
     * @return
     */
    public String getStreamData1(
            String baseUrl,
            HttpHeaders requestHeaders,Map<String, String> requestParams) {
        ResponseEntity<String> responseDto =
                httpClient.sendGetRequest(
                        baseUrl, requestHeaders, MediaType.APPLICATION_JSON, String.class);
        logger.info("response DTO : {}", responseDto);
        return extractStreamObjectFromJsonResponseForV3Object1(
                responseDto, requestHeaders, requestParams);
    }

    public String buildObjectUrlId(
            String objectName,
            HttpHeaders requestHeaders,
            Map<String, String> requestParams) {
        String finalUrl1 = "";
        try {
            finalUrl1 = authBaseUrl+ objectName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl1;
    }

    public String getStreamDataId(
            String baseUrl,
            HttpHeaders requestHeaders,
            Map<String, String> requestParams,
            String streamId) {
        ResponseEntity<String> responseDto =
                httpClient.sendGetRequestId(
                        baseUrl, requestParams, requestHeaders, MediaType.APPLICATION_JSON, String.class);
        logger.info("response DTO : {}", responseDto );
        return extractStreamObjectFromJsonResponseForV3Object(
                responseDto, streamId, requestHeaders, requestParams);
    }
    private String extractStreamObjectFromJsonResponseForV3Object1(
            ResponseEntity<String> response,
            HttpHeaders requestHeaders,
            Map<String, String> requestParams) {

        JSONObject payloads = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject payloadData = new JSONObject(response.getBody());
        dataArray.put(payloadData);
        payloads.put("payload",dataArray);
        return payloads.toString();

    }

    private String extractStreamObjectFromJsonResponseForV3Object(
            ResponseEntity<String> response,
            String streamId,
            HttpHeaders requestHeaders,
            Map<String, String> requestParams) {

        JSONObject payloads = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject payloadData = new JSONObject(response.getBody());

        dataArray.put(payloadData);
        payloads.put("payload",dataArray);
        return payloads.toString();
    }


    public Object createTag(String AccessToken, String tags) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(tags, headers);
        Object body = restTemplate.exchange(authBaseUrl2, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println("Tag: " + body);
        return body;
    }

    public Object postTagObject(String accessToken, String accept, String contentType, TagPayloads tag) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setAccept(MediaType.parseMediaTypes(accept));
        System.out.println(tag.getPayloads().get(0));
        JSONObject jsonObject = new JSONObject(tag.getPayloads().get(0));
        String test = jsonObject.toString();
        System.out.println(jsonObject);

        HttpEntity entity = new HttpEntity(test, headers);
        Object body = restTemplate.exchange(authBaseUrl2, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println("Tag_Body: " + body);
        return body;

    }

    public Object postConversationObject(String accessToken, String accept, String contentType, ConversationPayloads conversation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setAccept(MediaType.parseMediaTypes(accept));
        System.out.println(conversation.getPayloads());
        JSONObject jsonObject = new JSONObject(conversation.getPayloads().get(0));
        String test = jsonObject.toString();
        System.out.println(jsonObject);

        HttpEntity entity = new HttpEntity(test, headers);
        Object body = restTemplate.exchange(authBaseUrl1, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println("Converstion_Body: " + body);
        return body;

    }

}
