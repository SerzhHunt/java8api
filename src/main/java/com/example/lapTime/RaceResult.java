package com.example.lapTime;

import com.example.racers.Racer;

import java.io.PrintStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class RaceResult {
    private final LapTimeData lapTimeData;

    public RaceResult() {
        lapTimeData = new LapTimeData();
    }

    private static final int TOP_RACERS_AMOUNT = 16;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("mm:ss.SSS");
    private final Calendar calendar = Calendar.getInstance();
    private PrintStream printStream = new PrintStream(System.out);
    private int position = 1;

    public PrintStream getRaceResultTable(Map<String, Racer> racerData, Path startLogFile, Path endLogFile) {
        Map<String, Long> resultTimeLap = lapTimeData.calculationTimeLap(startLogFile, endLogFile);
        for (Map.Entry<String, Long> lapTime : resultTimeLap.entrySet()) {
            String key = lapTime.getKey();
            Long value = lapTime.getValue();
            Racer racer = racerData.get(key);
            String timeResult = getFormatLapTime(value);

            printStream = formatRaceResultTable(racer.getName(), racer.getTeam(), timeResult);
        }
        return printStream;
    }

    private PrintStream formatRaceResultTable(String racerName, String racerTeam, String timeResult) {
        printStream = System.out.printf("%-2d. %-20s |%-30s |%s \n",
                position++, racerName, racerTeam, timeResult);
        if (position == TOP_RACERS_AMOUNT) {
            System.out.println("-------------------------------------------------------------------");
        }
        return printStream;
    }

    private String getFormatLapTime(long time) {
        calendar.setTimeInMillis(time);
        return DATE_FORMAT.format(calendar.getTime());
    }
}
