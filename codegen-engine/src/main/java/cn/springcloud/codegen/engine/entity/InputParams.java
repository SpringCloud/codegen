package cn.springcloud.codegen.engine.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 19:27
 * @description : 子类的输出参数, 模板和输出路径等自定义在任何地方
 */
public class InputParams extends BaseInputParams implements Serializable{

    private static final long serialVersionUID = -1377887339408890399L;
    private String templateConfigEncode;
    private String templateDir;


    public InputParams(String templateConfigEncode, String dynamicOutPath,
                       String templateDir, String packageName,String templateName,
                       String fileName, String fileType, String moduleName) {

        // 父类的构造方法
        super(templateName, fileName, fileType, dynamicOutPath, moduleName, packageName);
        this.templateConfigEncode = templateConfigEncode;
        this.templateDir = templateDir;
    }

    public InputParams(){}

    public String getTemplateConfigEncode() {
        return templateConfigEncode;
    }

    public void setTemplateConfigEncode(String templateConfigEncode) {
        this.templateConfigEncode = templateConfigEncode;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

}
