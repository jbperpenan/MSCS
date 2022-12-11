package org.jbp.csc611m.mc01.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.jbp.csc611m.mc01.entities.Url;
import org.jbp.csc611m.mc01.repositories.UrlRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ImdbUrlGeneratorService {

    @Value( "${linksFilename}" )
    private String linksFilename;

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
        System.out.println("movie size = "+urls.size());
        //urlRepository.saveAll(urls);

        return urls;
    }

    private List<String[]> readAllLines() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(linksFilename).toURI());
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

    public void chunkLinks() throws Exception {

        List<String[]> links_1k = new ArrayList<>();
        List<String[]> links_2k = new ArrayList<>();
        List<String[]> links_3k = new ArrayList<>();
        List<String[]> links_4k = new ArrayList<>();
        List<String[]> links_5k = new ArrayList<>();
        List<String[]> links_6k = new ArrayList<>();
        List<String[]> links_7k = new ArrayList<>();
        List<String[]> links_8k = new ArrayList<>();
        List<String[]> links_9k = new ArrayList<>();

        links_1k.add(new String[] { "movieId","imdbId","tmdbId"});
        links_2k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_3k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_4k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_5k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_6k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_7k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_8k.add(new String[] { "movieId","imdbId","tmdbId" });
        links_9k.add(new String[] { "movieId","imdbId","tmdbId" });

        List<String[]> all = readAllLines();
        for(int i= 0; i<all.size(); i++){
            if(i <= 1000 && i != 0){
                links_1k.add(all.get(i));
            }

            if(i > 1000 && i <= 2000){
                links_2k.add(all.get(i));
            }

            if(i > 2000 && i <= 3000){
                links_3k.add(all.get(i));
            }

            if(i > 3000 && i <= 4000){
                links_4k.add(all.get(i));
            }

            if(i > 4000 && i <= 5000){
                links_5k.add(all.get(i));
            }

            if(i > 5000 && i <= 6000){
                links_6k.add(all.get(i));
            }

            if(i > 6000 && i <= 7000){
                links_7k.add(all.get(i));
            }

            if(i > 7000 && i <= 8000){
                links_8k.add(all.get(i));
            }

            if(i > 8000){
                links_9k.add(all.get(i));
            }
        }

        writeLineByLine(links_1k, "links_1k.csv");
        writeLineByLine(links_2k, "links_2k.csv");
        writeLineByLine(links_3k, "links_3k.csv");
        writeLineByLine(links_4k, "links_4k.csv");
        writeLineByLine(links_5k, "links_5k.csv");
        writeLineByLine(links_6k, "links_6k.csv");
        writeLineByLine(links_7k, "links_7k.csv");
        writeLineByLine(links_8k, "links_8k.csv");
        writeLineByLine(links_9k, "links_9k.csv");
    }

    public void chunkMovies() throws Exception {

        List<String[]> movies_1k = new ArrayList<>();
        List<String[]> movies_2k = new ArrayList<>();
        List<String[]> movies_3k = new ArrayList<>();
        List<String[]> movies_4k = new ArrayList<>();
        List<String[]> movies_5k = new ArrayList<>();
        List<String[]> movies_6k = new ArrayList<>();
        List<String[]> movies_7k = new ArrayList<>();
        List<String[]> movies_8k = new ArrayList<>();
        List<String[]> movies_9k = new ArrayList<>();

        movies_1k.add(new String[] { "movieId","title","genres"});
        movies_2k.add(new String[] { "movieId","title","genres" });
        movies_3k.add(new String[] { "movieId","title","genres" });
        movies_4k.add(new String[] { "movieId","title","genres" });
        movies_5k.add(new String[] { "movieId","title","genres" });
        movies_6k.add(new String[] { "movieId","title","genres" });
        movies_7k.add(new String[] { "movieId","title","genres" });
        movies_8k.add(new String[] { "movieId","title","genres" });
        movies_9k.add(new String[] { "movieId","title","genres" });

        List<String[]> all = readAllLines();
        for(int i= 0; i<all.size(); i++){
            if(i <= 1000 && i != 0){
                movies_1k.add(all.get(i));
            }

            if(i > 1000 && i <= 2000){
                movies_2k.add(all.get(i));
            }

            if(i > 2000 && i <= 3000){
                movies_3k.add(all.get(i));
            }

            if(i > 3000 && i <= 4000){
                movies_4k.add(all.get(i));
            }

            if(i > 4000 && i <= 5000){
                movies_5k.add(all.get(i));
            }

            if(i > 5000 && i <= 6000){
                movies_6k.add(all.get(i));
            }

            if(i > 6000 && i <= 7000){
                movies_7k.add(all.get(i));
            }

            if(i > 7000 && i <= 8000){
                movies_8k.add(all.get(i));
            }

            if(i > 8000){
                movies_9k.add(all.get(i));
            }
        }

        writeLineByLine(movies_1k, "movies_1k.csv");
        writeLineByLine(movies_2k, "movies_2k.csv");
        writeLineByLine(movies_3k, "movies_3k.csv");
        writeLineByLine(movies_4k, "movies_4k.csv");
        writeLineByLine(movies_5k, "movies_5k.csv");
        writeLineByLine(movies_6k, "movies_6k.csv");
        writeLineByLine(movies_7k, "movies_7k.csv");
        writeLineByLine(movies_8k, "movies_8k.csv");
        writeLineByLine(movies_9k, "movies_9k.csv");
    }

    private void writeLineByLine(List<String[]> lines, String fileName) throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()),
                CSVWriter.DEFAULT_SEPARATOR , CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        try {
            writer.writeAll(lines);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }
}
