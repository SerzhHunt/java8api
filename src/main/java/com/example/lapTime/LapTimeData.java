package com.example.lapTime;

import com.example.parser.impl.AbbreviationParser;
import com.example.parser.impl.TimeLogParser;

import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LapTimeData {
    private final AbbreviationParser abbreviationParser;
    private final TimeLogParser timeLogParser;

    public LapTimeData() {
        abbreviationParser = new AbbreviationParser();
        timeLogParser = new TimeLogParser();
    }

    protected Map<String, Long> calculationTimeLap(Path startLogFile, Path fileEndLog) {
        Map<String, Date> startLap = getTimeLapFromFile(startLogFile);
        Map<String, Date> endLap = getTimeLapFromFile(fileEndLog);

        Map<String, Long> resultTimeLap = createResultTimeLap(startLap, endLap);

        return sortedTimeResult(resultTimeLap);
    }

    private Map<String, Date> getTimeLapFromFile(Path path) {
        List<String> abbreviations = abbreviationParser.parse(path);
        List<Date> timeLap = timeLogParser.parse(path);
        return createDataToLapTime(abbreviations, timeLap);
    }

    private Map<String, Long> createResultTimeLap(Map<String, Date> startLap, Map<String, Date> endLap) {
        if (startLap == null || endLap == null) {
            throw new IllegalArgumentException("input parameters cannot be null.");
        }
        Map<String, Long> resultTimeLap = new LinkedHashMap<>();

        startLap.forEach((key, value) -> {
            long startTime = value.getTime();
            long endTime = endLap.get(key).getTime();
            resultTimeLap.put(key, endTime - startTime);
        });
        return resultTimeLap;
    }

    private Map<String, Date> createDataToLapTime(List<String> abbreviations, List<Date> timeLap) {
        if (abbreviations == null || timeLap == null) {
            throw new IllegalArgumentException("input parameters cannot be null.");
        }
        return IntStream
                .range(0, abbreviations.size())
                .boxed()
                .collect(Collectors.toMap(abbreviations::get, timeLap::get, (a, b) -> b, LinkedHashMap::new));
    }

    private Map<String, Long> sortedTimeResult(Map<String, Long> resultTimeLap) {
        return resultTimeLap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
