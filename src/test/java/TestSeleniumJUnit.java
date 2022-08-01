import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class TestSeleniumJUnit {
 public static WebDriver driver;

    @BeforeAll
    public static void setup(){
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
        FirefoxOptions ops=new FirefoxOptions(); // Creat firefox option variable
       ops.addArguments("--headed"); // Argument pass for Firefox run and close.
        driver=new FirefoxDriver(ops);
       // driver=new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
 @Test
    public  void getTitle(){
        driver.get("https://demoqa.com/");
        String title= driver.getTitle();
        System.out.println(title);
        //Assert.assertTrue(title.contains("ToolsQA"));
     Assertions.assertTrue(title.contains("ToolsQA"));
     System.out.println("Test Sucsessfully Done");
    }
    @AfterAll
    public static void finishTest(){
        driver.close();
    }
}
