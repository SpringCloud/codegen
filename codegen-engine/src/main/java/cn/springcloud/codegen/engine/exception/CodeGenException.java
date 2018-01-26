package cn.springcloud.codegen.engine.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/17
 * @time: 15:50
 * @description : 生成异常类
 */
public class CodeGenException extends RuntimeException {

    private static final long serialVersionUID = 1776723636365101508L;
    private String code;
    private String msg;

    public CodeGenException(String message){
        super(message);
    }

    public CodeGenException(String message, Throwable cause){
        super(message, cause);
    }

    public CodeGenException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
