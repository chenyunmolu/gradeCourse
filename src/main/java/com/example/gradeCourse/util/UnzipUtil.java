package com.example.gradeCourse.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Component
public class UnzipUtil {

    public static void unzip(String zipFilePath, String destDir) throws IOException {
        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdir();
        }

        ZipFile zipFile = new ZipFile(zipFilePath);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File entryFile = new File(destDir, entry.getName());

            if (entry.isDirectory()) {
                entryFile.mkdir();
            } else {
                InputStream in = zipFile.getInputStream(entry);
                FileOutputStream out = new FileOutputStream(entryFile);
                byte[] buffer = new byte[1024];
                int count;

                while ((count = in.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }

                out.close();
                in.close();
            }
        }

        zipFile.close();
    }

    public static String unzipFile(String zipFilePath, String destinationDirectory) throws IOException {
        String htmlFilePath="";
        File destDir = new File(destinationDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath), Charset.forName("GBK"));
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            String filePath = destinationDirectory + File.separator + entry.getName();
            if(!entry.isDirectory() && entry.getName().endsWith(".html")){
                htmlFilePath=filePath;
            }

            if (!entry.isDirectory()) {
                // 如果entry是文件，则创建该文件，并将zipInputStream中的数据写入文件
                FileOutputStream outputStream = new FileOutputStream(filePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
            } else {
                // 如果entry是目录，则创建该目录
                File dir = new File(filePath);
                dir.mkdirs();
            }
        }
        zipInputStream.close();
        return htmlFilePath;
    }
}
