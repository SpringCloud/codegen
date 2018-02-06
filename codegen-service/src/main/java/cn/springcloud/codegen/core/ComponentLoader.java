package cn.springcloud.codegen.core;

import cn.springcloud.codegen.engine.entity.ComponentMetadata;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.tools.ComponentXmlFileTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/02/05 22:42
 */
@Service
public class ComponentLoader implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, List<GeneratorMetadata>> componentGeneratorData = new HashMap<>();

    public Map<String, List<GeneratorMetadata>> getComponentGeneratorMap(){
        return componentGeneratorData;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            // 获取所有匹配的文件
            Resource[] resources = resolver.getResources("componentConfig.xml");
            for(Resource resource : resources) {
                InputStream stream = resource.getInputStream();
                ComponentMetadata componentMetadata = ComponentXmlFileTools.readXmlFile(stream);
                if (componentMetadata == null || componentMetadata.getComponentId() == null){
                    throw new IllegalArgumentException("component xml load has error, please check componentConfig.xml");
                }
                componentGeneratorData.put(componentMetadata.getComponentId(), componentMetadata.getGeneratorData());
            }
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
