package cn.springcloud.codegen.engine.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Object> paramMap = new HashMap<>();

    public InputParams(String dynamicOutPath, Map<String, Object> paramMap) {

        // 父类的构造方法
        super(dynamicOutPath);
        this.paramMap = paramMap;
    }

    public InputParams(){}


    public String getString(String key){
        if (StringUtils.isBlank(String.valueOf(paramMap.get(key)))
                || StringUtils.equals(String.valueOf(paramMap.get(key)), "null")) {
            throw new IllegalArgumentException("get value by key [" + key +"] is null");
        }
        return String.valueOf(paramMap.get(key));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
