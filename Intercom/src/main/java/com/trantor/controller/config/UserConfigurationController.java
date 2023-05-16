package com.trantor.controller.config;

import com.trantor.service.ResponseInterceptor;
import com.trantor.service.config.UserConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class UserConfigurationController {

  @Autowired
  UserConfigurationService userConfigurationService;

  @GetMapping("/getConfiguration")
  public ResponseEntity<String> getAuthUrl() {
    return ResponseInterceptor
        .prepareResponseEntity(userConfigurationService.getUserConfigurations());
  }
}
