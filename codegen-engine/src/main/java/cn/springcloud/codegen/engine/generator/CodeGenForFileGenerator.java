package cn.springcloud.codegen.engine.generator;

import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.entity.InputParamsContext;
import cn.springcloud.codegen.engine.exception.CodeGenException;
import cn.springcloud.codegen.engine.tools.*;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 18:14
 * @description : 文件的代码生成类：
 */
public class CodeGenForFileGenerator extends CodeGenEngineGenerator {

    /**
     * 多级组件生成
     * @param inputParams 外部输入值
     * @param generatorMetadata 生成器元信息
     * @param extendGenerators 多级组件数据
     * @throws CodeGenException
     * @throws IOException
     * @throws TemplateException
     */
    public void genrator(InputParams inputParams, GeneratorMetadata generatorMetadata, List<CodeGenExtendGenerator> extendGenerators) throws CodeGenException, IOException, TemplateException {
        super.genrator(inputParams, generatorMetadata, extendGenerators);
    }

    @Override
    public CodeOutType isJavaOrResourcesOrOtherCode(GeneratorDataContext dataContext) {
        CodeOutType codeOutType = CodeOutType.getType(FileTools.getTypeValue(String.valueOf(dataContext.getString("isJavaOrResourcesOrOtherCode"))));
        return codeOutType;
    }

    @Override
    public String getTemplateDir(GeneratorDataContext dataContext) {
        return ClassTools.getAbsolutePathOfClassLoaderClassPath(CodeGenForFileGenerator.class) + File.separator +dataContext.getString("templateDir");
    }

    @Override
    public Map<String, Object> getData(GeneratorDataContext dataContext) {
        return dataContext.getTemplateVariableData();
    }

}
