package cn.springcloud.codegen.service.impl;

import cn.springcloud.codegen.support.ComponentLoader;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenEngineGenerator;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.service.ComponentExecutor;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Vincent.
 * @createdOn 2018/02/05 23:06
 */
@Component
public class ComponentExecutorImpl implements ComponentExecutor {

    @Autowired
    private ComponentLoader componentLoader;

    public void generate(InputParams inputParams) throws IOException, TemplateException {
        List<GeneratorMetadata> generatorMetadata = componentLoader.getComponentGeneratorMap().get(inputParams.getParamMap().get("sc-alone-radio"));
        if (generatorMetadata == null || generatorMetadata.size() == 0){
            throw new IllegalArgumentException("not found [" + inputParams.getParamMap().get("sc-alone-radio") + "] component generator");
        }
        for (GeneratorMetadata metadata : generatorMetadata){
            CodeGenEngineGenerator codeGenEngineGenerator = CodeGenForFileGenerator.getInsatance();
            codeGenEngineGenerator.genrator(inputParams, metadata);
        }
    }
}
