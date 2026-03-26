package org.selenium.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class SeleniumDriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = ConfigReader.getProperty("browser").toLowerCase();
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    // Handling Chrome 146+ potential issues
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    // Adding headless mode for CI/CD environments
                    if (System.getProperty("headless") != null && System.getProperty("headless").equals("true")) {
                        chromeOptions.addArguments("--headless=new");
                    }
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (System.getProperty("headless") != null && System.getProperty("headless").equals("true")) {
                        firefoxOptions.addArguments("-headless");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (System.getProperty("headless") != null && System.getProperty("headless").equals("true")) {
                        edgeOptions.addArguments("--headless=new");
                    }
                    driver.set(new EdgeDriver(edgeOptions));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
