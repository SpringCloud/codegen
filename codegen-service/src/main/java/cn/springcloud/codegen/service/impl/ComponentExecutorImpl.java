package cn.springcloud.codegen.service.impl;

import cn.springcloud.codegen.config.GeneratorMetadataSelector;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent.
 * @createdOn 2018/02/05 23:06
 */
@Component
public class ComponentExecutorImpl implements ComponentExecutor {

    @Autowired
    private List<GeneratorMetadataSelector> selectors;

    public void generate(InputParams inputParams) throws IOException, TemplateException {
        List<GeneratorMetadata> allGeneratorMetadata = loadAllGeneratorMetaData(inputParams);
        CodeGenEngineGenerator codeGenEngineGenerator = CodeGenForFileGenerator.getInsatance();
        for (GeneratorMetadata metadata : allGeneratorMetadata){
            codeGenEngineGenerator.genrator(inputParams, metadata);
        }
    }

    private List<GeneratorMetadata> loadAllGeneratorMetaData(InputParams inputParams) {
        List<GeneratorMetadata> allGeneratorMetadata = new ArrayList<>();
        for (GeneratorMetadataSelector selector : selectors){
            List<GeneratorMetadata> generatorMetadata = selector.selectGeneratorMetadata(inputParams);
            if (generatorMetadata != null){
                allGeneratorMetadata.addAll(generatorMetadata);
            }
        }
        return allGeneratorMetadata;
    }
}
