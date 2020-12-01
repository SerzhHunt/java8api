package com.example.racers;

public class Racer {
    private final String name;
    private final String team;

    public Racer(String name, String team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "Racer{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
