package cn.springcloud.codegen.engine;

import cn.springcloud.codegen.engine.component.CodeGenComponent;
import cn.springcloud.codegen.engine.entity.ConfigParams;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.tools.ClassTools;
import cn.springcloud.codegen.engine.tools.JsonTools;
import cn.springcloud.codegen.engine.tools.TransforParamTools;
import com.alibaba.fastjson.JSONArray;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/24
 * @time: 14:04
 * @description : do some thing
 */
public class ConfigForInputParamsCodeGenConfigTest {

    public static void main(String[] args) {

        String fileDir =  ConfigForInputParamsCodeGenConfigTest.class.getResource("/").getPath() + "templates/xml_config/config_component_xml.xml";
        JSONArray jsonArray = TransforParamTools.transforXmlToJsonArray(fileDir);
        for (int i = 0; i < jsonArray.size(); i++){
            ConfigParams configParams = JsonTools.parseObjectByGenericity(jsonArray.getString(i), ConfigParams.class);
            // 输出对象
            InputParams inputParams = JsonTools.parseObjectByGenericity(configParams.getInitData(), InputParams.class);
            // 模板参数
            Map templateData = JsonTools.parseObjectByGenericity(configParams.getTemplateData(), Map.class);
            Map otherData = JsonTools.parseObjectByGenericity(configParams.getOtherData(), Map.class);

            try {

                String classPath = ClassTools.getAbsolutePathOfClassLoaderClassPath(ConfigForInputParamsCodeGenConfigTest.class);
                inputParams.setTemplateDir(classPath + File.separator + inputParams.getTemplateDir());
                new CodeGenComponent(inputParams, templateData, otherData).genrator();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }
}
