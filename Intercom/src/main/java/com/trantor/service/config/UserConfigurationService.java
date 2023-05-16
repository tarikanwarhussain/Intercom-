package com.trantor.service.config;

import com.trantor.utils.ResourceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationService {

    @Value("classpath:/input/intercomUserConfig.json")
    Resource userConfigResource;

    public String getUserConfigurations() {
        return ResourceUtils.getResourceData(userConfigResource);
    }
}
