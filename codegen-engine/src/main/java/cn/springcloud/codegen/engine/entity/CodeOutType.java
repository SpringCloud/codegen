package cn.springcloud.codegen.engine.entity;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/21
 * @time: 18:11
 * @description :
 *          代码的类型  :
 *          1. java : src/main/java
 *          2. resources : src/main/resources
 *          3. none : 不添加任何目录
 *          4. test_java : src/test/java
 *          5. test_resources : src/test/resources
 */
public enum CodeOutType {

    JAVA(CodeGenConstants.MAIN_JAVA_PATH, "java文件夹下的目录"),
    RESOURCES(CodeGenConstants.MAIN_RESOURCE_PATH, "resources文件夹下的目录"),
    NONE("", "其他文件类型的生成， 不考虑添加额外的目录"),
    TEST_JAVA(CodeGenConstants.TEST_JAVA_PATH, "test文件夹下的 java 目录"),
    TEST_RESOURCES(CodeGenConstants.TEST_RESOURCES_PATH, "test文件夹下的 resources目录");

    private final String key;
    private final String value;

    private CodeOutType(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /**
     * 通过value 反查 key
     * @param value
     * @return
     */
    public static String getLabelByValue(String value){
        CodeOutType[] types = CodeOutType.values();
        for (int i = 0; i < types.length; i++) {
            if(types[i].value.equals(value)){
                return types[i].getKey();
            }
        }
        return "";
    }

    /**
     * 获取枚举的属性
     * @param type
     * @return
     */
    public static CodeOutType getType(String type){
        CodeOutType[] types = CodeOutType.values();
        for (int i = 0; i < types.length; i++) {
            if(types[i].toString().equals(type)){
                return types[i];
            }
        }
        System.out.println("CodeOutType中，未找到对应的枚举属性");
        return  null;
    }

}
