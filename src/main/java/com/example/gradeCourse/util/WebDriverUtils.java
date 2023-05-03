package com.example.gradeCourse.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.stereotype.Component;

import java.io.File;


public class WebDriverUtils {

    private WebDriver driver;

    public WebDriverUtils(WebDriver driver){
        this.driver=driver;
    }

    public void takeScreenshot(String filePath) throws Exception {
        // Take a screenshot of the entire page
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Save the screenshot to a local file
        FileUtils.copyFile(screenshotFile, new File(filePath));
    }

    public String openHtmlAndGetScreenshot(String htmlPath,String screenshotDir){
        System.setProperty("webdriver.chrome.driver", "E:/Chrome/chromedriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true); // 设置为无头模式，不显示浏览器界面
        WebDriver driver = new ChromeDriver(options);
        driver.get(htmlPath);
        WebDriverUtils utils = new WebDriverUtils(driver);
        try {
            utils.takeScreenshot(screenshotDir+"/screenshot.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
        return screenshotDir+"/screenshot.jpg";
    }
}
