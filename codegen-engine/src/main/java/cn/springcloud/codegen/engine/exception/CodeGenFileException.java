package cn.springcloud.codegen.engine.exception;

import javax.annotation.processing.FilerException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 16:17
 * @description : 模板文件和生成文件异常
 */
public class CodeGenFileException extends Exception {

    private static final long serialVersionUID = 206605161158992130L;

    public CodeGenFileException(String message){
        super(message);
    }

    public CodeGenFileException(String message, Throwable cause){
        super(message, cause);
    }
}
