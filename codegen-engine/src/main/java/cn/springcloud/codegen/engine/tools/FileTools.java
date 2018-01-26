
package cn.springcloud.codegen.engine.tools;

import cn.springcloud.codegen.engine.constants.CodeGenConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 20:20
 * @description : 文件工具类
 */
public class FileTools {

    /**
     * 输出目录
     * @param dynamicOutPath
     * @param moduleName
     * @param classPackageName
     * @return
     */
    public static String getOutPath(String dynamicOutPath,String moduleName, String codeOutTypePath,String classPackageName){

        if(StringUtils.isNotBlank(classPackageName)){
            classPackageName = convertPackageToPath(classPackageName);
        }else{
            classPackageName = "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(dynamicOutPath).append(File.separator);
        sb.append(moduleName).append(File.separator);
        sb.append(codeOutTypePath).append(File.separator);
        sb.append(classPackageName).append(File.separator);

        return sb.toString().replace("\\", CodeGenConstants.FILE_SEPARATOR);
    }

    /**
     * 最终要出书的文件名
     * @param fileName
     * @param fileType
     * @return
     */
    public static String getFinalFileName(String fileName, String fileType){

        // 不传递文件类型的时候， 使用模板截取调后面的进行使用
        return  StringUtils.isNotBlank(fileType) ? fileName + "." + fileType : fileName;
    }

    /**
     * 将包名转换成路径名称(也就是将. 转化成/)
     * @param packageName
     * @return
     */
    public static String convertPackageToPath(String packageName){
        if(StringUtils.isNotBlank(packageName)){
            return packageName.replaceAll("\\.", CodeGenConstants.FILE_SEPARATOR);
        }
        return packageName;
    }

    /**
     * 将路径转化成包结构
     * @param path
     * @return
     */
    public static String convertPathToPackage(String path){
        if(StringUtils.isNotBlank(path)){
            return path.replaceAll("//", ".");
        }
        return path;
    }

    /**
     * 获取当前系统的临时目录
     * @return
     */
    public static String getSysTemp(){
        return System.getProperty("java.io.tmpdir");
    }


    /**
     * 获取输出文件的名称
     * @param templateName
     * @return
     */
    public static String getOutFileName(String templateName){
        return templateName.substring(0, templateName.lastIndexOf("."));
    }

    /**
     * 截取到想要的部分字符串信息
     * @param finalStr
     * @return
     */
    public static  String getTypeValue(String finalStr){
        return StringUtils.isNotBlank(finalStr) ? finalStr.substring(finalStr.lastIndexOf(".") + 1, finalStr.length()) : "";
    }

}
