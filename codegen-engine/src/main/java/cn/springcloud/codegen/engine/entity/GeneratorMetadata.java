package cn.springcloud.codegen.engine.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/29 23:08
 */
public class GeneratorMetadata {

    private Map<String, String> templateData = new HashMap<String, String>();

    private Map<String, String> fileData = new HashMap<String, String>();

    private Map<String, Object> variableData = new HashMap<String, Object>();

    public Map<String, String> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(Map<String, String> templateData) {
        this.templateData = templateData;
    }

    public Map<String, String> getFileData() {
        return fileData;
    }

    public void setFileData(Map<String, String> fileData) {
        this.fileData = fileData;
    }

    public Map<String, Object> getVariableData() {
        return variableData;
    }

    public void setVariableData(Map<String, Object> variableData) {
        this.variableData = variableData;
    }
}
