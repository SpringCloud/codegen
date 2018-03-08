package cn.springcloud.codegen.util;

import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.tools.ClassTools;
import cn.springcloud.codegen.entity.DownloadConfig;
import cn.springcloud.codegen.service.ComponentExecutor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/03/08 11:27
 */
public class CodeGenTransport {

    private ComponentExecutor componentExecutor;

    public CodeGenTransport(ComponentExecutor componentExecutor){
        this.componentExecutor = componentExecutor;
    }

    public ResponseEntity<Resource> downloadResponse(DownloadConfig downloadConfig) {
        String canonicalFileName = downloadConfig.getModuleName();

        InputParams inputParams = new InputParams();
        inputParams.setParamMap(changeMapValueToString(ClassTools.buildFieldValueToMap(downloadConfig)));
        byte[] bytes = componentExecutor.generate(inputParams);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("charset", "utf-8");

        headers.add("Content-Disposition", "attachment;filename=\"" + canonicalFileName + "\"");

        InputStream inputStream = new ByteArrayInputStream(bytes);
        Resource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/x-msdownload")).body(resource);
    }

    private Map<String,String> changeMapValueToString(Map<String, Object> downloadConfigMap) {
        Map<String, String> paramMap = new HashMap<>();
        downloadConfigMap.forEach((k,v)-> paramMap.put(k, String.valueOf(v)));
        return paramMap;
    }
}
