package cn.springcloud.codegen.engine.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/18
 * @time: 11:54
 * @description : 主要是使用于模板文件和操作类所在的路径一致的情况下使用
 */
public class InputParamsContext extends BaseInputParams implements Serializable{

    private static final long serialVersionUID = 8790588232535807382L;
    private String prefixTemplatePath;
    private String reducedTemplatePath;
    private Class<?> currentClasses;

    public InputParamsContext(String templateName,String fileName, String fileType, String dynamicOutPath, String moduleName,
                              String prefixTemplatePath, String reducedTemplatePath, Class<?> currentClasses, String packageName) {
        super(templateName, fileName, fileType, dynamicOutPath, moduleName, packageName);
        this.prefixTemplatePath = prefixTemplatePath;
        this.reducedTemplatePath = reducedTemplatePath;
        this.currentClasses = currentClasses;
    }

    /**
     *  这个需要set 方法设置 dynamicOutPath, moduleName，fileType 参数
     *  templateName, fileName， templateDir 使用当前类的共同属性
     *
     * @param prefixTemplatePath    模板的前缀
     * @param reducedTemplatePath   缩减路径
     * @param currentClasses         当前类的class
     */
    public InputParamsContext(String prefixTemplatePath, String reducedTemplatePath, Class<?> currentClasses) {
        this.prefixTemplatePath = prefixTemplatePath;
        this.reducedTemplatePath = reducedTemplatePath;
        this.currentClasses = currentClasses;
    }

    /**
     * 默认的缩减路径为类所在的路径
     * 生成的的名字默认为类名
     * 模板名称默认为类名称+ftl
     *
     * @param prefixTemplatePath
     * @param currentClasses
     */
    public InputParamsContext(String prefixTemplatePath, Class<?> currentClasses) {
        this.prefixTemplatePath = prefixTemplatePath;
        this.currentClasses = currentClasses;
    }

    public InputParamsContext() {}

    public String getPrefixTemplatePath() {
        return prefixTemplatePath;
    }

    public void setPrefixTemplatePath(String prefixTemplatePath) {
        this.prefixTemplatePath = prefixTemplatePath;
    }

    public String getReducedTemplatePath() {
        return reducedTemplatePath;
    }

    public void setReducedTemplatePath(String reducedTemplatePath) {
        this.reducedTemplatePath = reducedTemplatePath;
    }

    public Class<?> getCurrentClasses() {
        return currentClasses;
    }

    public void setCurrentClasses(Class<?> currentClasses) {
        this.currentClasses = currentClasses;
    }
}
