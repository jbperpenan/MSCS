package org.jbp.csc611m.mc01.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
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
        for(Crew crew : crews){
            lines.add(new String[] { crew.getDirector(), crew.getCast()});
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

    public List<String> read() throws Exception {
        List<String[]> urlList = readAllLines("movies.csv");
        List<String> urls = new ArrayList<>();
        for(String[] line: urlList) {
            urls.add(line[0]);
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
}
