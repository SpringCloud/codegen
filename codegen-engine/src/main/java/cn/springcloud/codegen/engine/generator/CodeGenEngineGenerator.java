package cn.springcloud.codegen.engine.generator;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.CodeOutType;
import cn.springcloud.codegen.engine.exception.CodeGenException;
import cn.springcloud.codegen.engine.service.ClassService;
import cn.springcloud.codegen.engine.service.OutFileService;
import cn.springcloud.codegen.engine.service.TemplateConfigService;
import cn.springcloud.codegen.engine.tools.FileTools;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 16:51
 * @description : 代码生成类，
 * 切换到不同的模式， 可以通过不同的提供者的形式，
 * FileProvider, DataBaseProvider
 * 等形式(注意 ： 生成应该用过多个线程跑)
 */
public abstract class CodeGenEngineGenerator implements TemplateConfigService, OutFileService, ClassService {

    private final Logger LOGGER = LoggerFactory.getLogger(CodeGenEngineGenerator.class);

    /**
     * 代码生成
     *
     * @throws CodeGenException
     * @throws IOException
     * @throws TemplateException
     */
    public void genrator() throws CodeGenException, IOException, TemplateException {

        /**
         *   1. 需要模板所在的目录
         *   2. 需要生成的编码格式
         *   3. 需要一个模板名称
         *   4. 需要数据源
         *   5. 需要一个输出路径
         */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(getTemplateDir()));
        cfg.setDefaultEncoding(getDefaultEncode());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        /**
         * 获取模板数据
         */
        Template template = cfg.getTemplate(getTemplateName());


        /**
         * 输出文件
         */
        OutputStream fos = new FileOutputStream(getOutPutFile(FileTools.getOutPath(getOutPath(getDynamicOutPath()), getModuleName(), isJavaOrResourcesOrOtherCode().getKey(), getClassPackageName()), FileTools.getFinalFileName(getFileName(), getFileType())));
        Writer out = new OutputStreamWriter(fos);

        /**
         * 生成文件, 模板需要的数据源和输出文件路径
         */
        template.process(getData(), out);
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
    private String getDefaultEncode() {
        return (getTemplateConfigEncode() == null || getTemplateConfigEncode() == "") ? CodeGenConstants.DEFAULT_ENCODE : getTemplateConfigEncode();
    }

    /**
     * 获取类所在的包名称
     *
     * @return
     */
    private String getClassPackageName() {
        return StringUtils.isNotBlank(getPackageName()) ? getPackageName() : getData().get("packageName") == null ? "" : String.valueOf(getData().get("packageName"));
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
     * 设置编码的模型, 可以有一个默认值
     *
     * @return
     */
    public abstract String getTemplateConfigEncode();

    /**
     * 根据类型添加， 额外的目录结构
     * @return
     */
    public abstract CodeOutType isJavaOrResourcesOrOtherCode();

}
