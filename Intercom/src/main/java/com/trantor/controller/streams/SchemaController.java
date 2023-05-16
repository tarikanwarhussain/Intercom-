package com.trantor.controller.streams;

import com.trantor.service.ResponseInterceptor;
import com.trantor.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchemaController {

    @Autowired SchemaService schemaService;

    @GetMapping("/stream/{streamId}/schema")
    public ResponseEntity<String> getObjectFields(@PathVariable String streamId) {
        return getobjectsMetadata(streamId);
    }

    private ResponseEntity<String> getobjectsMetadata(String streamId) {
        return ResponseInterceptor
                .prepareResponseEntity(schemaService.getSchemaObjectsMetaData(streamId));
    }
}
