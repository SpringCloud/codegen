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
 * @author Vincent.
 * @createdOn 2018/02/11 00:27
 */
@Service
public class BaseProjectSelector implements GeneratorMetadataSelector {

    @Autowired
    private ComponentLoader componentLoader;

    @Override
    public List<GeneratorMetadata> selectGeneratorMetadata(InputParams inputParams) {
        List<GeneratorMetadata> generatorMetadata = componentLoader.getComponentGeneratorMap().get(inputParams.getParamMap().get("appType"));
        if (generatorMetadata == null || generatorMetadata.size() == 0){
            throw new IllegalArgumentException("not found [" + inputParams.getParamMap().get("appType") + "] component generator");
        }
        return generatorMetadata;
    }
}
