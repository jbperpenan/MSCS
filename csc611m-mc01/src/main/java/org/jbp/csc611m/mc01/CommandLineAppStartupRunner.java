package org.jbp.csc611m.mc01;

import org.jbp.csc611m.mc01.entities.Crew;
import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.services.CsvService;
import org.jbp.csc611m.mc01.services.UserReviewScraperService;
import org.jbp.csc611m.mc01.services.ImdbUrlGeneratorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    private CsvService csvService;

    @Autowired
    private ImdbUrlGeneratorService imdbUrlGeneratorService;

    @Autowired
    private UserReviewScraperService userReviewScraperService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Value( "${website}" )
    private String website;

    @Value( "${thread}" )
    private Integer threadCount;

    @Value( "${time}" )
    private Integer runtime;

    @Override
    public void run(String... args) throws Exception {

        //imdbUrlGeneratorService.chunkLinks();
        //imdbUrlGeneratorService.chunkMovies();
        List<Url> urlList = imdbUrlGeneratorService.generateImdbUrls();
        //WebDriver driver = initBrowserDriverConfig();

        getUserReviews(urlList);
    }

    private WebDriver initBrowserDriverConfig() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        //options.addArguments("--disable-blink-features=AutomationControlled");
        //options.addArguments("--disable-extensions");
        //options.addArguments("useAutomationExtension", "FALSE");
        //options.addArguments("excludeSwitches", "enable-automation");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        WebDriver driver = new ChromeDriver(options);

        return driver;
    }

    private void getUserReviews(List<Url> urlList) throws Exception {
        System.out.println("====== start ======");
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        //options.addArguments("--disable-blink-features=AutomationControlled");
        //options.addArguments("--disable-extensions");
        //options.addArguments("useAutomationExtension", "FALSE");
        //options.addArguments("excludeSwitches", "enable-automation");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        WebDriver driver = new ChromeDriver(options);

        List<Crew> crewList = new ArrayList<>();
        for(int i = 0; i< urlList.size(); i++){
            try {
                Crew meta1 = userReviewScraperService.getUserReviewByUrl(urlList.get(i), driver);
                if(meta1 != null){
                    crewList.add(meta1);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

/*        while(taskExecutor.getActiveCount() !=0 && taskExecutor.getThreadPoolExecutor().getQueue().size() !=0){
            //wait for all threads to finish...
        }
        taskExecutor.shutdown();*/
        driver.close();
        System.out.println("====== end, create csv files ======");

        csvService.writeCsvOutputs(crewList);
        System.out.println("====== csv files created ======");
    }
}
