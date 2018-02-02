package cn.springcloud.codegen.engine;

import cn.springcloud.codegen.engine.entity.ComponentMetadata;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.engine.tools.ComponentXmlFileTools;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 20:57
 */
public class EurekaCodeGenTest {

    private static final CodeGenForFileGenerator generator = new CodeGenForFileGenerator();

    public static void main(String[] args) throws IOException, TemplateException {
        Map<String, List<GeneratorMetadata>> componentGeneratorData = loadComponentGeneratorData();

        InputParams inputParams = new InputParams();
        inputParams.setDynamicOutPath("E:\\");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("basePackage", "com.springcloud.component");
        paramMap.put("pomArtifactId","eureka-server");
        paramMap.put("pomName","eureka-server");
        paramMap.put("springBootVersion","1.5.9");
        paramMap.put("javaVersion","1.8");
        paramMap.put("moduleName","eureka-server-demo");

        /**
         * 模拟前端选择组件 demo
         */
        paramMap.put("selectComponentId", "eureka");
        inputParams.setParamMap(paramMap);

        for(GeneratorMetadata generatorMetadata : componentGeneratorData.get(paramMap.get("selectComponentId"))){
            generator.genrator(inputParams, generatorMetadata);
        }
    }

    /**
     * 模拟主程序启动加载全部xml过程
     * @return
     */
    private static Map<String,List<GeneratorMetadata>> loadComponentGeneratorData() {
        Map<String, List<GeneratorMetadata>> componentGeneratorData = new HashMap<>();
        ComponentMetadata metadata = ComponentXmlFileTools.readXmlFile(EurekaCodeGenTest.class.getResource("/").getPath() + "templates/component/erurekaComponent.xml");
        componentGeneratorData.put(metadata.getComponentId(), metadata.getGeneratorData());
        return  componentGeneratorData;
    }


}
