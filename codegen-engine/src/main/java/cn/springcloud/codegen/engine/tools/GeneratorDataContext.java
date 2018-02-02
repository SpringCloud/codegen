package cn.springcloud.codegen.engine.tools;

import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.generator.CodeGenExtendGenerator;
import cn.springcloud.codegen.engine.tools.PlaceholderParser.PlaceholderResolver;
import org.apache.commons.lang3.ArrayUtils;
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

    private Map<String, Object> inputParamMergeMap;

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
        init();
    }

    public GeneratorDataContext(GeneratorMetadata generatorMetadata){
        this(null, generatorMetadata);
    }

    private void init() {
        mergeInputParamsToMap(inputParams);
        resovler = new ComponentXmlResolver(inputParamMergeMap, generatorMetadata);
    }

    private void mergeInputParamsToMap(InputParams inputParams){
        if (inputParamMergeMap == null){
            inputParamMergeMap = new HashMap<>();
        }
        if (inputParams == null){
            return;
        }
        inputParamMergeMap.putAll(inputParams.getParamMap());
        inputParamMergeMap.putAll(ClassTools.buildFieldValueToMap(inputParams, new  Class[]{Map.class}));
    }

    public String getString(String key){
        return getString(key, true);
    }

    public String getString(String key, boolean isRequired){
        if (StringUtils.isNotBlank(cacheData.get(key))){
            return cacheData.get(key);
        }
        String value;
        if (StringUtils.isNotBlank(generatorMetadata.getFileData().get(key))){
            value = placeholderParser.replacePlaceholders(generatorMetadata.getFileData().get(key), resovler);
        }else if (StringUtils.isNotBlank(generatorMetadata.getTemplateData().get(key))){
            value = placeholderParser.replacePlaceholders(generatorMetadata.getTemplateData().get(key), resovler);
        }else if (inputParamMergeMap.get(key) != null && StringUtils.isNotBlank(String.valueOf(inputParamMergeMap.get(key)))){
            value = String.valueOf(inputParamMergeMap.get(key));
        }else if (!isRequired){
            value = "";
        }else {
            throw new IllegalArgumentException("get value by key [" + key +"] is null");
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
        variableCacheData = new HashMap<>();
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

        private ComponentXmlResolver(Map<String, Object> inputParams, GeneratorMetadata generatorMetadata){
            metadata.putAll(generatorMetadata.getFileData());
            metadata.putAll(inputParams);
        }

        private ComponentXmlResolver(InputParams inputParams, GeneratorMetadata generatorMetadata){
            metadata.putAll(generatorMetadata.getFileData());
            Map<String, Object> paramFieldMap = ClassTools.buildFieldValueToMap(inputParams, new Class[]{Map.class});
            metadata.putAll(paramFieldMap);
            if (inputParams.getParamMap() != null){
                metadata.putAll(inputParams.getParamMap());
            }
        }


        @Override
        public String resolvePlaceholder(String placeholderName) {
            return String.valueOf(metadata.get(placeholderName));
        }
    }

}
