import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class TestSeleniumJUnit {
   public static WebDriver driver;
    WebDriverWait wait;
    @BeforeAll
    public static void setup(){
     System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
        //System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
      FirefoxOptions ops=new FirefoxOptions();
       // ChromeOptions ops=new ChromeOptions();
        ops.addArguments("--headed");
       driver=new FirefoxDriver(ops);
       // driver=new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }
@Test
    public  void getTitle(){
        driver.get("https://demoqa.com");
        String title=driver.getTitle();
        System.out.println(title);
        Assertions.assertTrue(title.contains("ToolsQA"));
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
        //driver.findElement(By.id("userName")).sendKeys("Shanto");
       // driver.findElement(By.id("submit")).click();
    WebElement element = driver.findElement(By.xpath("//button[@id='submit']"));
    Actions actions = new Actions(driver);
    actions.moveToElement(element).click().perform();
    String text=driver.findElement(By.id("name")).getText();
        Assertions.assertTrue(text.contains("Shanto"));
    }

    @Test
    public void clickOnButton(){
        driver.get("https://demoqa.com/buttons");
        Actions action=new Actions(driver);
       List<WebElement> list= driver.findElements(By.cssSelector("button"));
      // List<WebElement> list=driver.findElements(By.cssSelector("button"));
        action.doubleClick(list.get(1)).perform();
        String text=driver.findElement(By.id("doubleClickMessage")).getText();
        Assertions.assertTrue(text.contains("You have done a double click"));

        action.contextClick(list.get(2)).perform();
        String text2=driver.findElement(By.id("rightClickMessage")).getText();
        Assertions.assertTrue(text2.contains("You have done a right click"));

       // action.click(list.get(3)).perform();
        list.get(3).click();
        String text3=driver.findElement(By.id("dynamicClickMessage")).getText();
        Assertions.assertTrue(text3.contains("You have done a dynamic click"));
    }

    @Test
    public void dropDown(){
        driver.get("https://demoqa.com/select-menu");
       Select colorSelect=new Select(driver.findElement(By.id("oldSelectMenu")));
       colorSelect.selectByValue("3");

        Select multipleCarSelect=new Select(driver.findElement(By.id("cars")));
          if(multipleCarSelect.isMultiple()){
              multipleCarSelect.selectByValue("volvo");
              multipleCarSelect.selectByValue("Audi");
          }

        }

        // Only Date picker
        @Test
        public void datePicker(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).clear();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("03/04/1997");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
        }

        //Date and time picker
        @Test
        public void datePicker2(){
            driver.get("https://demoqa.com/date-picker");
            driver.findElement(By.id("dateAndTimePickerInput")).clear();
            driver.findElement(By.id("dateAndTimePickerInput")).sendKeys("March 4, 1997 01:27 PM");
            driver.findElement(By.id("dateAndTimePickerInput")).sendKeys(Keys.ENTER);
        }

        @Test
        public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        driver.switchTo().alert().accept();
           //Promt Alart handle
        driver.findElement(By.id("promtButton")).click();
        driver.switchTo().alert().sendKeys("Shanto");
        sleep(2000);
        driver.switchTo().alert().accept();
        String text=driver.findElement(By.id("promptResult")).getText();
        Assertions.assertTrue(text.contains("Shanto"));
        }

        @Test
      public void handleTab() throws InterruptedException {
        driver.get("https://demoqa.com/links");
        driver.findElement(By.id("simpleLink")).click();
        sleep(5000);
          ArrayList<String> tab= new ArrayList<String>(driver.getWindowHandles());
          driver.switchTo().window(tab.get(1));
          System.out.println("New Tab Title"+driver.getTitle());

          Boolean status=driver.findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']")).isDisplayed();
          Assertions.assertEquals(true,status);
          driver.close();
          driver.switchTo().window(tab.get(0));
      }



@AfterAll
    public static void finishTest(){
       // driver.close();
    }

}
