package cn.springcloud.codegen.controller;

import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.service.ComponentExecutor;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/demo", method = RequestMethod.POST, consumes = "application/json")
    public void demoForGenerator(@RequestBody InputParams inputParams) throws IOException, TemplateException {
        componentExecutor.generate(inputParams);
    }


}
