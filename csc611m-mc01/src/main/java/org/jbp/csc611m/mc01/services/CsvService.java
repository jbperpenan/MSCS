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
import org.springframework.beans.factory.annotation.Value;
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

    @Value( "${moviesFilename}" )
    private String moviesFilename;

    @Value( "${meta1Filename}" )
    private String meta1Filename;

    public void writeCsvOutputs(List<Crew> crewList) throws Exception {
        List<String[]> lines = new ArrayList<>();

        lines.add(new String[] { "movieId","title","genres", "director", "casts" });

        //Iterable<Crew> i1 = crewRepository.findAll();
        //List<Crew> crews = new LinkedList<>();
        //i1.forEach(crews::add);

        List<Movie> movies = read();

        mov:
        for(int i=0; i<movies.size(); i++){
            boolean found = false;
            Movie tmpMovie = movies.get(i);
            Crew tmpCrew = null;
            crew:
            for(int j=0; j<crewList.size(); j++){
                tmpCrew = crewList.get(j);
                if(tmpMovie.getMovieId() == tmpCrew.getMovieId() || tmpMovie.getMovieId().equals(tmpCrew.getMovieId())){
                    System.out.println("MATCH crew: "+tmpCrew.getMovieId()+" mov: "+ tmpMovie.getMovieId());
                    found = true;
                    lines.add(new String[] { String.valueOf(tmpMovie.getMovieId()), tmpMovie.getTitle(), tmpMovie.getGenre(), tmpCrew.getDirector(), tmpCrew.getCast()});
                    break crew;
                }
            }
            if(found == false){
                System.out.println("NOT MATCH crew: "+tmpCrew.getMovieId()+" mov: "+ tmpMovie.getMovieId());
                lines.add(new String[] { String.valueOf(tmpMovie.getMovieId()), tmpMovie.getTitle(), tmpMovie.getGenre(), null , null});
            }
        }

        writeLineByLine(lines, meta1Filename);
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
        List<String[]> urlList = readAllLines(moviesFilename);
        List<Movie> urls = new ArrayList<>();
        int ctr = 1;
        for(String[] line: urlList) {
            if(ctr != 1){
                urls.add(new Movie(Integer.valueOf(line[0]), line[1], line[2]));
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
        private Integer movieId;
        private String title;
        private String genre;
    }
}
