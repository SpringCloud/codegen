package cn.springcloud.codegen.engine;

import cn.springcloud.codegen.engine.entity.ComponentMetadata;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.engine.tools.ComponentXmlFileTools;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 20:57
 */
public class FeignCodeGenTest {

    public static void main(String[] args) throws IOException, TemplateException {

        InputParams inputParams = new InputParams();
        inputParams.setDynamicOutPath("E:\\");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("basePackage", "com.springcloud.component");
        paramMap.put("pomArtifactId","eureka-server");
        paramMap.put("pomName","eureka-server");
        paramMap.put("springBootVersion","1.5.9");
        paramMap.put("javaVersion","1.8");
        paramMap.put("moduleName","eureka-server");
        inputParams.setParamMap(paramMap);
        ComponentMetadata metadata = ComponentXmlFileTools.readXmlFile(FeignCodeGenTest.class.getResource("/").getPath() + "templates/new_config/erurekaComponent.xml");
        for(GeneratorMetadata generatorMetadata : metadata.getGeneratorData()){
            CodeGenForFileGenerator generator = new CodeGenForFileGenerator(inputParams, generatorMetadata);
            generator.genrator();
        }
    }


}
