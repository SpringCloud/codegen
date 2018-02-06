package cn.springcloud.codegen.service;

import cn.springcloud.codegen.engine.entity.InputParams;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Vincent.
 * @createdOn 2018/02/06 00:23
 */
public interface ComponentExecutor {

    void generate(InputParams inputParams) throws IOException, TemplateException;
}
