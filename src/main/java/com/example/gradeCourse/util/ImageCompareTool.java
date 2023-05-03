package com.example.gradeCourse.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Component
public class ImageCompareTool {

    public static double compare(File file1, File file2) throws IOException {
        // 加载图片
        BufferedImage image1 = ImageIO.read(file1);
        BufferedImage image2 = ImageIO.read(file2);

        // 调整图片大小
        int width = Math.min(image1.getWidth(), image2.getWidth());
        int height = Math.min(image1.getHeight(), image2.getHeight());
        int[] pixels1 = image1.getRGB(0, 0, width, height, null, 0, width);
        int[] pixels2 = image2.getRGB(0, 0, width, height, null, 0, width);

        // 将像素数组转化为同一像素大小的图像
        BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage1.setRGB(0, 0, width, height, pixels1, 0, width);
        BufferedImage bufferedImage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage2.setRGB(0, 0, width, height, pixels2, 0, width);

        // 计算图片的相似度
        return calculateSimilarity(bufferedImage1, bufferedImage2);
    }

    private static double calculateSimilarity(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();

        // 计算两张图片的像素总数
        int totalPixels = width * height;

        // 初始化差异像素数
        int diffPixels = 0;

        // 遍历图片中的每一个像素点
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 获取当前位置的像素点颜色值
                Color color1 = new Color(image1.getRGB(x, y));
                Color color2 = new Color(image2.getRGB(x, y));

                // 计算两个像素点颜色值的差异
                int redDiff = Math.abs(color1.getRed() - color2.getRed());
                int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
                int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());

                // 计算像素点颜色值的总差异
                int pixelDiff = redDiff + greenDiff + blueDiff;

                // 如果像素点颜色值的差异大于阈值，则判定为不同的像素点
                if (pixelDiff > 30) {
                    diffPixels++;
                }
            }
        }

        // 计算图片的相似度
        double similarity = 1 - ((double) diffPixels / totalPixels);

        return similarity;
    }
}
