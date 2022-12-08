package org.jbp.csc611m.mc01.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jbp.csc611m.mc01.entities.Crew;
import org.jbp.csc611m.mc01.repositories.CrewRepository;
import org.jbp.csc611m.mc01.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CsvService {

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private UrlRepository urlRepository;

    public void writeCsvOutputs() throws Exception {
        List<String[]> lines = new ArrayList<>();

        lines.add(new String[] { "movieId","title","genres", "director", "casts" });

        Iterable<Crew> i1 = crewRepository.findAll();
        List<Crew> crews = new LinkedList<>();
        i1.forEach(crews::add);

        List<Movie> movies = read();

        for(Crew crew : crews){
            for(Movie movie: movies){
                if(crew.getMovieId() == Integer.valueOf(movie.getMovieId())){
                    lines.add(new String[] { movie.getMovieId(), movie.getTitle(), movie.getGenre(), crew.getDirector(), crew.getCast().replace(',','|')});
                }
            }
        }

        writeLineByLine(lines, "movies-director-casts.csv");
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

    private List<Movie> read() throws Exception {
        List<String[]> urlList = readAllLines("movies.csv");
        List<Movie> urls = new ArrayList<>();
        int ctr = 1;
        for(String[] line: urlList) {
            if(ctr != 1){
                urls.add(new Movie(line[0], line[1], line[2]));
            }
            ctr++;
        }

        return urls;
    }

    private List<String[]> readAllLines(String fileName) throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class Movie {
        private String movieId;
        private String title;
        private String genre;
    }
}
