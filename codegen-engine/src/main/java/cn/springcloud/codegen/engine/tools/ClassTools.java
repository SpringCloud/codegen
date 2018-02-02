
package cn.springcloud.codegen.engine.tools;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 封装入参的属性为以属性名为key，值为value的map
     * @param inputParams
     * @param includeFieldTypes
     * @param excludeFieldTypes
     * @return
     */
    public static Map<String,Object> buildFieldValueToMap(Object inputParams, Class<?>[] includeFieldTypes, Class<?>[] excludeFieldTypes) {
        Map<String, Object> paramFieldMap = new HashMap<>();
        Field fields[] = getClassFields(inputParams.getClass());
        Field.setAccessible(fields,true);
        for (Field field : fields){
            try {
                if (includeFieldTypes == null && excludeFieldTypes == null){
                    paramFieldMap.put(field.getName(), field.get(inputParams));
                    continue;
                }
                // 如果选择指定类型不为空，则筛选指定的属性封装成map
                if (includeFieldTypes == null && !ArrayUtils.contains(excludeFieldTypes, field.getType())){
                    paramFieldMap.put(field.getName(), field.get(inputParams));
                } else if (excludeFieldTypes == null && ArrayUtils.contains(includeFieldTypes, field.getType())){
                    paramFieldMap.put(field.getName(), field.get(inputParams));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return paramFieldMap;
    }

    /**
     * 封装入参的除指定属性类型之外的属性为以属性名为key，值为value的map
     * @param inputParams
     * @param excludeFieldTypes
     * @return
     */
    public static Map<String,Object> buildFieldValueToMap(Object inputParams, Class<?>[] excludeFieldTypes) {
        return buildFieldValueToMap(inputParams, null, excludeFieldTypes);
    }

    /**
     * 获取class的全部属性（不包括父类Object中的属性）
     * @param classes
     * @return
     */
    public static Field[] getClassFields(Class<?> classes){
        Field fields[] = classes.getDeclaredFields();
        if (!Object.class.equals(classes) && !classes.getSuperclass().equals(Object.class)){
            fields = ArrayUtils.addAll(getClassFields(classes.getSuperclass()), fields);
        }
        return fields;
    }
}
