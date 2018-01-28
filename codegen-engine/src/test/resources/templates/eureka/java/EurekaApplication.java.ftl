package ${packageName};

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
<#if classImportKey?exists>
    <#list classImportKey as model>
import ${model}
    </#list>
</#if>

@SpringBootApplication
@EnableEurekaServer
<#if classAnnotationKey?exists>
    <#list classAnnotationKey as model>
${model}
    </#list>
</#if>
public class EurekaApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaApplication.class).web(true).run(args);
    }
}