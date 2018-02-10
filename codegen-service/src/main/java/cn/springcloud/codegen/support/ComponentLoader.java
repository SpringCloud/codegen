package cn.springcloud.codegen.support;

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
import org.springframework.util.ClassUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author Vincent.
 * @createdOn 2018/02/05 22:42
 */
@Service
public class ComponentLoader implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_COMPONENT_CONFIG_LOCATION = "componentConfig.xml";

    private Map<String, List<GeneratorMetadata>> componentGeneratorData = new HashMap<>();

    private Map<String, List<GeneratorMetadata>> typeGroupGeneratorData = new HashMap<>();

    public Map<String, List<GeneratorMetadata>> getComponentGeneratorMap(){
        return componentGeneratorData;
    }

    public Map<String, List<GeneratorMetadata>> getlComponentGeneratorMapByType(){
        return typeGroupGeneratorData;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 获取所有匹配的文件
            Enumeration<URL> urls = ClassLoader.getSystemResources(DEFAULT_COMPONENT_CONFIG_LOCATION);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                URLConnection con = url.openConnection();
                ComponentMetadata componentMetadata = ComponentXmlFileTools.readXmlFile(con.getInputStream());
                if (componentMetadata == null || componentMetadata.getComponentId() == null){
                    throw new IllegalArgumentException("component xml load has error, please check componentConfig.xml");
                }
                componentGeneratorData.put(componentMetadata.getComponentId(), componentMetadata.getGeneratorData());
                addGeneratorDataByType(componentMetadata);
            }
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    private void addGeneratorDataByType(ComponentMetadata componentMetadata){
        List<GeneratorMetadata> generatorMetadata = typeGroupGeneratorData.get(componentMetadata.getComponentType());
        if (generatorMetadata == null){
            typeGroupGeneratorData.put(componentMetadata.getComponentType(), componentMetadata.getGeneratorData());
        }else {
            List<GeneratorMetadata> newGeneratorMetadata = new ArrayList<>(generatorMetadata);
            newGeneratorMetadata.addAll(componentMetadata.getGeneratorData());
            typeGroupGeneratorData.put(componentMetadata.getComponentType(), newGeneratorMetadata);
        }
    }
}
