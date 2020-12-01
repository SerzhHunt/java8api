package com.example.racers;


import com.example.parser.impl.AbbreviationParser;
import com.example.parser.impl.RacerParser;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class RacerData {

    private final AbbreviationParser abbreviationParser;
    private final RacerParser racerParser;

    public RacerData() {
        abbreviationParser = new AbbreviationParser();
        racerParser = new RacerParser();
    }

    public Map<String, Racer> createRacerDataFromFile(Path file) {
        List<String> abbreviations = abbreviationParser.parse(file);
        List<String> listRacersAndTeam = racerParser.parse(file);
        return createDataToRacers(abbreviations, listRacersAndTeam);
    }

    private Map<String, Racer> createDataToRacers(List<String> abbreviations, List<String> listRacerData) {
        if(abbreviations == null || listRacerData == null){
            throw new IllegalArgumentException("input parameters cannot be null.");
        }
        List<Racer> racers = createObjectsRacers(listRacerData);
        Map<String, Racer> racerData = new LinkedHashMap<>();
        IntStream.range(0, abbreviations.size()).forEach(i -> racerData.put(abbreviations.get(i), racers.get(i)));
        return racerData;
    }

    private List<Racer> createObjectsRacers(List<String> listRacersAndTeam) {
        List<Racer> racers = new ArrayList<>();
        Iterator<String> it = listRacersAndTeam.iterator();
        while (it.hasNext()) {
            racers.add(new Racer(it.next(), it.next()));
        }
        return racers;
    }
}
