package com.example.parser.impl;

import com.example.parser.Parsable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeLogParser implements Parsable<Date> {
    private static final int DATE_START_INDEX_IN_FILE = 3;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
    public static final Logger logger = Logger.getLogger(
            TimeLogParser.class.getName());

    @Override
    public List<Date> parse(Path file) {
        List<Date> formatDate = null;
        try (Stream<String> lineStream = Files.lines(file)) {
            formatDate = lineStream
                    .map(textFile -> textFile = textFile.substring(DATE_START_INDEX_IN_FILE))
                    .map(this::parsingStringToDate)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File not found", e);
        }
        return formatDate;
    }

    private Date parsingStringToDate(String input) {
        try {
            return DATE_FORMAT.parse(input);
        } catch (ParseException e) {
            logger.log(Level.SEVERE,"Incorrect date format", e);
        }
        return null;
    }
}
