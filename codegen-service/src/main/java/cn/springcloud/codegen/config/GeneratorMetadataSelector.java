package cn.springcloud.codegen.config;

import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;

import java.util.List;

/**
 * @author Vincent.
 * @createdOn 2018/02/10 13:07
 */
public interface GeneratorMetadataSelector {

    /**
     * 选择组件数据
     * @param inputParams 调用方传入参数
     * @return
     */
    List<GeneratorMetadata> selectGeneratorMetadata(InputParams inputParams);

}
