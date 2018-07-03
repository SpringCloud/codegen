package cn.springcloud.codegen.utils;

import java.util.UUID;

/**
 * @author Vincent.
 * @createdOn 2018/07/03 22:06
 */
public class UUIDHelper {

    public static String random() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
