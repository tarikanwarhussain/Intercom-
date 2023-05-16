package com.trantor.controller;

import com.trantor.constant.ConversationPayloads;
import com.trantor.constant.ApplicationConstant;
import com.trantor.constant.TagPayloads;
import com.trantor.service.IntercomService;
import com.trantor.service.ResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/intercom")
public class IntercomController {

    @Autowired
    IntercomService intercomService;

    @GetMapping
    public ResponseEntity<String> getStreamDataByQuery1(
            @RequestHeader(value = ApplicationConstant.AccessToken,
                    required = true) String AccessToken,
            @RequestHeader("Accept") String accept) {

        Map<String, String> requestParams = new HashMap<>();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(AccessToken);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        System.out.println(accept);

        return ResponseInterceptor.prepareResponseEntity(
                intercomService.getStreamObjectPaylod1(requestHeaders,requestParams));

    }

    @GetMapping("/stream/{streamId}")
    public ResponseEntity<String> getStreamDataByQuery(
            @RequestHeader(value = ApplicationConstant.AccessToken,
                    required = true) String AccessToken,
            @RequestHeader(value = ApplicationConstant.PAGINATION_META_DATA,
                    required = false) String paginationMetaData,
            @PathVariable String streamId) {

//        HashMap<String, String> requestHeaders = new HashMap<>();
        Map<String, String> requestParams = new HashMap<>();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(AccessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

//        requestHeaders.put(ApplicationConstant.AccessToken, AccessToken);

        return ResponseInterceptor.prepareResponseEntity(
                intercomService.getStreamObjectPaylodId(requestHeaders, requestParams, streamId));

    }

    @PostMapping("/saveTag")
    public Object postTagData(
            @RequestHeader(value = ApplicationConstant.AccessToken,
                    required = true) String authorization,
            @RequestHeader(value = ApplicationConstant.ACCEPT,
                    required = true) String accept,
            @RequestHeader(value = ApplicationConstant.CONTENT_TYPE, required = true) String contentType,
            @RequestBody TagPayloads tag) {
        return ResponseInterceptor.prepareResponseEntity(
                intercomService.postTagObject(authorization, accept,contentType, tag));
    }


    @PostMapping("/saveConversation")
    public Object postConversationData(
            @RequestHeader(value = ApplicationConstant.AccessToken,
                    required = true) String authorization,
            @RequestHeader(value = ApplicationConstant.ACCEPT,
                    required = true) String accept,
            @RequestHeader(value = ApplicationConstant.CONTENT_TYPE, required = true) String contentType,
            @RequestBody ConversationPayloads conversation) {
        return ResponseInterceptor.prepareResponseEntity(
                intercomService.postConversationObject(authorization, accept,contentType, conversation));
    }
}
