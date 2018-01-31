package cn.springcloud.codegen.engine.feign;

import cn.springcloud.codegen.engine.EurekaCodeGenConfigTest;
import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.ConfigParams;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.eureka_config.EurekaComponentGenerator;
import cn.springcloud.codegen.engine.generator.CodeGenExtendGenerator;
import cn.springcloud.codegen.engine.service.ExtendService;
import cn.springcloud.codegen.engine.tools.ClassTools;
import cn.springcloud.codegen.engine.tools.FileTools;
import cn.springcloud.codegen.engine.tools.JsonTools;
import cn.springcloud.codegen.engine.tools.ReadXmlFileTools;
import com.alibaba.fastjson.JSONArray;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 21:19
 */
public class FeignAssemblyGenerator {

//    public List<CodeGenExtendGenerator> execute(InputParams inputParam){
//        String fileDir =  FeignAssemblyGenerator.class.getResource("/").getPath() + "templates/xml_config/feign_component_xml.xml";
//
//        Map<String, Object> o = (Map<String, Object>) ReadXmlFileTools.readXmlFile(fileDir);
//        ConfigParams configParams = JsonTools.parseObjectByGenericity(JsonTools.objectToJson(o.get("component")).toString(), ConfigParams.class);
//        // 输出对象
//        InputParams inputParams = JsonTools.parseObjectByGenericity(configParams.getInitData(), InputParams.class);
//        // 模板参数
//        Map templateData = JsonTools.parseObjectByGenericity(configParams.getTemplateData(), Map.class);
//        Map otherData = JsonTools.parseObjectByGenericity(configParams.getOtherData(), Map.class);
//
//
//        // 这个值和CodeOutType 可以抽象到父类中。
//        String ss = FileTools.getTypeValue(inputParams.getTemplateConfigEncode());
//        System.out.println(ss);
//        Object constValue = CodeGenConstants.getConstValue(FileTools.getTypeValue(inputParams.getTemplateConfigEncode()));
//        inputParams.setTemplateConfigEncode(constValue.toString());
//
//        CodeOutType codeOutType = CodeOutType.getType(FileTools.getTypeValue(String.valueOf(otherData.get("isJavaOrResourcesOrOtherCode"))));
//        otherData.put("isJavaOrResourcesOrOtherCode", codeOutType);
//        System.out.println("=================== 分割线 ====================");
//
//        try {
//
//            String classPath = ClassTools.getAbsolutePathOfClassLoaderClassPath(EurekaComponentGenerator.class);
//            inputParams.setTemplateDir(classPath + File.separator + inputParams.getTemplateDir());
//            if (inputParam != null){
//                if (StringUtils.isNotBlank(inputParam.getDynamicOutPath())){
//                    inputParams.setDynamicOutPath(inputParam.getDynamicOutPath());
//                }
//                if (StringUtils.isNotBlank(inputParam.getModuleName())){
//                    inputParams.setModuleName(inputParam.getModuleName());
//                }
//            }
//            new FeignFileGenerator(inputParams, templateData, otherData).genrator();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//        List<CodeGenExtendGenerator> extendServices = new ArrayList<CodeGenExtendGenerator>();
//        extendServices.add(new FeignDataGenerator());
//        extendServices.add(new FeignPomDataGenerator());
//        return extendServices;
//    }
}
