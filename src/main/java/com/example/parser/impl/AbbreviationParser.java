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

public class AbbreviationParser implements Parsable<String> {
    private static final int ABBREVIATION_SIZE = 3;
    public static final Logger logger = Logger.getLogger(
            AbbreviationParser.class.getName());

    @Override
    public List<String> parse(Path file) {
        List<String> abbreviationRacers = null;
        try (Stream<String> lineStream = Files.lines(file)) {
            abbreviationRacers = lineStream
                    .map(textFile -> textFile = textFile.substring(0, ABBREVIATION_SIZE))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File not found", e);
        }
        return abbreviationRacers;
    }
}
