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
 
 @Test
    public void ifElementExists(){
        driver.get("https://demoqa.com");
        wait=new WebDriverWait(driver,20);
        Boolean status=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@src='/images/Toolsqa.jpg']"))).isDisplayed();
        // Boolean status= driver.findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']")).isDisplayed();

        Assertions.assertEquals(status,true);
    }
@Test
    public void writeOnTextBox(){
        driver.get("https://demoqa.com/elements");
        driver.findElement(By.xpath("//span[contains(text(),'Text Box')]")).click();
        wait=new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("userName"))).sendKeys("Shanto");
        driver.findElement(By.cssSelector("#submit")).click();
        String text=driver.findElement(By.cssSelector("id=name")).getText();
        Assertions.assertTrue(text.contains("Shanto"));
    }
 
    @AfterAll
    public static void finishTest(){
        driver.close();
    }
}
