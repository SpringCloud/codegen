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
    private String dynamicOutPath;

    public BaseInputParams(String dynamicOutPath) {
        this.dynamicOutPath = dynamicOutPath;
    }

    public BaseInputParams() {}


    public String getDynamicOutPath() {
        return dynamicOutPath;
    }

    public void setDynamicOutPath(String dynamicOutPath) {
        this.dynamicOutPath = dynamicOutPath;
    }

}
