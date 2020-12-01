package com.example.parser.impl;

import com.example.parser.Parsable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RacerParser implements Parsable<String> {
    private static final String SEPARATOR = "_";
    private static final int ABBREVIATION_SIZE = 3;
    public static final Logger logger = Logger.getLogger(
            RacerParser.class.getName());

    @Override
    public List<String> parse(Path file) {
        List<String> listRacerData = null;
        try (Stream<String> lineStream = Files.lines(file)) {
            listRacerData = lineStream
                    .flatMap(textFile -> Stream.of(textFile.split(SEPARATOR)))
                    .filter(word -> word.length() > ABBREVIATION_SIZE)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File not found", e);
        }
        return listRacerData;
    }
}
