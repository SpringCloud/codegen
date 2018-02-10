package cn.springcloud.codegen.engine.entity;

import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/29 22:49
 */
public class ComponentMetadata {

    /**
     * 组件id
     */
    private String componentId;

    private String componentType;

    /**
     * 生成器数据
     */
    private List<GeneratorMetadata> generatorData;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public List<GeneratorMetadata> getGeneratorData() {
        return generatorData;
    }

    public void setGeneratorData(List<GeneratorMetadata> generatorData) {
        this.generatorData = generatorData;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }
}
