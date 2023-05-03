package com.example.gradeCourse.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Component
public class ZipUtil {

    public static void zip(String sourceDirPath, String zipFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        ZipOutputStream zos = new ZipOutputStream(fos);
        zipDir(sourceDirPath, zos);
        zos.close();
        fos.close();
    }

    private static void zipDir(String dirPath, ZipOutputStream zos) throws IOException {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                zipDir(file.getAbsolutePath(), zos);
            } else {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(file.getName()));

                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }
        }
    }


}
