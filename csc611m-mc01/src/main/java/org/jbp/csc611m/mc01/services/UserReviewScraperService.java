package org.jbp.csc611m.mc01.services;

import org.jbp.csc611m.mc01.entities.Crew;
import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.repositories.CrewRepository;
import org.jbp.csc611m.mc01.repositories.UrlRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReviewScraperService {

    Logger logger = LoggerFactory.getLogger(UserReviewScraperService.class);

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private UrlRepository urlRepository;

    //@Async
    public Crew getUserReviewByUrl(Url url, WebDriver driver){

        //logger.info("Starting: url = {} with thread {}", url.getUrl(), Thread.currentThread().getName());
        //WebDriver driver = new ChromeDriver(options);
        List<Crew> meta1 = new ArrayList<>();
        try{
            driver.get(url.getUrl());

            String content  = driver.findElement(By.xpath("/html/head/meta[3]")).getAttribute("content");
            //System.out.println(content);

            url.setStatus("SCRAPED");
            url.setWorker(Thread.currentThread().getName());
            urlRepository.save(url);

/*            String[] splittedContent = content.split("[.]");
            String director = splittedContent[0].split("Directed by")[1].trim().replace(',','|');
            String casts = splittedContent[1].split("With")[1].replace(',','|');*/

            String director = getDirector(content);
            String casts = getCasts(content);


            Crew crew = new Crew(director,casts,url.getMovieId());
            System.out.println("URL = " + url.getUrl() + "->"+ crew.toString());

            return crew;
            //crewRepository.save(new Crew(director,casts,url.getMovieId()));
        }catch (Exception e) {
            //url.setStatus("ERROR");
            //url.setWorker(Thread.currentThread().getName());
            //urlRepository.save(url);

            logger.error(url.getUrl());
            e.printStackTrace();
        }finally {
            //driver.close();
        }

        return null;
    }

    private String getDirector(String content){
        int d1 = content.indexOf("Directed by");
        int d2 = content.indexOf(". With ");

        String director = content.substring(d1+12, d2).trim().replace(',','|');

        return director;
    }

    private String getCasts(String content){
        int d1 = content.indexOf(". With ");

        String cs = content.substring(d1+7, content.length());
        String casts = cs.substring(0, cs.indexOf("."));

        casts = casts.trim().replace(',','|');

        return casts;
    }
}
