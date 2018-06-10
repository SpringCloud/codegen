package cn.springcloud.codegen.selector;

import cn.springcloud.codegen.config.GeneratorMetadataSelector;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.support.ComponentLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by rencong on 2018/6/7
 **/
@Service
public class ChooseComponentSelector implements GeneratorMetadataSelector {

    @Autowired
    private ComponentLoader componentLoader;

    @Override
    public List<GeneratorMetadata> selectGeneratorMetadata(InputParams inputParams) {
        List<GeneratorMetadata> generatorMetadata = new ArrayList<>();
        if (inputParams.getParamMap().get("chooseComponent") == null){
            return generatorMetadata;
        }

        if (List.class.isAssignableFrom(inputParams.getParamMap().get("chooseComponent").getClass())){
            for (Object componentName : (List)inputParams.getParamMap().get("chooseComponent")){
                String componentKey = String.valueOf(componentName);
                List<GeneratorMetadata> componentMetadata = componentLoader.getComponentGeneratorMap().get(componentKey);
                if (componentMetadata == null || componentMetadata.size() == 0){
                    throw new IllegalArgumentException("not found [" + componentKey + "] component generator");
                }
                generatorMetadata.addAll(componentMetadata);
            }
        }
        return generatorMetadata;
    }
}
