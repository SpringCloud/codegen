package cn.springcloud.codegen.util;

import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.tools.ClassTools;
import cn.springcloud.codegen.entity.ProjectModel;
import cn.springcloud.codegen.service.ComponentExecutor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * @author Vincent.
 * @createdOn 2018/03/08 11:27
 */
public class CodeGenTransport {

    private ComponentExecutor componentExecutor;

    public CodeGenTransport(ComponentExecutor componentExecutor){
        this.componentExecutor = componentExecutor;
    }

    public byte[] downloadBytes(ProjectModel projectModel) {
        InputParams inputParams = new InputParams();
        inputParams.setParamMap(ClassTools.buildFieldValueToMap(projectModel));
        return componentExecutor.generate(inputParams);
    }

    public void downloadResponse(ProjectModel projectModel, HttpServletResponse response) throws URISyntaxException, IOException {
        InputParams inputParams = new InputParams();
        inputParams.setParamMap(ClassTools.buildFieldValueToMap(projectModel));
        byte[] bytes = componentExecutor.generate(inputParams);
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=codegen.zip");
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        response.flushBuffer();

    }

}
