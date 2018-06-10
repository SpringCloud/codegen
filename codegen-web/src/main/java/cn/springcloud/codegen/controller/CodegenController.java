package cn.springcloud.codegen.controller;

import cn.springcloud.codegen.entity.ProjectModel;
import cn.springcloud.codegen.service.ComponentExecutor;
import cn.springcloud.codegen.util.CodeGenTransport;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Vincent.
 * @createdOn 2018/02/06 00:18
 */
@RestController
public class CodegenController {

    @Autowired
    private ComponentExecutor componentExecutor;

    @RequestMapping(value = "/download", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Resource> downloadResponse(@RequestBody ProjectModel projectModel) throws IOException, TemplateException {
        return new CodeGenTransport(componentExecutor).downloadResponse(projectModel);
    }

}
