package cn.springcloud.codegen.utils;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileUtil {
    public static void toFile(String text, String directoryPath, String fileName, String encoding) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        File file = new File(directoryPath + File.separator + fileName);
        FileUtils.writeStringToFile(file, text, encoding);
    }

    public static void toFile(byte[] bytes, String directoryPath, String fileName) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            File file = new File(directoryPath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                IOUtils.closeQuietly(bos);
            }
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
        }
    }

    public static byte[] fromFile(String directoryPath, String fileName) throws IOException {
        File file = new File(directoryPath + File.separator + fileName);

        return FileUtils.readFileToByteArray(file);
    }

    public static void forceDeleteDirectory(File directory, int forceTimes) {
        if (directory.isDirectory() && directory.exists()) {
            try {
                FileUtils.deleteDirectory(directory);
                forceTimes--;
                if (forceTimes > 0) {
                    forceDeleteDirectory(directory, forceTimes);
                } else {
                    throw new IOException("Force delete directory=" + directory + " failed");
                }
            } catch (IOException e) {

            }
        }
    }

    public static void forceDeleteFile(File file, int forceTimes) {
        if (file.isFile() && file.exists()) {
            try {
                FileUtils.deleteQuietly(file);
                forceTimes--;
                if (forceTimes > 0) {
                    forceDeleteFile(file, forceTimes);
                } else {
                    throw new IOException("Force delete file=" + file + " failed");
                }
            } catch (IOException e) {

            }
        }
    }

    public static byte[] getBytes(File file) throws FileNotFoundException, IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

            return IOUtils.toByteArray(inputStream);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }
}