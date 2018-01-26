package cn.springcloud.codegen.engine.generator;

import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.entity.InputParamsContext;
import cn.springcloud.codegen.engine.tools.ObjectCopyValueToVoTools;
import cn.springcloud.codegen.engine.tools.TransforParamTools;

import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 18:14
 * @description : 文件的代码生成类：
 *    支持三种构造方法 :
 *    1. 输入所有自定的数据，模板路径， 生成文件名， 模板名称
 *    2. 模板所在的路径和类在的路径一致， 只需要模板模板名等参数
 *    3. 传递一个公共配置文件所在位置， 解析配置问题件。
 */
public abstract class CodeGenForFileGenerator extends CodeGenEngineGenerator {

    private InputParams inputParams;
    private Map<String, Object> tempateData;
    private Map<String, Object> otherData;

    /**
     * 输出全部参数模型
     * @param inputParams
     */
    public CodeGenForFileGenerator(InputParams inputParams){
        this.inputParams = inputParams;
    }

    /**
     * 模板参数又外部输入
     * @param inputParams
     * @param templateData
     */
    public CodeGenForFileGenerator(InputParams inputParams, Map<String, Object> templateData){
        this.inputParams = inputParams;
        this.tempateData = templateData;
    }

    /**
     * 模板参数的其他辅助参数由外部输入
     * @param inputParams
     * @param templateData
     * @param otherData
     */
    public CodeGenForFileGenerator(InputParams inputParams, Map<String, Object> templateData,
                                   Map<String, Object> otherData){
        this.inputParams = inputParams;
        this.tempateData = templateData;
        this.otherData = otherData;
    }

    /**
     *  默认的缩减路径为类所在的路径
     *  生成的的名字默认为类名
     *  模板名称默认为类名称+ftl
     * @param context
     */
    public CodeGenForFileGenerator(InputParamsContext context){
        this.inputParams = TransforParamTools.transforParam(context);
    }

    /**
     * 模板参数由外部输入
     * @param context
     * @param templateData
     */
    public CodeGenForFileGenerator(InputParamsContext context, Map<String, Object> templateData){
        this.inputParams = TransforParamTools.transforParam(context);
        this.tempateData = templateData;
    }

    /**
     * 模板参数和其他参数由外部输入
     * @param context
     * @param templateData
     * @param otherData
     */
    public CodeGenForFileGenerator(InputParamsContext context, Map<String, Object> templateData,
                                   Map<String, Object> otherData){
        this.inputParams = TransforParamTools.transforParam(context);
        this.tempateData = templateData;
        this.otherData = otherData;
    }

    /**
     * 通过配置文件的方式读取模型， 并且将properties 转化成bean的形式
     */
    public CodeGenForFileGenerator(Properties properties){
        this.inputParams = ObjectCopyValueToVoTools.bean2Object(new InputParams(), properties);
    }

    @Override
    public String getTemplateConfigEncode() {
        return inputParams.getTemplateConfigEncode();
    }

    @Override
    public String getDynamicOutPath() {
        return inputParams.getDynamicOutPath();
    }

    @Override
    public String getTemplateDir() {
        return inputParams.getTemplateDir();
    }

    @Override
    public String getTemplateName() {
        return inputParams.getTemplateName();
    }

    @Override
    public Map<String, Object> getData() {
        return this.tempateData == null ? getTemplateData() : this.tempateData;
    }

    @Override
    public String getFileName(){
        return inputParams.getFileName();
    }

    @Override
    public String getFileType(){
        return inputParams.getFileType();
    }

    @Override
    public String getPackageName(){
        return inputParams.getPackageName();
    }

    @Override
    public String getModuleName(){
        return inputParams.getModuleName();
    }

    /**
     * 模板要渲染的数据源
     * @return
     */
    public abstract Map<String, Object> getTemplateData();
}
