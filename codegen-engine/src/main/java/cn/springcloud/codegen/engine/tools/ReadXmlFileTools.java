package cn.springcloud.codegen.engine.tools;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/23
 * @time: 22:08
 * @description : 读取解析XML文件工具类
 */
public class ReadXmlFileTools {

    /**
     *   默认是一list的方式执行， 设置为false是map 的形式
      */
    private final static boolean DEFAULT_EXCUTE_FLAG = false;

    public static Object parse(Element root) {
        List<?> elements = root.elements();
        if (elements.size() == 0) {
            // 没有子元素
            return root.getTextTrim();
        } else {
            // 有子元素
            String prev = null;
            boolean guess = DEFAULT_EXCUTE_FLAG; // 默认按照数组处理

            Iterator<?> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element elem = (Element) iterator.next();
                String name = elem.getName();
                if (prev == null) {
                    prev = name;
                } else {
                    guess = name.equals(prev);
                    break;
                }
            }
            iterator = elements.iterator();
            if (guess) {
                List<Object> data = new ArrayList<Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    ((List<Object>) data).add(parse(elem));
                }
                return data;
            } else {
                Map<String, Object> data = new HashMap<String, Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    ((Map<String, Object>) data).put(elem.getName(), parse(elem));
                }
                return data;
            }
        }
    }

    /**
     * 返回读取到的对象信息
     * @param filePath
     * @return
     */
    public static Object readXmlFile(String filePath){

        InputStream in = null;
        try {

            SAXReader reader = new SAXReader();
            File file = new File(filePath);
            in = new FileInputStream(file);
            Document document = reader.read(in);
            Element root = document.getRootElement();

            return parse(root);
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
