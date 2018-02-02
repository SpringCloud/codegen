package cn.springcloud.codegen.engine.service;

import cn.springcloud.codegen.engine.tools.GeneratorDataContext;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 15:58
 * @description : 模板配置的接口
 */
public interface TemplateConfigService {

    String getTemplateDir(GeneratorDataContext dataContext);

    Map<String, Object> getData(GeneratorDataContext dataContext);
}
