import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.KeyInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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



      // Multiple Window Handle
      @Test
      public void windowHandle(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("windowButton")).click();
        WebDriverWait wait=new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("windowButton")));
        String mainwindowHandle=driver.getWindowHandle();
        Set<String> allWindowHandels=driver.getWindowHandles();
          Iterator<String> iterator=allWindowHandels.iterator();

          while (iterator.hasNext()){
              String childWindow=iterator.next();
              if(!mainwindowHandle.equalsIgnoreCase(childWindow)){
                  driver.switchTo().window(childWindow);
                  String text=driver.findElement(By.id("sampleHeading")).getText();
                  Assertions.assertTrue(text.contains("This is a sample page"));

              }
          }
      }

      // Modal Dialog
      @Test
public void modalDialog() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        //Small modal

       // WebElement element=wait.until(ExpectedConditions.elementToBeClickable(By.id("showSmallModal")));
       // element.click();
          driver.findElement(By.id("showSmallModal")).click();
          sleep(10000);
         driver.findElement(By.id("closeSmallModal")).click();

         //Large modal
          driver.findElement(By.id("showLargeModal")).click();
          sleep(10000);
          driver.findElement(By.id("closeLargeModal")).click();
}


// WebTable data edit and assertions
@Test
 public void webTable(){
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-1']")).click();
        driver.findElement(By.xpath("//input[@id='firstName']")).clear();
     driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("Shakil Alam");

     driver.findElement(By.xpath("//input[@id='lastName']")).click();
     driver.findElement(By.xpath("//input[@id='lastName']")).clear();
     driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Shanto");

     driver.findElement(By.id("submit")).click();
    String firstName= driver.findElement(By.xpath("//div[contains(text(),'Shakil Alam')]")).getText();
     String secondName= driver.findElement(By.xpath("//div[contains(text(),'Shanto')]")).getText();

     Assertions.assertTrue(firstName.contains("Shakil Alam"));
     Assertions.assertTrue(secondName.contains("Shanto"));
 }

  // Scrap Data from Table
 @Test
 public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table=driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows=table.findElements(By.className("rt-tr"));
        int i=0;
        for(WebElement row:allRows){
            List<WebElement>cells=row.findElements(By.className("rt-td"));
            for(WebElement cell:cells){
                i++;
                System.out.println("Num("+i+")"+cell.getText());
            }
        }
 }

 // Upload Image
 @Test
        public void uploadImage(){
          driver.get("https://demoqa.com/upload-download");
         WebElement upload= driver.findElement(By.id("uploadFile"));
         //upload.click();
         upload.sendKeys("C:\\Users\\Shanto\\Pictures\\demoqa.PNG");
         String text=driver.findElement(By.id("uploadedFilePath")).getText();
         Assertions.assertTrue(text.contains("demoqa.PNG"));
        }

        //Google image search
/*
     public void googleImageSearch() throws InterruptedException {
        driver.get("https://images.google.com/");
         Actions action=new Actions(driver);
         List<WebElement> id=driver.findElements(By.cssSelector("button"));
        action.click(id.get(4)).perform();
         sleep(5000);

       // driver.findElement(By.xpath("//span[contains(text(),'Upload an image')]")).click();
       // driver.findElement(By.xpath("//input[@id='awyMjb']")).sendKeys("C:\\\\Users\\\\Shanto\\\\Pictures\\\\demoqa.PNG");
    }

*/


    // IFrame Handle
    @Test
    public void iFrame(){
        driver.get("https://demoqa.com/frames");
       driver.switchTo().frame("frame2");
       String text=driver.findElement(By.id("sampleHeading")).getText();
       Assertions.assertTrue(text.contains("This is a sample page"));
       driver.switchTo().defaultContent();
    }


    // Mouse Hover
@Test
    public void mouseHovering() throws InterruptedException {
        driver.get("https://seu.edu.bd/");
        Actions action=new Actions(driver);
        WebElement mainMenu=driver.findElement(By.xpath("//a[contains(text(),'Academics')]"));
        action.moveToElement(mainMenu).perform();
        Thread.sleep(5000);
        WebElement submenu=driver.findElement(By.xpath("//a[contains(text(),'School of Science & Engineering')]"));
        action.moveToElement(submenu).perform();
        submenu.click();
    }


    // Keyboard Event
    @Test
    public void keyBoardEvent(){
        driver.get("https://demoqa.com/automation-practice-form");
       WebElement textbox= driver.findElement(By.id("firstName"));
        Actions action =new Actions(driver);
        action.click(textbox).perform();
        action.keyDown(Keys.SHIFT)
                .sendKeys("Shakil Alam")
                .keyUp(Keys.SHIFT)
                .doubleClick()
                .contextClick()
                .sendKeys(Keys.ENTER)
               .perform();
    }

    // Take Screenshot
    @Test
    public void takeScreenshot() throws IOException {
    driver.get("https://demoqa.com");
    File screenshotFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    String time =new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
    String fileWithPath= "./src/test/resources/screenshots/"+time+".png";
    File DestFile=new File(fileWithPath);
        FileUtils.copyFile(screenshotFile,DestFile);
    }

    // Read From Excel
    @Test
    public void readExcelFile() throws IOException {
  String filePath="./sec/test/resources";
  Utils.readFromExcl(filePath,"SampleExcelFile.xls", "Sheet_1" );
    }

@AfterAll
    public static void finishTest(){
       // driver.close();
    }

}
