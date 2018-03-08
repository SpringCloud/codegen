package cn.springcloud.codegen.service;

import cn.springcloud.codegen.engine.entity.InputParams;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/02/06 00:23
 */
public interface ComponentExecutor {

    byte[] generate(InputParams inputParams);
}
