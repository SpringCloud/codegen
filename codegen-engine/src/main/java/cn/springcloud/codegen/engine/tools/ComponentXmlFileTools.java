package cn.springcloud.codegen.engine.tools;

import cn.springcloud.codegen.engine.entity.ComponentMetadata;
import cn.springcloud.codegen.engine.entity.ConfigParams;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import com.alibaba.fastjson.JSONArray;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

/**
 * @author Vincent.
 * @createdOn 2018/01/29 22:45
 * xml解析工具类
 */
public class ComponentXmlFileTools {


    /**
     *   默认是一list的方式执行， 设置为false是map 的形式
     */
    private final static boolean DEFAULT_EXCUTE_FLAG = false;

    public static ComponentMetadata parseToMetadata(Element root) {
        ComponentMetadata componentMetadata = new ComponentMetadata();
        Attribute attribute = root.attribute("id");
        componentMetadata.setComponentId(attribute == null ?  "" : attribute.getValue());
        List<GeneratorMetadata> generatorMetadataList = new ArrayList<GeneratorMetadata>();
        List<?> elements = root.elements();
        if (elements.size() != 0) {
            // 有子元素
            Iterator<?> iterator = elements.iterator();
            List<Object> list = new ArrayList<Object>();
            while (iterator.hasNext()) {
                Element elem = (Element) iterator.next();
                list.add(parseElement(elem));
            }
            JSONArray jsonArray = (JSONArray) JsonTools.objectToJson(list);
            for (int i = 0; i < jsonArray.size(); i++){
                GeneratorMetadata generatorMetadata = JsonTools.parseObjectByGenericity(jsonArray.getString(i), GeneratorMetadata.class);
                generatorMetadataList.add(generatorMetadata);
            }
        }
        componentMetadata.setGeneratorData(generatorMetadataList);
        return componentMetadata;
    }

    public static Object parseElement(Element element) {
        List<?> elements = element.elements();

        if (elements.size() == 0) {
            Attribute value = element.attribute("value");
            return value == null ? element.getTextTrim() : value.getValue();
        } else {
            Map<String, Object> data = new HashMap<String, Object>();
            Iterator<?> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element elem = (Element) iterator.next();
                Attribute key = elem.attribute("key");
                data.put(key == null ? elem.getName() : key.getValue(), parseElement(elem));
            }
            return data;
        }
    }

    /**
     * 返回读取到的对象信息
     * @param filePath
     * @return
     */
    public static ComponentMetadata readXmlFile(String filePath){

        InputStream in = null;
        try {

            SAXReader reader = new SAXReader();
            File file = new File(filePath);
            in = new FileInputStream(file);
            Document document = reader.read(in);
            Element root = document.getRootElement();

            return parseToMetadata(root);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {

            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  null;
    }
}
