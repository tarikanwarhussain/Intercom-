package com.trantor.utils;

import com.trantor.exception.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ResourceUtils {

    private static Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    /**
     * Read the resource file and return its contents.
     *
     * @return
     */
    public static String getResourceData(Resource resource) {
        String resourceJson = "";
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            resourceJson = FileCopyUtils.copyToString(reader);
            logger.debug("Resource contents are :: {}", resourceJson);
        } catch (IOException e) {
            throw new ResourceException("Error reading resource file :: " + resource.getFilename(), e);
        }
        return resourceJson;
    }

    private ResourceUtils() {}
}
