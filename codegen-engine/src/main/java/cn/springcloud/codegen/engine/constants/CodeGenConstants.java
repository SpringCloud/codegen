package cn.springcloud.codegen.engine.constants;


import jdk.nashorn.internal.objects.Global;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 15:57
 * @description : 全局常量
 */
public class CodeGenConstants {

    /**
     * 默认编码的格式
     */
    public static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * 默认的模板名称
     */
    public static final String DEFAULT_TEMPLATE_POSTFIX = "ftl";

    /**
     * 文件分隔符
     */
    public static final String POINT_STR = ".";

    /**
     * 文件切个类型
     */
    public static final String FILE_SEPARATOR = "/";

    /**
     *  生成目录下的片接
     */
    public static final String MAIN_JAVA_PATH = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "java" + FILE_SEPARATOR;
    public static final String MAIN_RESOURCE_PATH = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;
    public static final String TEST_JAVA_PATH = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR;
    public static final String TEST_RESOURCES_PATH = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;

    /**
     * 获取静态的常量属性
     * @param field
     * @return
     */
    public static Object getConstValue(String field){
        try {
            // null 表示的是static的
            return CodeGenConstants.class.getField(field).get(null);
        } catch (Exception e) {

        }
        return null;
    }
}
