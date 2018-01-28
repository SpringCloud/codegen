package cn.springcloud.codegen.engine.generator;

import cn.springcloud.codegen.engine.service.ExtendService;

import java.util.Map;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 15:52
 */
public abstract class CodeGenExtendGenerator implements ExtendService {

    @Override
    public abstract Map<String, Object> getExtendData();

    /**
     * 扩展数据作用文件标识，可用作将改数据作用到指定文件上
     * @return
     */
    public abstract String targetIdentifier();

}
