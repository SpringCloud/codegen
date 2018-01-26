package cn.springcloud.codegen.engine.config;

import cn.springcloud.codegen.engine.exception.CodeGenException;
import cn.springcloud.codegen.engine.exception.CodeGenFileException;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/18
 * @time: 16:04
 * @description : 配置文件的解析, 多种文件解析都写在这里
 * 例如： properties ， xml, json 等文件格式的解析要支持
 */
public class FileConfig {

    /**
     * 加载properties 资源文件
     * @param in 输入流
     * @return
     */
    public static Properties getProperties(InputStream in) {
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new CodeGenException("couldn't load properties file '" + in +"'", e);
        }
        return properties;
    }

    /**
     * 配置文件路径
     * @param configPath
     * @return
     */
    public static Properties getProperties(String configPath){

        try {
            File file = new File(configPath);
            InputStream in = new FileInputStream(file);
            return getProperties(in);
        } catch (FileNotFoundException e) {
            throw new CodeGenException("couldn't find config file in " + configPath + " " + e);
        }
    }

    /**
     *@paramurl
     *@return
     *@throwsIOException
     */
    public static InputStream getStream(URL url) throws IOException{
        if(url!=null){
            return url.openStream();
        }else{
            return null;
        }
    }
}
