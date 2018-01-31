package cn.springcloud.codegen.engine.generator;

import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.entity.InputParamsContext;
import cn.springcloud.codegen.engine.tools.*;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 18:14
 * @description : 文件的代码生成类：
 *    支持三种构造方法 :
 *    1. 输入所有自定的数据，模板路径， 生成文件名， 模板名称
 *    2. 模板所在的路径和类在的路径一致， 只需要模板模板名等参数
 *    3. 传递一个公共配置文件所在位置， 解析配置问题件。
 */
public class CodeGenForFileGenerator extends CodeGenEngineGenerator {

    private InputParams inputParams;

    private GeneratorDataContext generatorData;

    public CodeGenForFileGenerator(InputParams inputParams, GeneratorMetadata generatorMetadata){
        this.inputParams = inputParams;
        this.generatorData = new GeneratorDataContext(inputParams, generatorMetadata);
    }

    @Override
    public String getTemplateConfigEncode() {
        return generatorData.getString("templateConfigEncode");
    }

    @Override
    public CodeOutType isJavaOrResourcesOrOtherCode() {
        CodeOutType codeOutType = CodeOutType.getType(FileTools.getTypeValue(String.valueOf(generatorData.getString("isJavaOrResourcesOrOtherCode"))));
        return codeOutType;
    }

    @Override
    public String getDynamicOutPath() {
        return inputParams.getDynamicOutPath();
    }

    @Override
    public String getTemplateDir() {
        return ClassTools.getAbsolutePathOfClassLoaderClassPath(CodeGenForFileGenerator.class) + File.separator +generatorData.getString("templateDir");
    }

    @Override
    public String getTemplateName() {
        return generatorData.getString("templateName");
    }

    @Override
    public Map<String, Object> getData() {
        return generatorData.getTemplateVariableData();
    }

    @Override
    public String getFileName(){
        return generatorData.getString("fileName");
    }

    @Override
    public String getFileType(){
        return generatorData.getString("fileType");
    }

    @Override
    public String getPackageName(){
        return generatorData.getString("packageName", false);
    }

    @Override
    public String getModuleName(){
        return generatorData.getString("moduleName");
    }


}
