package org.homemade.elo.dto;

public class Match {
    private int first;
    private int second;

    public Match(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public Match(String first, String second) {
        this.first = Integer.parseInt(first);
        this.second = Integer.parseInt(second);
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
