package cn.springcloud.codegen.engine.tools;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import cn.springcloud.codegen.engine.entity.InputParams;
import cn.springcloud.codegen.engine.entity.InputParamsContext;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/19
 * @time: 16:49
 * @description : 将参数转化成， 代码生成器格式的， 没有设置packageName 默认就是模板Map参数里面的
 */
public class TransforParamTools {

    public static InputParams transforParam(InputParamsContext ipc){

        InputParams in = new InputParams();
        Class<?> currentClasses = ipc.getCurrentClasses();
        String simpleName = ClassTools.getClassSimpleName(currentClasses);

        // 等到当前类所在路径
        String classPath = ClassTools.getAbsolutePathOfClassLoaderClassPath(currentClasses);

        // 三个必要参数
        in.setModuleName(ipc.getModuleName());
        in.setFileType(ipc.getFileType());
        in.setDynamicOutPath(ipc.getDynamicOutPath());
        in.setTemplateDir(classPath + File.separator + ipc.getPrefixTemplatePath());
        /**
         *  判断是否传递进来模板名称，如果没有默认为： 类名 + 模板后缀 (Application.java.ftl)
         *  这种情况需要类名和模板名称必须要一致
          */
        in.setTemplateName(setDynamicTemplateName(ipc, simpleName));
        in.setFileName(getFileName(ipc, simpleName));

        return in;
    }

    /**
     * 判断是否传递进来模板名称，如果没有默认为： 类名 + 模板后缀 (Application.java.ftl)
     * 这种情况需要类名和模板名称必须要一致
     * 动态设置模板名
     * @return
     */
    private static String setDynamicTemplateName(InputParamsContext ipc, String simpleName){
        return StringUtils.isNotBlank(ipc.getTemplateName()) ? ipc.getTemplateName() : simpleName + CodeGenConstants.POINT_STR + ipc.getFileType() + CodeGenConstants.POINT_STR + CodeGenConstants.DEFAULT_TEMPLATE_POSTFIX;
    }

    /**
     * 获取输出文件名
     * @param ipc
     * @return
     */
    private static String getFileName(InputParamsContext ipc, String simpleName){
        return StringUtils.isNotBlank(ipc.getFileType()) ? simpleName : FileTools.getOutFileName(ipc.getTemplateName());
    }
}
