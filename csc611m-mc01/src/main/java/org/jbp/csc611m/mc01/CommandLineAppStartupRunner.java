package org.jbp.csc611m.mc01;

import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.services.CsvService;
import org.jbp.csc611m.mc01.services.UserReviewScraperService;
import org.jbp.csc611m.mc01.services.ImdbUrlGeneratorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
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

        List<Url> urlList = imdbUrlGeneratorService.generateImdbUrls();
        WebDriver driver = initBrowserDriverConfig();

        try {
            System.out.println("URL to load: "+urlList.get(0).getUrl());
            driver.get(urlList.get(0).getUrl());

            String kk = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/div/section/div/div[1]/section[1]/div/ul/li/div/ul/li/span"))
                    .getAttribute("innerHTML");

            System.out.println("awards: "+kk);

        } catch (Exception e) {
            //System.out.println(0);
            e.printStackTrace();
        }

        //getUserReviews(urlList, driver);
    }

    private WebDriver initBrowserDriverConfig() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        WebDriver driver = new ChromeDriver(options);

        return driver;
    }

    private void getUserReviews(List<Url> urlList, WebDriver driver) throws Exception {
        for(int i = 0; i< urlList.size(); i++){
            try {
                userReviewScraperService.getUserReviewByUrl(urlList.get(i), driver);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        while(taskExecutor.getActiveCount() !=0 && taskExecutor.getThreadPoolExecutor().getQueue().size() !=0){
            //wait for all threads to finish...
        }
        driver.close();
    }
}
