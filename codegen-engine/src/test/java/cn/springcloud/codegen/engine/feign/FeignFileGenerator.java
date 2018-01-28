package cn.springcloud.codegen.engine.feign;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.engine.service.ExtendService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 18:55
 */
public class FeignFileGenerator extends CodeGenForFileGenerator {

    // 其他的数据
    Map<String, Object> otherData = null;

    public FeignFileGenerator(InputParams inputParams, Map<String, Object> templateData, Map<String, Object> otherData) {
        super(inputParams, templateData);
        this.otherData = otherData;
    }

    public FeignFileGenerator(InputParams inputParams, Map<String, Object> templateData, Map<String, Object> otherData, List<ExtendService> extendServices) {
        super(inputParams, templateData);
//        super.setExtendServices(extendServices);
        this.otherData = otherData;
    }

    /**
     * 模板里面需要的参数， 其他的不要在这里添加, 使用templateData 的参数构造方法， 该方法无效
     * @return
     */
    @Override
    public Map<String, Object> getTemplateData() {
        Map<String, Object> template = new HashMap<String, Object>();
        template.put(CodeGenConstants.CLASS_ANNOTATION_KEY,"//test");
        return template;
    }

    @Override
    public CodeOutType isJavaOrResourcesOrOtherCode() {
        return otherData.get("isJavaOrResourcesOrOtherCode") != null ? (CodeOutType) otherData.get("isJavaOrResourcesOrOtherCode") : CodeOutType.NONE;
    }
}
