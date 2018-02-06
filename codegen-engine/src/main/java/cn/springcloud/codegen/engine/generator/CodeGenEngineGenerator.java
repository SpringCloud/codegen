package cn.springcloud.codegen.engine.generator;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.entity.GeneratorMetadata;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.exception.CodeGenException;
import cn.springcloud.codegen.engine.service.TemplateConfigService;
import cn.springcloud.codegen.engine.tools.FileTools;
import cn.springcloud.codegen.engine.tools.GeneratorDataContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 16:51
 * @description : 代码生成类，
 */
public abstract class CodeGenEngineGenerator implements TemplateConfigService {

    private final Logger LOGGER = LoggerFactory.getLogger(CodeGenEngineGenerator.class);

    /**
     * 多级组件生成
     * @param inputParams 外部输入值
     * @param generatorMetadata 生成器元信息
     * @param extendGenerators 多级组件数据
     * @throws CodeGenException
     * @throws IOException
     * @throws TemplateException
     */
    public void genrator(InputParams inputParams, GeneratorMetadata generatorMetadata, List<CodeGenExtendGenerator> extendGenerators) throws CodeGenException, IOException, TemplateException {
        GeneratorDataContext dataContext = new GeneratorDataContext(inputParams, generatorMetadata, extendGenerators);
        genrator(dataContext);
    }

    /**
     * 单组件生成
     * @param inputParams 外部输入值
     * @param generatorMetadata 生成器元信息
     * @throws CodeGenException
     * @throws IOException
     * @throws TemplateException
     */
    public void genrator(InputParams inputParams, GeneratorMetadata generatorMetadata) throws CodeGenException, IOException, TemplateException {
        GeneratorDataContext dataContext = new GeneratorDataContext(inputParams, generatorMetadata);
        genrator(dataContext);
    }

    /**
     * 代码生成
     *
     * @throws CodeGenException
     * @throws IOException
     * @throws TemplateException
     */
    public void genrator(GeneratorDataContext dataContext) throws CodeGenException, IOException, TemplateException {

        if (dataContext == null){
            throw new IllegalArgumentException("generator data context can't be null");
        }
        /**
         *   1. 需要模板所在的目录
         *   2. 需要生成的编码格式
         *   3. 需要一个模板名称
         *   4. 需要数据源
         *   5. 需要一个输出路径
         */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(CodeGenEngineGenerator.class, getTemplateDir(dataContext));
        cfg.setDefaultEncoding(getDefaultEncode(dataContext));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        /**
         * 获取模板数据
         */
        Template template = cfg.getTemplate(dataContext.getString("templateName"));


        /**
         * 输出文件
         */
        OutputStream fos = new FileOutputStream(getOutPutFile(FileTools.getOutPath(getOutPath(dataContext.getString("dynamicOutPath")), dataContext.getString("moduleName"), isJavaOrResourcesOrOtherCode(dataContext).getKey(), getClassPackageName(dataContext)), FileTools.getFinalFileName(dataContext.getString("fileName"), dataContext.getString("fileType"))));
        Writer out = new OutputStreamWriter(fos);

        /**
         * 生成文件, 模板需要的数据源和输出文件路径
         */
        template.process(getData(dataContext), out);
        out.flush();
        out.close();
        System.out.println("----------------------");
        System.out.println("| gen code success ! |");
        System.out.println("| gen code success ! |");
        System.out.println("----------------------");
    }

    /**
     * 获取设置的编码， 默认为
     *
     * @return
     */
    private String getDefaultEncode(GeneratorDataContext dataContext) {
        return (dataContext.getString("templateConfigEncode") == null || "".equals(dataContext.getString("templateConfigEncode"))) ? CodeGenConstants.DEFAULT_ENCODE : dataContext.getString("templateConfigEncode");
    }

    /**
     * 获取类所在的包名称
     *
     * @return
     */
    private String getClassPackageName(GeneratorDataContext dataContext) {
        return StringUtils.isNotBlank(dataContext.getString("packageName", false)) ? dataContext.getString("packageName") : getData(dataContext).get("packageName") == null ? "" : String.valueOf(getData(dataContext).get("packageName"));
    }

    /**
     * 获取动态的输出路径,
     * 如果传递进来的输出路径为空的话， 使用系统的默认路径
     *
     * @param dynamicOutPath
     * @return
     */
    private String getOutPath(String dynamicOutPath) {
        return StringUtils.isNotBlank(dynamicOutPath) ? dynamicOutPath : FileTools.getSysTemp();
    }

    /**
     * 创建输出文件对象
     *
     * @param outPath  路径
     * @param fileName 文件(Person.java)
     * @return
     */
    private File getOutPutFile(String outPath, String fileName) {

        LOGGER.info("outPut : [ " + outPath + " ]");
        File file = new File(outPath, fileName);
        File dir = new File(outPath);
        if (!dir.exists()) {
            // 可以多级的生成目录
            dir.mkdirs();
        }
        return file;
    }


    /**
     * 根据类型添加， 额外的目录结构
     * @return
     */
    public abstract CodeOutType isJavaOrResourcesOrOtherCode(GeneratorDataContext dataContext);

}
