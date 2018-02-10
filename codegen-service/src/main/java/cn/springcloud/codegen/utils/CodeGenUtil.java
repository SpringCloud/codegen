package cn.springcloud.codegen.utils;

/**
 * @author xujin
 */
public class CodeGenUtil {

    public static String formatGeneratePath(String generatePath) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatePath);
        String path = sb.toString();
        path = path.replace("\\", "/");
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }

    /**
     * 获取代码生成器默认的临时生成目录
     * @return
     */
    public static String getCodegenTempGeneratePath() {
        String tempGeneratePath = formatGeneratePath(System.getProperty("java.io.tmpdir")) + "codegen";
        return tempGeneratePath;
    }
}
