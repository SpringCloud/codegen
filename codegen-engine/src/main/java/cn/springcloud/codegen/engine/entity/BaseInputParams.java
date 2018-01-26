package cn.springcloud.codegen.engine.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/18
 * @time: 15:38
 * @description : 输入参数的基础类型(这里的输出参数不是指模板文件的参数)
 */
public class BaseInputParams implements Serializable{

    private static final long serialVersionUID = 2822361127561108856L;
    private String templateName;
    private String fileName;
    private String fileType;
    private String dynamicOutPath;
    private String moduleName;
    private String packageName;

    public BaseInputParams(String templateName, String fileName, String fileType, String dynamicOutPath, String moduleName, String packageName) {
        this.templateName = templateName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.dynamicOutPath = dynamicOutPath;
        this.moduleName = moduleName;
        this.packageName = packageName;
    }

    public BaseInputParams() {}

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDynamicOutPath() {
        return dynamicOutPath;
    }

    public void setDynamicOutPath(String dynamicOutPath) {
        this.dynamicOutPath = dynamicOutPath;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
