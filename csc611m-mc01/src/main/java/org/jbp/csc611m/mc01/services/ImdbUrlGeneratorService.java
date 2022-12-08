package org.jbp.csc611m.mc01.services;

import com.opencsv.CSVReader;
import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.repositories.UrlRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ImdbUrlGeneratorService {

    @Autowired
    private UrlRepository urlRepository;

    public List<Url> generateImdbUrls() throws Exception {
        int ctr = 1;
        String imdbBaseUrl = "https://www.imdb.com/title/tt";

        List<Url> urls = new LinkedList<>();

        for (String[] str: readAllLines()) {

            if (ctr == 1) {
                ctr++;
                continue;
            }

            urls.add(new Url(imdbBaseUrl+str[1]+"/",null,"PENDING",Integer.valueOf(str[0])));
        }

        urlRepository.saveAll(urls);

        return urls;
    }

    private List<String[]> readAllLines() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("test-links.csv").toURI());
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}
