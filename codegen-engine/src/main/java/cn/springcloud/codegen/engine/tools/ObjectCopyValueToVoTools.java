package cn.springcloud.codegen.engine.tools;

import cn.springcloud.codegen.engine.exception.CodeGenException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/18
 * @time: 17:48
 * @description : 将properties 属性的值， 映射到实体上(vo, entity)
 */
public class ObjectCopyValueToVoTools {

    /**
     * 第一个参数是要复制值的
     * 第二个参数是之前存在值的
     * @param targetClass
     * @param properties
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> T properties2Object(final T targetClass, final Properties properties) {
        BeanUtilsBean instance = BeanUtilsBean2.getInstance();
        try {
            instance.copyProperties(targetClass, properties);
        } catch (IllegalAccessException e) {
            throw new CodeGenException("targetClass = [" + targetClass + "]" +
                    " or " + "originClass = [" + properties + "] is null.");
        } catch (InvocationTargetException e) {
            throw new CodeGenException("targetClass = [" + targetClass + "]" + "is not invocation.");
        }
        return targetClass;
    }

    /**
     * 将对象的值， 复制到目标对象上
     * @param targetClass
     * @param originClass
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> T bean2Object(final T targetClass, final Object originClass) {

        BeanUtilsBean instance = BeanUtilsBean2.getInstance();
        try {
            instance.copyProperties(targetClass, originClass);
        } catch (IllegalAccessException e) {
            throw new CodeGenException("targetClass = [" + targetClass + "]" +
                    " or " + "originClass = [" + originClass + "] is null.");
        } catch (InvocationTargetException e) {
            throw new CodeGenException("targetClass = [" + targetClass + "]" + "is not invocation.");
        }
        return targetClass;
    }
}
