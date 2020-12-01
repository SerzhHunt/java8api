package com.example;

import com.example.lapTime.RaceResult;
import com.example.racers.Racer;
import com.example.racers.RacerData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    private static final Path pathAbbreviations = Paths.get("abbreviations.txt");
    private static final Path pathStartLog = Paths.get("start.log");
    private static final Path pathEndLog = Paths.get("end.log");

    public static void main(String[] args) {
        RacerData racer = new RacerData();
        RaceResult raceResult = new RaceResult();

        Map<String, Racer> racersData = racer.createRacerDataFromFile(pathAbbreviations);
        raceResult.getRaceResultTable(racersData, pathStartLog, pathEndLog);
    }
}
