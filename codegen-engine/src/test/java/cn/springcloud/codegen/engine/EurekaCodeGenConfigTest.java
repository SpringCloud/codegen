package cn.springcloud.codegen.engine;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.ConfigParams;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.eureka_config.EurekaComponentGenerator;
import cn.springcloud.codegen.engine.tools.ClassTools;
import cn.springcloud.codegen.engine.tools.FileTools;
import cn.springcloud.codegen.engine.tools.JsonTools;
import cn.springcloud.codegen.engine.tools.ReadXmlFileTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/24
 * @time: 14:04
 * @description : do some thing
 */
public class EurekaCodeGenConfigTest {

    public static void main(String[] args) {

        String fileDir =  EurekaCodeGenTest.class.getResource("/").getPath() + "templates/xml_config/eureka_component_xml.xml";
        Object o = ReadXmlFileTools.readXmlFile(fileDir);
        JSONArray jsonArray = (JSONArray) JsonTools.objectToJson(o);
        for (int i = 0; i < jsonArray.size(); i++){
            ConfigParams configParams = JsonTools.parseObjectByGenericity(jsonArray.getString(i), ConfigParams.class);
            // 输出对象
            InputParams inputParams = JsonTools.parseObjectByGenericity(configParams.getInitData(), InputParams.class);
            // 模板参数
            Map templateData = JsonTools.parseObjectByGenericity(configParams.getTemplateData(), Map.class);
            Map otherData = JsonTools.parseObjectByGenericity(configParams.getOtherData(), Map.class);


            // 这个值和CodeOutType 可以抽象到父类中。
            String ss = FileTools.getTypeValue(inputParams.getTemplateConfigEncode());
            System.out.println(ss);
            Object constValue = CodeGenConstants.getConstValue(FileTools.getTypeValue(inputParams.getTemplateConfigEncode()));
            inputParams.setTemplateConfigEncode(constValue.toString());

            CodeOutType codeOutType = CodeOutType.getType(FileTools.getTypeValue(String.valueOf(otherData.get("isJavaOrResourcesOrOtherCode"))));
            otherData.put("isJavaOrResourcesOrOtherCode", codeOutType);
            System.out.println("=================== 分割线 ====================");

            try {

                String classPath = ClassTools.getAbsolutePathOfClassLoaderClassPath(EurekaComponentGenerator.class);
                inputParams.setTemplateDir(classPath + File.separator + inputParams.getTemplateDir());
                new EurekaComponentGenerator(inputParams, templateData, otherData).genrator();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }
}
