package cn.springcloud.codegen.engine.tools;

import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/23
 * @time: 22:25
 * @description : json 工具类，解析json 文件和将对象转化成json 对象
 */
public class JsonTools {

    /**
     * 将对象转化成json 数据
     * @param obj
     * @return
     */
    public static Object objectToJson(Object obj){
        return JSON.toJSON(obj);
    }

    /**
     * 将json 字符串信息转化成对象信息, 通过泛型的形式转化
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObjectByGenericity(String jsonStr, Class<T> clazz){
        T t = JSON.parseObject(jsonStr, clazz);
        return t;
    }

    /**
     * json 文件的位置
     * @param path
     * @return
     */
    public static Object readJsonFile(String path){
        return readJsonFile(new File(path));
    }

    /**
     * 读取json 配置文件
     * @param file
     * @return
     */
    public static Object readJsonFile(File file){

        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempString = "";
            while((tempString = reader.readLine()) != null){
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
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

        return JSON.parse(sb.toString());
    }
}
