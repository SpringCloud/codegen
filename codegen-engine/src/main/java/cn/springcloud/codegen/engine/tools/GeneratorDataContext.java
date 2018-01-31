package cn.springcloud.codegen.engine.tools;

import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenExtendGenerator;
import cn.springcloud.codegen.engine.tools.PlaceholderParser.PlaceholderResolver;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/30 00:30
 */
public class GeneratorDataContext {

    private InputParams inputParams;

    private GeneratorMetadata generatorMetadata;

    private List<CodeGenExtendGenerator> extendGenerators;

    private PlaceholderParser placeholderParser;

    private ComponentXmlResolver resovler;

    private Map<String, Object> variableCacheData;

    /**
     * 数据处理之后保存，省去重复处理的过程
     */
    private Map<String, String> cacheData = new HashMap<>();

    public GeneratorDataContext(InputParams inputParams, GeneratorMetadata generatorMetadata){
        this(inputParams, generatorMetadata, null);

    }

    public GeneratorDataContext(InputParams inputParams, GeneratorMetadata generatorMetadata, List<CodeGenExtendGenerator> extendGenerators){
        this.inputParams = inputParams;
        this.generatorMetadata = generatorMetadata;
        this.extendGenerators = extendGenerators;
        placeholderParser = new PlaceholderParser("${","}", ":");
        resovler = new ComponentXmlResolver(inputParams, generatorMetadata);
    }

    public GeneratorDataContext(GeneratorMetadata generatorMetadata){
        this(null, generatorMetadata);
    }

    public String getString(String key){
        return getString(key, true);
    }

    public String getString(String key, boolean isRequired){
        if (StringUtils.isNotBlank(cacheData.get(key))){
            return cacheData.get(key);
        }
        String value;
        if (StringUtils.isBlank(generatorMetadata.getFileData().get(key))){
            if (StringUtils.isBlank(generatorMetadata.getTemplateData().get(key))){
                if (StringUtils.isBlank(inputParams.getParamMap().get(key))){
                    if (isRequired){
                        throw new IllegalArgumentException("get value by key [" + key +"] is null");
                    }else {
                        value = "";
                    }
                }else {
                    value = inputParams.getParamMap().get(key);
                }
            }else {
                value = placeholderParser.replacePlaceholders(generatorMetadata.getTemplateData().get(key), resovler);
            }
        }else {
            value = placeholderParser.replacePlaceholders(generatorMetadata.getFileData().get(key), resovler);
        }
        cacheData.put(key, value);
        return value;
    }

    public Map<String, Object> getTemplateVariableData() {
        if (this.variableCacheData != null){
            return this.variableCacheData;
        }
        Map<String, Object> data = this.generatorMetadata.getVariableData();
        if (this.extendGenerators != null && extendGenerators.size() > 0){
            for (CodeGenExtendGenerator extendGenerator : extendGenerators){
                MapTools.addMap(data, extendGenerator.getExtendData());
            }
        }
        variableCacheData = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            String value;
            if (entry.getValue() instanceof String){
                value = placeholderParser.replacePlaceholders((String)entry.getValue(), resovler);
                variableCacheData.put(key, value);
            }else {
                variableCacheData.put(key, entry.getValue());
            }
        }
        return variableCacheData;
    }

    private static class ComponentXmlResolver implements PlaceholderResolver {

        private Map<String, Object> metadata = new HashMap<>();

        private ComponentXmlResolver(InputParams inputParams, GeneratorMetadata generatorMetadata){
            metadata.putAll(generatorMetadata.getFileData());
            Map<String, Object> paramFieldMap = getFieldMap(inputParams);
            if(paramFieldMap.size()>0){
                metadata.putAll(paramFieldMap);
            }
            if (inputParams.getParamMap() != null && inputParams.getParamMap().size() > 0){
                metadata.putAll(inputParams.getParamMap());
            }
        }

        private Map<String,Object> getFieldMap(InputParams inputParams) {
            Map<String, Object> paramFieldMap = new HashMap<>();
            Field fields[] = InputParams.class.getDeclaredFields();
            InputParams.class.getFields();
            InputParams.class.getSuperclass().getDeclaredFields();
            Field.setAccessible(fields,true);
            for (Field field : fields){
                try {
                    // String 类型的属性封装成map
                    if (String.class.equals(field.getType())){
                        paramFieldMap.put(field.getName(), field.get(inputParams));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return paramFieldMap;
        }

        @Override
        public String resolvePlaceholder(String placeholderName) {
            return String.valueOf(metadata.get(placeholderName));
        }
    }

}
