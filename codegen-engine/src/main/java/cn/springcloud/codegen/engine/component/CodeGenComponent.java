package cn.springcloud.codegen.engine.component;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.entity.InputParamsContext;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.engine.tools.FileTools;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/30
 * @time: 16:48
 * @description : 组件的统一创建对象
 */
public final class CodeGenComponent extends CodeGenForFileGenerator {

    private Map<String, Object> otherData;

    public CodeGenComponent(InputParamsContext context, Map<String, Object> templateData, Map<String, Object> otherData) {
        super(context, templateData, otherData);
        this.otherData = otherData;
    }

    public CodeGenComponent(InputParams inputParams, Map<String, Object> templateData, Map<String, Object> otherData) {
        super(inputParams, templateData, otherData);
        this.otherData = otherData;
        transforParam(inputParams);
    }

    /**
     * 如果在templateData 中的参数为空的时候才会使用这个重写的参数
     * @return
     */
    @Override
    public Map<String, Object> getTemplateData() {
        return null;
    }

    @Override
    public CodeOutType isJavaOrResourcesOrOtherCode() {
        CodeOutType codeOutType = CodeOutType.getType(FileTools.getTypeValue(String.valueOf(otherData.get("isJavaOrResourcesOrOtherCode"))));
        return codeOutType != null ? codeOutType : CodeOutType.NONE;
    }

    /**
     * 转化参数信息
     * @param inputParams
     * @return
     */
    private void transforParam(InputParams inputParams){

        Object constValue = CodeGenConstants.getConstValue(FileTools.getTypeValue(inputParams.getTemplateConfigEncode()));
        inputParams.setTemplateConfigEncode(constValue.toString());
    }
}
