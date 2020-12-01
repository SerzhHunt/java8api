package com.example.Tests;

import com.example.parser.impl.RacerParser;
import org.junit.jupiter.api.Test;
import com.example.lapTime.RaceResult;
import com.example.parser.impl.AbbreviationParser;
import com.example.parser.impl.TimeLogParser;
import com.example.racers.Racer;
import com.example.racers.RacerData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RacerTests {
    private static final Path pathAbbreviations = Paths.get("src/test/resources/abbreviations.txt");
    private static final Path pathStartLog = Paths.get("src/test/resources/start.log");
    private static final Path pathEndLog = Paths.get("src/test/resources/end.log");
    private static final Path invalidTestFile = Paths.get("src/test/resources/invalidFile");

    private static final String CHECK_CORRECT_DATE_OUTPUT = "\\d{4}(-)\\d{2}(-)\\d{2}(_)\\d{2}(:)\\d{2}(:)\\d{2}(.)\\d{3}";
    private static final String CHECK_CORRECT_ABBREVIATION_OUTPUT = "[A-Z]{3}";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");

    private final RacerData racer = new RacerData();
    RaceResult raceResult = new RaceResult();
    private final AbbreviationParser abbreviationParser = new AbbreviationParser();
    private final RacerParser racerParser = new RacerParser();
    private final TimeLogParser timeLogParser = new TimeLogParser();

    @Test
    public void shouldCompareCorrectAbbreviationsListFromAbbreviationsLogFile() {
        List<String> list = abbreviationParser.parse(pathAbbreviations);
        String expected = "DRR, SVF, LHM, KRF, VBM, EOF, FAM, CSR, SPF, PGS," +
                " NHR, SVM, SSW, CLS, RGH, BHS, MES, LSW, KMH";
        String actual = String.join(", ", list);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareCorrectAbbreviationsListFromStartLogFile() {
        List<String> list = abbreviationParser.parse(pathStartLog);
        String expected = "SVF, NHR, FAM, KRF, SVM, MES, LSW, BHS, EOF, RGH," +
                " SSW, KMH, PGS, CSR, SPF, DRR, LHM, CLS, VBM";
        String actual = String.join(", ", list);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareCorrectAbbreviationsListFromEndLogFile() {
        List<String> list = abbreviationParser.parse(pathEndLog);
        String expected = "MES, RGH, SPF, LSW, DRR, NHR, CSR, KMH, BHS, SVM," +
                " KRF, VBM, SVF, EOF, PGS, SSW, FAM, CLS, LHM";
        String actual = String.join(", ", list);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareCorrectListRacerDataFromAbbreviationsFile() {
        List<String> list = racerParser.parse(pathAbbreviations);
        String expected = "Daniel Ricciardo, RED BULL RACING TAG HEUER," +
                " Sebastian Vettel, FERRARI, Lewis Hamilton, MERCEDES," +
                " Kimi Raikkonen, FERRARI, Valtteri Bottas, MERCEDES," +
                " Esteban Ocon, FORCE INDIA MERCEDES, Fernando Alonso," +
                " MCLAREN RENAULT, Carlos Sainz, RENAULT, Sergio Perez," +
                " FORCE INDIA MERCEDES, Pierre Gasly, SCUDERIA TORO ROSSO HONDA," +
                " Nico Hulkenberg, RENAULT, Stoffel Vandoorne, MCLAREN RENAULT," +
                " Sergey Sirotkin, WILLIAMS MERCEDES, Charles Leclerc, SAUBER FERRARI," +
                " Romain Grosjean, HAAS FERRARI, Brendon Hartley, SCUDERIA TORO ROSSO HONDA," +
                " Marcus Ericsson, SAUBER FERRARI, Lance Stroll, WILLIAMS MERCEDES, Kevin Magnussen, HAAS FERRARI";
        String actual = String.join(", ", list);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareCorrectListAbbreviationsAndRacersObjects() {
        Map<String, Racer> racerData = racer.createRacerDataFromFile(pathAbbreviations);
        String expected =
                "DRR=Racer{name='Daniel Ricciardo', team='RED BULL RACING TAG HEUER'}\r\n" +
                        "SVF=Racer{name='Sebastian Vettel', team='FERRARI'}\r\n" +
                        "LHM=Racer{name='Lewis Hamilton', team='MERCEDES'}\r\n" +
                        "KRF=Racer{name='Kimi Raikkonen', team='FERRARI'}\r\n" +
                        "VBM=Racer{name='Valtteri Bottas', team='MERCEDES'}\r\n" +
                        "EOF=Racer{name='Esteban Ocon', team='FORCE INDIA MERCEDES'}\r\n" +
                        "FAM=Racer{name='Fernando Alonso', team='MCLAREN RENAULT'}\r\n" +
                        "CSR=Racer{name='Carlos Sainz', team='RENAULT'}\r\n" +
                        "SPF=Racer{name='Sergio Perez', team='FORCE INDIA MERCEDES'}\r\n" +
                        "PGS=Racer{name='Pierre Gasly', team='SCUDERIA TORO ROSSO HONDA'}\r\n" +
                        "NHR=Racer{name='Nico Hulkenberg', team='RENAULT'}\r\n" +
                        "SVM=Racer{name='Stoffel Vandoorne', team='MCLAREN RENAULT'}\r\n" +
                        "SSW=Racer{name='Sergey Sirotkin', team='WILLIAMS MERCEDES'}\r\n" +
                        "CLS=Racer{name='Charles Leclerc', team='SAUBER FERRARI'}\r\n" +
                        "RGH=Racer{name='Romain Grosjean', team='HAAS FERRARI'}\r\n" +
                        "BHS=Racer{name='Brendon Hartley', team='SCUDERIA TORO ROSSO HONDA'}\r\n" +
                        "MES=Racer{name='Marcus Ericsson', team='SAUBER FERRARI'}\r\n" +
                        "LSW=Racer{name='Lance Stroll', team='WILLIAMS MERCEDES'}\r\n" +
                        "KMH=Racer{name='Kevin Magnussen', team='HAAS FERRARI'}\r\n";
        String actual = racerData
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue() + "\r\n").collect(Collectors.joining());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareCorrectDateListFromStartLogFile() {
        List<Date> list = timeLogParser.parse(pathStartLog);
        String expected =
                "[Thu May 24 12:02:58 MSK 2018, Thu May 24 12:02:49 MSK 2018," +
                        " Thu May 24 12:13:04 MSK 2018, Thu May 24 12:03:01 MSK 2018," +
                        " Thu May 24 12:18:37 MSK 2018, Thu May 24 12:04:45 MSK 2018," +
                        " Thu May 24 12:06:13 MSK 2018, Thu May 24 12:14:51 MSK 2018," +
                        " Thu May 24 12:17:58 MSK 2018, Thu May 24 12:05:14 MSK 2018," +
                        " Thu May 24 12:16:11 MSK 2018, Thu May 24 12:02:51 MSK 2018," +
                        " Thu May 24 12:07:23 MSK 2018, Thu May 24 12:03:15 MSK 2018," +
                        " Thu May 24 12:12:01 MSK 2018, Thu May 24 12:14:12 MSK 2018," +
                        " Thu May 24 12:18:20 MSK 2018, Thu May 24 12:09:41 MSK 2018," +
                        " Thu May 24 12:00:00 MSK 2018]";
        String actual = list.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareCorrectDateListFromEndLogFile() {
        List<Date> list = timeLogParser.parse(pathEndLog);
        String expected =
                "[Thu May 24 12:05:58 MSK 2018, Thu May 24 12:06:27 MSK 2018," +
                        " Thu May 24 12:13:13 MSK 2018, Thu May 24 12:07:26 MSK 2018," +
                        " Thu May 24 12:15:24 MSK 2018, Thu May 24 12:04:02 MSK 2018," +
                        " Thu May 24 12:04:28 MSK 2018, Thu May 24 12:04:04 MSK 2018," +
                        " Thu May 24 12:16:05 MSK 2018, Thu May 24 12:19:50 MSK 2018," +
                        " Thu May 24 12:04:13 MSK 2018, Thu May 24 12:01:12 MSK 2018," +
                        " Thu May 24 12:04:03 MSK 2018, Thu May 24 12:19:11 MSK 2018," +
                        " Thu May 24 12:08:36 MSK 2018, Thu May 24 12:17:24 MSK 2018," +
                        " Thu May 24 12:14:17 MSK 2018, Thu May 24 12:10:54 MSK 2018," +
                        " Thu May 24 12:19:32 MSK 2018]";
        String actual = list.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldOutputTrueWhenAllMatchAbbreviationsCorrectFromFileStartLog() {
        List<String> abbreviations = abbreviationParser.parse(pathStartLog);
        boolean checkAbbreviation = abbreviations
                .stream()
                .allMatch(abbreviation -> abbreviation.matches(CHECK_CORRECT_ABBREVIATION_OUTPUT));
        assertTrue(checkAbbreviation);
    }

    @Test
    public void shouldOutputTrueWhenAllMatchAbbreviationsCorrectFromFileEndLog() {
        List<String> abbreviations = abbreviationParser.parse(pathEndLog);
        boolean checkAbbreviation = abbreviations
                .stream()
                .allMatch(abbreviation -> abbreviation.matches(CHECK_CORRECT_ABBREVIATION_OUTPUT));
        assertTrue(checkAbbreviation);
    }

    @Test
    public void shouldOutputTrueWhenAllMatchCorrectDateFormatFromStartLog() {
        List<Date> list = timeLogParser.parse(pathStartLog);
        boolean checkFormatDate = list
                .stream()
                .allMatch(date -> DATE_FORMAT.format(date).matches(CHECK_CORRECT_DATE_OUTPUT));
        assertTrue(checkFormatDate);
    }

    @Test
    public void shouldOutputTrueWhenAllMatchDateFormatCorrectFromEndLog() {
        List<Date> list = timeLogParser.parse(pathEndLog);
        boolean checkFormatDate = list
                .stream()
                .allMatch(i -> DATE_FORMAT.format(i).matches(CHECK_CORRECT_DATE_OUTPUT));
        assertTrue(checkFormatDate);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFileDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> racer.createRacerDataFromFile(invalidTestFile));
    }
}
