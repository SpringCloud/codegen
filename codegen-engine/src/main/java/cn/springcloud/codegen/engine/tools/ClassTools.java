
package cn.springcloud.codegen.engine.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/18
 * @time: 19:50
 * @description : 公共方法存放类
 */
public class ClassTools {

    private static Log log= LogFactory.getLog(ClassTools.class);

    /**
     * 加载Java类。 使用全限定类名
     *@paramclassName
     *@return
     */
    public static Class loadClass(String className) {
        try {
          return getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
          throw new RuntimeException("class not found '"+className+"'", e);
        }
     }
     /**
       *得到类加载器
       *@return
       */
     public static ClassLoader getClassLoader() {
        return ClassTools.class.getClassLoader();
     }

     /**
       * 得到本Class所在的ClassLoader的Classpat的绝对路径。
       * URL形式的
       * @return
       */
     public static String getAbsolutePathOfClassLoaderClassPath(){
         ClassTools.log.info(ClassTools.getClassLoader().getResource("").toString());
         return ClassTools.getClassLoader().getResource("").toString();

     }

    /**
     * 得到绝对路径的 : 例如 D://test/template/
     * @param clazz
     * @return
     */
     public static String getAbsolutePathOfClassLoaderClassPath(Class clazz){
         URL resource = clazz.getClassLoader().getResource("");
         return resource.getPath();
     }

    /**
     * 获取纯类名称：例如 Application.java 得到 Application
     * @param clazz
     * @return
     */
     public static String getClassSimpleName(Class clazz){
         return clazz.getSimpleName();
     }

    /**
     * 得到包名称 ： 例如 cn.springcloud.engine
     * @param clazz
     * @return
     */
     public static String getClassPackageName(Class clazz){
        return clazz.getPackage().getName();
     }

    /**
     * 得到类的全路径，是 包.类名称   cn.springcloud.Application
     * @param clazz
     * @return
     */
     public static String getClassFullPath(Class clazz){
         return clazz.getName();
     }
}
