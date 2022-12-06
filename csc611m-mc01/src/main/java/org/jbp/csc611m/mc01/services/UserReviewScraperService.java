package org.jbp.csc611m.mc01.services;

import org.jbp.csc611m.mc01.entities.Email;
import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.repositories.EmailRepository;
import org.jbp.csc611m.mc01.repositories.UrlRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserReviewScraperService {

    Logger logger = LoggerFactory.getLogger(UserReviewScraperService.class);

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UrlRepository urlRepository;

    @Async
    public CompletableFuture<Set<String>> getUserReviewByUrl(Url url, WebDriver driver){
        Set<String> scrapedEmails = new HashSet<>();

        //logger.info("Starting: url = {} with thread {}", url.getUrl(), Thread.currentThread().getName());
        try{
            driver.get(url.getUrl());

            //AWARDS
            //String kk = driver.findElement(By.xpath(
                    //"//*[@id=\"__next\"]/main/div/section[1]/div/section/div/div[1]/section[1]/div/ul/li/div/ul/li/span"))
                    //.getAttribute("innerHTML");
            //System.out.println(kk.substring(0,2));
//*[@id="__next"]/main/div/section[1]/div/section/div/div[1]/section[1]/div/ul/li/div/ul/li/label
            //*[@id="__next"]/main/div/section[1]/div/section/div/div[1]/section[1]/div/ul/li/div/ul/li

            String kk = driver.findElement(By.xpath(
                    "//*[@id=\"__next\"]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]"))
                    .getAttribute("innerHTML");
            //*[@id="__next"]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]
            //*[@id="__next"]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]/div
            //*[@id="__next"]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]/div/div

            //xpath
            //String kk = driver.findElement(By.xpath("//*[@id='__next]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]/div/div")).getText();

            //full xpath
            //String kk = driver.findElement(By.xpath("/html/body/div[2]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]/div/div")).getText();

            //div class id
            //String kk = driver.findElement(By.xpath("//div[@class='ipc-html-content-inner-div']")).getText();

            //by classname
            //String kk = driver.findElement(By.className("ipc-html-content-inner-div")).toString();
            //String kk = driver.findElement(By.className("ipc-html-content ipc-html-content--base")).toString();

            //*[@id="__next"]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]/div/div
            ///html/body/div[2]/main/div/section[1]/div/section/div/div[1]/section[8]/div[2]/div[1]/div[3]/div/div
            //System.out.println(kk.substring(0,2));
            System.out.println(kk);


            url.setStatus("SCRAPED");
            url.setWorker(Thread.currentThread().getName());
            urlRepository.save(url);

            scrapedEmails.stream()
                    .forEach(eadd -> emailRepository.save(new Email(eadd.split("@")[0],eadd)));

            return CompletableFuture.completedFuture(scrapedEmails);

        }catch (Exception e) {
            url.setStatus("ERROR");
            url.setWorker(Thread.currentThread().getName());
            urlRepository.save(url);

            e.printStackTrace();
            //logger.error("Error scraping email for {}", url.getUrl());
        }

        //logger.info("Complete: url = {} with thread {}", url.getUrl(), Thread.currentThread().getName());
        return CompletableFuture.completedFuture(scrapedEmails);
    }
}
