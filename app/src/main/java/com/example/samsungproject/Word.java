package com.example.samsungproject;

public class Word {
    long id;
    String name;
    String value;
    int stage;
    long next_time;

    public Word(long id, String name, String value, int stage, long next_time) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.stage = stage;
        this.next_time = next_time;
    }

    @Override
    public String toString() {
        return name + ";" + value + ";" + stage + ";" + next_time;
    }
}
