package com.trantor.service;

import com.trantor.enums.SchemaObjectEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class SchemaService {

    private Logger logger = LoggerFactory.getLogger(SchemaService.class);

    @Value("classpath:/input/intercomSchemaFieldTags.json")
    Resource intercomSchemaFieldTags;

    @Value("classpath:/input/intercomSchemaFieldConversations.json")
    Resource intercomSchemaFieldConversations;

    @Value("classpath:/input/intercomSchemaFieldContacts.json")
    Resource intercomSchemaFieldContacts;

    public String getSchemaObjectsMetaData(String streamId) {
        if (SchemaObjectEnum.TAGS.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(intercomSchemaFieldTags);
        } else if (SchemaObjectEnum.CONVERSATIONS.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(intercomSchemaFieldConversations);
        } else if (SchemaObjectEnum.CONTACT.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(intercomSchemaFieldContacts);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
    public String getJsonResponseFromFile(Resource inputResource) {
        String schemaFieldDetails = "";
        try (Reader reader = new InputStreamReader(inputResource.getInputStream())) {
            schemaFieldDetails = FileCopyUtils.copyToString(reader);
            logger.debug(" Input Resource of filename {} :: {}", inputResource.getFilename(),
                    schemaFieldDetails);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error while reading " + inputResource.getFilename() + " from file.", e);
        }
        return schemaFieldDetails;
    }
}
