package cn.springcloud.codegen.engine.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/24
 * @time: 15:20
 * @description : 配置文件的参数参数转化, 全部转字符串， 可以通过fastJson 转化在转化成对应的构造bean即可
 */
public class ConfigParams implements Serializable {

    private static final long serialVersionUID = 6380397981444700402L;

    /**
     * 初始化需要的参数
     */
    private String initData;

    /**
     * 模板需要的参数
     */
    private String templateData;

    /**
     * 其他的补充参数
     */
    private String otherData;

    public String getInitData() {
        return initData;
    }

    public void setInitData(String initData) {
        this.initData = initData;
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "ConfigParams{" +
                "initData='" + initData + '\'' +
                ", templateData='" + templateData + '\'' +
                ", otherData='" + otherData + '\'' +
                '}';
    }
}
