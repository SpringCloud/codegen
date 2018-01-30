package cn.springcloud.codegen;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 代码生成器主程序入口
 * @author xujin
 */
@SpringBootApplication
public class SmartCodeGenApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SmartCodeGenApplication.class).web(true).run(args);
    }
}