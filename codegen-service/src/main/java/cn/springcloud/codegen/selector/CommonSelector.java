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
 * @createdOn 2018/02/10 13:15
 */
@Service
public class CommonSelector implements GeneratorMetadataSelector{

    private static final String COMPONENT_COMMON_TYPE_NAME = "common";

    @Autowired
    private ComponentLoader componentLoader;

    @Override
    public List<GeneratorMetadata> selectGeneratorMetadata(InputParams inputParams) {
        List<GeneratorMetadata> commonGeneratorMetadata = componentLoader.getlComponentGeneratorMapByType().get(COMPONENT_COMMON_TYPE_NAME);
        return  commonGeneratorMetadata == null ? new ArrayList<>() : commonGeneratorMetadata;
    }
}
