package cn.springcloud.codegen.engine;

import cn.springcloud.codegen.engine.feign.FeignAssemblyGenerator;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 20:57
 */
public class FeignCodeGenTest {

    public static void main(String[] args) {
        new FeignAssemblyGenerator().execute(null);
    }
}
