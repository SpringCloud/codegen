package cn.springcloud.codegen.engine.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zzf
 * @date: 2018/1/18
 * @time: 13:27
 * @description :  代码生成器的线程池
 */
public class CodeGenPool {

    private final Logger logger = LoggerFactory.getLogger(CodeGenPool.class);

    /**
     * 线程池的大小默认为， CPU核数 * 2
     */
    public static int  DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static CodeGenPool instance = null;
    private ExecutorService executors = null;

    private CodeGenPool(){
        this.executors = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
    }

    public static CodeGenPool getInstance(){
        if(instance == null){
            synchronized (CodeGenPool.class){
                if(instance == null){
                    instance = new CodeGenPool();
                }
            }
        }

        return instance;
    }

    /**
     *  执行生成任务
     * @param callable
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable){
        return this.executors.submit(callable);
    }

    /**
     * 获取线程池大小
     * @return 线程池大小
     */
    public static int size() {
        return DEFAULT_POOL_SIZE;
    }

    /**
     * 销毁线程池
     */
    public static void destroy() {
        if (instance != null) {
            instance.executors.shutdown();
        }
    }
}