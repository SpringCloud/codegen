package cn.springcloud.codegen.controller;

import cn.springcloud.codegen.entity.Metadata;
import cn.springcloud.codegen.entity.ProjectModel;
import cn.springcloud.codegen.service.ComponentExecutor;
import cn.springcloud.codegen.util.CodeGenTransport;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent.
 * @createdOn 2018/02/06 00:18
 */
@RestController
public class CodegenController {

    @Autowired
    private ComponentExecutor componentExecutor;

    @RequestMapping(value = "/download", method = RequestMethod.POST, consumes = "application/json",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadResponse(@RequestBody ProjectModel projectModel ) throws IOException, TemplateException, URISyntaxException {
        return new CodeGenTransport(componentExecutor).downloadResponse(projectModel);
    }

    @RequestMapping(value = "/download/bytes", method = RequestMethod.POST, consumes = "application/json")
    public byte[] downloadBytes(@RequestBody ProjectModel projectModel) throws IOException, TemplateException {
        return new CodeGenTransport(componentExecutor).downloadBytes(projectModel);
    }

}
