package cn.springcloud.codegen.service.impl;

import cn.springcloud.codegen.exception.CodeGenException;
import cn.springcloud.codegen.support.ComponentLoader;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenEngineGenerator;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.service.ComponentExecutor;
import cn.springcloud.codegen.utils.CodeGenUtil;
import cn.springcloud.codegen.utils.FileUtil;
import cn.springcloud.codegen.utils.ZipUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
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

    @Override
    public byte[] generate(InputParams inputParams){
        String generatePath = CodeGenUtil.getCodegenTempGeneratePath();
        try {
            inputParams.setDynamicOutPath(generatePath);
            List<GeneratorMetadata> generatorMetadata = componentLoader.getComponentGeneratorMap().get(inputParams.getParamMap().get("sc-alone-radio"));
            if (generatorMetadata == null || generatorMetadata.size() == 0) {
                throw new IllegalArgumentException("not found [" + inputParams.getParamMap().get("sc-alone-radio") + "] component generator");
            }
            CodeGenEngineGenerator codeGenEngineGenerator = CodeGenForFileGenerator.getInsatance();
            for (GeneratorMetadata metadata : generatorMetadata) {
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
