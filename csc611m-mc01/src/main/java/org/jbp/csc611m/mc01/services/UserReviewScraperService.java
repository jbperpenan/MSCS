package org.jbp.csc611m.mc01.services;

import org.jbp.csc611m.mc01.entities.Crew;
import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.repositories.CrewRepository;
import org.jbp.csc611m.mc01.repositories.UrlRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserReviewScraperService {

    Logger logger = LoggerFactory.getLogger(UserReviewScraperService.class);

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private UrlRepository urlRepository;

    //@Async
    public void getUserReviewByUrl(Url url, WebDriver driver){

        //logger.info("Starting: url = {} with thread {}", url.getUrl(), Thread.currentThread().getName());
        try{
            driver.get(url.getUrl());

            String content  = driver.findElement(By.xpath("/html/head/meta[3]")).getAttribute("content");
            //System.out.println(content);

            url.setStatus("SCRAPED");
            url.setWorker(Thread.currentThread().getName());
            urlRepository.save(url);

            String[] splittedContent = content.split("[.]");
            String director = splittedContent[0].split("Directed by")[1];
            String casts = splittedContent[1].split("With")[1];

            crewRepository.save(new Crew(director,casts,url.getMovieId()));
        }catch (Exception e) {
            url.setStatus("ERROR");
            url.setWorker(Thread.currentThread().getName());
            urlRepository.save(url);

            e.printStackTrace();
        }finally {
            driver.close();
        }
    }
}
