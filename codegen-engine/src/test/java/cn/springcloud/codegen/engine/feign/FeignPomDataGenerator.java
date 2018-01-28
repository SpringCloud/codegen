package cn.springcloud.codegen.engine.feign;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.DependenciesGav;
import cn.springcloud.codegen.engine.generator.CodeGenExtendGenerator;
import cn.springcloud.codegen.engine.service.ExtendService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 21:14
 */
public class FeignPomDataGenerator extends CodeGenExtendGenerator  {

    @Override
    public Map<String, Object> getExtendData() {
        Map<String, Object> extendData = new HashMap<String, Object>();
        DependenciesGav gavDependencies = new DependenciesGav();
        gavDependencies.setArtifactId("spring-cloud-starter-feign");
        gavDependencies.setGroupId("org.springframework.cloud");
        extendData.put(CodeGenConstants.POM_DEPENDENCY_KEY, gavDependencies);
        return extendData;
    }

    @Override
    public String targetIdentifier() {
        return "pom";
    }

}
