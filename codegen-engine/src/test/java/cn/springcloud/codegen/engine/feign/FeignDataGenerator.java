package cn.springcloud.codegen.engine.feign;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.DependenciesGav;
import cn.springcloud.codegen.engine.generator.CodeGenExtendGenerator;
import cn.springcloud.codegen.engine.service.ExtendService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 16:00
 */
public class FeignDataGenerator extends CodeGenExtendGenerator {

    @Override
    public Map<String, Object> getExtendData() {
        Map<String, Object> extendData = new HashMap<String, Object>();

        List<String> annotation = new ArrayList<String>();
        annotation.add("@EnableFeignClients(basePackages = {\"cn.springcloud.codegen.component.feign\"})");
        extendData.put(CodeGenConstants.CLASS_ANNOTATION_KEY, annotation);

        List<String> importData = new ArrayList<String>();
        importData.add("org.springframework.cloud.netflix.feign.EnableFeignClients;");
        extendData.put(CodeGenConstants.CLASS_IMPORT_KEY, importData);
        return extendData;
    }

    @Override
    public String targetIdentifier() {
        return "mainClass";
    }

}
