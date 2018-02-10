package cn.springcloud.codegen.service.impl;

import cn.springcloud.codegen.config.GeneratorMetadataSelector;
import cn.springcloud.codegen.exception.CodeGenException;
import cn.springcloud.codegen.support.ComponentLoader;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenEngineGenerator;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.exception.CodeGenException;
import cn.springcloud.codegen.service.ComponentExecutor;
import cn.springcloud.codegen.utils.CodeGenUtil;
import cn.springcloud.codegen.utils.FileUtil;
import cn.springcloud.codegen.utils.ZipUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

/**
 * @author Vincent.
 * @createdOn 2018/02/05 23:06
 */
@Component
public class ComponentExecutorImpl implements ComponentExecutor {

    @Autowired
    private List<GeneratorMetadataSelector> selectors;

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

    @Override
    public byte[] generate(InputParams inputParams){
        String generatePath = CodeGenUtil.getCodegenTempGeneratePath();
        try {
            inputParams.setDynamicOutPath(generatePath);
            List<GeneratorMetadata> allGeneratorMetadata = loadAllGeneratorMetaData(inputParams);
            CodeGenEngineGenerator codeGenEngineGenerator = CodeGenForFileGenerator.getInsatance();
            for (GeneratorMetadata metadata : allGeneratorMetadata){
                codeGenEngineGenerator.genrator(inputParams, metadata);
            }
            String zipFilePath = ZipUtil.zip(generatePath, null);
            File zipFile = new File(zipFilePath);
            return FileUtil.getBytes(zipFile);
        } catch (Exception e) {
            throw new CodeGenException(e.getMessage(), e);
        } finally {
            File directory = new File(generatePath);
            FileUtil.forceDeleteDirectory(directory, 5);
        }
    }

}
