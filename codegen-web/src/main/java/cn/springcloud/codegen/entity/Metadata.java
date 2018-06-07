package cn.springcloud.codegen.entity;

import cn.springcloud.codegen.engine.entity.MapFieldModel;

import java.util.List;

public class Metadata implements MapFieldModel {

    private String appType;

    private List<String> chooseComponent;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public List<String> getChooseComponent() {
        return chooseComponent;
    }

    public void setChooseComponent(List<String> chooseComponent) {
        this.chooseComponent = chooseComponent;
    }

}
