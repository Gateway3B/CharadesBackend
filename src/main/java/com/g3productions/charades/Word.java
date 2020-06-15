package com.g3productions.charades;

public class Word {
    
    private String word;

    private int time;

    private User user;

    Word(String word, int time, User user) {
        this.word = word;
        this.time = time;
        this.user = user;
    }

    public String getWord() {
        return word;
    }

    public int getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

}