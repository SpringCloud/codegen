package cn.springcloud.codegen.controller;

import cn.springcloud.codegen.entity.Metadata;
import cn.springcloud.codegen.entity.ProjectModel;
import cn.springcloud.codegen.service.ComponentExecutor;
import cn.springcloud.codegen.util.CodeGenTransport;
import cn.springcloud.codegen.util.JsonMapper;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

//    @RequestMapping(value = "/download", method = RequestMethod.POST, consumes = "application/json",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<InputStreamResource> downloadResponse(@RequestBody ProjectModel projectModel ) throws IOException, TemplateException, URISyntaxException {
//        return new CodeGenTransport(componentExecutor).downloadResponse(projectModel, response);
//    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadResponse(@RequestParam String json, HttpServletResponse response ) throws IOException, TemplateException, URISyntaxException {
        ProjectModel projectModel = JsonMapper.nonDefaultMapper().fromJson(json, ProjectModel.class);
        new CodeGenTransport(componentExecutor).downloadResponse(projectModel, response);
    }

}
