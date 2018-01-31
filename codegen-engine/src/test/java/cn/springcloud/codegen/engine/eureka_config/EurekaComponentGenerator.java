package cn.springcloud.codegen.engine.eureka_config;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenExtendGenerator;
import cn.springcloud.codegen.engine.generator.CodeGenForFileGenerator;
import cn.springcloud.codegen.engine.service.ExtendService;
import cn.springcloud.codegen.engine.tools.MapTools;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/21
 * @time: 0:29
 * @description : eureka java 类的生成
 */
public class EurekaComponentGenerator{

    // 其他的数据
    Map<String, Object> otherData = null;

//    public EurekaComponentGenerator(InputParams inputParams, Map<String, Object> templateData, Map<String, Object> otherData) {
//        super(inputParams, templateData);
//        this.otherData = otherData;
//    }
//
//    public EurekaComponentGenerator(InputParams inputParams, Map<String, Object> templateData, Map<String, Object> otherData, List<CodeGenExtendGenerator> extendGenerators) {
//        super(inputParams, templateData, extendGenerators);
//        this.otherData = otherData;
//    }

    /**
     * 模板里面需要的参数， 其他的不要在这里添加, 使用templateData 的参数构造方法， 该方法无效
     * @return
     */
//    @Override
//    public Map<String, Object> getTemplateData() {
//        Map<String, Object> template = new HashMap<String, Object>();
//        return template;
//    }

//    @Override
//    public CodeOutType isJavaOrResourcesOrOtherCode() {
//        return otherData.get("isJavaOrResourcesOrOtherCode") != null ? (CodeOutType) otherData.get("isJavaOrResourcesOrOtherCode") : CodeOutType.NONE;
//    }
}
