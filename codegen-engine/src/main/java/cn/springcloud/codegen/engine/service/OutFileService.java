package cn.springcloud.codegen.engine.service;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 16:08
 * @description : 输出文件接口
 */
public interface OutFileService {

    String getDynamicOutPath();

    String getFileName();

    String getFileType();
}
